package com.SoftwareProject.beuth;

import android.support.v7.app.AppCompatActivity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.annotation.TargetApi;
import android.os.Build;
import android.preference.PreferenceManager;

@TargetApi(Build.VERSION_CODES.M)
public class QuestionTypeActivity extends AppCompatActivity {
	
	private static final int RESULT_SETTINGS = 1;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		Thread.setDefaultUncaughtExceptionHandler(new ExceptionHandler(this));
		setContentView(R.layout.activity_question_type);
		
		Button openQuestion = (Button) findViewById(R.id.addQuestionTypeOpen);
		Button yesNoQuestion = (Button) findViewById(R.id.addQuestionTypeYesNo);
		Button singleChoiceQuestion = (Button) findViewById(R.id.addQuestionTypeSingleChoice);
		Button mulitpleChoiceQuestion = (Button) findViewById(R.id.addQuestionTypeMultipleChoice);
		
		openQuestion.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Intent intentopen = new Intent(QuestionTypeActivity.this, UnderConstructionActivity.class);
				QuestionTypeActivity.this.startActivity(intentopen);
			}
		});

		yesNoQuestion.setOnClickListener(new View.OnClickListener() {
			
		 	@Override
		 	public void onClick(View v) {
		 		Intent intentclosed = new Intent(QuestionTypeActivity.this, QuestionInputActivity.class);
		 		QuestionTypeActivity.this.startActivity(intentclosed);
		 	}
		});
		
		singleChoiceQuestion.setOnClickListener(new View.OnClickListener() {
		 	
		 	@Override
		 	public void onClick(View v) {
		 		Intent intentsingle = new Intent(QuestionTypeActivity.this, UnderConstructionActivity.class);
		 		QuestionTypeActivity.this.startActivity(intentsingle);
		 	}
		});

		mulitpleChoiceQuestion.setOnClickListener(new View.OnClickListener() {
		 	
		    @Override
		 	public void onClick(View v) {
		 		Intent intentmulitple = new Intent(QuestionTypeActivity.this, UnderConstructionActivity.class);
		 		QuestionTypeActivity.this.startActivity(intentmulitple);
			}
		});
	}

	/**
	 * Diese Klasse oeffnet das Menue in der Aktionsleiste, bzw. fuegt Menuepunkte hinzu, sofern diese existieren
	 */	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.question_type, menu);
		return true;
	}
	
	/**
	 * Die folgenden 3 Klassen verarbeiten Klicks auf das Einstellungsmenue in der Aktionsleiste
	 * und holen Daten aus EinstellungenActivity, strings.xml, preferences.xml und arrays.xml
	 */
		@Override
		public boolean onOptionsItemSelected(MenuItem item) {
			int id = item.getItemId();
			if (id == R.id.action_settings) {
				startActivity(new Intent(this, EinstellungenActivity.class));
				return true;
			}
			
			switch (item.getItemId()) {
			
			case R.id.action_settings:
				Intent i = new Intent(this, EinstellungenActivity.class);
				startActivityForResult(i, RESULT_SETTINGS);
				break;
			}
			return super.onOptionsItemSelected(item);
		}
		
		@Override
		protected void onActivityResult(int requestCode, int resultCode, Intent data) {
			super.onActivityResult(requestCode, resultCode, data);
			
			switch (requestCode) {
			case RESULT_SETTINGS:
				showUserSettings();
				break;
			}
 		}
		
		private void showUserSettings() {
			SharedPreferences sharedPrefs = PreferenceManager.getDefaultSharedPreferences(this);
			StringBuilder builder = new StringBuilder();
 
			builder.append("\n Benutzername: "
                + sharedPrefs.getString("prefUsername", "NULL"));
 
			builder.append("\n Bericht senden:"
                + sharedPrefs.getBoolean("prefSendReport", false));
 
			builder.append("\n Wiederholung: "
                + sharedPrefs.getString("prefSyncFrequency", "NULL"));
 
			TextView settingsTextView = (TextView) findViewById(R.id.textUserSettings);
 
			settingsTextView.setText(builder.toString());
		}
}