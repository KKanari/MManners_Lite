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
	 * クラス名：MannersReceiver
	 * 機能概要：MannersのBroadcast Receiver
	 * 
	 * Var. 日付       氏名               更新履歴
	 * ---- ---------- ------------------ -----------------------------
	 * V0.1 2009/08/10 笹野佑樹           初版作成
	 *******************************************************************/
	
	@Override
	public void onReceive(Context context, Intent intent) {
		
		/*******************************************************************
		 * メソッド名：onReceive
		 * 機能概要：MannersReceiverのonReceiveメソッド
		 * 
		 * Var. 日付       氏名               更新履歴
		 * ---- ---------- ------------------ -----------------------------
		 * V0.1 2009/08/10 笹野佑樹           初版作成
		 *******************************************************************/
		
		String action = intent.getAction();
		
		// ACTION_BOOT_COMPLETEDのとき
		
		if (action.equals(Intent.ACTION_BOOT_COMPLETED)) {
			
			// 設定保存ファイルを読み込みONの設定があればMannersServiceを実行する
			if (chkExecuteMannersService(context, intent) == true) {
				Intent mannersServiceIntent = new Intent(context, MannersService.class);
				context.startService(mannersServiceIntent);
			}
			
		}
	}
	
	private Boolean chkExecuteMannersService(Context context, Intent intent) {
		
		/*******************************************************************
		 * メソッド名：chkExecuteMannersService
		 * 機能概要：設定保存ファイルを読み込みONの設定があればtrueを返す
		 * 
		 * Var. 日付       氏名               更新履歴
		 * ---- ---------- ------------------ -----------------------------
		 * V0.1 2009/08/10 笹野佑樹           初版作成
		 *******************************************************************/
		
		try {
			String savefileFullPath = context.getString(R.string.data_dir) + context.getPackageName() + context.getString(R.string.file_name);
			
			FileInputStream fis = new FileInputStream(savefileFullPath);
			InputStreamReader isr = new InputStreamReader(fis);
			BufferedReader br = new BufferedReader(isr);
			
			
			// ファイル終端まで読み込み処理を行う
			
			for (int i = 1; i <= 5; i++) {
				
				String line = br.readLine();
				
				
				// 読み込んだ文字列を分割文字で分割する
				
				String splittedLine[] = line.split(MainForm.SPLITTER);
				
				// ON/OFFがtrueかどうか判断
				// trueであれば戻り値にtrueを返却
				
				if (Boolean.parseBoolean(splittedLine[MainForm.IND_ON_OFF]) == true) {
					
					return true;
					
				}
				
			}
			
		} catch (Exception e) {
			
			Toast.makeText(context, context.getString(R.string.app_name) + " Info\nファイル読み込みエラー\nエラー理由:" + e.getMessage(), Toast.LENGTH_SHORT).show();
			
		}
		
		// すべてOFFなのでfalseを返却
		return false;
	}

}
