package com.SoftwareProject.beuth;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

/*Hilfsklasse mit deren Hilfe die SQLite-Datenbank erstellt wird
 * enth�lt weiterhin wichtige Konstanten
 * 		wie Tabellennamen,
 * 		Datenbankversion, 
 * 		oder Namen der Spalten 
 * */
public class PeatDbHelper extends SQLiteOpenHelper{

    private static final String LOG_TAG = PeatDbHelper.class.getSimpleName();

    public static final String DB_NAME = "peat.db";
    public static final int DB_VERSION = 1;

    public static final String TABLE_QUESTIONTYPE = "QuestionType";
    public static final String TABLE_THEMES = "Themes";
    public static final String TABLE_PEATUSER = "PeatUser";
    public static final String TABLE_QUESTIONS = "Questions";
    public static final String TABLE_ANSWERS = "Answers";    
    public static final String TABLE_THEMES_HAS_QUESTIONS = "Themes_has_Questions";
    public static final String TABLE_PEATUSER_HAS_QUESTIONS = "PeatUser_has_Questions";
    public static final String TABLE_PEATUSER_HAS_THEMES = "PeatUser_has_Themes";
    public static final String TABLE_HISTORIEREPLIES = "HistorieReplies";

    
  
    public static final String SQL_CREATE_QUESTIONTYPE =
    	"CREATE TABLE " + TABLE_QUESTIONTYPE +
    	"(idQuestionType INTEGER   NOT NULL , title VARCHAR(255)   NOT NULL UNIQUE, explanation VARCHAR(255), PRIMARY KEY(idQuestionType));";

	public static final String SQL_CREATE_THEMES =
		"CREATE TABLE " + TABLE_THEMES +
		"(idThemes INTEGER NOT NULL , title VARCHAR(255), explanation VARCHAR(255), PRIMARY KEY(idThemes));";

	public static final String SQL_CREATE_PEATUSER =
		"CREATE TABLE " + TABLE_PEATUSER + 
		"(idPeatUser INTEGER NOT NULL, PRIMARY KEY(idPeatUser));";

	public static final String SQL_CREATE_QUESTIONS =
		"CREATE TABLE " + TABLE_QUESTIONS +
		"(idQuestions INTEGER NOT NULL, QuestionType_idQuestionType INTEGER NOT NULL, text VARCHAR(255) NOT NULL, PRIMARY KEY(idQuestions), " +
		"FOREIGN KEY(QuestionType_idQuestionType) REFERENCES QuestionType(idQuestionType));";

	public static final String SQL_CREATE_INDEX1 =
		"CREATE INDEX Questions_FKIndex1 ON Questions (QuestionType_idQuestionType);" +
		"CREATE INDEX IFK_Rel_01 ON Questions (QuestionType_idQuestionType);";

	public static final String SQL_CREATE_ANSWERS =
		"CREATE TABLE " + TABLE_ANSWERS + 
		"(idAnswers INTEGER NOT NULL, Questions_idQuestions INTEGER NOT NULL, text VARCHAR(255) NOT NULL, isCorrect BOOL NOT NULL," +
		"PRIMARY KEY(idAnswers), FOREIGN KEY(Questions_idQuestions) REFERENCES Questions(idQuestions));";

	public static final String SQL_CREATE_INDEX2 =
		"CREATE INDEX Answers_FKIndex1 ON Answers (Questions_idQuestions);" +
		"CREATE INDEX IFK_Rel_04 ON Answers (Questions_idQuestions);";

	public static final String SQL_CREATE_THEMES_HAS_QUESTIONS =
		"CREATE TABLE " + TABLE_THEMES_HAS_QUESTIONS +
		"(Themes_idThemes INTEGER NOT NULL, Questions_idQuestions INTEGER NOT NULL, PRIMARY KEY(Themes_idThemes, Questions_idQuestions)," +
		"FOREIGN KEY(Themes_idThemes) REFERENCES Themes(idThemes), FOREIGN KEY(Questions_idQuestions) REFERENCES Questions(idQuestions));";

	public static final String SQL_CREATE_INDEX3 =
		"CREATE INDEX Themes_has_Questions_FKIndex1 ON Themes_has_Questions (Themes_idThemes);" +
		"CREATE INDEX Themes_has_Questions_FKIndex2 ON Themes_has_Questions (Questions_idQuestions);" +
		"CREATE INDEX IFK_Rel_02 ON Themes_has_Questions (Themes_idThemes);" +
		"CREATE INDEX IFK_Rel_03 ON Themes_has_Questions (Questions_idQuestions);";

	public static final String SQL_CREATE_PEATUSER_HAS_QUESTIONS =
		"CREATE TABLE " + TABLE_PEATUSER_HAS_QUESTIONS + 
		"(PeatUser_idPeatUser INTEGER NOT NULL, Questions_idQuestions INTEGER NOT NULL , isIgnore BOOL NOT NULL, lastShown DATETIME, " +
		"isLastAnswerCorrect BOOL, " +
		"PRIMARY KEY(PeatUser_idPeatUser, Questions_idQuestions), FOREIGN KEY(PeatUser_idPeatUser)  REFERENCES PeatUser(idPeatUser), " +
		"FOREIGN KEY(Questions_idQuestions) REFERENCES Questions(idQuestions));";

