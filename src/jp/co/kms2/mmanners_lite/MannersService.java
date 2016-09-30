package jp.co.kms2.mmanners_lite;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.Calendar;
import java.util.Timer;
import java.util.TimerTask;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.media.AudioManager;
import android.os.IBinder;
import android.widget.Toast;

public class MannersService extends Service {
	
	/*******************************************************************
	 * �N���X���FMannersService
	 * �@�\�T�v�F�T�[�r�X�Ƃ��Ď��s����ݒ�̓��e�ɂ��T�C�����g���[�h��
	 *         �@�؂�ւ���
	 * 
	 * Var. ���t       ����               �X�V����
	 * ---- ---------- ------------------ -----------------------------
	 * V0.1 2009/08/10 ����C��           ���ō쐬
	 *******************************************************************/
	
	///////////////////////////////////////////////////////////////////////////
	// �p�u���b�N�萔
	///////////////////////////////////////////////////////////////////////////
	// �X�e�[�^�X�o�[�֕\������Notification��ID
	private final static int NOTIFICATION_ID = 0;
	
	
	///////////////////////////////////////////////////////////////////////////
	// �p�u���b�N�ϐ�
	///////////////////////////////////////////////////////////////////////////
	// �n���h��
	android.os.Handler handler = new android.os.Handler();
	
	// �ݒ���
	private SettingInfo settingInfo01 = new SettingInfo();
	private SettingInfo settingInfo02 = new SettingInfo();
	private SettingInfo settingInfo03 = new SettingInfo();
	private SettingInfo settingInfo04 = new SettingInfo();
	private SettingInfo settingInfo05 = new SettingInfo();
	
	class SettingInfo {
		
		/*******************************************************************
		 * �N���X���FSettingInfo
		 * �@�\�T�v�F�ݒ�̓��e��ێ����邽�߂̃N���X
		 * 
		 * Var. ���t       ����               �X�V����
		 * ---- ---------- ------------------ -----------------------------
		 * V0.1 2009/08/10 ����C��           ���ō쐬
		 *******************************************************************/
		
		Boolean enabled;       // ON/OFF
		int     onTimeHour;    // ON����-�� 
		int     onTimeMinute;  // ON����-��
		int     offTimeHour;   // OFF����-��
		int     offTimeMinute; // OFF����-��
		String  weekNumber;    // �j���ݒ�
		
		SettingInfo() {
			
			/*******************************************************************
			 * ���\�b�h���FSettingForm
			 * �@�\�T�v�F�N���XSettingInfo�̃R���X�g���N�^
			 * 
			 * Var. ���t       ����               �X�V����
			 * ---- ---------- ------------------ -----------------------------
			 * V0.1 2009/08/10 ����C��           ���ō쐬
			 *******************************************************************/
			
			// �����o�̏�����
			enabled = false;
			onTimeHour = 0;
			onTimeMinute = 0;
			offTimeHour = 0;
			offTimeMinute = 0;
			weekNumber = "##";
		}
	}

	@Override
	public IBinder onBind(Intent arg0) {
		
		/******************************************************************************
		 * ���\�b�h���FonBind
		 * �T�v�FMannersService��onBind���\�b�h�i���g�p�j
		 * 
		 * Var. ���t       ����               �X�V����
		 * ---- ---------- ------------------ -----------------------------
		 * V1.0 2009/08/12 ����C��           ���ō쐬
		 ******************************************************************************/
		
		return null;
	}
	
	@Override
	public void onCreate() {
		
		/******************************************************************************
		 * ���\�b�h���FonCreate
		 * �T�v�FMannersService��onCreate���\�b�h
		 * 
		 * Var. ���t       ����               �X�V����
		 * ---- ---------- ------------------ -----------------------------
		 * V1.0 2009/08/12 ����C��           ���ō쐬
		 ******************************************************************************/
		super.onCreate();
		
		// �X�e�[�^�X�o�[�A�C�R���̊J�n
		//startNotification();
		
		// �^�C�}�[�C�x���g
		Timer timerIvent = new Timer();
		Calendar calendar = Calendar.getInstance();
		timerIvent.schedule(new changeSilentMode(), 60000 - calendar.get(Calendar.SECOND) * 1000, 60000);
		
	}
	
	public class changeSilentMode extends TimerTask {
		
