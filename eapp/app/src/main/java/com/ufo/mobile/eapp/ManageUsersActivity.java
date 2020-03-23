package com.ufo.mobile.eapp;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.ufo.mobile.eapp.Adapters.UserAdapter;
import com.ufo.mobile.eapp.Adapters.UserSelectedCallback;

import java.util.ArrayList;
import java.util.List;

import ModelManager.DaoSession;
import ModelManager.User;
import ModelManager.UserDao;
import Utils.Constants;

import static com.ufo.mobile.eapp.OrderDetailActivity.REQUEST_SIGNATURE;

public class ManageUsersActivity extends AppCompatActivity {

    //Logic items
    private DaoSession daoSession;
    private List<User> users = new ArrayList<>();
    //UI Elements
    private RecyclerView recyclerUsers;
    private RecyclerView.LayoutManager layoutManager;
    private UserAdapter userAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manage_users);
        Constants.setLayoutOrientation(this);
        //Set back button on action bar
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        daoSession = ((DaoApp)getApplication()).getDaoSession();
        users = daoSession.getUserDao().queryBuilder().where(UserDao.Properties.Type.eq(User.REGULAR_USER)).list();

        layoutManager = new LinearLayoutManager(this);
        recyclerUsers = findViewById(R.id.users_recyclerview);
        recyclerUsers.setLayoutManager(layoutManager);
        setRecycler(users);

    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }


    private void setRecycler(List<User> usersList){
        //Set adapter
        userAdapter = new UserAdapter(usersList, new UserSelectedCallback() {
            @Override
            public void userSelectedCallback(User user) {
                final User u = user;
                new AlertDialog.Builder(ManageUsersActivity.this)
                        .setTitle("Eliminar usuario")
                        .setMessage("¿Esta seguro que desea eliminar a este usuario?")
                        .setIcon(android.R.drawable.ic_dialog_alert)
                        .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int whichButton) {
                                daoSession.getUserDao().delete(u);
                                users = daoSession.getUserDao().queryBuilder().where(UserDao.Properties.Type.eq(User.REGULAR_USER)).list();
                                setRecycler(users);
                            }})
                        .setNegativeButton(android.R.string.no, null).show();

            }
        }, true);
        recyclerUsers.setAdapter(userAdapter);
    }
}
