package com.ufo.mobile.eapp.Dialogs;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Application;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import com.ufo.mobile.eapp.DaoApp;
import com.ufo.mobile.eapp.MainActivity;
import com.ufo.mobile.eapp.R;

import org.greenrobot.greendao.query.QueryBuilder;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import ModelManager.Area;
import ModelManager.DaoSession;
import ModelManager.Section;
import ModelManager.User;
import ModelManager.UserDao;
import Utils.Constants;

public class CreateUserDialog extends Dialog {

    private Bitmap selectedImage;
    private CreateUserCallback callback;
    private Context mContext;
    private Application application;
    private DaoSession daoSession;
    private Section selectedSection;
    private Area selectedArea;
    private List<Area> areas;
    private List<Section> sections;

    private EditText editIdentification,editName;
    private Spinner spnArea,spnSection;
    private ImageView imgPhoto;
    private Button btnCancel,btnSave;
    private ImageButton btnPhoto;

    public CreateUserDialog(Application application,@NonNull Context context, final CreateUserCallback callback) {
        super(context);
        this.callback = callback;
        mContext = context;
        this.application = application;
        daoSession = ((DaoApp)this.application).getDaoSession();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        editIdentification = (EditText) findViewById(R.id.edit_identification);
        editName = (EditText) findViewById(R.id.edit_name);
        spnArea = (Spinner) findViewById(R.id.spn_area);
        spnSection = (Spinner) findViewById(R.id.spn_section);
        imgPhoto = (ImageView) findViewById(R.id.img_photo);
        btnCancel = (Button) findViewById(R.id.btn_cancel);
        btnSave = (Button) findViewById(R.id.btn_save);
        btnPhoto = (ImageButton) findViewById(R.id.btn_photo);

        populateSections();
        populateAreas(null);

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
                new AlertDialog.Builder(mContext)
                        .setTitle(mContext.getResources().getString(R.string.add_image_title))
                        .setMessage(mContext.getResources().getString(R.string.add_image_body))
                        .setPositiveButton(R.string.gallery, new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                //Get the gallery
                                Intent photoPickerIntent = new Intent(Intent.ACTION_PICK);
                                photoPickerIntent.setType("image/*");
                                ((Activity) mContext).startActivityForResult(photoPickerIntent, MainActivity.RESULT_LOAD_IMG_USER);
                            }
                        })
                        .setNeutralButton(R.string.take_picture, new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                Intent cameraIntent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
                                ((Activity) mContext).startActivityForResult(cameraIntent, MainActivity.RESULT_LOAD_IMG_USER_CAMARA);
                            }
                        })
                        .setIcon(R.drawable.ic_add_a_photo)
                        .show();
            }
        });
        spnSection.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if(position == 0){
                    selectedSection = null;
                }else{
                    selectedSection = sections.get(position-1);
                    populateAreas(selectedSection);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        spnArea.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if(position == 0){
                    selectedArea = null;
                }else{
                    selectedArea = areas.get(position-1);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    /**
     * Saves a new item
     */
    private void saveUser(){
        final String identification = editIdentification.getText().toString();
        final String name = editName.getText().toString();
        String imagePath = "";
        if(selectedImage != null){
            imagePath = Constants.saveImageOnStorage(mContext,selectedImage);
        }

        if(!identification.isEmpty() &&
                !name.isEmpty() &&
                selectedArea != null &&
                selectedSection != null &&
                !imagePath.isEmpty()){
            final User userWithName = userExistWithName(name);
            if(userWithName != null){
                final String finalImagePath = imagePath;
                new AlertDialog.Builder(mContext)
                        .setTitle(mContext.getResources().getString(R.string.user_already_exists))
                        .setMessage(mContext.getResources().getString(R.string.add_image_body))
                        .setPositiveButton(R.string.update_existent, new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                userWithName.setIdentification(identification);
                                userWithName.setName(name);
                                userWithName.setArea(selectedArea.getName());
                                userWithName.setAreaId(selectedArea.getId());
                                userWithName.setSection(selectedSection.getName());
                                userWithName.setSectionId(selectedSection.getId());
                                userWithName.setType(User.REGULAR_USER);
                                userWithName.setImage(finalImagePath);
                                userWithName.setCreationDate(new Date());
                                callback.createUserCallback(userWithName, false);
                            }
                        })
                        .setNeutralButton(R.string.create_new, new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                User userToCreate = createUser(identification,name, finalImagePath);
                                callback.createUserCallback(userToCreate, true);
                            }
                        })
                        .show();
            }else{
                User userToCreate = createUser(identification,name,imagePath);
                callback.createUserCallback(userToCreate, true);
            }
        }else{
            Toast.makeText(mContext,mContext.getResources().getString(R.string.fill_the_fields),Toast.LENGTH_LONG).show();
        }
    }

    /**
     *
     * @param identification
     * @param name
     * @param imagePath
     * @return
     */
    private User createUser(String identification,String name,String imagePath){
        User userToCreate = new User();
        userToCreate.setIdentification(identification);
        userToCreate.setName(name);
        userToCreate.setArea(selectedArea.getName());
        userToCreate.setAreaId(selectedArea.getId());
        userToCreate.setSection(selectedSection.getName());
        userToCreate.setSectionId(selectedSection.getId());
        userToCreate.setType(User.REGULAR_USER);
        userToCreate.setImage(imagePath);
        userToCreate.setCreationDate(new Date());
        return userToCreate;
    }

    /**
     * Sets the image with the selected bitmap
     * @param imageBitmap
     */
    public void setImageBitmap (Bitmap imageBitmap){
        selectedImage = imageBitmap;
        imgPhoto.setImageBitmap(selectedImage);
    }

    /**
     * Populates the spn with the sections
     */
    private void populateSections(){
        sections = daoSession.getSectionDao().loadAll();
        List<String> list = new ArrayList<>();
        list.add(mContext.getResources().getString(R.string.section));
        for (int i = 0; i < sections.size(); i++){
            list.add(sections.get(i).getName());
        }
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(mContext,R.layout.simple_spinner_item,list);
        spnSection.setAdapter(adapter);
    }

    /**
     *
     * @param section
     */
    private void populateAreas(Section section){
        List<String> list = new ArrayList<>();
        list.add(mContext.getResources().getString(R.string.area));

        if(section != null){
            if(section.getAreas() != null){
                List<Long> areasIds = Constants.getIdsSplitedIds(section.getAreas());
                areas = getAreasFromList(areasIds);
                for (int i = 0; i < areas.size(); i++){
                    list.add(areas.get(i).getName());
                }
            }else{
                Toast.makeText(getContext(),getContext().getResources().getString(R.string.section_no_areas),Toast.LENGTH_LONG).show();
            }
        }

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(mContext,R.layout.simple_spinner_item,list);
        spnArea.setAdapter(adapter);
    }

    /**
     *
     * @param ids
     * @return
     */
    private List<Area> getAreasFromList(List<Long> ids){
        List<Area> areas = new ArrayList<>();

        for (int i = 0; i < ids.size(); i ++){
            Area area = daoSession.getAreaDao().load(ids.get(i));
            areas.add(area);
        }

        return areas;
    }

    /**
     *
     * @param param
     * @return
     */
     private User userExistWithName(String param){
         List<User> us = daoSession.getUserDao().queryBuilder()
                 .where(UserDao.Properties.Name.eq(param)).list();
         if(us.size() > 0){
             return us.get(0);
         }
         return null;
     }
}