		public void run() {
			handler.post(new Runnable() {
				
				@Override
				public void run() {
					Calendar calendar = Calendar.getInstance();
					int nowTimeHour = calendar.get(Calendar.HOUR_OF_DAY);
					int nowTimeMinute = calendar.get(Calendar.MINUTE);
					int nowTimeWeek = calendar.get(Calendar.DAY_OF_WEEK);
					int chk = -1;
					
					
					// �ݒ�1
					chk = checkChangeTime(settingInfo01, nowTimeHour, nowTimeMinute, nowTimeWeek);
					
					if (chk == 1) {
						
						AudioManager manager = (AudioManager)getSystemService(Context.AUDIO_SERVICE);
						manager.setRingerMode(AudioManager.RINGER_MODE_SILENT);
						Toast.makeText(MannersService.this, getString(R.string.app_name) + " Info\n�ݒ�1:�}�i�[���[�h��ON�ɂ��܂����B", Toast.LENGTH_SHORT).show();
						
						return;
						
					} else if (chk == 0){
						
						AudioManager manager = (AudioManager)getSystemService(Context.AUDIO_SERVICE);
						manager.setRingerMode(AudioManager.RINGER_MODE_NORMAL);
						Toast.makeText(MannersService.this, getString(R.string.app_name) + " Info\n�ݒ�1:�}�i�[���[�h��OFF�ɂ��܂����B", Toast.LENGTH_SHORT).show();

						return;
						
					}
					
					// �ݒ�2
					chk = checkChangeTime(settingInfo02, nowTimeHour, nowTimeMinute, nowTimeWeek);
					
					if (chk == 1) {
						
						AudioManager manager = (AudioManager)getSystemService(Context.AUDIO_SERVICE);
						manager.setRingerMode(AudioManager.RINGER_MODE_SILENT);
						Toast.makeText(MannersService.this, getString(R.string.app_name) + " Info\n�ݒ�2:�}�i�[���[�h��ON�ɂ��܂����B", Toast.LENGTH_SHORT).show();
						
						return;
						
					} else if (chk == 0){
						
						AudioManager manager = (AudioManager)getSystemService(Context.AUDIO_SERVICE);
						manager.setRingerMode(AudioManager.RINGER_MODE_NORMAL);
						Toast.makeText(MannersService.this, getString(R.string.app_name) + " Info\n�ݒ�2:�}�i�[���[�h��OFF�ɂ��܂����B", Toast.LENGTH_SHORT).show();
						
						return;
						
					}
					
					// �ݒ�3
					chk = checkChangeTime(settingInfo03, nowTimeHour, nowTimeMinute, nowTimeWeek);
					
					if (chk == 1) {
						
						AudioManager manager = (AudioManager)getSystemService(Context.AUDIO_SERVICE);
						manager.setRingerMode(AudioManager.RINGER_MODE_SILENT);
						Toast.makeText(MannersService.this, getString(R.string.app_name) + " Info\n�ݒ�3:�}�i�[���[�h��ON�ɂ��܂����B", Toast.LENGTH_SHORT).show();
					
						return;
						
					} else if (chk == 0){
						
						AudioManager manager = (AudioManager)getSystemService(Context.AUDIO_SERVICE);
						manager.setRingerMode(AudioManager.RINGER_MODE_NORMAL);
						Toast.makeText(MannersService.this, getString(R.string.app_name) + " Info\n�ݒ�3:�}�i�[���[�h��OFF�ɂ��܂����B", Toast.LENGTH_SHORT).show();
						
						return;
						
					}
					
					// �ݒ�4
					chk = checkChangeTime(settingInfo04, nowTimeHour, nowTimeMinute, nowTimeWeek);
					
					if (chk == 1) {
						
						AudioManager manager = (AudioManager)getSystemService(Context.AUDIO_SERVICE);
						manager.setRingerMode(AudioManager.RINGER_MODE_SILENT);
						Toast.makeText(MannersService.this, getString(R.string.app_name) + " Info\n�ݒ�4:�}�i�[���[�h��ON�ɂ��܂����B", Toast.LENGTH_SHORT).show();
					
						return;
						
					} else if (chk == 0){
						
						AudioManager manager = (AudioManager)getSystemService(Context.AUDIO_SERVICE);
						manager.setRingerMode(AudioManager.RINGER_MODE_NORMAL);
						Toast.makeText(MannersService.this, getString(R.string.app_name) + " Info\n�ݒ�4:�}�i�[���[�h��OFF�ɂ��܂����B", Toast.LENGTH_SHORT).show();
						
						return;
						
					}
					
					// �ݒ�5
					chk = checkChangeTime(settingInfo05, nowTimeHour, nowTimeMinute, nowTimeWeek);
					
					if (chk == 1) {
						AudioManager manager = (AudioManager)getSystemService(Context.AUDIO_SERVICE);
						manager.setRingerMode(AudioManager.RINGER_MODE_SILENT);
						Toast.makeText(MannersService.this, getString(R.string.app_name) + " Info\n�ݒ�5:�}�i�[���[�h��ON�ɂ��܂����B", Toast.LENGTH_SHORT).show();
					
						return;
						
					} else if (chk == 0){
						
						AudioManager manager = (AudioManager)getSystemService(Context.AUDIO_SERVICE);
						manager.setRingerMode(AudioManager.RINGER_MODE_NORMAL);
						Toast.makeText(MannersService.this, getString(R.string.app_name) + " Info\n�ݒ�5:�}�i�[���[�h��OFF�ɂ��܂����B", Toast.LENGTH_SHORT).show();
						
						return;
						
					}
				}
			});
		}
	}
	
