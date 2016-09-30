package jp.co.kms2.mmanners_lite;

/*******************************************************************
 * �p�b�P�[�W���Fjp.co.kms2.manners
 * 
 * Var. ���t       ����               �X�V����
 * ---- ---------- ------------------ -----------------------------
 * V0.1 2009/08/07 ����C��           ���ō쐬
 *******************************************************************/

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.*;

public class SettingForm extends Activity {
	
	/*******************************************************************
	 * �N���X���FSettingForm
	 * �@�\�T�v�F�T�C�����g���[�h��ύX���鎞�ԁA�j����ݒ肷���ʂ��\������
	 *           ���[�U�����͂��ꂽ�l��MainForm�֎󂯓n�����s��
	 * 
	 * Var. ���t       ����               �X�V����
	 * ---- ---------- ------------------ -----------------------------
	 * V0.1 2009/08/10 ����C��           ���ō쐬
	 *******************************************************************/
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		
		/*******************************************************************
		 * ���\�b�h���FonCreate
		 * �@�\�T�v�F���A�N�e�B�r�e�B��onCreate���\�b�h
		 * 
		 * Var. ���t       ����               �X�V����
		 * ---- ---------- ------------------ -----------------------------
		 * V0.1 2009/08/10 ����C��           ���ō쐬
		 *******************************************************************/
		
		super.onCreate(savedInstanceState);
		setContentView(R.layout.setting);
		
		// ��ʂ̏�����
		initSettingViews();
		
		Button buttonFinish = (Button)findViewById(R.id.buttonFinish);
		buttonFinish.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				
				/*******************************************************************
				 * ���\�b�h���FonClick
				 * �T�v�FbuttonFinish�̃N���b�N�C�x���g���X�i�[
				 * 
				 * Var. ���t       ����               �X�V����
				 * ---- ---------- ------------------ -----------------------------
				 * V1.0 2009/08/12 ����C��           ���ō쐬
				 *******************************************************************/
				
				Intent intent = new Intent();
				
				
				// ���͂��ꂽ�T�C�����g���[�h��/�؎��Ԃ�00:00�̌`����Intent��
				
				TimePicker timePickerOnTime = (TimePicker)findViewById(R.id.timePickerOnTime);
				TimePicker timePickerOffTime = (TimePicker)findViewById(R.id.timePickerOffTime);
				
				int onTimeHour = timePickerOnTime.getCurrentHour();
				int onTimeMinute = timePickerOnTime.getCurrentMinute();
				int offTimeHour = timePickerOffTime.getCurrentHour();
				int offTimeMinute = timePickerOffTime.getCurrentMinute();
				
				intent.putExtra(MainForm.INTENT_ON_TIME, String.format("%1$02d", onTimeHour) + ":" + String.format("%1$02d", onTimeMinute));
				intent.putExtra(MainForm.INTENT_OFF_TIME, String.format("%1$02d", offTimeHour) + ":" + String.format("%1$02d", offTimeMinute));
				
				
				// �I�����ꂽ�j�����擾���ȉ��̃��[���̌`����Intent��
				// ����1, ����2, �΁�3, ����4, �؁�5, ����6, �y��7
				// ��L���l�������Ɍ�������������`��
				// ��j
				// �I��  �F��, ��, ��, �y
				// ������F1257
				
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
		 * ���\�b�h���FinitSettingViews
		 * �T�v�F�ݒ��ʂ̊e����͗v�f������������
		 *       �������̒l��MainForm�����Intent�l�������͏����l
		 * 
		 * Var. ���t       ����               �X�V����
		 * ---- ---------- ------------------ -----------------------------
		 * V1.0 2009/08/12 ����C��           ���ō쐬
		 *******************************************************************/
		
		Bundle extras = getIntent().getExtras();
		
		// �ݒ�No.
		TextView textViewSettingNo = (TextView) findViewById(R.id.textViewSettingnNo);
		textViewSettingNo.setText(extras.getCharSequence(MainForm.INTNAME_SETTING_NO));
		
		// ON����
		TimePicker timePickerOnTime = (TimePicker)findViewById(R.id.timePickerOnTime);
		
		timePickerOnTime.setCurrentHour(Integer.parseInt(extras.getCharSequence(MainForm.INTNAME_ONTIME_HOUR).toString()));
		timePickerOnTime.setCurrentMinute(Integer.parseInt(extras.getCharSequence(MainForm.INTNAME_ONTIME_MINUTE).toString()));
		
		// OFF����
		TimePicker timePickerOffTime = (TimePicker)findViewById(R.id.timePickerOffTime);
		
		timePickerOffTime.setCurrentHour(Integer.parseInt(extras.getCharSequence(MainForm.INTNAME_OFFTIME_HOUR).toString()));
		timePickerOffTime.setCurrentMinute(Integer.parseInt(extras.getCharSequence(MainForm.INTNAME_OFFTIME_MINUTE).toString()));
		
		// �j���ݒ�
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
