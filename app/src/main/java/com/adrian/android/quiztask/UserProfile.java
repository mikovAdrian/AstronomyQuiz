package com.adrian.android.quiztask;

import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.animation.GridLayoutAnimationController;
import android.widget.Button;
import android.widget.TextView;

public class UserProfile extends AppCompatActivity implements View.OnClickListener{

    private static final String SWITCH_STATE = "switchState";

    private TextView mName, mUserName, mEmail;
    private Button mLogOut, mBackBtn;
    SharedPreferences mSharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_profile);

        mSharedPreferences = PreferenceManager.getDefaultSharedPreferences(getBaseContext());

        mName = (TextView) findViewById(R.id.name_logout_textView);
        mName.setText(mSharedPreferences.getString("name", null));

        mUserName = (TextView) findViewById(R.id.userName_logout_textView);
        mUserName.setText(mSharedPreferences.getString("userName", null));


        mEmail = (TextView) findViewById(R.id.email_logout_textView);
        mEmail.setText(mSharedPreferences.getString("email", null));

        mLogOut = (Button) findViewById(R.id.logout_button);
        mLogOut.setOnClickListener(this);

        mBackBtn = (Button) findViewById(R.id.back_to_result_button);
        mBackBtn.setOnClickListener(this);


    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.logout_button:
                SharedPreferences.Editor spEditor = mSharedPreferences.edit();
                spEditor.putString("userName", null);
                spEditor.putString("password",null);
                spEditor.putBoolean(SWITCH_STATE, false);
                spEditor.commit();

                startActivity(new Intent(this, LogIn.class));
                break;
            case R.id.back_to_result_button:
                startActivity(new Intent(this, MainActivity.class));
        }

    }
}
