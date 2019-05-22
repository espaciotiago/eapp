package com.ufo.mobile.eapp;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.AsyncTask;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import com.ufo.mobile.eapp.Dialogs.CreateAreaCallback;
import com.ufo.mobile.eapp.Dialogs.CreateAreaDialog;
import com.ufo.mobile.eapp.Dialogs.CreateItemCallback;
import com.ufo.mobile.eapp.Dialogs.CreateItemDialog;
import com.ufo.mobile.eapp.Dialogs.CreateSectionCallback;
import com.ufo.mobile.eapp.Dialogs.CreateSectionDialog;
import com.ufo.mobile.eapp.Dialogs.CreateUserCallback;
import com.ufo.mobile.eapp.Dialogs.CreateUserDialog;

import java.io.IOException;
import java.util.List;

import ModelManager.Area;
import ModelManager.DaoSession;
import ModelManager.Item;
import ModelManager.Section;
import ModelManager.User;
import Utils.Constants;

import static com.ufo.mobile.eapp.MainActivity.RESULT_LOAD_IMG_ITEM;
import static com.ufo.mobile.eapp.MainActivity.RESULT_LOAD_IMG_USER;

public class ConfigurationActivity extends AppCompatActivity {

    //Logic items
    private DaoSession daoSession;
    // UI Elements
    private View viewAddItem,viewAddUser,viewAddArea,viewAddSection,viewLogout;
    private CreateItemDialog createItemDialog;
    private CreateUserDialog createUserDialog;
    private CreateAreaDialog createAreaDialog;
    private CreateSectionDialog createSectionDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_configuration);
        //Set back button on action bar
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        daoSession = ((DaoApp)getApplication()).getDaoSession();

        viewAddArea = (View) findViewById(R.id.view_add_area);
        viewAddUser = (View) findViewById(R.id.view_add_user);
        viewAddItem = (View) findViewById(R.id.view_add_item);
        viewAddSection = (View) findViewById(R.id.view_add_section);
        viewLogout = (View) findViewById(R.id.view_logout);

        //Actions
        viewAddSection.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                createSectionDialog = new CreateSectionDialog(ConfigurationActivity.this, new CreateSectionCallback() {
                    @Override
                    public void createSectionCallback(Section section) {
                        Section.insertSection(section,ConfigurationActivity.this, getApplication(),true);
                        if(createSectionDialog != null && createSectionDialog.isShowing()){
                            createSectionDialog.dismiss();
                        }
                    }
                });
                createSectionDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                createSectionDialog.setContentView(R.layout.dialog_create_section);
                createSectionDialog.setCanceledOnTouchOutside(false);
                createSectionDialog.getWindow().setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.MATCH_PARENT);
                createSectionDialog.show();
            }
        });
        viewAddArea.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                List<Section> sections = daoSession.getSectionDao().loadAll();
                createAreaDialog = new CreateAreaDialog(sections, ConfigurationActivity.this, new CreateAreaCallback() {
                    @Override
                    public void createAreaCallback(Area area, Section section) {

                        Long idArea = daoSession.getAreaDao().insert(area);
                        if(section.getAreas() == null) {
                            section.setAreas(Constants.appendIdToString("",idArea));
                        }else{
                            section.setAreas(Constants.appendIdToString(section.getAreas(),idArea));
                        }
                        daoSession.getSectionDao().update(section);

                        if(createAreaDialog != null && createAreaDialog.isShowing()){
                            createAreaDialog.dismiss();
                        }
                    }
                });
                createAreaDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                createAreaDialog.setContentView(R.layout.dialog_create_area);
                createAreaDialog.setCanceledOnTouchOutside(false);
                createAreaDialog.getWindow().setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.MATCH_PARENT);
                createAreaDialog.show();
            }
        });
        viewAddUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                createUserDialog = new CreateUserDialog(getApplication(),ConfigurationActivity.this, new CreateUserCallback() {
                    @Override
                    public void createUserCallback(User user) {
                        daoSession.getUserDao().insert(user);
                        if(createUserDialog != null && createUserDialog.isShowing()){
                            createUserDialog.dismiss();
                        }
                    }
                });
                createUserDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                createUserDialog.setContentView(R.layout.dialog_create_user);
                createUserDialog.setCanceledOnTouchOutside(false);
                createUserDialog.getWindow().setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.MATCH_PARENT);
                createUserDialog.show();
            }
        });
        viewAddItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                createItemDialog = new CreateItemDialog(ConfigurationActivity.this,new CreateItemCallback() {
                    @Override
                    public void createItemCallback(Item item) {
                        daoSession.getItemDao().insert(item);
                        if(createItemDialog != null && createItemDialog.isShowing()){
                            createItemDialog.dismiss();
                        }
                    }
                });
                createItemDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                createItemDialog.setContentView(R.layout.dialog_create_item);
                createItemDialog.setCanceledOnTouchOutside(false);
                createItemDialog.getWindow().setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.MATCH_PARENT);
                createItemDialog.show();
            }
        });
        viewLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                daoSession.getUserSessionDao().deleteAll();
                Intent goToLogin = new Intent(ConfigurationActivity.this,LoginActivity.class);
                goToLogin.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK|Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(goToLogin);
                finish();
            }
        });
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode == Activity.RESULT_OK)
            switch (requestCode){
                case RESULT_LOAD_IMG_ITEM:
                    Uri selectedImage = data.getData();
                    try {
                        Bitmap bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), selectedImage);
                        createItemDialog.setImageBitmap(bitmap);
                    } catch (IOException e) {
                        Log.e("TAG", "Some exception " + e);
                    }
                    break;
                case RESULT_LOAD_IMG_USER:
                    Uri selectedImageUser = data.getData();
                    try {
                        Bitmap bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), selectedImageUser);
                        createUserDialog.setImageBitmap(bitmap);
                    } catch (IOException e) {
                        Log.e("TAG", "Some exception " + e);
                    }
                    break;
            }
    }
}
