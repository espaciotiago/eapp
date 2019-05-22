package com.ufo.mobile.eapp;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.ufo.mobile.eapp.Adapters.UserAdapter;
import com.ufo.mobile.eapp.Adapters.UserSelectedCallback;
import com.ufo.mobile.eapp.Dialogs.CreateUserCallback;
import com.ufo.mobile.eapp.Dialogs.CreateUserDialog;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import ModelManager.DaoSession;
import ModelManager.Item;
import ModelManager.ItemDao;
import ModelManager.Order;
import ModelManager.OrderDao;
import ModelManager.User;
import ModelManager.UserDao;
import Utils.Constants;
import de.hdodenhof.circleimageview.CircleImageView;

import static com.ufo.mobile.eapp.MainActivity.RESULT_LOAD_IMG_USER;

public class NewOrderActivity extends AppCompatActivity {

    private Item item;
    private User owner;
    private List<User> users = new ArrayList<>();
    private DaoSession daoSession;

    //UI Elements
    private RecyclerView recyclerUsers;
    private RecyclerView.LayoutManager layoutManager;
    private UserAdapter userAdapter;
    private EditText editQty, editComments, editSearchUser;
    private TextView txtAddNewUser;
    private Button btnNewOrder;
    // Owner preview UI
    private View viewOwner;
    private CircleImageView imgOwner;
    private TextView txtOwnerName;
    // Item preview UI
    private ImageView imgItem;
    private TextView txtItemName,txtItemReference,txtItemStock;
    private CreateUserDialog createUserDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_order);

        //Set back button on action bar
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        //Close keyboard on touch screen
        Constants.closeKeyboardOnTouch(R.id.scroll,this);

        //Get the database session
        daoSession = ((DaoApp)getApplication()).getDaoSession();
        //Get item from intent
        Long itemId =  getIntent().getLongExtra("itemId",-1);
        if(itemId >= 0){
            item = daoSession.getItemDao().queryBuilder().where(ItemDao.Properties.Id.eq(itemId)).unique();
        }
        users = daoSession.getUserDao().queryBuilder().where(UserDao.Properties.Type.eq(User.REGULAR_USER)).list();

        //UI Elements
        editQty = findViewById(R.id.edit_qty);
        editComments = findViewById(R.id.edit_comments);
        editSearchUser = findViewById(R.id.edit_search);
        viewOwner = findViewById(R.id.owner_view);
        txtOwnerName = findViewById(R.id.txt_owner_name);
        imgOwner = findViewById(R.id.img_owner);
        imgItem = findViewById(R.id.img_item);
        txtItemName = findViewById(R.id.txt_item_name);
        txtItemReference = findViewById(R.id.txt_item_ref);
        txtItemStock = findViewById(R.id.txt_item_stock);
        txtAddNewUser = findViewById(R.id.txt_add_new_user);
        btnNewOrder = findViewById(R.id.btn_new_order);
        layoutManager = new LinearLayoutManager(this);
        recyclerUsers = findViewById(R.id.recycler_users);
        recyclerUsers.setLayoutManager(layoutManager);

        //Set UI
        setRecycler(users);
        setItemPreview(item);

        //Actions
        txtAddNewUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                createUserDialog = new CreateUserDialog(getApplication(),NewOrderActivity.this, new CreateUserCallback() {
                    @Override
                    public void createUserCallback(User user) {
                        daoSession.getUserDao().insert(user);
                        users.add(user);
                        if(createUserDialog != null && createUserDialog.isShowing()){
                            createUserDialog.dismiss();
                        }
                        setRecycler(users);
                    }
                });
                createUserDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                createUserDialog.setContentView(R.layout.dialog_create_user);
                createUserDialog.setCanceledOnTouchOutside(false);
                createUserDialog.getWindow().setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.MATCH_PARENT);
                createUserDialog.show();
            }
        });
        btnNewOrder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                createOrder();
            }
        });
        TextWatcher searcher = new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                List<User> usersSearched = searchUsers(s.toString());
                setRecycler(usersSearched);
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        };
        editSearchUser.addTextChangedListener(searcher);
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode == Activity.RESULT_OK)
            switch (requestCode){
                case RESULT_LOAD_IMG_USER:
                    Uri selectedImage = data.getData();
                    try {
                        Bitmap bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), selectedImage);
                        createUserDialog.setImageBitmap(bitmap);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    break;
            }
    }

    /**
     * --------------------------------------------------------------------
     * @param users
     */
    private void setRecycler(List<User> users){
        //Set adapter
        userAdapter = new UserAdapter(users, new UserSelectedCallback() {
            @Override
            public void userSelectedCallback(User user) {
                owner = user;
                setOwnerPreview(owner);
            }
        });
        recyclerUsers.setAdapter(userAdapter);
    }

    /**
     * --------------------------------------------------------------------
     * @param item
     */
    private void setItemPreview(Item item){
        if(item != null) {
            txtItemName.setText(item.getName());
            txtItemReference.setText(item.getReference());
            txtItemStock.setText(String.valueOf(item.getStock()));
            Bitmap bp = Constants.loadImageFromStorage(this, item.getImagePath());
            imgItem.setImageBitmap(bp);
        }

    }

    /**
     * --------------------------------------------------------------------
     * @param user
     */
    private void setOwnerPreview(User user){
        if(user != null) {
            txtOwnerName.setText(user.getName());
            Bitmap bp = Constants.loadImageFromStorage(this,user.getImage());
            imgOwner.setImageBitmap(bp);
            viewOwner.setVisibility(View.VISIBLE);
        }
    }

    /**
     * --------------------------------------------------------------------
     * @param search
     * @return
     */
    private List<User> searchUsers(String search){
        ArrayList<User> usersList = new ArrayList<>();

        for(int i = 0; i < users.size(); i ++){
            User u = users.get(i);
            if(u.getName().toLowerCase().contains(search.toLowerCase()) ||
                    u.getIdentification().toLowerCase().contains(search.toLowerCase())){
                usersList.add(u);
            }
        }
        return usersList;
    }

    /**
     * --------------------------------------------------------------------
     */
    private void createOrder(){
        String qtyStr = editQty.getText().toString();
        String comments = editComments.getText().toString();

        if(!qtyStr.isEmpty() && !comments.isEmpty() && owner != null && item != null){
            try{
                double qty = Double.parseDouble(qtyStr);
                if(qty <= item.getStock()) {
                    boolean continueToCreate = true;

                    Order orderToCreate = new Order();
                    orderToCreate.setOwner(owner.getId());
                    orderToCreate.setItem(item.getId());
                    orderToCreate.setQty(qty);
                    orderToCreate.setComments(comments);
                    orderToCreate.setStatus(Order.CREATED);

                    //TODO Calcular si hay disponibilidad de cantidad cada x días
                    //TODO Si llega a 0, quiere decir que ya no deberia haber disponibilidad
                    if(item.getNextAvailable()!=null){
                        if(item.getNextAvailable().compareTo(new Date()) == 1){
                            continueToCreate = false;
                            Toast.makeText(this,"No disponible hasta: " + Constants.formatDate(item.getNextAvailable()),Toast.LENGTH_LONG).show();
                        }else{
                            double ordersUntilNow = calculateOrdersOfItem(item.getId(),item.getNextAvailable());
                            double requested = ordersUntilNow + qty;
                            if(requested <= item.getQtyInSpecificTime()) {
                                if((item.getQtyInSpecificTime() - requested) == 0) {
                                    item.setNextAvailable(Constants.calculateDatePlusDays(item.getNextAvailable(), item.getEachInDays()));
                                }
                            }else{
                                continueToCreate = false;
                                Toast.makeText(this,
                                        "La cantidad solicitada sobrepasa la cantidad permitida hasta: " + Constants.formatDate(item.getNextAvailable()),
                                        Toast.LENGTH_LONG).show();
                            }
                        }
                    }else{
                        double ordersUntilNow = calculateOrdersOfItem(item.getId(),item.getNextAvailable());
                        double requested = ordersUntilNow + qty;
                        if(requested <= item.getQtyInSpecificTime()) {
                            // Is the first time that a item is requested and get the qty permited
                            if((item.getQtyInSpecificTime() - requested) == 0) {
                                item.setNextAvailable(Constants.calculateDatePlusDays(new Date(), item.getEachInDays()));
                            }
                        }else{
                            continueToCreate = false;
                            Toast.makeText(this,
                                    "La cantidad solicitada sobrepasa la cantidad permitida",
                                    Toast.LENGTH_LONG).show();
                        }
                    }
                    if(continueToCreate) {
                        daoSession.getItemDao().update(item);
                        Long orderId = daoSession.getOrderDao().insert(orderToCreate);
                        Intent intent = new Intent(this, OrderDetailActivity.class);
                        intent.putExtra("orderId", orderId);
                        intent.putExtra("isNew", true);
                        startActivity(intent);
                    }
                }else{
                    Toast.makeText(this,this.getResources().getString(R.string.qty_error),Toast.LENGTH_LONG).show();
                }
            }catch (Exception e){
                e.printStackTrace();
                Toast.makeText(this,this.getResources().getString(R.string.default_error),Toast.LENGTH_LONG).show();
            }
        }else{
            Toast.makeText(this,this.getResources().getString(R.string.fill_the_fields),Toast.LENGTH_LONG).show();
        }
    }

    /**
     *
     * @param itemId
     * @return
     */
    public double calculateOrdersOfItem(Long itemId, Date lastAvailable){
        double ordersCount = 0;
        List<Order> orders = daoSession.getOrderDao().queryBuilder().where(OrderDao.Properties.Item.eq(itemId)).list();
        for(int i = 0; i < orders.size(); i++){
            Order or = orders.get(i);
            if(lastAvailable != null) {
                if (or.getCreationDate().compareTo(lastAvailable) == 1) {
                    ordersCount += or.getQty();
                }
            }else{
                ordersCount += or.getQty();
            }
        }
        return ordersCount;
    }
}
