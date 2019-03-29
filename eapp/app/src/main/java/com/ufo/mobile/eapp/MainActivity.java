package com.ufo.mobile.eapp;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

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

        daoSession = ((DaoApp)getApplication()).getDaoSession();
        //TODO Test login
        List<UserSession> sessions = daoSession.getUserSessionDao().loadAll();
        if(sessions.isEmpty()) {

            UserSession userSession = new UserSession();
            userSession.setName("Santiago Moreno");
            userSession.setUsername("smoreno");
            userSession.setPassword("123");
            ((DaoApp) getApplication()).getDaoSession().getUserSessionDao().insert(userSession);
        }else{
            UserSession userSession = sessions.get(0);
            Log.e("USER",userSession.getName());
        }
    }
}
