package com.ufo.mobile.eapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.crashlytics.android.Crashlytics;

import org.greenrobot.greendao.query.QueryBuilder;

import java.util.List;

import ModelManager.DaoSession;
import ModelManager.User;
import ModelManager.UserDao;
import ModelManager.UserSession;
import Utils.Constants;

public class LoginActivity extends AppCompatActivity {

    //Logic elements
    private DaoSession daoSession;

    //UI Elements
    private EditText editUsername;
    private EditText editPassword;
    private Button btnLogin;
    private TextView txtSignup;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        // Logs  a user for crashlytics
        logUser();

        //Close keyboard on touch screen
        Constants.closeKeyboardOnTouch(R.id.scroll,this);
        //Get the database session
        daoSession = ((DaoApp)getApplication()).getDaoSession();

        //Verify Sessions
        List<UserSession> sessions = daoSession.getUserSessionDao().loadAll();
        if(!sessions.isEmpty()) {
            Intent i = new Intent(this,MainActivity.class);
            startActivity(i);
            finish();
        }

        //UI Elements
        editUsername = (EditText) findViewById(R.id.edit_username);
        editPassword = (EditText) findViewById(R.id.edit_password);
        btnLogin = (Button) findViewById(R.id.btn_login);
        txtSignup = (TextView) findViewById(R.id.txt_signup);

        //Actions
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String username = editUsername.getText().toString();
                String password = editPassword.getText().toString();
                login(username,password);
            }
        });

        txtSignup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(LoginActivity.this,SignupActivity.class);
                startActivity(i);
            }
        });
    }

    /**
     * Do the login verification
     * @param username
     * @param password
     */
    private void login(String username, String password){

        if(username != null && !username.equals("") && password != null && !password.equals("")){
            //Verify database existence
            QueryBuilder<User> queryUser = (QueryBuilder) daoSession.getUserDao().queryBuilder();
            queryUser.where(UserDao.Properties.Username.eq(username));
            queryUser.and(UserDao.Properties.Password.eq(password),UserDao.Properties.Type.eq(User.ADMIN_USER));
            User user = queryUser.unique();

            if (user != null){
                //Saves the session
                UserSession session = new UserSession();
                session.setIdentification(user.getIdentification());
                session.setName(user.getName());
                session.setUsername(user.getUsername());
                session.setPassword(user.getPassword());
                daoSession.getUserSessionDao().insert(session);

                Intent i = new Intent(this,MainActivity.class);
                startActivity(i);
                finish();
            }else{
                Toast.makeText(this,getResources().getString(R.string.authentication_failed),Toast.LENGTH_LONG).show();
            }

        }else{
            Toast.makeText(this,getResources().getString(R.string.fill_the_fields),Toast.LENGTH_LONG).show();
        }
    }


    private void logUser() {
        // You can call any combination of these three methods
        Crashlytics.setUserEmail("msantim@hotmail.com");
        Crashlytics.setUserIdentifier("12345");
        Crashlytics.setUserName("Tiago");
    }

}