	@Override
	public void onStart(Intent intent, int startId) {
		super.onStart(intent, startId);
		
		// �X�e�[�^�X�o�[�փA�C�R����\��
		startNotification();
		
		// �ݒ��ǂݍ���
		readSettingInfo();
	}
	
	@Override
	public void onDestroy() {
		
		/******************************************************************************
		 * ���\�b�h���FonDestroy
		 * �T�v�FMannersService��onDestroy���\�b�h
		 * 
		 * Var. ���t       ����               �X�V����
		 * ---- ---------- ------------------ -----------------------------
		 * V1.0 2009/08/12 ����C��           ���ō쐬
		 ******************************************************************************/
		
		super.onDestroy();
		
		// �X�e�[�^�X�o�[�A�C�R���̏I��
		NotificationManager notificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
		notificationManager.cancel(NOTIFICATION_ID);
	}
	
	private void startNotification() {
		
		/******************************************************************************
		 * ���\�b�h���FstartNotification
		 * �T�v�F�X�e�[�^�X�o�[�փA�C�R����\������
		 * 
		 * Var. ���t       ����               �X�V����
		 * ---- ---------- ------------------ -----------------------------
		 * V1.0 2009/08/12 ����C��           ���ō쐬
		 ******************************************************************************/
		
		NotificationManager notificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
		Notification notification = new Notification(R.drawable.notify_icon, getString(R.string.app_name), System.currentTimeMillis());
		
		PendingIntent contentIntent = PendingIntent.getActivity(this, 0, new Intent(this, MainForm.class), Intent.FLAG_ACTIVITY_NEW_TASK);
		
		notification.setLatestEventInfo(this, getString(R.string.app_name), "���s��", contentIntent);
		
		notificationManager.notify(NOTIFICATION_ID, notification);
	}
	
