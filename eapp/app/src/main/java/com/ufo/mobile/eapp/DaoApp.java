package com.ufo.mobile.eapp;

import android.app.Application;

import org.greenrobot.greendao.database.Database;

import ModelManager.DaoMaster;
import ModelManager.DaoSession;
import ModelManager.DbOpenHelper;
import Utils.Constants;

public class DaoApp extends Application {

    private DaoSession daoSession;

    @Override
    public void onCreate() {
        super.onCreate();

        // regular SQLite database
        DaoMaster.DevOpenHelper helper = new DaoMaster.DevOpenHelper(this, Constants.DB_NAME);
        Database db = helper.getWritableDb();

        daoSession = new DaoMaster(db).newSession();
    }

    public DaoSession getDaoSession() {
        return daoSession;
    }

}
