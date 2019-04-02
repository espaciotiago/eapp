package com.ufo.mobile.eapp;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import ModelManager.DaoSession;
import ModelManager.User;
import Utils.Constants;

public class SignupActivity extends AppCompatActivity {

    //Logic elements
    private DaoSession daoSession;

    //UI Elements
    private EditText editIdentification,editName,editUsername,editPassword;
    private Button btnSignup;
    private TextView txtLogin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        //Close keyboard on touch screen
        Constants.closeKeyboardOnTouch(R.id.scroll,this);
        //Get the database session
        daoSession = ((DaoApp)getApplication()).getDaoSession();

        //UI Elements
        editIdentification = (EditText) findViewById(R.id.edit_identification);
        editName = (EditText) findViewById(R.id.edit_name);
        editUsername = (EditText) findViewById(R.id.edit_username);
        editPassword = (EditText) findViewById(R.id.edit_password);
        btnSignup = (Button) findViewById(R.id.btn_signup);
        txtLogin = (TextView) findViewById(R.id.txt_login);

        //Actions
        btnSignup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String identification = editIdentification.getText().toString();
                String name = editName.getText().toString();
                String username = editUsername.getText().toString();
                String password = editPassword.getText().toString();
                signup(identification,name,username,password);
            }
        });

        txtLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
    }

    /**
     * Creates a new user admin on database
     * @param identification
     * @param name
     * @param username
     * @param password
     */
    private void signup(String identification,String name, String username,String password){
        if(username != null && !username.equals("") && password != null && !password.equals("")
                && identification != null && !identification.equals("") && name != null && !name.equals("")){
            //Creates user on database
            User user = new User();
            user.setIdentification(identification);
            user.setName(name);
            user.setUsername(username);
            user.setPassword(password);
            user.setType(User.ADMIN_USER);
            daoSession.getUserDao().insert(user);

            onBackPressed();
        }else{
            Toast.makeText(this,getResources().getString(R.string.fill_the_fields),Toast.LENGTH_LONG).show();
        }
    }
}
