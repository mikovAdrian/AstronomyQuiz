package com.adrian.android.quiztask;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Register extends AppCompatActivity implements View.OnClickListener {

    public static final Pattern VALID_EMAIL_ADDRESS_REGEX =
            Pattern.compile("^[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,6}$", Pattern.CASE_INSENSITIVE);

    private DatabaseHelper dbHelper;

    private Button mRegister;
    private EditText mNameET, mEmailET, mUserNameET, mPasswordET, mConfPasswordET;
    private TextView mIncorectName, mIncorectEmail, mIncorectUserName,mIncorectPassword, mIncorectConfirmPassword;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        dbHelper =  new DatabaseHelper(this);

        mNameET = (EditText) findViewById(R.id.nameRegisterEditText);
        mEmailET = (EditText) findViewById(R.id.emailRegisterEditText);
        mUserNameET = (EditText) findViewById(R.id.userNameRegisterEditText);
        mPasswordET = (EditText) findViewById(R.id.passRegisterEditText);
        mConfPasswordET = (EditText) findViewById(R.id.passConfRegisterEditText);

        mRegister  = (Button) findViewById(R.id.register_button);
        mRegister.setOnClickListener(this);

        mIncorectName =  (TextView) findViewById(R.id.nameIncorectTextView);
        mIncorectName.setVisibility(View.GONE);

        mIncorectEmail =  (TextView) findViewById(R.id.emailIncorectTextView);
        mIncorectEmail.setVisibility(View.GONE);

        mIncorectUserName = (TextView) findViewById(R.id.userNamelIncorectTextView);
        mIncorectUserName.setVisibility(View.GONE);

        mIncorectPassword = (TextView) findViewById(R.id.passwordIncorectTextView);
        mIncorectPassword.setVisibility(View.GONE);

        mIncorectConfirmPassword = (TextView) findViewById(R.id.passwordConfirmlIncorectTextView);
        mIncorectConfirmPassword .setVisibility(View.GONE);


    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.register_button:
                mIncorectName.setVisibility(View.GONE);
                mIncorectEmail.setVisibility(View.GONE);
                mIncorectUserName.setVisibility(View.GONE);
                mIncorectPassword.setVisibility(View.GONE);
                mIncorectConfirmPassword .setVisibility(View.GONE);

                String name = mNameET.getText().toString();
                if(name.trim().length() == 0){
                    mIncorectName.setText(R.string.empty_name_field);
                    mIncorectName.setVisibility(View.VISIBLE);
                    break;
                }

                String userName =  mUserNameET.getText().toString();
                if(userName.trim().length() == 0){
                    mIncorectUserName.setText(R.string.empty_userName_field);
                    mIncorectUserName.setVisibility(View.VISIBLE);
                    break;
                }else if(dbHelper.isUserNameUsed(userName)){
                    mIncorectUserName.setText(R.string.used_userName);
                    mIncorectUserName.setVisibility(View.VISIBLE);
                    break;
                }

                String password = mPasswordET.getText().toString();
                if(password.trim().length() == 0){
                    mIncorectPassword.setText(R.string.empty_password);
                    mIncorectPassword.setVisibility(View.VISIBLE);
                    break;
                }
                String confPassword = mConfPasswordET.getText().toString();
                if(confPassword.trim().length() == 0){
                    mIncorectConfirmPassword.setText(R.string.empty_confPassword);
                    mIncorectConfirmPassword.setVisibility(View.VISIBLE);
                    break;
                }

                String email = mEmailET.getText().toString();
                if(email.trim().length() == 0){
                    mIncorectEmail.setText(R.string.empty_email);
                    mIncorectEmail.setVisibility(View.VISIBLE);
                    break;
                }else if(dbHelper.isEmailUsed(email)){
                    mIncorectEmail.setText(R.string.used_email);
                    mIncorectEmail.setVisibility(View.VISIBLE);
                    break;
                }
                if (!isValidEmail(email)){
                    mIncorectEmail.setText(R.string.invalide_email);
                    mIncorectEmail.setVisibility(View.VISIBLE);
                    break;
                }

                if (password.equals(confPassword)){
                    User newUser =  new User(name, userName, password, email);
                    dbHelper.createUserAccount(newUser);

                    startActivity(new Intent(this, LogIn.class));

                }else{
                    mIncorectPassword.setText(R.string.passwords_notMatches);
                    mIncorectPassword.setVisibility(View.VISIBLE);
                    mIncorectConfirmPassword.setText(R.string.passwords_notMatches);
                    mIncorectConfirmPassword.setVisibility(View.VISIBLE);
                }



                break;
        }
    }


    public static boolean isValidEmail(String emailStr) {
        Matcher matcher = VALID_EMAIL_ADDRESS_REGEX.matcher(emailStr);
        return matcher.find();
    }
}