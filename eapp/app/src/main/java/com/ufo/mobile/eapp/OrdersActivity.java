package com.ufo.mobile.eapp;

import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.ufo.mobile.eapp.Adapters.OrderAdapter;
import com.ufo.mobile.eapp.Adapters.UserAdapter;
import com.ufo.mobile.eapp.Adapters.UserSelectedCallback;
import com.ufo.mobile.eapp.Dialogs.CreateItemCallback;
import com.ufo.mobile.eapp.Dialogs.CreateItemDialog;

import java.util.ArrayList;
import java.util.List;

import ModelManager.DaoSession;
import ModelManager.Item;
import ModelManager.ItemDao;
import ModelManager.Order;
import ModelManager.User;
import Utils.Constants;
import Utils.ExcelExportManager;

public class OrdersActivity extends AppCompatActivity {

    private DaoSession daoSession;
    private List<Order> orders = new ArrayList<>();

    //UI Elements
    private RecyclerView recyclerOrders;
    private TextView txtDownload;
    private ProgressBar loader;
    private RecyclerView.LayoutManager layoutManager;
    private OrderAdapter orderAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_orders);
        Constants.setLayoutOrientation(this);
        //Set back button on action bar
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        //Get the database session
        daoSession = ((DaoApp)getApplication()).getDaoSession();
        orders = daoSession.getOrderDao().loadAll();

        //UI Elements
        layoutManager = new LinearLayoutManager(this);
        recyclerOrders = (RecyclerView) findViewById(R.id.recycler_orders);
        recyclerOrders.setLayoutManager(layoutManager);
        txtDownload = findViewById(R.id.txt_download);
        loader = findViewById(R.id.loader);

        //Set UI
        setRecycler(orders);

        //Actions
        txtDownload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loader.setVisibility(View.VISIBLE);
                txtDownload.setVisibility(View.GONE);
                ExcelExportManager.setUpPOI();
                ExcelExportManager.createDataSheet(daoSession,OrdersActivity.this);
                loader.setVisibility(View.GONE);
                txtDownload.setVisibility(View.VISIBLE);

            }
        });

    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main_menu, menu);

        //Hide other items
        MenuItem configItem = menu.findItem(R.id.configuration);
        configItem.setVisible(false);
        MenuItem ordersItem = menu.findItem(R.id.list_orders);
        ordersItem.setVisible(false);
        MenuItem cartsItem = menu.findItem(R.id.charts);
        cartsItem.setVisible(false);
        MenuItem addItem = menu.findItem(R.id.add_item);
        addItem.setVisible(false);

        // Search configuration
        final MenuItem searchItem = menu.findItem(R.id.search);
        SearchManager searchManager = (SearchManager) OrdersActivity.this.getSystemService(Context.SEARCH_SERVICE);

        SearchView searchView = null;
        if (searchItem != null) {
            searchView = (SearchView) searchItem.getActionView();
        }
        if (searchView != null) {
            searchView.setSearchableInfo(searchManager.getSearchableInfo(OrdersActivity.this.getComponentName()));
            searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
                @Override
                public boolean onQueryTextSubmit(String s) {
                    return true;
                }

                @Override
                public boolean onQueryTextChange(String s) {
                    if(s.isEmpty()){
                        setRecycler(orders);
                    }else {
                        List<Order> orders = searchOrder(s);
                        setRecycler(orders);
                    }
                    return false;
                }
            });
        }

        return true;
    }

    private List<Order> searchOrder(String search){
        List<Order> orders = new ArrayList<>();

        for (int i = 0; i < this.orders.size(); i++){
            Order order = this.orders.get(i);
            Item item = daoSession.getItemDao().queryBuilder().where(ItemDao.Properties.Id.eq(order.getItem())).unique();
            if(item != null && (item.getName().toLowerCase().contains(search) || item.getReference().toLowerCase().contains(search))){
                orders.add(order);
            }else{
                try{
                    Long searchLng = Long.parseLong(search);
                    if(searchLng.equals(order.getId())){
                        orders.add(order);
                    }
                }catch (Exception e){
                    e.printStackTrace();
                }
            }
        }

        return orders;
    }


    private void setRecycler(List<Order> orders){
        //Set adapter
        orderAdapter = new OrderAdapter(orders,daoSession);
        recyclerOrders.setAdapter(orderAdapter);
    }

    public void onDeleteOrder(Order order){
        daoSession.getOrderDao().delete(order);
        orders = daoSession.getOrderDao().loadAll();
        setRecycler(orders);
    }
}
