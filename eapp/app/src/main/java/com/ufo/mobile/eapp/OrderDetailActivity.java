package com.ufo.mobile.eapp;

import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Date;

import ModelManager.DaoSession;
import ModelManager.Item;
import ModelManager.ItemDao;
import ModelManager.Order;
import ModelManager.OrderDao;
import ModelManager.User;
import ModelManager.UserDao;
import Utils.Constants;
import de.hdodenhof.circleimageview.CircleImageView;

public class OrderDetailActivity extends AppCompatActivity {

    public final static int REQUEST_SIGNATURE = 1;

    private Order order;
    private Item item;
    private User owner;
    private DaoSession daoSession;
    private String signImagePath;
    private boolean isNew;

    //UI Elements
    private EditText editQty;
    private EditText editComments;
    private TextView txtTouchToSign,txtSingnAut;
    private ImageView imgSign,imgSignAut;
    private Button btnNewOrder;
    //Item
    private TextView txtItemName;
    private TextView txtItemReference;
    private TextView txtItemStock;
    private ImageView imgItem;
    //User owner
    private TextView txtOwnerName;
    private CircleImageView imgOwner;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_detail);
        //Set back button on action bar
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        //Get the database session
        daoSession = ((DaoApp)getApplication()).getDaoSession();
        Long orderId = getIntent().getLongExtra("orderId",-1);
        isNew = getIntent().getBooleanExtra("isNew",false);

        //UI Elements
        editComments = (EditText) findViewById(R.id.edit_comments);
        editQty = (EditText) findViewById(R.id.edit_qty);
        txtTouchToSign = (TextView) findViewById(R.id.txt_touch_sign);
        txtItemName = (TextView) findViewById(R.id.txt_item_name);
        txtItemReference = (TextView) findViewById(R.id.txt_item_ref);
        txtItemStock = (TextView) findViewById(R.id.txt_item_stock);
        txtSingnAut = (TextView) findViewById(R.id.txt_sign_aut);
        txtOwnerName = (TextView) findViewById(R.id.txt_owner_name);
        imgItem = (ImageView) findViewById(R.id.img_item);
        imgSign = (ImageView) findViewById(R.id.img_sign);
        imgOwner = (CircleImageView) findViewById(R.id.img_owner);
        imgSignAut = findViewById(R.id.img_sign_aut);
        btnNewOrder = (Button) findViewById(R.id.btn_new_order);

        //Set UI
        editComments.setEnabled(false);
        editQty.setEnabled(false);
        getOrderData(orderId);

        //Actions
        txtTouchToSign.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(OrderDetailActivity.this,SignatureActivity.class);
                startActivityForResult(intent, REQUEST_SIGNATURE);
            }
        });
        btnNewOrder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveOrder();
            }
        });
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

    @Override
    public void onBackPressed() {
        if(order != null && isNew) {
            daoSession.getOrderDao().delete(order);
        }
        super.onBackPressed();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_SIGNATURE) {
            if(resultCode == RESULT_OK) {
                signImagePath = data.getStringExtra("signature");
                if(signImagePath != null && !signImagePath.isEmpty()) {
                    Bitmap bp = Constants.loadImageFromStorage(OrderDetailActivity.this, signImagePath);
                    imgSign.setImageBitmap(bp);
                    imgSign.setVisibility(View.VISIBLE);
                    txtTouchToSign.setVisibility(View.GONE);
                }
            }
        }
    }

    private void getOrderData(Long orderId){
        if(orderId >= 0) {
            order = daoSession.getOrderDao().queryBuilder().where(OrderDao.Properties.Id.eq(orderId)).unique();
            if(order != null){
                item = daoSession.getItemDao().queryBuilder().where(ItemDao.Properties.Id.eq(order.getItem())).unique();
                owner = daoSession.getUserDao().queryBuilder().where(UserDao.Properties.Id.eq(order.getOwner())).unique();
                setOrderView();
            }
        }
    }

    private void setOrderView(){
        //Set item info
        if(item != null){
            txtItemName.setText(item.getName());
            txtItemReference.setText(item.getReference());
            txtItemStock.setText(String.valueOf(item.getStock()));
            Bitmap itemBitmap = Constants.loadImageFromStorage(this, item.getImagePath());
            if (itemBitmap != null) {
                imgItem.setImageBitmap(itemBitmap);
            }

        }
        //Set owner info
        if(owner != null){
            txtOwnerName.setText(owner.getName());
            Bitmap ownerBitmap = Constants.loadImageFromStorage(this,owner.getImage());
            if(ownerBitmap != null){
                imgOwner.setImageBitmap(ownerBitmap);
            }
        }
        //Set order info
        editComments.setText(order.getComments());
        editQty.setText(String.valueOf(order.getQty()));

        //Set view if is not new
        if(!isNew){
            Bitmap bp = Constants.loadImageFromStorage(this,order.getUserSignImage());
            if(bp != null){
                imgSign.setImageBitmap(bp);
                imgSign.setVisibility(View.VISIBLE);
                txtTouchToSign.setVisibility(View.GONE);
                btnNewOrder.setVisibility(View.GONE);
            }
        }
        // Is has authorization sign
        if(order.getAutorizationSignImage() != null && !order.getAutorizationSignImage().isEmpty()){
            Bitmap bp = Constants.loadImageFromStorage(this,order.getAutorizationSignImage());
            if(bp != null){
                imgSignAut.setImageBitmap(bp);
                imgSignAut.setVisibility(View.VISIBLE);
                txtSingnAut.setVisibility(View.VISIBLE);
            }
        }
    }

    private void saveOrder(){
        if(order != null && item != null && owner != null){
            if(signImagePath != null && !signImagePath.isEmpty()){
                order.setStatus(Order.REQUESTED);
                order.setUserSignImage(signImagePath);
                order.setCreationDate(new Date());
                item.setStock(item.getStock()- order.getQty());

                daoSession.getOrderDao().update(order);
                daoSession.getItemDao().update(item);

                Intent intent = new Intent(this,MainActivity.class);//TODO Go to the order list
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK|Intent.FLAG_ACTIVITY_NEW_TASK|Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
            }else{
                Toast.makeText(this,this.getResources().getString(R.string.signature_needed),Toast.LENGTH_LONG).show();
            }
        }else{
            Toast.makeText(this,this.getResources().getString(R.string.fill_the_fields),Toast.LENGTH_LONG).show();
        }
    }
}
