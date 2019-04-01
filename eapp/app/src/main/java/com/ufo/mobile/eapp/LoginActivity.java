package com.ufo.mobile.eapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;

import java.util.List;

import ModelManager.DaoSession;
import ModelManager.UserSession;
import Utils.Constants;

public class LoginActivity extends AppCompatActivity {

    //Logic elements
    private DaoSession daoSession;

    //UI Elements


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        //Close keyboard on touch screen
        Constants.closeKeyboardOnTouch(R.id.login_activity,this);
        //Get the database session
        daoSession = ((DaoApp)getApplication()).getDaoSession();

        //TODO Remove after test
        daoSession.getUserSessionDao().deleteAll();

        //Verify Sessions
        List<UserSession> sessions = daoSession.getUserSessionDao().loadAll();
        if(!sessions.isEmpty()) {
            Intent i = new Intent(this,MainActivity.class);
            startActivity(i);
            finish();
        }

        //UI Elements

        //Actions

    }
}