	private void readSettingInfo() {
		
		/******************************************************************************
		 * ���\�b�h���FreadSettingInfo
		 * �T�v�F�ݒ�ۑ��t�@�C����ǂݍ��ݐݒ��Ԃ��������֊i�[����
		 * 
		 * Var. ���t       ����               �X�V����
		 * ---- ---------- ------------------ -----------------------------
		 * V1.0 2009/08/12 ����C��           ���ō쐬
		 ******************************************************************************/
		
		String savefileFullPath = getString(R.string.data_dir) + getPackageName() + getString(R.string.file_name);
		
		if (!new File(savefileFullPath).exists()) {
			return;
		}
		
		try {
			
			FileInputStream fis = new FileInputStream(savefileFullPath);
			InputStreamReader isr = new InputStreamReader(fis);
			BufferedReader br = new BufferedReader(isr);
			String line;
			String splittedLine[];
			
			// �t�@�C���I�[�܂œǂݍ��ݏ������s��
			
			for (int i = 1; i <= 5; i++) {
				
				line = br.readLine();
				
				// �ǂݍ��񂾕�����𕪊������ŕ�������
				
				splittedLine = line.split(MainForm.SPLITTER);
				
				
				// ���[�v�񐔂ɂ��e�ݒ�֏����𕪊򂷂�
				
				switch(i) {
				
				case 1:
				
					// �ݒ�1
					settingInfo01.enabled = Boolean.parseBoolean(splittedLine[MainForm.IND_ON_OFF]);
					settingInfo01.onTimeHour = Integer.parseInt(splittedLine[MainForm.IND_ON_TIME].substring(0, 2));
					settingInfo01.onTimeMinute = Integer.parseInt(splittedLine[MainForm.IND_ON_TIME].substring(3));
					settingInfo01.offTimeHour = Integer.parseInt(splittedLine[MainForm.IND_OFF_TIME].substring(0, 2));
					settingInfo01.offTimeMinute = Integer.parseInt(splittedLine[MainForm.IND_OFF_TIME].substring(3));
					settingInfo01.weekNumber = splittedLine[MainForm.IND_WEEK_TAG];
					
					break;
				
				case 2:
				
					// �ݒ�2
					settingInfo02.enabled = Boolean.parseBoolean(splittedLine[MainForm.IND_ON_OFF]);
					settingInfo02.onTimeHour = Integer.parseInt(splittedLine[MainForm.IND_ON_TIME].substring(0, 2));
					settingInfo02.onTimeMinute = Integer.parseInt(splittedLine[MainForm.IND_ON_TIME].substring(3));
					settingInfo02.offTimeHour = Integer.parseInt(splittedLine[MainForm.IND_OFF_TIME].substring(0, 2));
					settingInfo02.offTimeMinute = Integer.parseInt(splittedLine[MainForm.IND_OFF_TIME].substring(3));
					settingInfo02.weekNumber = splittedLine[MainForm.IND_WEEK_TAG];
					
					break;
				
				case 3:
				
					// �ݒ�3
					settingInfo03.enabled = Boolean.parseBoolean(splittedLine[MainForm.IND_ON_OFF]);
					settingInfo03.onTimeHour = Integer.parseInt(splittedLine[MainForm.IND_ON_TIME].substring(0, 2));
					settingInfo03.onTimeMinute = Integer.parseInt(splittedLine[MainForm.IND_ON_TIME].substring(3));
					settingInfo03.offTimeHour = Integer.parseInt(splittedLine[MainForm.IND_OFF_TIME].substring(0, 2));
					settingInfo03.offTimeMinute = Integer.parseInt(splittedLine[MainForm.IND_OFF_TIME].substring(3));
					settingInfo03.weekNumber = splittedLine[MainForm.IND_WEEK_TAG];
					
					break;
				
				case 4:
				
					// �ݒ�4
					settingInfo04.enabled = Boolean.parseBoolean(splittedLine[MainForm.IND_ON_OFF]);
					settingInfo04.onTimeHour = Integer.parseInt(splittedLine[MainForm.IND_ON_TIME].substring(0, 2));
					settingInfo04.onTimeMinute = Integer.parseInt(splittedLine[MainForm.IND_ON_TIME].substring(3));
					settingInfo04.offTimeHour = Integer.parseInt(splittedLine[MainForm.IND_OFF_TIME].substring(0, 2));
					settingInfo04.offTimeMinute = Integer.parseInt(splittedLine[MainForm.IND_OFF_TIME].substring(3));
					settingInfo04.weekNumber = splittedLine[MainForm.IND_WEEK_TAG];
					
					break;
				
				case 5:
				
					// �ݒ�5
					settingInfo05.enabled = Boolean.parseBoolean(splittedLine[MainForm.IND_ON_OFF]);
					settingInfo05.onTimeHour = Integer.parseInt(splittedLine[MainForm.IND_ON_TIME].substring(0, 2));
					settingInfo05.onTimeMinute = Integer.parseInt(splittedLine[MainForm.IND_ON_TIME].substring(3));
					settingInfo05.offTimeHour = Integer.parseInt(splittedLine[MainForm.IND_OFF_TIME].substring(0, 2));
					settingInfo05.offTimeMinute = Integer.parseInt(splittedLine[MainForm.IND_OFF_TIME].substring(3));
					settingInfo05.weekNumber = splittedLine[MainForm.IND_WEEK_TAG];
					
					break;
				
				default:
					
					break;	
				}
			}
			
			br.close();
			isr.close();
			fis.close();
			
		} catch(Exception e) {
			
			Toast.makeText(this, "�ݒ�ۑ��t�@�C���̓ǂݍ��݂Ɏ��s���܂����B\n�f�t�H���g�̒l�ŕ\�����܂��B", Toast.LENGTH_SHORT).show();
		}
	}
	
	private int checkChangeTime(SettingInfo settingInfo, int nowHour, int nowMinute, int nowWeek) {
		
		/******************************************************************************
		 * ���\�b�h���FcheckChangeTime
		 * �T�v  �F�T�C�����g���[�h�؂�ւ��̎������ǂ������肷��
		 * �߂�l�F1.ON����  0.OFF����  -1.�؂�ւ������ł͂Ȃ�
		 * 
		 * Var. ���t       ����               �X�V����
		 * ---- ---------- ------------------ -----------------------------
		 * V1.0 2009/08/12 ����C��           ���ō쐬
		 ******************************************************************************/
		
		if (settingInfo.enabled == true) {
			
			// ON�����̔���
			if (nowHour == settingInfo.onTimeHour 
					&& nowMinute == settingInfo.onTimeMinute 
					&& settingInfo.weekNumber.indexOf(String.valueOf(nowWeek)) != -1) {
				return 1;
			} else if (nowHour == settingInfo.offTimeHour 
					&& nowMinute == settingInfo.offTimeMinute 
					&& settingInfo.weekNumber.indexOf(String.valueOf(nowWeek)) != -1) {
				return 0;
			} else {
				return -1;
			}
		}
		
		return -1;
	}
}
