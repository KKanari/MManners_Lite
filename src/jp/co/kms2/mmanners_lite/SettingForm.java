package jp.co.kms2.mmanners_lite;

/*******************************************************************
 * パッケージ名：jp.co.kms2.manners
 * 
 * Var. 日付       氏名               更新履歴
 * ---- ---------- ------------------ -----------------------------
 * V0.1 2009/08/07 笹野佑樹           初版作成
 *******************************************************************/

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.*;

public class SettingForm extends Activity {
	
	/*******************************************************************
	 * クラス名：SettingForm
	 * 機能概要：サイレントモードを変更する時間、曜日を設定する画面を構成する
	 *           ユーザより入力された値をMainFormへ受け渡しを行う
	 * 
	 * Var. 日付       氏名               更新履歴
	 * ---- ---------- ------------------ -----------------------------
	 * V0.1 2009/08/10 笹野佑樹           初版作成
	 *******************************************************************/
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		
		/*******************************************************************
		 * メソッド名：onCreate
		 * 機能概要：当アクティビティのonCreateメソッド
		 * 
		 * Var. 日付       氏名               更新履歴
		 * ---- ---------- ------------------ -----------------------------
		 * V0.1 2009/08/10 笹野佑樹           初版作成
		 *******************************************************************/
		
		super.onCreate(savedInstanceState);
		setContentView(R.layout.setting);
		
		// 画面の初期化
		initSettingViews();
		
		Button buttonFinish = (Button)findViewById(R.id.buttonFinish);
		buttonFinish.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				
				/*******************************************************************
				 * メソッド名：onClick
				 * 概要：buttonFinishのクリックイベントリスナー
				 * 
				 * Var. 日付       氏名               更新履歴
				 * ---- ---------- ------------------ -----------------------------
				 * V1.0 2009/08/12 笹野佑樹           初版作成
				 *******************************************************************/
				
				Intent intent = new Intent();
				
				
				// 入力されたサイレントモード入/切時間を00:00の形式でIntentへ
				
				TimePicker timePickerOnTime = (TimePicker)findViewById(R.id.timePickerOnTime);
				TimePicker timePickerOffTime = (TimePicker)findViewById(R.id.timePickerOffTime);
				
				int onTimeHour = timePickerOnTime.getCurrentHour();
				int onTimeMinute = timePickerOnTime.getCurrentMinute();
				int offTimeHour = timePickerOffTime.getCurrentHour();
				int offTimeMinute = timePickerOffTime.getCurrentMinute();
				
				intent.putExtra(MainForm.INTENT_ON_TIME, String.format("%1$02d", onTimeHour) + ":" + String.format("%1$02d", onTimeMinute));
				intent.putExtra(MainForm.INTENT_OFF_TIME, String.format("%1$02d", offTimeHour) + ":" + String.format("%1$02d", offTimeMinute));
				
				
				// 選択された曜日を取得し以下のルールの形式でIntentへ
				// 日→1, 月→2, 火→3, 水→4, 木→5, 金→6, 土→7
				// 上記数値を昇順に結合した文字列形式
				// 例）
				// 選択  ：日, 月, 木, 土
				// 文字列：1257
				
				String selectedWeek = "";
				CheckBox checkBoxSunday = (CheckBox)findViewById(R.id.checkBoxSunday);
				CheckBox checkBoxMonday = (CheckBox)findViewById(R.id.checkBoxMonday);
				CheckBox checkBoxTuesday = (CheckBox)findViewById(R.id.checkBoxTuesday);
				CheckBox checkBoxWednesday = (CheckBox)findViewById(R.id.checkBoxWednesday);
				CheckBox checkBoxThursday = (CheckBox)findViewById(R.id.checkBoxThursday);
				CheckBox checkBoxFriday = (CheckBox)findViewById(R.id.checkBoxFriday);
				CheckBox checkBoxSaturday = (CheckBox)findViewById(R.id.checkBoxSaturday);
				
