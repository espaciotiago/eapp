package com.ufo.mobile.eapp.Dialogs;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

import com.ufo.mobile.eapp.MainActivity;
import com.ufo.mobile.eapp.R;

import java.util.Date;

import ModelManager.User;
import Utils.Constants;

public class CreateUserDialog extends Dialog {

    private Bitmap selectedImage;
    private CreateUserCallback callback;
    private Context mContext;

    private EditText editIdentification,editName,editArea,editSection;
    private ImageView imgPhoto;
    private Button btnCancel,btnSave;
    private ImageButton btnPhoto;

    public CreateUserDialog(@NonNull Context context, final CreateUserCallback callback) {
        super(context);
        this.callback = callback;
        mContext = context;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        editIdentification = (EditText) findViewById(R.id.edit_identification);
        editName = (EditText) findViewById(R.id.edit_name);
        editArea = (EditText) findViewById(R.id.edit_area);
        editSection = (EditText) findViewById(R.id.edit_section);
        imgPhoto = (ImageView) findViewById(R.id.img_photo);
        btnCancel = (Button) findViewById(R.id.btn_cancel);
        btnSave = (Button) findViewById(R.id.btn_save);
        btnPhoto = (ImageButton) findViewById(R.id.btn_photo);

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
                saveUser();
            }
        });
        btnPhoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Get the gallery
                Intent photoPickerIntent = new Intent(Intent.ACTION_PICK);
                photoPickerIntent.setType("image/*");
                ((Activity) mContext).startActivityForResult(photoPickerIntent, MainActivity.RESULT_LOAD_IMG_USER);
            }
        });
    }

    /**
     * Saves a new item
     */
    private void saveUser(){
        String identification = editIdentification.getText().toString();
        String name = editName.getText().toString();
        String area = editArea.getText().toString();
        String section = editSection.getText().toString();
        String imagePath = "";
        if(selectedImage != null){
            imagePath = Constants.saveImageOnStorage(mContext,selectedImage);
        }

        if(!identification.isEmpty() &&
                !name.isEmpty() &&
                !area.isEmpty() &&
                !section.isEmpty() &&
                !imagePath.isEmpty()){
            User userToCreate = new User();
            userToCreate.setIdentification(identification);
            userToCreate.setName(name);
            userToCreate.setArea(area);
            userToCreate.setSection(section);
            userToCreate.setType(User.REGULAR_USER);
            userToCreate.setImage(imagePath);
            userToCreate.setCreationDate(new Date());
            callback.createUserCallback(userToCreate);
        }else{
            Toast.makeText(mContext,mContext.getResources().getString(R.string.fill_the_fields),Toast.LENGTH_LONG).show();
        }
    }

    /**
     * Sets the image with the selected bitmap
     * @param imageBitmap
     */
    public void setImageBitmap (Bitmap imageBitmap){
        selectedImage = imageBitmap;
        imgPhoto.setImageBitmap(selectedImage);
    }
}
