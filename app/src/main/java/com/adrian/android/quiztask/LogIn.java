package com.adrian.android.quiztask;

import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class LogIn extends AppCompatActivity implements View.OnClickListener {
    private static final String GAME_MODE_KEY = "gameMode";
    private static final String USER_NAME_KEY = "userName";
    private static final String EMAIL_KEY = "email";
    private static final String NAME_KEY = "name";
    private static final String PASSWORD_KEY = "password";
    private SharedPreferences sharedPref;
    private  DatabaseHelper dbHelper;
    private List<String> mUserData;
    private Button mLogInBtn;
    private EditText mUserNameET ;
    private EditText mPasswordET;
    private TextView mRegistrationLink, incorrectUserNameTV, incorrectPasswordTV;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_log_in);

        sharedPref = PreferenceManager.getDefaultSharedPreferences(LogIn.this);
        mUserData = new ArrayList<>();
        dbHelper = new DatabaseHelper(this);

        mUserNameET = (EditText) findViewById(R.id.userNameLoginEditText);
        mPasswordET = (EditText) findViewById(R.id.passLoginEditText);

        mLogInBtn = (Button) findViewById(R.id.btn_logIn);
        mLogInBtn.setOnClickListener(this);

        mRegistrationLink = (TextView) findViewById(R.id.registrationLink_textView);
        mRegistrationLink.setOnClickListener(this);

        incorrectPasswordTV = (TextView) findViewById(R.id.incorrectPasswordTextView);
        incorrectPasswordTV.setVisibility(View.GONE);

        incorrectUserNameTV =  (TextView) findViewById(R.id.incorrectUserNameTextView);
        incorrectUserNameTV.setVisibility(View.GONE);



    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btn_logIn:
                String userName = mUserNameET.getText().toString();
                String password = mPasswordET.getText().toString();
                String confirmedPassword;
                 mUserData= dbHelper.searchPassword(userName);
                if (mUserData.size() == 0){
                    confirmedPassword = "not found";
                }else{
                    confirmedPassword = mUserData.get(1);
                }


                if (confirmedPassword.equals(password)){
                    SharedPreferences.Editor spEditor = sharedPref.edit();
                    String userN =  mUserNameET.getText().toString();


                    spEditor.putString(USER_NAME_KEY, mUserNameET.getText().toString());
                    spEditor.putString(PASSWORD_KEY, mPasswordET.getText().toString());
                    spEditor.putString(EMAIL_KEY, mUserData.get(2));
                    spEditor.putString(NAME_KEY, mUserData.get(3));
                    spEditor.putString(GAME_MODE_KEY, "Binary");

                    spEditor.commit();
                    startActivity(new Intent(this, MainActivity.class));
                }else{
                    incorrectUserNameTV.setText(R.string.incorrct_logIn_fileds);
                    incorrectUserNameTV.setVisibility(View.VISIBLE);

                    incorrectPasswordTV.setText(R.string.incorrct_logIn_fileds);
                    incorrectPasswordTV.setVisibility(View.VISIBLE);
                }


                break;
            case R.id.registrationLink_textView:
                startActivity( new Intent(this, Register.class));
                break;
        }
    }
}