				if (checkBoxSunday.isChecked() == true) {
					selectedWeek += MainForm.CHAR_SUNDAY;
				}
				if (checkBoxMonday.isChecked() == true) {
					selectedWeek += MainForm.CHAR_MONDAY;
				}
				if (checkBoxTuesday.isChecked() == true) {
					selectedWeek += MainForm.CHAR_TUESDAY;
				}
				if (checkBoxWednesday.isChecked() == true) {
					selectedWeek += MainForm.CHAR_WEDNESDAY;
				}
				if (checkBoxThursday.isChecked() == true) {
					selectedWeek += MainForm.CHAR_THURSDAY;
				}
				if (checkBoxFriday.isChecked() == true) {
					selectedWeek += MainForm.CHAR_FRIDAY;
				}
				if (checkBoxSaturday.isChecked() == true) {
					selectedWeek += MainForm.CHAR_SATURDAY;
				}
				
				intent.putExtra(MainForm.INTENT_WEEK, selectedWeek);
				
				
				setResult(RESULT_OK, intent);
				finish();
			}
		});
	}
	
	private void initSettingViews() {
		
		/*******************************************************************
		 * メソッド名：initSettingViews
		 * 概要：設定画面の各種入力要素を初期化する
		 *       初期化の値はMainFormからのIntent値もしくは初期値
		 * 
		 * Var. 日付       氏名               更新履歴
		 * ---- ---------- ------------------ -----------------------------
		 * V1.0 2009/08/12 笹野佑樹           初版作成
		 *******************************************************************/
		
		Bundle extras = getIntent().getExtras();
		
		// 設定No.
		TextView textViewSettingNo = (TextView) findViewById(R.id.textViewSettingnNo);
		textViewSettingNo.setText(extras.getCharSequence(MainForm.INTNAME_SETTING_NO));
		
		// ON時刻
		TimePicker timePickerOnTime = (TimePicker)findViewById(R.id.timePickerOnTime);
		
		timePickerOnTime.setCurrentHour(Integer.parseInt(extras.getCharSequence(MainForm.INTNAME_ONTIME_HOUR).toString()));
		timePickerOnTime.setCurrentMinute(Integer.parseInt(extras.getCharSequence(MainForm.INTNAME_ONTIME_MINUTE).toString()));
		
		// OFF時刻
		TimePicker timePickerOffTime = (TimePicker)findViewById(R.id.timePickerOffTime);
		
		timePickerOffTime.setCurrentHour(Integer.parseInt(extras.getCharSequence(MainForm.INTNAME_OFFTIME_HOUR).toString()));
		timePickerOffTime.setCurrentMinute(Integer.parseInt(extras.getCharSequence(MainForm.INTNAME_OFFTIME_MINUTE).toString()));
		
		// 曜日設定
		String weekNumber = extras.getCharSequence(MainForm.INTNAME_WEEK).toString();
		
		if (weekNumber == getString(R.string.non_week)) {
			return;
		}
		if (weekNumber.indexOf(MainForm.CHAR_SUNDAY) != -1) {
			CheckBox checkBoxSunday = (CheckBox)findViewById(R.id.checkBoxSunday);
			checkBoxSunday.setChecked(true);
		}
		if (weekNumber.indexOf(MainForm.CHAR_MONDAY) != -1) {
			CheckBox checkBoxMonday = (CheckBox)findViewById(R.id.checkBoxMonday);
			checkBoxMonday.setChecked(true);
		}
		if (weekNumber.indexOf(MainForm.CHAR_TUESDAY) != -1) {
			CheckBox checkBoxTuesday = (CheckBox)findViewById(R.id.checkBoxTuesday);
			checkBoxTuesday.setChecked(true);
		}
		if (weekNumber.indexOf(MainForm.CHAR_WEDNESDAY) != -1) {
			CheckBox checkBoxWednesday = (CheckBox)findViewById(R.id.checkBoxWednesday);
			checkBoxWednesday.setChecked(true);
		}
		if (weekNumber.indexOf(MainForm.CHAR_THURSDAY) != -1) {
			CheckBox checkBoxThursday = (CheckBox)findViewById(R.id.checkBoxThursday);
			checkBoxThursday.setChecked(true);
		}
		if (weekNumber.indexOf(MainForm.CHAR_FRIDAY) != -1) {
			CheckBox checkBoxFriday = (CheckBox)findViewById(R.id.checkBoxFriday);
			checkBoxFriday.setChecked(true);
		}
		if (weekNumber.indexOf(MainForm.CHAR_SATURDAY) != -1) {
			CheckBox checkBoxSaturday = (CheckBox)findViewById(R.id.checkBoxSaturday);
			checkBoxSaturday.setChecked(true);
		}
	}
}
