package com.ufo.mobile.eapp;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;

import java.util.List;

import ModelManager.DaoSession;
import ModelManager.User;
import ModelManager.UserSession;

public class MainActivity extends AppCompatActivity {

    private DaoSession daoSession;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Get the database session
        daoSession = ((DaoApp)getApplication()).getDaoSession();

        //UI Elemnts


        //Set the UI
        getSupportActionBar().setTitle(R.string.inventory);
    }
}
