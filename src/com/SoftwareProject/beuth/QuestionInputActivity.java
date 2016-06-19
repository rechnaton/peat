package com.SoftwareProject.beuth;

import android.annotation.TargetApi;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

@TargetApi(Build.VERSION_CODES.M)
public class QuestionInputActivity extends AppCompatActivity {
	
	/**
	 * Deklaration aller f�r Erzeugung des Question-Objektes notwendigen Variablen sowie der SQLite-Datenbank
	 */
	private String QuestionTypeTitle;
	private String questionText;
	private String questionTheme;
	private String[] answers;
	private Boolean[] isCorrect;
	
	private PeatDataSource dataSource;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		// Initialisierung der SQLite-Datenbank
		dataSource = new PeatDataSource(this);
		// Verbindung mit der SQLite-Datenbank aufbauen
		dataSource.open();
		// Initialisierung der beiden Question-Objekt-Arrays f�r Ja/Nein-Fragen
		answers = new String[2];
		answers[0] = "Ja";
		answers[1] = "Nein";
		isCorrect = new Boolean[2];
		// Auffangen nicht behandelter Ausnahmen
		Thread.setDefaultUncaughtExceptionHandler(new ExceptionHandler(this));
		// Layoutauswahl
		setContentView(R.layout.activity_question_input);
		// Instantiierung eines Button-Objektes f�r den OnClickListener
		Button addQuestion = (Button) findViewById(R.id.saveQuestionTypeYesNo);
		
		addQuestion.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
			    
				// Setzen Feld Fragetyp
				QuestionTypeTitle = "Choice"; // offen ist hier noch ob String oder Integer-Wert
				
				// Auslesen Textfeld Thema
				EditText editQuestionTypeYesNoTheme = (EditText) findViewById(R.id.editQuestionTypeYesNoTheme);
				// Eingabe in einen String umwandeln
				questionTheme = editQuestionTypeYesNoTheme.getText().toString();
				if(TextUtils.isEmpty(questionTheme)) {
					editQuestionTypeYesNoTheme.setError(getString(R.string.editText_errorMessage));
				    return;
				}
				
				// Auslesen Textfeld Frage
				EditText editQuestionTypeYesNoQuestion = (EditText) findViewById(R.id.editQuestionTypeYesNoQuestion);
				// Eingabe in einen String umwandeln
				questionText = editQuestionTypeYesNoQuestion.getText().toString();
				// Pr�fen, ob Feld leer ist
				if(TextUtils.isEmpty(questionText)) {
					editQuestionTypeYesNoQuestion.setError(getString(R.string.editText_errorMessage));
				    return;
				}
				
				// Markieren der korrekten Antwort im isCorrectArray des Question-Objektes
				RadioButton answer1 = (RadioButton) findViewById(R.id.radioQuestionYes);
				
				if (answer1.isChecked()) {
					isCorrect[0] = true;
					isCorrect[1] = false;
				} else {
					isCorrect[0] = false;
					isCorrect[1] = true;
				}
				
			    // Speichern der ausgelesenen Werte in die SQLite Datenbank
				// 1. Question-Objekt erzeugen
				Question oQuestion = new Question(questionText, QuestionTypeTitle, answers, isCorrect);
				// 2. Speichermethode auf dem Question-Objekt aufrufen
				dataSource.putQuestionInDB(oQuestion);
				dataSource.logAllQuestionsOfDB();
				
			    // Aktualisieren der Anzeige
				editQuestionTypeYesNoTheme.setText("");
				editQuestionTypeYesNoQuestion.setText("");
				RadioGroup radioGroupCorrect = (RadioGroup) findViewById(R.id.radioQuestionCorrect);
				radioGroupCorrect.check(R.id.radioQuestionYes);
				
		        Toast.makeText(QuestionInputActivity.this, "Frage gespeichert.", Toast.LENGTH_LONG).show();
		        
			}
		});

		Toast.makeText(this, "Zur�ck mit Back-Button.", Toast.LENGTH_SHORT).show();
	}
	
}