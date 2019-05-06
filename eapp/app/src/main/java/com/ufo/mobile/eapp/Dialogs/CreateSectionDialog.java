package com.ufo.mobile.eapp.Dialogs;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.ufo.mobile.eapp.MainActivity;
import com.ufo.mobile.eapp.R;

import ModelManager.Section;

public class CreateSectionDialog extends Dialog {

    private Context mContext;
    private CreateSectionCallback callback;

    private EditText editName;
    private Button btnCancel, btnSave;

    public CreateSectionDialog(@NonNull Context context,final CreateSectionCallback callback) {
        super(context);
        mContext = context;
        this.callback = callback;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        editName = (EditText) findViewById(R.id.edit_name);
        btnCancel = (Button) findViewById(R.id.btn_cancel);
        btnSave = (Button) findViewById(R.id.btn_save);

        //Actions
        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });
        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveSection();
            }
        });
    }

    private void saveSection(){
        String name = editName.getText().toString();
        if (name != null && !name.isEmpty()){
            Section section = new Section();
            section.setName(name);
            callback.createSectionCallback(section);
        }else{
            Toast.makeText(mContext,mContext.getResources().getString(R.string.fill_the_fields),Toast.LENGTH_LONG).show();
        }
    }

}
