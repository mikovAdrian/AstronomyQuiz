package com.adrian.android.quiztask;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by vanina on 26.04.17.
 */

public class DatabaseHelper extends SQLiteOpenHelper {

    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "astronomyQuizDB.db";

   /*------------------------------------------------------------------------------------------*/
    private static final String TABLE_NAME_ACCOUNTS = "accounts";
    private static final String COLUMN_ID = "id";
    private static final String COLUMN_NAME = "name";
    private static final String COLUMN_EMAIL = "email";
    private static final String COLUMN_USER_NAME = "userName";
    private static final String COLUMN_PASSWORD = "password";

    /*-------------------------------------------------------------------------------------------*/

    private static final String TABLE_NAME_QUESTIONS = "questions";
    private static final String COLUMN_QUESTION_ID = "id";
    private static final String COLUMN_QUESTION = "question";
    private static final String COLUMN_ANSWER = "answer";
    private static final String COLUMN_OPTION_ONE = "optionOne";
    private static final String COLUMN_OPTION_TWO = "optionTow";
    private static final String COLUMN_OPTION_THREE = "optionThree";


    SQLiteDatabase db;
    DatabaseHelper dbHelper;



    private static final String CREATE_TABLE_ACCOUNTS = "CREATE TABLE " + TABLE_NAME_ACCOUNTS + "(" + COLUMN_ID + " integer prymary key not null, " +
            COLUMN_NAME + " text not null, " + COLUMN_EMAIL + " text not null, " + COLUMN_USER_NAME +
            " text not null, " + COLUMN_PASSWORD + " text not null);";

