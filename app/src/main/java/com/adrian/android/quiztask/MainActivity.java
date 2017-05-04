package com.adrian.android.quiztask;

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
import java.util.Random;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private static final String GAME_MODE_KEY = "gameMode";
    private static final String USER_NAME_KEY = "userName";
    private static final String LOG_KEY = "com.adrian.android.binary";

    private TextView mQuetion, mPropose;
    private Button mTrue, mFalse;
    private int sQuestionQursor =0;
    private int questionCount;
    private int mUserScore = 0;
    private int randIntValue =0;
    private String mAnswer;
    private Random rand;
    private DatabaseHelper sDbHelper;
    private List<Question> sQuestins;
    private String currentPoposalAnswer;

    SharedPreferences mSharedPreferences;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mSharedPreferences = PreferenceManager.getDefaultSharedPreferences(getBaseContext());
        sDbHelper = new DatabaseHelper(this);



        if (mSharedPreferences.getString(USER_NAME_KEY, null) == null) {
            startActivity(new Intent(this, LogIn.class));
        } else {
            if (mSharedPreferences.getString(GAME_MODE_KEY, null).equals("Multiple")) {
                startActivity(new Intent(this, MultipleChoice.class));
            }
        }

         
        BottomNavigationView mMainBottomNavigationView = (BottomNavigationView) findViewById(R.id.bottom_navigation_main);
        mMainBottomNavigationView.setOnNavigationItemSelectedListener( new BottomNavigationView.OnNavigationItemSelectedListener(){
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()){
                    case R.id.settings_main:
                       goToSettingsAcctivity();
                        break;
                    case R.id.profile_main:
                        goToUserProfileAcctivity();

                }
                return true;
            }
        });



        sQuestins = sDbHelper.getAllQuestions();
        questionCount = sQuestins.size() -1;
        rand =  new Random();

        mQuetion = (TextView) findViewById(R.id.questionTextView);
        mPropose = (TextView) findViewById(R.id.proposalAnswer);

        mTrue = (Button)findViewById(R.id.true_button);
        mTrue.setOnClickListener(this);

        mFalse = (Button) findViewById(R.id.false_button);
        mFalse.setOnClickListener(this);




        setNextQuestion();
    }
    @Override
    public void onClick (View v){
        switch (v.getId()) {
            case R.id.true_button:
                checkAnswer(true);
                moveQuestionQursor();
                setNextQuestion();
                break;

            case R.id.false_button:
                checkAnswer(false);
                moveQuestionQursor();
                setNextQuestion();
                break;

        }
    }


    private void setNextQuestion(){
        randIntValue = rand.nextInt(3);
        Question currentQuestion = sQuestins.get(sQuestionQursor);
        mAnswer= currentQuestion.getAnswer();
        switch (randIntValue) {
            case 0:
                mPropose.setText(currentQuestion.getOptionOne());
                currentPoposalAnswer = currentQuestion.getOptionOne();
                break;
            case 1:
                mPropose.setText(currentQuestion.getOptionTow() );
                currentPoposalAnswer =currentQuestion.getOptionTow();
                break;
            case 2:
                mPropose.setText(currentQuestion.getOptionThree() );
                currentPoposalAnswer = currentQuestion.getOptionThree();
                break;
        }
        mQuetion.setText(currentQuestion.getQuestion());
    }

    public void checkAnswer(Boolean userGuess){
        String judge;

        if(currentPoposalAnswer.equalsIgnoreCase(mAnswer) && userGuess == true){
            mUserScore++;
            judge =  getString(R.string.correct_answer) + mAnswer;

        }else if(!(mPropose.getText().toString().equals(mAnswer)) && (userGuess == false)){
            mUserScore++;
            judge =  getString(R.string.correct_answer) + mAnswer;
        }else{
            judge = getString(R.string.wrong_answer) + mAnswer;
        }
        Toast.makeText(this, judge, Toast.LENGTH_LONG).show();
    }

    private void moveQuestionQursor(){

        if(sQuestionQursor < questionCount) {
            sQuestionQursor++;
        }else{
            gameEnd();
        }
    }


    private void gameEnd(){
        SharedPreferences.Editor spEditor = mSharedPreferences.edit();
        String userScore =  mUserScore + "";
        spEditor.putString("score", userScore).commit();
        mUserScore=0;
        sQuestionQursor=0;
        startActivity(new Intent(this, GameResult.class).putExtra("score", userScore));
    }


private void goToSettingsAcctivity(){
    startActivity(new Intent(this, Settings.class));
}

private void goToUserProfileAcctivity(){
    startActivity(new Intent(this, UserProfile.class));
}



}
