package jp.co.kms2.mmanners_lite;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.InputStreamReader;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

public class MannersReceiver extends BroadcastReceiver {

	/*******************************************************************
	 * �N���X���FMannersReceiver
	 * �@�\�T�v�FManners��Broadcast Receiver
	 * 
	 * Var. ���t       ����               �X�V����
	 * ---- ---------- ------------------ -----------------------------
	 * V0.1 2009/08/10 ����C��           ���ō쐬
	 *******************************************************************/
	
	@Override
	public void onReceive(Context context, Intent intent) {
		
		/*******************************************************************
		 * ���\�b�h���FonReceive
		 * �@�\�T�v�FMannersReceiver��onReceive���\�b�h
		 * 
		 * Var. ���t       ����               �X�V����
		 * ---- ---------- ------------------ -----------------------------
		 * V0.1 2009/08/10 ����C��           ���ō쐬
		 *******************************************************************/
		
		String action = intent.getAction();
		
		// ACTION_BOOT_COMPLETED�̂Ƃ�
		
		if (action.equals(Intent.ACTION_BOOT_COMPLETED)) {
			
			// �ݒ�ۑ��t�@�C����ǂݍ���ON�̐ݒ肪�����MannersService�����s����
			if (chkExecuteMannersService(context, intent) == true) {
				Intent mannersServiceIntent = new Intent(context, MannersService.class);
				context.startService(mannersServiceIntent);
			}
			
		}
	}
	
	private Boolean chkExecuteMannersService(Context context, Intent intent) {
		
		/*******************************************************************
		 * ���\�b�h���FchkExecuteMannersService
		 * �@�\�T�v�F�ݒ�ۑ��t�@�C����ǂݍ���ON�̐ݒ肪�����true��Ԃ�
		 * 
		 * Var. ���t       ����               �X�V����
		 * ---- ---------- ------------------ -----------------------------
		 * V0.1 2009/08/10 ����C��           ���ō쐬
		 *******************************************************************/
		
		try {
			String savefileFullPath = context.getString(R.string.data_dir) + context.getPackageName() + context.getString(R.string.file_name);
			
			FileInputStream fis = new FileInputStream(savefileFullPath);
			InputStreamReader isr = new InputStreamReader(fis);
			BufferedReader br = new BufferedReader(isr);
			
			
			// �t�@�C���I�[�܂œǂݍ��ݏ������s��
			
			for (int i = 1; i <= 5; i++) {
				
				String line = br.readLine();
				
				
				// �ǂݍ��񂾕�����𕪊������ŕ�������
				
				String splittedLine[] = line.split(MainForm.SPLITTER);
				
				// ON/OFF��true���ǂ������f
				// true�ł���Ζ߂�l��true��ԋp
				
				if (Boolean.parseBoolean(splittedLine[MainForm.IND_ON_OFF]) == true) {
					
					return true;
					
				}
				
			}
			
		} catch (Exception e) {
			
			Toast.makeText(context, context.getString(R.string.app_name) + " Info\n�t�@�C���ǂݍ��݃G���[\n�G���[���R:" + e.getMessage(), Toast.LENGTH_SHORT).show();
			
		}
		
		// ���ׂ�OFF�Ȃ̂�false��ԋp
		return false;
	}

}
