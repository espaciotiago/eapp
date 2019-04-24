package com.ufo.mobile.eapp;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.ufo.mobile.eapp.Adapters.OrderAdapter;
import com.ufo.mobile.eapp.Adapters.UserAdapter;
import com.ufo.mobile.eapp.Adapters.UserSelectedCallback;

import java.util.ArrayList;
import java.util.List;

import ModelManager.DaoSession;
import ModelManager.Order;
import ModelManager.User;

public class OrdersActivity extends AppCompatActivity {

    private DaoSession daoSession;
    private List<Order> orders = new ArrayList<>();

    //UI Elements
    private RecyclerView recyclerOrders;
    private RecyclerView.LayoutManager layoutManager;
    private OrderAdapter orderAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_orders);

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

        //Set UI
        setRecycler(orders);
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }


    private void setRecycler(List<Order> orders){
        //Set adapter
        orderAdapter = new OrderAdapter(orders,daoSession);
        recyclerOrders.setAdapter(orderAdapter);
    }
}
