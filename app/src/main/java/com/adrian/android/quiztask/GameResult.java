package com.adrian.android.quiztask;

import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.ButtonBarLayout;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class GameResult extends AppCompatActivity implements View.OnClickListener {
    private static final String USER_NAME_KEY ="userName";
    private static final String GAME_MODE_KEY ="gameMode";
    private static final String USER_SCORE_KEY ="score";
    Button mRestartGameBtn, mGoToProfileBtn;
    TextView mNameTextView, mScoreTextView;
    SharedPreferences mSharedPreferences;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_result);


        mSharedPreferences = PreferenceManager.getDefaultSharedPreferences(getBaseContext());

        mRestartGameBtn = (Button) findViewById(R.id.restartButton);
        mRestartGameBtn.setOnClickListener(this);

        mGoToProfileBtn = (Button) findViewById(R.id.profileButton);
        mGoToProfileBtn.setOnClickListener(this);

        mNameTextView = (TextView) findViewById(R.id.gameResultNameTV);
        mNameTextView.setText(mSharedPreferences.getString(USER_NAME_KEY, null) + ",");

        mScoreTextView =(TextView) findViewById(R.id.gameResultScoreTV);
        mScoreTextView.setText("Your Score is: " + mSharedPreferences.getString(USER_SCORE_KEY, null + "!"));





    }

    @Override
    public void onClick(View v) {
        switch(v.getId()){
            case R.id.restartButton:
                if(mSharedPreferences.getString(GAME_MODE_KEY, null) == "multipleChoice "){
                    startActivity(new Intent(this, MultipleChoice.class));

                }else if(mSharedPreferences.getString(GAME_MODE_KEY, null) == "binaryMode"){
                    startActivity(new Intent(this, MainActivity.class));
                }else{
                    startActivity(new Intent(this, MainActivity.class));
                }
                break;
            case R.id.profileButton:
                startActivity(new Intent(this, UserProfile.class));
                break;
        }
    }
}
