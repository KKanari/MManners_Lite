package jp.co.kms2.mmanners_lite;

/*******************************************************************
 * パッケージ名：jp.co.kms2.manners
 * 
 * Var. 日付       氏名               更新履歴
 * ---- ---------- ------------------ -----------------------------
 * V0.1 2009/08/07 笹野佑樹           初版作成
 *******************************************************************/

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;

import android.app.Activity;
import android.content.Intent;
import android.opengl.Visibility;
import android.os.Bundle;
import android.view.View;
import android.widget.*;

public class MainForm extends Activity {
	
	/*******************************************************************
	 * クラス名：MainForm
	 * 機能概要：メインアクティビティ
	 *           メイン画面を構成する
	 * 
	 * Var. 日付       氏名               更新履歴
	 * ---- ---------- ------------------ -----------------------------
	 * V0.1 2009/08/12 笹野佑樹           初版作成
	 *******************************************************************/
	
	///////////////////////////////////////////////////////////////////////////
	// パブリック定数
	///////////////////////////////////////////////////////////////////////////
	// 設定保存ファイルレコードの分割後インデックス
	public final static int IND_ON_OFF    = 0;
	public final static int IND_ON_TIME   = 1;
	public final static int IND_OFF_TIME  = 2;
	public final static int IND_WEEK      = 3;
	public final static int IND_WEEK_TAG  = 4;
	
	// 設定画面へのintentに設定する値のname値
	public final static String INTNAME_SETTING_NO     = "SETTING_NO";
	public final static String INTNAME_ONTIME_HOUR    = "ONTIME_HOUR";
	public final static String INTNAME_ONTIME_MINUTE  = "ONTIME_MINUTE";
	public final static String INTNAME_OFFTIME_HOUR   = "OFFTIME_HOUR";
	public final static String INTNAME_OFFTIME_MINUTE = "OFFTIME_MINUTE";
	public final static String INTNAME_WEEK           = "WEEK";
	
	// 設定画面へのintentに設定するid値
	public final static int REQ_SETTING01 = 1;
	public final static int REQ_SETTING02 = 2;
	public final static int REQ_SETTING03 = 3;
	public final static int REQ_SETTING04 = 4;
	public final static int REQ_SETTING05 = 5;
	
	// メイン画面へのintentに設定するname値
	public final static String INTENT_ON_TIME = "INTENT_ON_TIME";
	public final static String INTENT_OFF_TIME = "INTENT_OFF_TIME";
	public final static String INTENT_WEEK = "INTENT_WEEK";
	
	// 曜日（数値）
	public final static String CHAR_SUNDAY    = "1";
	public final static String CHAR_MONDAY    = "2";
	public final static String CHAR_TUESDAY   = "3";
	public final static String CHAR_WEDNESDAY = "4";
	public final static String CHAR_THURSDAY  = "5";
	public final static String CHAR_FRIDAY    = "6";
	public final static String CHAR_SATURDAY  = "7";
	
	// 曜日（漢字）
	public final static String KANJI_SUNDAY    = "日";
	public final static String KANJI_MONDAY    = "月";
	public final static String KANJI_TUESDAY   = "火";
	public final static String KANJI_WEDNESDAY = "水";
	public final static String KANJI_THURSDAY  = "木";
	public final static String KANJI_FRIDAY    = "金";
	public final static String KANJI_SATURDAY  = "土";
	
	//保存ファイル内分割文字
	public final static String SPLITTER = "<>"; 
	
	// 無料版制限判断のフラグ
	public final static int RESTRICTED = 1;
	public final static int NO_RESTRICTED = 0;

