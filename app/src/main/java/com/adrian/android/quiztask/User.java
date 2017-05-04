package com.adrian.android.quiztask;

/**
 * Created by vanina on 26.04.17.
 */

public class User {
    String mName, mUserName, mPassword, mEmail;


    public User(String name, String userName, String password,String email) {
        this.mName = name;
        this.mUserName = userName;
        this.mPassword = password;
        this.mEmail = email;
    }



    public String getName() {
        return mName;
    }


    public void setName(String name) {
        mName = name;
    }

    public String getUserName() {
        return mUserName;
    }

    public void setUserName(String userName) {
        mUserName = userName;
    }

    public String getPassword() {
        return mPassword;
    }

    public void setPassword(String password) {
        mPassword = password;
    }

    public String getEmail() {
        return mEmail;
    }

    public void setEmail(String email) {
        mEmail = email;
    }
}