    private static final String CREATE_TABLE_QUESTIONS = "CREATE TABLE IF NOT EXISTS " + TABLE_NAME_QUESTIONS + " (" + COLUMN_QUESTION_ID +
            " integer primary key autoincrement, " + COLUMN_QUESTION + " text not null, " +
            COLUMN_ANSWER + " text not null, " + COLUMN_OPTION_ONE + " text not null, " +
            COLUMN_OPTION_TWO + " text not null, " + COLUMN_OPTION_THREE + " text not null);";

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        this.db = db;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        this.db = db;
        db.execSQL(CREATE_TABLE_ACCOUNTS);
        db.execSQL(CREATE_TABLE_QUESTIONS);
        addQuestions();

    }

    public void createUserAccount(User user){
        db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        String countingRecordsQuery = "SELECT * FROM " + TABLE_NAME_ACCOUNTS;
        Cursor cursor = db.rawQuery(countingRecordsQuery, null);
        int count = cursor.getCount();

        values.put(COLUMN_ID, count);
        values.put(COLUMN_NAME, user.getName());
        values.put(COLUMN_EMAIL, user.getEmail());
        values.put(COLUMN_USER_NAME, user.getUserName());
        values.put(COLUMN_PASSWORD, user.getPassword());

        db.insert(TABLE_NAME_ACCOUNTS, null, values);
    }

    public List<String> searchPassword(String  userName){
        String findedUserName;
        String findedUserPassword = "not found";
        List<String> userData = new ArrayList<>();
        db = this.getReadableDatabase();

        String searchQuery =  "SELECT "+ COLUMN_USER_NAME + "," +  COLUMN_PASSWORD + "," + COLUMN_EMAIL + "," + COLUMN_NAME +" FROM " + TABLE_NAME_ACCOUNTS;
        Cursor cursor = db.rawQuery(searchQuery, null);

        if(cursor.moveToFirst()){
            do {
                findedUserName = cursor.getString(0);

                if(findedUserName.equals(userName)){
                    findedUserPassword = cursor.getString(1);
                    userData.add(findedUserName);
                    userData.add(findedUserPassword);
                    userData.add(cursor.getString(2));
                    userData.add(cursor.getString(3));
                    break;
                }
            }while (cursor.moveToNext());
        }
        return userData;
    }

    public boolean isEmailUsed(String email){
        db =  this.getReadableDatabase();
        String searchEmail = "SELECT * FROM " + TABLE_NAME_ACCOUNTS + " WHERE " + COLUMN_EMAIL  + " = '" + email + "';";
        Cursor  cursor = db.rawQuery(searchEmail, null);
        if(cursor.getCount() > 0){
            return true;
        }else{
            return false;
        }
    }

    //droid.database.sqlite.SQLiteException: no such column: sa (code 1): , while compiling: SELECT * FROM contacts WHERE userName = sa;

    public boolean isUserNameUsed(String userName){

        String lookingFor = userName;
        String searchUserName = "SELECT * FROM " + TABLE_NAME_ACCOUNTS + " WHERE " + COLUMN_USER_NAME  + " = '" + lookingFor + "';";
        db =  this.getReadableDatabase();
        Cursor  cursor = db.rawQuery(searchUserName, null);
        if(cursor.getCount() > 0){
            return true;
        }else{
            return false;
        }
    }


    private void addQuestions(){
        Question questionOne = new Question("How may moons does the planet Mars have?", "Two", "One", "Three", "Two");
        this.addQuestion(questionOne);

        Question questionTwo = new Question("Which astronomer proposed that the planets orbit the sun, and was later proved right?, ",
                "Copernicus", "Newton", "Copernicus", "Kappler");
        this.addQuestion(questionTwo);

        Question questionThree =  new Question("Which is the only planet in our solar system to rotate clockwise?","Venus",
                "Venus", "Saturn", "Mercury");
        this.addQuestion(questionThree);

        Question questionFour =  new Question("How many stars make up Orion’s Belt?", "Three", "Six", "Three", "Eight");
        this.addQuestion(questionFour);

        Question questionFive =  new Question("The Asteroid Belt lies between the orbits of which two planets in our solar system?",
                "Mars and Jupiter", "Mars and Jupiter", "Mercury and Venus", "Uranus and Saturn");
        this.addQuestion(questionFive);

        Question questionSix =  new Question("Olympus Mons is the highest mountain on which planet in our solar system?",
                "Mars","Mercury", "Venus", "Mars");
        this.addQuestion(questionSix);

        Question questionSeven =  new Question("Which US President’s name can be found on the Apollo 11 moon plaque?",
                "Richard Nixon","John Fitzgerald Kennedy","Lyndon Baines Johnson", "Richard Nixon");
        this.addQuestion(questionSeven);

        Question questionEight =  new Question("Titania, Miranda and Bianca are all moons of which planet in our solar system?",
                "Uranus", "Mars", "Venus","Uranus");
        this.addQuestion(questionEight);

        Question questionNine =  new Question("Which is the fastest rotating planet in our solar system?", "Jupiter",
                "Saturn", "Jupiter", "Venus");
        this.addQuestion(questionNine);

        Question questionTen =  new Question("Which planet in our solar system is known is ‘The Morning Star’ when it appears in the eastern sky before sunrise?",
                "Venus", "Mars", "Mercury", "Venus");
        this.addQuestion(questionTen);



    }

    public void addQuestion(Question quest) {
        ContentValues values = new ContentValues();
        values.put(COLUMN_QUESTION, quest.getQuestion());
        values.put(COLUMN_ANSWER, quest.getAnswer());
        values.put(COLUMN_OPTION_ONE, quest.getOptionOne());
        values.put(COLUMN_OPTION_TWO, quest.getOptionTow());
        values.put(COLUMN_OPTION_THREE, quest.getOptionThree());
        db.insert(TABLE_NAME_QUESTIONS, null, values);
        Log.i("QUESTION ADDED", "QUESTION ADDET TO QUESTIONS DATABASE");
    }

    public List<Question> getAllQuestions() {
        List<Question> quesList = new ArrayList<Question>();

        String selectQuery = "SELECT  * FROM " + TABLE_NAME_QUESTIONS;
        db=this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        if (cursor.moveToFirst()) {
            do {
                Question quest = new Question();
                quest.setId(cursor.getInt(0));
                quest.setQuestion(cursor.getString(1));
                quest.setAnswer(cursor.getString(2));
                quest.setOptionOne(cursor.getString(3));
                quest.setOptionTow(cursor.getString(4));
                quest.setOptionThree(cursor.getString(5));
                quesList.add(quest);
            } while (cursor.moveToNext());
        }
// return quest list
        return quesList;
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        String dropTableAccounts = "DROP TABLE IF EXISTS " + TABLE_NAME_ACCOUNTS;
        db.execSQL(dropTableAccounts);
        String dropTableQuestions = "DROP TABLE IF EXISTS " + TABLE_NAME_QUESTIONS;
        db.execSQL(dropTableQuestions);
        this.onCreate(db);
    }



}
