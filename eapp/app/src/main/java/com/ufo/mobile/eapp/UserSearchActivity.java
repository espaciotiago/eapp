package com.ufo.mobile.eapp;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.TextView;

import com.ufo.mobile.eapp.Adapters.AreaAdapter;
import com.ufo.mobile.eapp.Adapters.AreaSelectedCallback;
import com.ufo.mobile.eapp.Adapters.UserAdapter;
import com.ufo.mobile.eapp.Adapters.UserSelectedCallback;
import com.ufo.mobile.eapp.Dialogs.CreateUserCallback;
import com.ufo.mobile.eapp.Dialogs.CreateUserDialog;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import ModelManager.Area;
import ModelManager.DaoSession;
import ModelManager.User;
import ModelManager.UserDao;
import Utils.Constants;

import static com.ufo.mobile.eapp.MainActivity.RESULT_LOAD_IMG_USER;
import static com.ufo.mobile.eapp.MainActivity.RESULT_LOAD_IMG_USER_CAMARA;
import static com.ufo.mobile.eapp.OrderDetailActivity.REQUEST_SIGNATURE;

public class UserSearchActivity extends AppCompatActivity {

    private User owner;
    private Area areaOwner;
    private List<User> users = new ArrayList<>();
    private List<Area> areas = new ArrayList<>();
    private DaoSession daoSession;
    private boolean isForArea;

    //UI Elements
    private RecyclerView recyclerUsers;
    private RecyclerView.LayoutManager layoutManager;
    private UserAdapter userAdapter;
    private AreaAdapter areaAdapter;
    private EditText editSearchUser;
    private TextView txtAddNewUser;
    private CreateUserDialog createUserDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_search);
        Constants.setLayoutOrientation(this);
        //Set back button on action bar
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        //Close keyboard on touch screen
        Constants.closeKeyboardOnTouch(R.id.view_users,this);

        isForArea = getIntent().getBooleanExtra("isForArea",false);
        //Get the database session
        daoSession = ((DaoApp)getApplication()).getDaoSession();
        users = daoSession.getUserDao().queryBuilder().where(UserDao.Properties.Type.eq(User.REGULAR_USER)).list();
        areas = daoSession.getAreaDao().loadAll();

        //UI Elements
        editSearchUser = findViewById(R.id.edit_search);
        txtAddNewUser = findViewById(R.id.txt_add_new_user);
        layoutManager = new LinearLayoutManager(this);
        recyclerUsers = findViewById(R.id.recycler_users);
        recyclerUsers.setLayoutManager(layoutManager);

        //Set UI
        if(isForArea) {
            setRecyclerForAreas(areas);
        }else{
            setRecycler(users);
        }

        //Actions
        txtAddNewUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                createUserDialog = new CreateUserDialog(getApplication(),UserSearchActivity.this, new CreateUserCallback() {
                    @Override
                    public void createUserCallback(User user,boolean isNew) {
                        if(isNew) {
                            daoSession.getUserDao().insert(user);
                            users.add(user);
                        }else{
                            daoSession.getUserDao().update(user);
                            users = daoSession.getUserDao().queryBuilder().where(UserDao.Properties.Type.eq(User.REGULAR_USER)).list();
                        }
                        if(createUserDialog != null && createUserDialog.isShowing()){
                            createUserDialog.dismiss();
                        }
                        setRecycler(users);
                    }
                });
                createUserDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                createUserDialog.setContentView(R.layout.dialog_create_user);
                createUserDialog.setCanceledOnTouchOutside(false);
                createUserDialog.getWindow().setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.MATCH_PARENT);
                createUserDialog.show();
            }
        });
        TextWatcher searcher = new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if(isForArea){
                    List<Area> areasSearched = searchArea(s.toString());
                    setRecyclerForAreas(areasSearched);
                }else {
                    List<User> usersSearched = searchUsers(s.toString());
                    setRecycler(usersSearched);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        };
        editSearchUser.addTextChangedListener(searcher);
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
                case RESULT_LOAD_IMG_USER:
                    Uri selectedImage = data.getData();
                    try {
                        Bitmap bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), selectedImage);
                        createUserDialog.setImageBitmap(bitmap);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    break;
                case RESULT_LOAD_IMG_USER_CAMARA:
                    try {
                        Bitmap bitmap = (Bitmap) data.getExtras().get("data");
                        createUserDialog.setImageBitmap(bitmap);
                    } catch (Exception e) {
                        Log.e("RESULT_IMG_USER_CAMARA", e.getMessage());
                    }
                    break;
            }
    }

    /**
     * --------------------------------------------------------------------
     * @param users
     */
    private void setRecycler(List<User> users){
        //Set adapter
        userAdapter = new UserAdapter(users, new UserSelectedCallback() {
            @Override
            public void userSelectedCallback(User user) {
                owner = user;
                Intent intent = new Intent();
                intent.putExtra("user", owner.getId());
                setResult(RESULT_OK, intent);
                finish();
            }
        });
        recyclerUsers.setAdapter(userAdapter);
    }
    /**
     * --------------------------------------------------------------------
     * @param areas
     */
    private void setRecyclerForAreas(List<Area> areas){
        //Set adapter
        areaAdapter = new AreaAdapter(areas, new AreaSelectedCallback() {
            @Override
            public void AreaSelectedCallback(Area area) {
                areaOwner = area;
                Intent intent = new Intent();
                intent.putExtra("area", areaOwner.getId());
                setResult(RESULT_OK, intent);
                finish();
            }
        });
        recyclerUsers.setAdapter(areaAdapter);
        txtAddNewUser.setVisibility(View.GONE);
        editSearchUser.setHint(getResources().getText(R.string.search_area));
    }

    /**
     * --------------------------------------------------------------------
     * @param search
     * @return
     */
    private List<User> searchUsers(String search){
        ArrayList<User> usersList = new ArrayList<>();

        for(int i = 0; i < users.size(); i ++){
            User u = users.get(i);
            if(u.getName().toLowerCase().contains(search.toLowerCase()) ||
                    u.getIdentification().toLowerCase().contains(search.toLowerCase())){
                usersList.add(u);
            }
        }
        return usersList;
    }

    /**
     * --------------------------------------------------------------------
     * @param search
     * @return
     */
    private List<Area> searchArea(String search){
        ArrayList<Area> areaList = new ArrayList<>();

        for(int i = 0; i < areas.size(); i ++){
            Area u = areas.get(i);
            if(u.getName().toLowerCase().contains(search.toLowerCase())){
                areaList.add(u);
            }
        }
        return areaList;
    }
}
