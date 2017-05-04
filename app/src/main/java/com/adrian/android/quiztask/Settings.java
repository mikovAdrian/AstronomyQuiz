package com.adrian.android.quiztask;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.Switch;

public class Settings extends AppCompatActivity implements CompoundButton.OnCheckedChangeListener {

    private Switch mModeSwitcher;
    SharedPreferences mSharedPreferences;
    SharedPreferences.Editor spEditor;
    private static final String GAME_MODE = "gameMode";
    private static final String SWITCH_STATE = "switchState";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        BottomNavigationView mSettingsBottomNavigationView = (BottomNavigationView) findViewById(R.id.bottom_navigation_settings);
        mSettingsBottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener()
        {

            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()){
                    case R.id.home_settings:
                        goToMainActivity();
                        break;
                    case R.id.profile_settings:
                        goToUserProfile();
                }
                return true;
            }
        });

        mSharedPreferences = PreferenceManager.getDefaultSharedPreferences(Settings.this);
        spEditor = mSharedPreferences.edit();

        mModeSwitcher = (Switch) findViewById(R.id.multiModeSwitcher);
        mModeSwitcher.setChecked(mSharedPreferences.getBoolean(SWITCH_STATE, true));
        mModeSwitcher.setOnCheckedChangeListener(this);


    }

    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

        if(isChecked){
            spEditor.putString(GAME_MODE, "Multiple");
            spEditor.putBoolean(SWITCH_STATE, true);
            spEditor.commit();

           // startActivity(new Intent(this, MultipleChoice.class));
        }else{
            spEditor.putString(GAME_MODE, "Binary");
            spEditor.putBoolean(SWITCH_STATE, false);
            spEditor.commit();
           // startActivity(new Intent(this, MainActivity.class));

        }
    }
    public  void goToMainActivity(){
        startActivity(new Intent(this, MainActivity.class));
    }
    public void goToUserProfile(){
        startActivity(new Intent(this, UserProfile.class));
    }
}
