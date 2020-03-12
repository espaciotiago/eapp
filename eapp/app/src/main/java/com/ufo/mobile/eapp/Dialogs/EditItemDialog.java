package com.ufo.mobile.eapp.Dialogs;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.ufo.mobile.eapp.R;

import ModelManager.Area;
import ModelManager.Item;

public class EditItemDialog extends Dialog {

    private Context mContext;
    private EditItemCallback callback;
    private Item item;

    private EditText editInventory;
    private Button btnCancel,btnSave;

    public EditItemDialog(@NonNull Context context, Item item, final EditItemCallback callback) {
        super(context);
        this.item = item;
        mContext = context;
        this.callback = callback;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        editInventory = (EditText) findViewById(R.id.edit_inventory);
        btnCancel = (Button) findViewById(R.id.btn_cancel);
        btnSave = (Button) findViewById(R.id.btn_save);

        // Actions
        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });
        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                editItem();
            }
        });
    }

    private void editItem(){
        String inventory = editInventory.getText().toString();

        try{
            double value = Double.parseDouble(inventory);
            item.setStock(value);
            callback.editItemCallback(item,value);
        }catch (Exception e){
            dismiss();
        }

    }
}