	///////////////////////////////////////////////////////////////////////////
	// パブリック変数
	///////////////////////////////////////////////////////////////////////////
	
	
	@Override
    public void onCreate(Bundle savedInstanceState) {

		/*******************************************************************
		 * メソッド名：onCreate
		 * 概要：当アクティビティのonCreateメソッド
		 * 
		 * Var. 日付       氏名               更新履歴
		 * ---- ---------- ------------------ -----------------------------
		 * V0.1 2009/08/12 笹野佑樹           初版作成
		 *******************************************************************/

        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        
        // 設定保存ファイルを読み込む
        setViewSavedState();
        
        // 無料版の制限をかける
        if (Integer.parseInt(getString(R.string.restrict)) == RESTRICTED) {
        	
        	districtFreeEdition();
        	
        }
        
        // 設定1.設定ボタンへクリックイベントリスナーを付与する
        Button buttonSetting01 = (Button) findViewById(R.id.buttonSetting01);
        buttonSetting01.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				
				/*******************************************************************
				 * メソッド名：onClick
				 * 概要：buttonSetting01のクリックイベントリスナー
				 * 
				 * Var. 日付       氏名               更新履歴
				 * ---- ---------- ------------------ -----------------------------
				 * V0.1 2009/08/12 笹野佑樹           初版作成
				 *******************************************************************/
				
				Intent intent = new Intent(MainForm.this, SettingForm.class);
				
				TextView textViewOnTimeSetting01 = (TextView)findViewById(R.id.textViewOnTimeSetting01);
				TextView textViewOffTimeSetting01 = (TextView)findViewById(R.id.textViewOffTimeSetting01);
				TextView textViewWeek01 = (TextView)findViewById(R.id.textViewWeek01);
				
				intent.putExtra(INTNAME_SETTING_NO, getString(R.string.setting01));
				intent.putExtra(INTNAME_ONTIME_HOUR, textViewOnTimeSetting01.getText().toString().substring(0, 2));
				intent.putExtra(INTNAME_ONTIME_MINUTE, textViewOnTimeSetting01.getText().toString().substring(3));
				intent.putExtra(INTNAME_OFFTIME_HOUR, textViewOffTimeSetting01.getText().toString().substring(0, 2));
				intent.putExtra(INTNAME_OFFTIME_MINUTE, textViewOffTimeSetting01.getText().toString().substring(3));
				try {
					intent.putExtra(INTNAME_WEEK, textViewWeek01.getTag().toString());
				} catch(NullPointerException e) {
					intent.putExtra(INTNAME_WEEK, getString(R.string.non_week));
				} catch(Exception e) {
					intent.putExtra(INTNAME_WEEK, getString(R.string.non_week));
				}
				
				startActivityForResult(intent, REQ_SETTING01);
			}
		});
        
        // 設定2.設定ボタンへクリックイベントリスナーを付与する
        Button buttonSetting02 = (Button)findViewById(R.id.buttonSetting02);
        buttonSetting02.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				
				/*******************************************************************
				 * メソッド名：onClick
				 * 概要：buttonSetting02のクリックイベントリスナー
				 * 
				 * Var. 日付       氏名               更新履歴
				 * ---- ---------- ------------------ -----------------------------
				 * V0.1 2009/08/12 笹野佑樹           初版作成
				 *******************************************************************/
				
				Intent intent = new Intent(MainForm.this, SettingForm.class);
				
				TextView textViewOnTimeSetting02 = (TextView)findViewById(R.id.textViewOnTimeSetting02);
				TextView textViewOffTimeSetting02 = (TextView)findViewById(R.id.textViewOffTimeSetting02);
				TextView textViewWeek02 = (TextView)findViewById(R.id.textViewWeek02);
				
				intent.putExtra(INTNAME_SETTING_NO, getString(R.string.setting02));
				intent.putExtra(INTNAME_ONTIME_HOUR, textViewOnTimeSetting02.getText().toString().substring(0, 2));
				intent.putExtra(INTNAME_ONTIME_MINUTE, textViewOnTimeSetting02.getText().toString().substring(3));
				intent.putExtra(INTNAME_OFFTIME_HOUR, textViewOffTimeSetting02.getText().toString().substring(0, 2));
				intent.putExtra(INTNAME_OFFTIME_MINUTE, textViewOffTimeSetting02.getText().toString().substring(3));
				try {
					intent.putExtra(INTNAME_WEEK, textViewWeek02.getTag().toString());
				} catch(NullPointerException e) {
					intent.putExtra(INTNAME_WEEK, getString(R.string.non_week));
				}
				
				startActivityForResult(intent, REQ_SETTING02);
			}
		});
        
        // 設定3.設定ボタンへクリックイベントリスナーを付与する
        Button buttonSetting03 = (Button)findViewById(R.id.buttonSetting03);
        buttonSetting03.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				
				/*******************************************************************
				 * メソッド名：onClick
				 * 概要：buttonSetting03のクリックイベントリスナー
				 * 
				 * Var. 日付       氏名               更新履歴
				 * ---- ---------- ------------------ -----------------------------
				 * V0.1 2009/08/12 笹野佑樹           初版作成
				 *******************************************************************/
				
				Intent intent = new Intent(MainForm.this, SettingForm.class);
				
				TextView textViewOnTimeSetting03 = (TextView)findViewById(R.id.textViewOnTimeSetting03);
				TextView textViewOffTimeSetting03 = (TextView)findViewById(R.id.textViewOffTimeSetting03);
				TextView textViewWeek03 = (TextView)findViewById(R.id.textViewWeek03);
				
				intent.putExtra(INTNAME_SETTING_NO, getString(R.string.setting03));
				intent.putExtra(INTNAME_ONTIME_HOUR, textViewOnTimeSetting03.getText().toString().substring(0, 2));
				intent.putExtra(INTNAME_ONTIME_MINUTE, textViewOnTimeSetting03.getText().toString().substring(3));
				intent.putExtra(INTNAME_OFFTIME_HOUR, textViewOffTimeSetting03.getText().toString().substring(0, 2));
				intent.putExtra(INTNAME_OFFTIME_MINUTE, textViewOffTimeSetting03.getText().toString().substring(3));
				try {
					intent.putExtra(INTNAME_WEEK, textViewWeek03.getTag().toString());
				} catch(NullPointerException e) {
					intent.putExtra(INTNAME_WEEK, getString(R.string.non_week));
				}
				
				startActivityForResult(intent, REQ_SETTING03);
			}
		});
        
        // 設定4.設定ボタンへクリックイベントリスナーを付与する
        Button buttonSetting04 = (Button)findViewById(R.id.buttonSetting04);
        buttonSetting04.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				
				/*******************************************************************
				 * メソッド名：onClick
				 * 概要：buttonSetting04のクリックイベントリスナー
				 * 
				 * Var. 日付       氏名               更新履歴
				 * ---- ---------- ------------------ -----------------------------
				 * V0.1 2009/08/12 笹野佑樹           初版作成
				 *******************************************************************/
				
				Intent intent = new Intent(MainForm.this, SettingForm.class);
				
				TextView textViewOnTimeSetting04 = (TextView)findViewById(R.id.textViewOnTimeSetting04);
				TextView textViewOffTimeSetting04 = (TextView)findViewById(R.id.textViewOffTimeSetting04);
				TextView textViewWeek04 = (TextView)findViewById(R.id.textViewWeek04);
				
				intent.putExtra(INTNAME_SETTING_NO, getString(R.string.setting04));
				intent.putExtra(INTNAME_ONTIME_HOUR, textViewOnTimeSetting04.getText().toString().substring(0, 2));
				intent.putExtra(INTNAME_ONTIME_MINUTE, textViewOnTimeSetting04.getText().toString().substring(3));
				intent.putExtra(INTNAME_OFFTIME_HOUR, textViewOffTimeSetting04.getText().toString().substring(0, 2));
				intent.putExtra(INTNAME_OFFTIME_MINUTE, textViewOffTimeSetting04.getText().toString().substring(3));
				try {
					intent.putExtra(INTNAME_WEEK, textViewWeek04.getTag().toString());
				} catch(NullPointerException e) {
					intent.putExtra(INTNAME_WEEK, getString(R.string.non_week));
				}
				
				startActivityForResult(intent, REQ_SETTING04);
			}
		});
        
        // 設定5.設定ボタンへクリックイベントリスナーを付与する
        Button buttonSetting05 = (Button)findViewById(R.id.buttonSetting05);
        buttonSetting05.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				
				/*******************************************************************
				 * メソッド名：onClick
				 * 概要：buttonSetting05のクリックイベントリスナー
				 * 
				 * Var. 日付       氏名               更新履歴
				 * ---- ---------- ------------------ -----------------------------
				 * V0.1 2009/08/12 笹野佑樹           初版作成
				 *******************************************************************/
				
				Intent intent = new Intent(MainForm.this, SettingForm.class);
				
				TextView textViewOnTimeSetting05 = (TextView)findViewById(R.id.textViewOnTimeSetting05);
				TextView textViewOffTimeSetting05 = (TextView)findViewById(R.id.textViewOffTimeSetting05);
				TextView textViewWeek05 = (TextView)findViewById(R.id.textViewWeek05);
				
				intent.putExtra(INTNAME_SETTING_NO, getString(R.string.setting05));
				intent.putExtra(INTNAME_ONTIME_HOUR, textViewOnTimeSetting05.getText().toString().substring(0, 2));
				intent.putExtra(INTNAME_ONTIME_MINUTE, textViewOnTimeSetting05.getText().toString().substring(3));
				intent.putExtra(INTNAME_OFFTIME_HOUR, textViewOffTimeSetting05.getText().toString().substring(0, 2));
				intent.putExtra(INTNAME_OFFTIME_MINUTE, textViewOffTimeSetting05.getText().toString().substring(3));
				try {
					intent.putExtra(INTNAME_WEEK, textViewWeek05.getTag().toString());
				} catch(NullPointerException e) {
					intent.putExtra(INTNAME_WEEK, getString(R.string.non_week));
				}
				
				startActivityForResult(intent, REQ_SETTING05);
			}
		});
        
        // 終了ボタンへクリックイベントリスナーを付与する
        Button buttonClose = (Button)findViewById(R.id.buttonClose);
        buttonClose.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				
				/*******************************************************************
				 * メソッド名：onClick
				 * 概要：buttonCloseのクリックイベントリスナー
				 * 
				 * Var. 日付       氏名               更新履歴
				 * ---- ---------- ------------------ -----------------------------
				 * V0.1 2009/08/12 笹野佑樹           初版作成
				 *******************************************************************/
				
				finish();
			}
		});
    }
	
	public void onDestroy() {
		super.onDestroy();
		
		/*******************************************************************
		 * メソッド名：onDestroy
		 * 概要：MainFormのonDestroyメソッド
		 * 
		 * Var. 日付       氏名               更新履歴
		 * ---- ---------- ------------------ -----------------------------
		 * V0.1 2009/08/12 笹野佑樹           初版作成
		 *******************************************************************/
		
		// 状態を設定保存ファイルへ出力する
		outputSaveFile();
		
		// サービスの開始/終了
		controlServiceStartOrStop();
	}
	
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		
		/*******************************************************************
		 * メソッド名：onActivityResult
		 * 概要：startActivityForResultで実行したintent（SettingForm.class）
		 *       から戻ってきた際に実行される
		 *       設定画面で入力された値をメイン画面に反映する
		 * 
		 * Var. 日付       氏名               更新履歴
		 * ---- ---------- ------------------ -----------------------------
		 * V0.1 2009/08/12 笹野佑樹           初版作成
		 *******************************************************************/
		
		if (resultCode != RESULT_OK) {
			return;
		}
		
		// リクエストコード（startActivityForResult呼び出し時に設定）による分岐処理
		
		switch(requestCode) {
		
		case REQ_SETTING01:
			
			// 設定1の値に反映する
			TextView textViewOnTimeSetting01 = (TextView)findViewById(R.id.textViewOnTimeSetting01);
			TextView textViewOffTimeSetting01 = (TextView)findViewById(R.id.textViewOffTimeSetting01);
			TextView textViewWeek01 = (TextView)findViewById(R.id.textViewWeek01);
			
			setTextViewInputData(textViewOnTimeSetting01, textViewOffTimeSetting01, textViewWeek01, data);
			
			break;
			
		case REQ_SETTING02:
			
			// 設定2の値に反映する
			TextView textViewOnTimeSetting02 = (TextView)findViewById(R.id.textViewOnTimeSetting02);
			TextView textViewOffTimeSetting02 = (TextView)findViewById(R.id.textViewOffTimeSetting02);
			TextView textViewWeek02 = (TextView)findViewById(R.id.textViewWeek02);
			
			setTextViewInputData(textViewOnTimeSetting02, textViewOffTimeSetting02, textViewWeek02, data);
			
			break;
			
		case REQ_SETTING03:
			
			// 設定3の値に反映する
			TextView textViewOnTimeSetting03 = (TextView)findViewById(R.id.textViewOnTimeSetting03);
			TextView textViewOffTimeSetting03 = (TextView)findViewById(R.id.textViewOffTimeSetting03);
			TextView textViewWeek03 = (TextView)findViewById(R.id.textViewWeek03);
			
			setTextViewInputData(textViewOnTimeSetting03, textViewOffTimeSetting03, textViewWeek03, data);
			
			break;
			
		case REQ_SETTING04:
			
			// 設定4の値に反映する
			TextView textViewOnTimeSetting04 = (TextView)findViewById(R.id.textViewOnTimeSetting04);
			TextView textViewOffTimeSetting04 = (TextView)findViewById(R.id.textViewOffTimeSetting04);
			TextView textViewWeek04 = (TextView)findViewById(R.id.textViewWeek04);
			
			setTextViewInputData(textViewOnTimeSetting04, textViewOffTimeSetting04, textViewWeek04, data);
			
			break;
			
		case REQ_SETTING05:
			
			// 設定5の値に反映する
			TextView textViewOnTimeSetting05 = (TextView)findViewById(R.id.textViewOnTimeSetting05);
			TextView textViewOffTimeSetting05 = (TextView)findViewById(R.id.textViewOffTimeSetting05);
			TextView textViewWeek05 = (TextView)findViewById(R.id.textViewWeek05);
			
			setTextViewInputData(textViewOnTimeSetting05, textViewOffTimeSetting05, textViewWeek05, data);
			
			break;
			
		}
	}
	
	private void districtFreeEdition() {
		
		/*******************************************************************
		 * メソッド名：districtFreeEdition
		 * 概要：無料版のみ設定2～設定5を非表示にする
		 * 
		 * Var. 日付       氏名               更新履歴
		 * ---- ---------- ------------------ -----------------------------
		 * V0.1 2009/08/12 笹野佑樹           初版作成
		 *******************************************************************/
		
		// 設定2～設定5のViewのインスタンスの取得
		TextView textViewSetting02 = (TextView)findViewById(R.id.textViewSetting02);
		ToggleButton toggleButtonEffective02 = (ToggleButton)findViewById(R.id.toggleButtonEffective02);
		TextView textViewOnSetting02 = (TextView)findViewById(R.id.textViewOnSetting02);
		TextView textViewOnTimeSetting02 = (TextView)findViewById(R.id.textViewOnTimeSetting02);
		TextView textViewOffSetting02 = (TextView)findViewById(R.id.textViewOffSetting02);
		TextView textViewOffTimeSetting02 = (TextView)findViewById(R.id.textViewOffTimeSetting02);
		TextView textViewWeekSetting02 = (TextView)findViewById(R.id.textViewWeekSetting02);
		TextView textViewWeek02 = (TextView)findViewById(R.id.textViewWeek02);
		Button buttonSetting02 = (Button)findViewById(R.id.buttonSetting02);
		
		TextView textViewSetting03 = (TextView)findViewById(R.id.textViewSetting03);
		ToggleButton toggleButtonEffective03 = (ToggleButton)findViewById(R.id.toggleButtonEffective03);
		TextView textViewOnSetting03 = (TextView)findViewById(R.id.textViewOnSetting03);
		TextView textViewOnTimeSetting03 = (TextView)findViewById(R.id.textViewOnTimeSetting03);
		TextView textViewOffSetting03 = (TextView)findViewById(R.id.textViewOffSetting03);
		TextView textViewOffTimeSetting03 = (TextView)findViewById(R.id.textViewOffTimeSetting03);
		TextView textViewWeekSetting03 = (TextView)findViewById(R.id.textViewWeekSetting03);
		TextView textViewWeek03 = (TextView)findViewById(R.id.textViewWeek03);
		Button buttonSetting03 = (Button)findViewById(R.id.buttonSetting03);
		
		TextView textViewSetting04 = (TextView)findViewById(R.id.textViewSetting04);
		ToggleButton toggleButtonEffective04 = (ToggleButton)findViewById(R.id.toggleButtonEffective04);
		TextView textViewOnSetting04 = (TextView)findViewById(R.id.textViewOnSetting04);
		TextView textViewOnTimeSetting04 = (TextView)findViewById(R.id.textViewOnTimeSetting04);
		TextView textViewOffSetting04 = (TextView)findViewById(R.id.textViewOffSetting04);
		TextView textViewOffTimeSetting04 = (TextView)findViewById(R.id.textViewOffTimeSetting04);
		TextView textViewWeekSetting04 = (TextView)findViewById(R.id.textViewWeekSetting04);
		TextView textViewWeek04 = (TextView)findViewById(R.id.textViewWeek04);
		Button buttonSetting04 = (Button)findViewById(R.id.buttonSetting04);
		
		TextView textViewSetting05 = (TextView)findViewById(R.id.textViewSetting05);
		ToggleButton toggleButtonEffective05 = (ToggleButton)findViewById(R.id.toggleButtonEffective05);
		TextView textViewOnSetting05 = (TextView)findViewById(R.id.textViewOnSetting05);
		TextView textViewOnTimeSetting05 = (TextView)findViewById(R.id.textViewOnTimeSetting05);
		TextView textViewOffSetting05 = (TextView)findViewById(R.id.textViewOffSetting05);
		TextView textViewOffTimeSetting05 = (TextView)findViewById(R.id.textViewOffTimeSetting05);
		TextView textViewWeekSetting05 = (TextView)findViewById(R.id.textViewWeekSetting05);
		TextView textViewWeek05 = (TextView)findViewById(R.id.textViewWeek05);
		Button buttonSetting05 = (Button)findViewById(R.id.buttonSetting05);
		
		// 全て非表示にする
		textViewSetting02.setVisibility(View.INVISIBLE);
		toggleButtonEffective02.setVisibility(View.INVISIBLE);
		textViewOnSetting02.setVisibility(View.INVISIBLE);
		textViewOnTimeSetting02.setVisibility(View.INVISIBLE);
		textViewOffSetting02.setVisibility(View.INVISIBLE);
		textViewOffTimeSetting02.setVisibility(View.INVISIBLE);
		textViewWeekSetting02.setVisibility(View.INVISIBLE);
		textViewWeek02.setVisibility(View.INVISIBLE);
		buttonSetting02.setVisibility(View.INVISIBLE);
		
		textViewSetting03.setVisibility(View.INVISIBLE);
		toggleButtonEffective03.setVisibility(View.INVISIBLE);
		textViewOnSetting03.setVisibility(View.INVISIBLE);
		textViewOnTimeSetting03.setVisibility(View.INVISIBLE);
		textViewOffSetting03.setVisibility(View.INVISIBLE);
		textViewOffTimeSetting03.setVisibility(View.INVISIBLE);
		textViewWeekSetting03.setVisibility(View.INVISIBLE);
		textViewWeek03.setVisibility(View.INVISIBLE);
		buttonSetting03.setVisibility(View.INVISIBLE);
		
		textViewSetting04.setVisibility(View.INVISIBLE);
		toggleButtonEffective04.setVisibility(View.INVISIBLE);
		textViewOnSetting04.setVisibility(View.INVISIBLE);
		textViewOnTimeSetting04.setVisibility(View.INVISIBLE);
		textViewOffSetting04.setVisibility(View.INVISIBLE);
		textViewOffTimeSetting04.setVisibility(View.INVISIBLE);
		textViewWeekSetting04.setVisibility(View.INVISIBLE);
		textViewWeek04.setVisibility(View.INVISIBLE);
		buttonSetting04.setVisibility(View.INVISIBLE);
		
		textViewSetting05.setVisibility(View.INVISIBLE);
		toggleButtonEffective05.setVisibility(View.INVISIBLE);
		textViewOnSetting05.setVisibility(View.INVISIBLE);
		textViewOnTimeSetting05.setVisibility(View.INVISIBLE);
		textViewOffSetting05.setVisibility(View.INVISIBLE);
		textViewOffTimeSetting05.setVisibility(View.INVISIBLE);
		textViewWeekSetting05.setVisibility(View.INVISIBLE);
		textViewWeek05.setVisibility(View.INVISIBLE);
		buttonSetting05.setVisibility(View.INVISIBLE);

	}
	
	private void setViewSavedState() {
		
		/*******************************************************************
		 * メソッド名：setViewSavedState
		 * 概要：設定保存ファイルを読み込み各Viewに設定を反映させる
		 * 
		 * Var. 日付       氏名               更新履歴
		 * ---- ---------- ------------------ -----------------------------
		 * V0.1 2009/08/12 笹野佑樹           初版作成
		 *******************************************************************/
		
		// 各Viewのインスタンスを取得する
		
		ToggleButton toggleButtonEffective01 = (ToggleButton)findViewById(R.id.toggleButtonEffective01);
		TextView textViewOnTimeSetting01 = (TextView)findViewById(R.id.textViewOnTimeSetting01);
		TextView textViewOffTimeSetting01 = (TextView)findViewById(R.id.textViewOffTimeSetting01);
		TextView textViewWeek01 = (TextView)findViewById(R.id.textViewWeek01);
		
		ToggleButton toggleButtonEffective02 = (ToggleButton)findViewById(R.id.toggleButtonEffective02);
		TextView textViewOnTimeSetting02 = (TextView)findViewById(R.id.textViewOnTimeSetting02);
		TextView textViewOffTimeSetting02 = (TextView)findViewById(R.id.textViewOffTimeSetting02);
		TextView textViewWeek02 = (TextView)findViewById(R.id.textViewWeek02);
		
		ToggleButton toggleButtonEffective03 = (ToggleButton)findViewById(R.id.toggleButtonEffective03);
		TextView textViewOnTimeSetting03 = (TextView)findViewById(R.id.textViewOnTimeSetting03);
		TextView textViewOffTimeSetting03 = (TextView)findViewById(R.id.textViewOffTimeSetting03);
		TextView textViewWeek03 = (TextView)findViewById(R.id.textViewWeek03);
		
		ToggleButton toggleButtonEffective04 = (ToggleButton)findViewById(R.id.toggleButtonEffective04);
		TextView textViewOnTimeSetting04 = (TextView)findViewById(R.id.textViewOnTimeSetting04);
		TextView textViewOffTimeSetting04 = (TextView)findViewById(R.id.textViewOffTimeSetting04);
		TextView textViewWeek04 = (TextView)findViewById(R.id.textViewWeek04);
		
		ToggleButton toggleButtonEffective05 = (ToggleButton)findViewById(R.id.toggleButtonEffective05);
		TextView textViewOnTimeSetting05 = (TextView)findViewById(R.id.textViewOnTimeSetting05);
		TextView textViewOffTimeSetting05 = (TextView)findViewById(R.id.textViewOffTimeSetting05);
		TextView textViewWeek05 = (TextView)findViewById(R.id.textViewWeek05);
		
		//設定保存ファイルを読み込み各Viewへ設定を反映する
		
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
				
				splittedLine = line.split(SPLITTER);
				
				
				// ループ回数により各設定へ処理を分岐する
				
				switch(i) {
				
				case 1:
				
					// 設定1のViewに対して反映する
					toggleButtonEffective01.setChecked(Boolean.parseBoolean(splittedLine[IND_ON_OFF]));
					textViewOnTimeSetting01.setText(splittedLine[IND_ON_TIME]);
					textViewOffTimeSetting01.setText(splittedLine[IND_OFF_TIME]);
					textViewWeek01.setText(splittedLine[IND_WEEK]);
					textViewWeek01.setTag(splittedLine[IND_WEEK_TAG]);
					
					break;
				
				case 2:
				
					// 設定2のViewに対して反映する
					toggleButtonEffective02.setChecked(Boolean.parseBoolean(splittedLine[IND_ON_OFF]));
					textViewOnTimeSetting02.setText(splittedLine[IND_ON_TIME]);
					textViewOffTimeSetting02.setText(splittedLine[IND_OFF_TIME]);
					textViewWeek02.setText(splittedLine[IND_WEEK]);
					textViewWeek02.setTag(splittedLine[IND_WEEK_TAG]);
					
					break;
				
				case 3:
				
					// 設定3のViewに対して反映する
					toggleButtonEffective03.setChecked(Boolean.parseBoolean(splittedLine[IND_ON_OFF]));
					textViewOnTimeSetting03.setText(splittedLine[IND_ON_TIME]);
					textViewOffTimeSetting03.setText(splittedLine[IND_OFF_TIME]);
					textViewWeek03.setText(splittedLine[IND_WEEK]);
					textViewWeek03.setTag(splittedLine[IND_WEEK_TAG]);
					
					break;
				
				case 4:
				
					// 設定4のViewに対して反映する
					toggleButtonEffective04.setChecked(Boolean.parseBoolean(splittedLine[IND_ON_OFF]));
					textViewOnTimeSetting04.setText(splittedLine[IND_ON_TIME]);
					textViewOffTimeSetting04.setText(splittedLine[IND_OFF_TIME]);
					textViewWeek04.setText(splittedLine[IND_WEEK]);
					textViewWeek04.setTag(splittedLine[IND_WEEK_TAG]);
					
					break;
				
				case 5:
				
					// 設定5のViewに対して反映する
					toggleButtonEffective05.setChecked(Boolean.parseBoolean(splittedLine[IND_ON_OFF]));
					textViewOnTimeSetting05.setText(splittedLine[IND_ON_TIME]);
					textViewOffTimeSetting05.setText(splittedLine[IND_OFF_TIME]);
					textViewWeek05.setText(splittedLine[IND_WEEK]);
					textViewWeek05.setTag(splittedLine[IND_WEEK_TAG]);
					
					break;
				
				default:
					
					break;	
				}
			}
			
			br.close();
			isr.close();
			fis.close();
			
		} catch(Exception e) {
			
			Toast.makeText(this, getString(R.string.msg001), Toast.LENGTH_SHORT).show();
		}
	}
	
	private void setTextViewInputData(TextView tvOnTime, TextView tvOffTime, TextView tvWeek, Intent intent) {
		
		/******************************************************************************
		 * メソッド名：setTextViewInputData
		 * 概要：設定画面より受け取ったintent（入力データ）をテキストビューに反映する
		 * 
		 * Var. 日付       氏名               更新履歴
		 * ---- ---------- ------------------ -----------------------------
		 * V0.1 2009/08/12 笹野佑樹           初版作成
		 * V0.1 2010/01/29 生田目篤           設定画面で曜日のチェックがないとエラーが発生するバグの修正
		 ******************************************************************************/
		
		String extrasWeek;
		String translatedWeek;
		
		tvOnTime.setText(intent.getCharSequenceExtra(INTENT_ON_TIME));
		tvOffTime.setText(intent.getCharSequenceExtra(INTENT_OFF_TIME));
		
		// 数値を漢字に変換する
		extrasWeek = (String) intent.getCharSequenceExtra(INTENT_WEEK);
		translatedWeek = extrasWeek;
		translatedWeek = translatedWeek.replaceAll(CHAR_SUNDAY, KANJI_SUNDAY);
		translatedWeek = translatedWeek.replaceAll(CHAR_MONDAY, KANJI_MONDAY);
		translatedWeek = translatedWeek.replaceAll(CHAR_TUESDAY, KANJI_TUESDAY);
		translatedWeek = translatedWeek.replaceAll(CHAR_WEDNESDAY, KANJI_WEDNESDAY);
		translatedWeek = translatedWeek.replaceAll(CHAR_THURSDAY, KANJI_THURSDAY);
		translatedWeek = translatedWeek.replaceAll(CHAR_FRIDAY, KANJI_FRIDAY);
		translatedWeek = translatedWeek.replaceAll(CHAR_SATURDAY, KANJI_SATURDAY);
		
		// 追加 [2010/01/29] どの曜日にもチェックが入っていなかった場合は "##" を入れておく
		if(extrasWeek.equals("")){
			extrasWeek = getString(R.string.non_week);
		}
		
		tvWeek.setText(translatedWeek);
		tvWeek.setTag(extrasWeek);
	}
	
	private void controlServiceStartOrStop() {
		
		/******************************************************************************
		 * メソッド名：controlServiceStartOrStop
		 * 概要：各設定のON/OFFの状況によりサービスを開始・終了する
		 * 
		 * Var. 日付       氏名               更新履歴
		 * ---- ---------- ------------------ -----------------------------
		 * V0.1 2009/08/12 笹野佑樹           初版作成
		 ******************************************************************************/
		
		// 各設定のToggleButtonのインスタンスを取得
		ToggleButton toggleButtonEffective01 = (ToggleButton)findViewById(R.id.toggleButtonEffective01);
		ToggleButton toggleButtonEffective02 = (ToggleButton)findViewById(R.id.toggleButtonEffective02);
		ToggleButton toggleButtonEffective03 = (ToggleButton)findViewById(R.id.toggleButtonEffective03);
		ToggleButton toggleButtonEffective04 = (ToggleButton)findViewById(R.id.toggleButtonEffective04);
		ToggleButton toggleButtonEffective05 = (ToggleButton)findViewById(R.id.toggleButtonEffective05);
		
		// Intent
		Intent intent = new Intent(MainForm.this, MannersService.class);
		
		if (toggleButtonEffective01.isChecked() 
				|| toggleButtonEffective02.isChecked()
				|| toggleButtonEffective03.isChecked()
				|| toggleButtonEffective04.isChecked()
				|| toggleButtonEffective05.isChecked()) {
			
			// 1つでもONの状態であれば設定状態を渡しサービスを開始
			startService(intent);
			
		} else {
			
			// すべてOFFの状態であればサービスを終了
			stopService(intent);
		}
	}
	
	private void outputSaveFile() {
		
		/*******************************************************************
		 * メソッド名：outputSaveFile
		 * 概要：現在のビューの状態を設定保存ファイルへ出力する
		 * 
		 * Var. 日付       氏名               更新履歴
		 * ---- ---------- ------------------ -----------------------------
		 * V0.1 2009/08/12 笹野佑樹           初版作成
		 *******************************************************************/
		
		// ビューのインスタンスの取得
		ToggleButton toggleButtonEffective01 = (ToggleButton)findViewById(R.id.toggleButtonEffective01);
		TextView textViewOnTimeSetting01 = (TextView)findViewById(R.id.textViewOnTimeSetting01);
		TextView textViewOffTimeSetting01 = (TextView)findViewById(R.id.textViewOffTimeSetting01);
		TextView textViewWeek01 = (TextView)findViewById(R.id.textViewWeek01);
		
		ToggleButton toggleButtonEffective02 = (ToggleButton)findViewById(R.id.toggleButtonEffective02);
		TextView textViewOnTimeSetting02 = (TextView)findViewById(R.id.textViewOnTimeSetting02);
		TextView textViewOffTimeSetting02 = (TextView)findViewById(R.id.textViewOffTimeSetting02);
		TextView textViewWeek02 = (TextView)findViewById(R.id.textViewWeek02);
		
		ToggleButton toggleButtonEffective03 = (ToggleButton)findViewById(R.id.toggleButtonEffective03);
		TextView textViewOnTimeSetting03 = (TextView)findViewById(R.id.textViewOnTimeSetting03);
		TextView textViewOffTimeSetting03 = (TextView)findViewById(R.id.textViewOffTimeSetting03);
		TextView textViewWeek03 = (TextView)findViewById(R.id.textViewWeek03);
		
		ToggleButton toggleButtonEffective04 = (ToggleButton)findViewById(R.id.toggleButtonEffective04);
		TextView textViewOnTimeSetting04 = (TextView)findViewById(R.id.textViewOnTimeSetting04);
		TextView textViewOffTimeSetting04 = (TextView)findViewById(R.id.textViewOffTimeSetting04);
		TextView textViewWeek04 = (TextView)findViewById(R.id.textViewWeek04);
		
		ToggleButton toggleButtonEffective05 = (ToggleButton)findViewById(R.id.toggleButtonEffective05);
		TextView textViewOnTimeSetting05 = (TextView)findViewById(R.id.textViewOnTimeSetting05);
		TextView textViewOffTimeSetting05 = (TextView)findViewById(R.id.textViewOffTimeSetting05);
		TextView textViewWeek05 = (TextView)findViewById(R.id.textViewWeek05);
		
		// 出力する文字列を生成する
		String saveDataSetting01 = String.valueOf(toggleButtonEffective01.isChecked())
		                         + SPLITTER
		                         + textViewOnTimeSetting01.getText()
		                         + SPLITTER
		                         + textViewOffTimeSetting01.getText()
		                         + SPLITTER
		                         + textViewWeek01.getText()
		                         + SPLITTER
		                         + nvl(textViewWeek01.getTag(), getString(R.string.non_week)).toString()
		                         + "\n";
		
		String saveDataSetting02 = String.valueOf(toggleButtonEffective02.isChecked())
                                 + SPLITTER
                                 + textViewOnTimeSetting02.getText()
                                 + SPLITTER
                                 + textViewOffTimeSetting02.getText()
                                 + SPLITTER
                                 + textViewWeek02.getText()
		                         + SPLITTER
		                         + nvl(textViewWeek02.getTag(), getString(R.string.non_week)).toString()
		                         + "\n";
		
		String saveDataSetting03 = String.valueOf(toggleButtonEffective03.isChecked())
                                 + SPLITTER
                                 + textViewOnTimeSetting03.getText()
                                 + SPLITTER
                                 + textViewOffTimeSetting03.getText()
                                 + SPLITTER
                                 + textViewWeek03.getText()
		                         + SPLITTER
		                         + nvl(textViewWeek03.getTag(), getString(R.string.non_week)).toString()
		                         + "\n";
		
        String saveDataSetting04 = String.valueOf(toggleButtonEffective04.isChecked())
                                 + SPLITTER
                                 + textViewOnTimeSetting04.getText()
                                 + SPLITTER
                                 + textViewOffTimeSetting04.getText()
                                 + SPLITTER
                                 + textViewWeek04.getText()
		                         + SPLITTER
		                         + nvl(textViewWeek04.getTag(), getString(R.string.non_week)).toString()
		                         + "\n";
		
		String saveDataSetting05 = String.valueOf(toggleButtonEffective05.isChecked())
                                 + SPLITTER
                                 + textViewOnTimeSetting05.getText()
                                 + SPLITTER
                                 + textViewOffTimeSetting05.getText()
                                 + SPLITTER
                                 + textViewWeek05.getText()
		                         + SPLITTER
		                         + nvl(textViewWeek05.getTag(), getString(R.string.non_week)).toString()
		                         + "\n";
		
		
		// ファイルへ出力する
		String savefileFullPath = getString(R.string.data_dir) + getPackageName() + getString(R.string.file_name);
		File file = new File(savefileFullPath);
		
		try {
			
			if (!file.exists()) {
				file.createNewFile();
			}
			
		} catch(Exception e) {
			Toast.makeText(this, getString(R.string.msg002) + e.getMessage(), Toast.LENGTH_SHORT).show();
			return;
		}
		
				
		try {
		
			FileOutputStream fos = new FileOutputStream(savefileFullPath);
			OutputStreamWriter osw = new OutputStreamWriter(fos);
			BufferedWriter bw = new BufferedWriter(osw);
			
			bw.write(saveDataSetting01);
			bw.write(saveDataSetting02);
			bw.write(saveDataSetting03);
			bw.write(saveDataSetting04);
			bw.write(saveDataSetting05);
			
			bw.close();
			osw.close();
			fos.close();
			
		} catch (Exception e) {
			Toast.makeText(this, getString(R.string.msg003) + e.getMessage(), Toast.LENGTH_SHORT).show();
		}
	}
	
	private String nvl(Object str1, String str2) {
		if (str1 != null) {
			return str1.toString();
		} else {
			return str2;
		}
	}
}