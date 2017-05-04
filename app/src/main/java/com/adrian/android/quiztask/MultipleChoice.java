package com.adrian.android.quiztask;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

public class MultipleChoice extends AppCompatActivity implements View.OnClickListener {


    private static final String LOG_KEY = "com.adrian.android.quiztask.multipleChoice";
    private Button mOptionOneBtn, mObtionTwoBtn, mOptionThreeBtn;
    private TextView mQuestionTW;
    private  int sQuestionQursor =0;
    private int questionCount;
    private int mUserScore = 0;
    private DatabaseHelper sDbHelper;
    private List<Question> sQuestins;
    private String mAnswer, mUserGuess;

    SharedPreferences mSharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_multiple_choice);
        mSharedPreferences = PreferenceManager.getDefaultSharedPreferences(getBaseContext());


        BottomNavigationView mMultiBottomNavigationView = (BottomNavigationView) findViewById(R.id.bottom_navigation_multi);
        mMultiBottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener(){
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()){
                    case R.id.settings_multi:
                        goToSettingsActivity();
                        break;
                    case R.id.profile_multi:
                        goToUserProfileActivity();
                }
                return true;
            }
        });

        sDbHelper = new DatabaseHelper(this);
        sQuestins = sDbHelper.getAllQuestions();
        questionCount = sQuestins.size() -1;

        mOptionOneBtn = (Button) findViewById(R.id.optionOneButton);
        mOptionOneBtn.setOnClickListener(this);


        mObtionTwoBtn = (Button) findViewById(R.id.optionTwoButton);
        mObtionTwoBtn.setOnClickListener(this);

        mOptionThreeBtn = (Button) findViewById(R.id.optionThreeButton);
        mOptionThreeBtn.setOnClickListener(this);

        mQuestionTW = (TextView) findViewById(R.id.questionTextView);
        setNextQuestion();

    }



    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.optionOneButton:
                mUserGuess = mOptionOneBtn.getText().toString();

                checkAnswer(mUserGuess);
                moveQuestionQursor();
                setNextQuestion();
                break;
            case R.id.optionTwoButton:
                mUserGuess = mObtionTwoBtn.getText().toString();
                checkAnswer(mUserGuess);
                moveQuestionQursor();
                setNextQuestion();
                break;
            case R.id.optionThreeButton:
                mUserGuess = mOptionThreeBtn.getText().toString();

                checkAnswer(mUserGuess);
                moveQuestionQursor();
                setNextQuestion();
                break;


        }


    }


    private void moveQuestionQursor(){

        if(sQuestionQursor < questionCount) {
            sQuestionQursor++;
        }else{
            gameEnd();
        }
    }

    private void setNextQuestion(){

        Question currentQuestion = sQuestins.get(sQuestionQursor);
        mAnswer= currentQuestion.getAnswer();

        mOptionOneBtn.setText(currentQuestion.getOptionOne());
        mObtionTwoBtn.setText(currentQuestion.getOptionTow());
        mOptionThreeBtn.setText(currentQuestion.getOptionThree());

        mQuestionTW.setText(currentQuestion.getQuestion());

    }

    @SuppressLint("LongLogTag")
    public void checkAnswer(String userGuess){
        String judge;
        Log.i(LOG_KEY, "UserGuess: " + userGuess);
        Log.i(LOG_KEY, "Correct answer: " + mAnswer);

        if(userGuess.equals(mAnswer)){
            mUserScore++;
            judge =  getString(R.string.correct_answer) + mAnswer;

        }else{
            judge = getString(R.string.wrong_answer) + mAnswer;
        }
        Toast.makeText(this, judge, Toast.LENGTH_SHORT).show();

    }

    @SuppressLint("LongLogTag")
    private void gameEnd(){
        SharedPreferences.Editor spEditor = mSharedPreferences.edit();
        String userScore =  mUserScore + "";
        spEditor.putString("score", userScore).commit();
        mUserScore=0;
        sQuestionQursor=0;
        startActivity(new Intent(this, GameResult.class).putExtra("score", userScore));
    }



    private void goToSettingsActivity(){
        startActivity(new Intent(this, Settings.class));
    }

    private void goToUserProfileActivity(){
        startActivity(new Intent(this, UserProfile.class));
    }
}