	public static final String SQL_CREATE_INDEX4 =
		"CREATE INDEX PeatUser_has_Questions_FKIndex1 ON PeatUser_has_Questions (PeatUser_idPeatUser);" +
		"CREATE INDEX PeatUser_has_Questions_FKIndex2 ON PeatUser_has_Questions (Questions_idQuestions);" +
		"CREATE INDEX IFK_Rel_07 ON PeatUser_has_Questions (PeatUser_idPeatUser);" +
		"CREATE INDEX IFK_Rel_08 ON PeatUser_has_Questions (Questions_idQuestions);";

	public static final String SQL_CREATE_PEATUSER_HAS_THEMES =
		"CREATE TABLE " + TABLE_PEATUSER_HAS_THEMES + 
		"(PeatUser_idPeatUser INTEGER NOT NULL, Themes_idThemes INTEGER NOT NULL, questionEveryMinutes INTEGER NOT NULL, " +
		"PRIMARY KEY(PeatUser_idPeatUser, Themes_idThemes), " +
		"FOREIGN KEY(PeatUser_idPeatUser) REFERENCES PeatUser(idPeatUser), FOREIGN KEY(Themes_idThemes) REFERENCES Themes(idThemes));";

	public static final String SQL_CREATE_INDEX5 =
		"CREATE INDEX PeatUser_has_Themes_FKIndex1 ON PeatUser_has_Themes (PeatUser_idPeatUser);" +
		"CREATE INDEX PeatUser_has_Themes_FKIndex2 ON PeatUser_has_Themes (Themes_idThemes);" +
		"CREATE INDEX IFK_Rel_05 ON PeatUser_has_Themes (PeatUser_idPeatUser);" +
		"CREATE INDEX IFK_Rel_06 ON PeatUser_has_Themes (Themes_idThemes);";

	public static final String SQL_CREATE_HISTORIEREPLIES =
		"CREATE TABLE " + TABLE_HISTORIEREPLIES + 
		"(idHistorieReplies INTEGER NOT NULL, PeatUser_has_Questions_Questions_idQuestions INTEGER NOT NULL, " +
		"PeatUser_has_Questions_PeatUser_idPeatUser INTEGER NOT NULL, dateOfReply DATETIME NOT NULL," +
		"wasCorrectReply BOOL NOT NULL, "+
		"PRIMARY KEY(idHistorieReplies), FOREIGN KEY(PeatUser_has_Questions_PeatUser_idPeatUser, PeatUser_has_Questions_Questions_idQuestions) " +
		"REFERENCES PeatUser_has_Questions(PeatUser_idPeatUser, Questions_idQuestions));";

	public static final String SQL_CREATE_INDEX6 =
    	"CREATE INDEX HistorieReplies_FKIndex1 ON HistorieReplies (PeatUser_has_Questions_PeatUser_idPeatUser, PeatUser_has_Questions_Questions_idQuestions);" +
    	"CREATE INDEX IFK_Rel_09 ON HistorieReplies (PeatUser_has_Questions_PeatUser_idPeatUser, PeatUser_has_Questions_Questions_idQuestions);";


    public PeatDbHelper(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
        Log.d(LOG_TAG, "DbHelper hat die Datenbank: " + getDatabaseName() + " erzeugt.");
    }

    // Die onCreate-Methode wird nur aufgerufen, falls die Datenbank noch nicht existiert
    @Override
    public void onCreate(SQLiteDatabase db) {
        try {
            CreateSQL(db, TABLE_QUESTIONTYPE, SQL_CREATE_QUESTIONTYPE);
            CreateSQL(db, TABLE_THEMES, SQL_CREATE_THEMES);
            CreateSQL(db, TABLE_PEATUSER, SQL_CREATE_PEATUSER);
            CreateSQL(db, TABLE_QUESTIONS, SQL_CREATE_QUESTIONS);
            CreateSQL(db, TABLE_ANSWERS, SQL_CREATE_ANSWERS);
            CreateSQL(db, TABLE_THEMES_HAS_QUESTIONS, SQL_CREATE_THEMES_HAS_QUESTIONS);
            CreateSQL(db, TABLE_PEATUSER_HAS_QUESTIONS, SQL_CREATE_PEATUSER_HAS_QUESTIONS);
            CreateSQL(db, TABLE_PEATUSER_HAS_THEMES, SQL_CREATE_PEATUSER_HAS_THEMES);
            CreateSQL(db, TABLE_HISTORIEREPLIES, SQL_CREATE_HISTORIEREPLIES);
            CreateSQL(db, "Index 1", SQL_CREATE_INDEX1);
            CreateSQL(db, "Index 2", SQL_CREATE_INDEX2);
            CreateSQL(db, "Index 3", SQL_CREATE_INDEX3);
            CreateSQL(db, "Index 4", SQL_CREATE_INDEX4);
            CreateSQL(db, "Index 5", SQL_CREATE_INDEX5);
            CreateSQL(db, "Index 6", SQL_CREATE_INDEX6);
        }
        catch (Exception ex) {
            Log.e(LOG_TAG, "Fehler beim Anlegen der Tabelle: " + ex.getMessage());
        }
    }
    
    private void CreateSQL(SQLiteDatabase db, String tablename, String SQL_CREATE){
    	Log.d(LOG_TAG, "Die Tabelle " + tablename + " wird mit SQL-Befehl: " + SQL_CREATE + " angelegt.");
        db.execSQL(SQL_CREATE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}