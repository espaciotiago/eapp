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

import ModelManager.Item;
import Utils.Constants;

public class CreateItemDialog extends Dialog {

    private Bitmap selectedImage;
    private CreateItemCallback callback;
    private Context mContext;

    private EditText editReference,editName,editDescription,editStock,editQty;
    private ImageView imgPhoto;
    private Button btnCancel,btnSave;
    private ImageButton btnPhoto;

    public CreateItemDialog(@NonNull Context context, final CreateItemCallback callback) {
        super(context);
        this.callback = callback;
        mContext = context;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.dialog_create_item);

        editReference = (EditText) findViewById(R.id.edit_reference);
        editName = (EditText) findViewById(R.id.edit_name);
        editDescription = (EditText) findViewById(R.id.edit_name);
        editStock = (EditText) findViewById(R.id.edit_stock);
        editQty = (EditText) findViewById(R.id.edit_qty);
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
                saveItem();
            }
        });
        btnPhoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Get the gallery
                Intent photoPickerIntent = new Intent(Intent.ACTION_PICK);
                photoPickerIntent.setType("image/*");
                ((Activity) mContext).startActivityForResult(photoPickerIntent, MainActivity.RESULT_LOAD_IMG);
            }
        });
    }

    /**
     * Saves a new item
     */
    private void saveItem(){
        String reference = editReference.getText().toString();
        String name = editName.getText().toString();
        String description = editDescription.getText().toString();
        String stockStr = editStock.getText().toString();
        String qtyStr = editQty.getText().toString();
        String imagePath = "";
        if(selectedImage != null){
            imagePath = Constants.saveImageOnStorage(mContext,selectedImage);
        }

        if(!name.isEmpty() && !description.isEmpty() &&
           !stockStr.isEmpty() && !qtyStr.isEmpty() &&
           !imagePath.isEmpty()){
            try{
                double stock = Double.parseDouble(stockStr);
                double qty = Double.parseDouble(qtyStr);

                Item itemToCreate = new Item();
                itemToCreate.setReference(reference);
                itemToCreate.setName(name);
                itemToCreate.setDescription(description);
                itemToCreate.setStock(stock);
                itemToCreate.setQtyDefault(qty);
                itemToCreate.setImagePath(imagePath);
                itemToCreate.setCreationDate(new Date());

                callback.createItemCallback(itemToCreate);

            }catch (Exception e){
                e.printStackTrace();
                Toast.makeText(mContext,mContext.getResources().getString(R.string.default_error),Toast.LENGTH_LONG).show();
            }
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
