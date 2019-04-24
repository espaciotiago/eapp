package com.ufo.mobile.eapp;

import android.content.Intent;
import android.graphics.Bitmap;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;

import com.ufo.mobile.eapp.Views.RequestSignatureView;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;

import Utils.Constants;

public class SignatureActivity extends AppCompatActivity {

    private String signImagePath;

    //UI Elements
    private RequestSignatureView requestSignatureView;
    private RelativeLayout signParentView;
    private Button btnSaveSign;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signature);
        //Set back button on action bar
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        //UI Elements
        btnSaveSign = (Button) findViewById(R.id.btn_save_sign);
        signParentView = (RelativeLayout) findViewById(R.id.sign_image_parent);
        requestSignatureView = new RequestSignatureView(this);

        //Set UI
        signParentView.addView(requestSignatureView);

        //Actions
        btnSaveSign.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.putExtra("signature", getImageFromSignature());
                setResult(RESULT_OK, intent);
                finish();
            }
        });
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

    private String getImageFromSignature(){
        signParentView.setDrawingCacheEnabled(true);
        Bitmap b = signParentView.getDrawingCache();
        return Constants.saveImageOnStorage(this,b);
    }
}
