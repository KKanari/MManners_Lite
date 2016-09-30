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
	 * クラス名：MannersService
	 * 機能概要：サービスとして実行され設定の内容によりサイレントモードを
	 *         　切り替える
	 * 
	 * Var. 日付       氏名               更新履歴
	 * ---- ---------- ------------------ -----------------------------
	 * V0.1 2009/08/10 笹野佑樹           初版作成
	 *******************************************************************/
	
	///////////////////////////////////////////////////////////////////////////
	// パブリック定数
	///////////////////////////////////////////////////////////////////////////
	// ステータスバーへ表示するNotificationのID
	private final static int NOTIFICATION_ID = 0;
	
	
	///////////////////////////////////////////////////////////////////////////
	// パブリック変数
	///////////////////////////////////////////////////////////////////////////
	// ハンドラ
	android.os.Handler handler = new android.os.Handler();
	
	// 設定情報
	private SettingInfo settingInfo01 = new SettingInfo();
	private SettingInfo settingInfo02 = new SettingInfo();
	private SettingInfo settingInfo03 = new SettingInfo();
	private SettingInfo settingInfo04 = new SettingInfo();
	private SettingInfo settingInfo05 = new SettingInfo();
	
	class SettingInfo {
		
		/*******************************************************************
		 * クラス名：SettingInfo
		 * 機能概要：設定の内容を保持するためのクラス
		 * 
		 * Var. 日付       氏名               更新履歴
		 * ---- ---------- ------------------ -----------------------------
		 * V0.1 2009/08/10 笹野佑樹           初版作成
		 *******************************************************************/
		
		Boolean enabled;       // ON/OFF
		int     onTimeHour;    // ON時刻-時 
		int     onTimeMinute;  // ON時刻-分
		int     offTimeHour;   // OFF時刻-時
		int     offTimeMinute; // OFF時刻-分
		String  weekNumber;    // 曜日設定
		
		SettingInfo() {
			
			/*******************************************************************
			 * メソッド名：SettingForm
			 * 機能概要：クラスSettingInfoのコンストラクタ
			 * 
			 * Var. 日付       氏名               更新履歴
			 * ---- ---------- ------------------ -----------------------------
			 * V0.1 2009/08/10 笹野佑樹           初版作成
			 *******************************************************************/
			
			// メンバの初期化
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
		 * メソッド名：onBind
		 * 概要：MannersServiceのonBindメソッド（未使用）
		 * 
		 * Var. 日付       氏名               更新履歴
		 * ---- ---------- ------------------ -----------------------------
		 * V1.0 2009/08/12 笹野佑樹           初版作成
		 ******************************************************************************/
		
		return null;
	}
	
	@Override
	public void onCreate() {
		
		/******************************************************************************
		 * メソッド名：onCreate
		 * 概要：MannersServiceのonCreateメソッド
		 * 
		 * Var. 日付       氏名               更新履歴
		 * ---- ---------- ------------------ -----------------------------
		 * V1.0 2009/08/12 笹野佑樹           初版作成
		 ******************************************************************************/
		super.onCreate();
		
		// ステータスバーアイコンの開始
		//startNotification();
		
		// タイマーイベント
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
					
					
					// 設定1
					chk = checkChangeTime(settingInfo01, nowTimeHour, nowTimeMinute, nowTimeWeek);
					
					if (chk == 1) {
						
						AudioManager manager = (AudioManager)getSystemService(Context.AUDIO_SERVICE);
						manager.setRingerMode(AudioManager.RINGER_MODE_SILENT);
						Toast.makeText(MannersService.this, getString(R.string.app_name) + " Info\n設定1:マナーモードをONにしました。", Toast.LENGTH_SHORT).show();
						
						return;
						
					} else if (chk == 0){
						
						AudioManager manager = (AudioManager)getSystemService(Context.AUDIO_SERVICE);
						manager.setRingerMode(AudioManager.RINGER_MODE_NORMAL);
						Toast.makeText(MannersService.this, getString(R.string.app_name) + " Info\n設定1:マナーモードをOFFにしました。", Toast.LENGTH_SHORT).show();

						return;
						
					}
					
					// 設定2
					chk = checkChangeTime(settingInfo02, nowTimeHour, nowTimeMinute, nowTimeWeek);
					
					if (chk == 1) {
						
						AudioManager manager = (AudioManager)getSystemService(Context.AUDIO_SERVICE);
						manager.setRingerMode(AudioManager.RINGER_MODE_SILENT);
						Toast.makeText(MannersService.this, getString(R.string.app_name) + " Info\n設定2:マナーモードをONにしました。", Toast.LENGTH_SHORT).show();
						
						return;
						
					} else if (chk == 0){
						
						AudioManager manager = (AudioManager)getSystemService(Context.AUDIO_SERVICE);
						manager.setRingerMode(AudioManager.RINGER_MODE_NORMAL);
						Toast.makeText(MannersService.this, getString(R.string.app_name) + " Info\n設定2:マナーモードをOFFにしました。", Toast.LENGTH_SHORT).show();
						
						return;
						
					}
					
					// 設定3
					chk = checkChangeTime(settingInfo03, nowTimeHour, nowTimeMinute, nowTimeWeek);
					
					if (chk == 1) {
						
						AudioManager manager = (AudioManager)getSystemService(Context.AUDIO_SERVICE);
						manager.setRingerMode(AudioManager.RINGER_MODE_SILENT);
						Toast.makeText(MannersService.this, getString(R.string.app_name) + " Info\n設定3:マナーモードをONにしました。", Toast.LENGTH_SHORT).show();
					
						return;
						
					} else if (chk == 0){
						
						AudioManager manager = (AudioManager)getSystemService(Context.AUDIO_SERVICE);
						manager.setRingerMode(AudioManager.RINGER_MODE_NORMAL);
						Toast.makeText(MannersService.this, getString(R.string.app_name) + " Info\n設定3:マナーモードをOFFにしました。", Toast.LENGTH_SHORT).show();
						
						return;
						
					}
					
					// 設定4
					chk = checkChangeTime(settingInfo04, nowTimeHour, nowTimeMinute, nowTimeWeek);
					
					if (chk == 1) {
						
						AudioManager manager = (AudioManager)getSystemService(Context.AUDIO_SERVICE);
						manager.setRingerMode(AudioManager.RINGER_MODE_SILENT);
						Toast.makeText(MannersService.this, getString(R.string.app_name) + " Info\n設定4:マナーモードをONにしました。", Toast.LENGTH_SHORT).show();
					
						return;
						
					} else if (chk == 0){
						
						AudioManager manager = (AudioManager)getSystemService(Context.AUDIO_SERVICE);
						manager.setRingerMode(AudioManager.RINGER_MODE_NORMAL);
						Toast.makeText(MannersService.this, getString(R.string.app_name) + " Info\n設定4:マナーモードをOFFにしました。", Toast.LENGTH_SHORT).show();
						
						return;
						
					}
					
					// 設定5
					chk = checkChangeTime(settingInfo05, nowTimeHour, nowTimeMinute, nowTimeWeek);
					
					if (chk == 1) {
						AudioManager manager = (AudioManager)getSystemService(Context.AUDIO_SERVICE);
						manager.setRingerMode(AudioManager.RINGER_MODE_SILENT);
						Toast.makeText(MannersService.this, getString(R.string.app_name) + " Info\n設定5:マナーモードをONにしました。", Toast.LENGTH_SHORT).show();
					
						return;
						
					} else if (chk == 0){
						
						AudioManager manager = (AudioManager)getSystemService(Context.AUDIO_SERVICE);
						manager.setRingerMode(AudioManager.RINGER_MODE_NORMAL);
						Toast.makeText(MannersService.this, getString(R.string.app_name) + " Info\n設定5:マナーモードをOFFにしました。", Toast.LENGTH_SHORT).show();
						
						return;
						
					}
				}
			});
		}
	}
	
	@Override
	public void onStart(Intent intent, int startId) {
		super.onStart(intent, startId);
		
		// ステータスバーへアイコンを表示
		startNotification();
		
		// 設定を読み込む
		readSettingInfo();
	}
	
	@Override
	public void onDestroy() {
		
		/******************************************************************************
		 * メソッド名：onDestroy
		 * 概要：MannersServiceのonDestroyメソッド
		 * 
		 * Var. 日付       氏名               更新履歴
		 * ---- ---------- ------------------ -----------------------------
		 * V1.0 2009/08/12 笹野佑樹           初版作成
		 ******************************************************************************/
		
		super.onDestroy();
		
		// ステータスバーアイコンの終了
		NotificationManager notificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
		notificationManager.cancel(NOTIFICATION_ID);
	}
	
	private void startNotification() {
		
		/******************************************************************************
		 * メソッド名：startNotification
		 * 概要：ステータスバーへアイコンを表示する
		 * 
		 * Var. 日付       氏名               更新履歴
		 * ---- ---------- ------------------ -----------------------------
		 * V1.0 2009/08/12 笹野佑樹           初版作成
		 ******************************************************************************/
		
		NotificationManager notificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
		Notification notification = new Notification(R.drawable.notify_icon, getString(R.string.app_name), System.currentTimeMillis());
		
		PendingIntent contentIntent = PendingIntent.getActivity(this, 0, new Intent(this, MainForm.class), Intent.FLAG_ACTIVITY_NEW_TASK);
		
		notification.setLatestEventInfo(this, getString(R.string.app_name), "実行中", contentIntent);
		
		notificationManager.notify(NOTIFICATION_ID, notification);
	}
	
	private void readSettingInfo() {
		
		/******************************************************************************
		 * メソッド名：readSettingInfo
		 * 概要：設定保存ファイルを読み込み設定状態をメモリへ格納する
		 * 
		 * Var. 日付       氏名               更新履歴
		 * ---- ---------- ------------------ -----------------------------
		 * V1.0 2009/08/12 笹野佑樹           初版作成
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
			
			// ファイル終端まで読み込み処理を行う
			
			for (int i = 1; i <= 5; i++) {
				
				line = br.readLine();
				
				// 読み込んだ文字列を分割文字で分割する
				
				splittedLine = line.split(MainForm.SPLITTER);
				
				
				// ループ回数により各設定へ処理を分岐する
				
				switch(i) {
				
				case 1:
				
					// 設定1
					settingInfo01.enabled = Boolean.parseBoolean(splittedLine[MainForm.IND_ON_OFF]);
					settingInfo01.onTimeHour = Integer.parseInt(splittedLine[MainForm.IND_ON_TIME].substring(0, 2));
					settingInfo01.onTimeMinute = Integer.parseInt(splittedLine[MainForm.IND_ON_TIME].substring(3));
					settingInfo01.offTimeHour = Integer.parseInt(splittedLine[MainForm.IND_OFF_TIME].substring(0, 2));
					settingInfo01.offTimeMinute = Integer.parseInt(splittedLine[MainForm.IND_OFF_TIME].substring(3));
					settingInfo01.weekNumber = splittedLine[MainForm.IND_WEEK_TAG];
					
					break;
				
				case 2:
				
					// 設定2
					settingInfo02.enabled = Boolean.parseBoolean(splittedLine[MainForm.IND_ON_OFF]);
					settingInfo02.onTimeHour = Integer.parseInt(splittedLine[MainForm.IND_ON_TIME].substring(0, 2));
					settingInfo02.onTimeMinute = Integer.parseInt(splittedLine[MainForm.IND_ON_TIME].substring(3));
					settingInfo02.offTimeHour = Integer.parseInt(splittedLine[MainForm.IND_OFF_TIME].substring(0, 2));
					settingInfo02.offTimeMinute = Integer.parseInt(splittedLine[MainForm.IND_OFF_TIME].substring(3));
					settingInfo02.weekNumber = splittedLine[MainForm.IND_WEEK_TAG];
					
					break;
				
				case 3:
				
					// 設定3
					settingInfo03.enabled = Boolean.parseBoolean(splittedLine[MainForm.IND_ON_OFF]);
					settingInfo03.onTimeHour = Integer.parseInt(splittedLine[MainForm.IND_ON_TIME].substring(0, 2));
					settingInfo03.onTimeMinute = Integer.parseInt(splittedLine[MainForm.IND_ON_TIME].substring(3));
					settingInfo03.offTimeHour = Integer.parseInt(splittedLine[MainForm.IND_OFF_TIME].substring(0, 2));
					settingInfo03.offTimeMinute = Integer.parseInt(splittedLine[MainForm.IND_OFF_TIME].substring(3));
					settingInfo03.weekNumber = splittedLine[MainForm.IND_WEEK_TAG];
					
					break;
				
				case 4:
				
					// 設定4
					settingInfo04.enabled = Boolean.parseBoolean(splittedLine[MainForm.IND_ON_OFF]);
					settingInfo04.onTimeHour = Integer.parseInt(splittedLine[MainForm.IND_ON_TIME].substring(0, 2));
					settingInfo04.onTimeMinute = Integer.parseInt(splittedLine[MainForm.IND_ON_TIME].substring(3));
					settingInfo04.offTimeHour = Integer.parseInt(splittedLine[MainForm.IND_OFF_TIME].substring(0, 2));
					settingInfo04.offTimeMinute = Integer.parseInt(splittedLine[MainForm.IND_OFF_TIME].substring(3));
					settingInfo04.weekNumber = splittedLine[MainForm.IND_WEEK_TAG];
					
					break;
				
				case 5:
				
					// 設定5
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
			
			Toast.makeText(this, "設定保存ファイルの読み込みに失敗しました。\nデフォルトの値で表示します。", Toast.LENGTH_SHORT).show();
		}
	}
	
	private int checkChangeTime(SettingInfo settingInfo, int nowHour, int nowMinute, int nowWeek) {
		
		/******************************************************************************
		 * メソッド名：checkChangeTime
		 * 概要  ：サイレントモード切り替えの時刻かどうか判定する
		 * 戻り値：1.ON時刻  0.OFF時刻  -1.切り替え時刻ではない
		 * 
		 * Var. 日付       氏名               更新履歴
		 * ---- ---------- ------------------ -----------------------------
		 * V1.0 2009/08/12 笹野佑樹           初版作成
		 ******************************************************************************/
		
		if (settingInfo.enabled == true) {
			
			// ON時刻の判定
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
