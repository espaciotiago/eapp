package com.ufo.mobile.eapp;

import android.Manifest;
import android.app.Activity;
import android.app.Application;
import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.AsyncTask;
import android.provider.MediaStore;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;

import com.ufo.mobile.eapp.Adapters.ItemAdapter;
import com.ufo.mobile.eapp.Dialogs.*;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import ModelManager.DaoSession;
import ModelManager.Item;
import Utils.Constants;
import Utils.DummyDataManager;
import Utils.ExcelExportManager;

public class MainActivity extends AppCompatActivity {
    private final static int CAMERA = 0;
    private final static int GALLERY = 1;
    public final static int RESULT_LOAD_IMG_ITEM = 2;
    public final static int RESULT_LOAD_IMG_ITEM_CAMARA = 4;
    public final static int RESULT_LOAD_IMG_USER = 3;
    public final static int RESULT_LOAD_IMG_USER_CAMARA = 5;

    //Logic items
    private DummyDataManager dummyDataManager;
    private DaoSession daoSession;
    private List<Item> items = new ArrayList<>();
    private int numberOfRows = 3;

    //UI Elements
    private TextView txtError;
    private RecyclerView recyclerItems;
    private GridLayoutManager layoutManager;
    private ItemAdapter itemAdapter;
    private CreateItemDialog createItemDialog;
    private EditItemDialog editItemDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Constants.setLayoutOrientation(this);
        if(Constants.isPortrait(this)){
            numberOfRows = 1;
        }

        //Get the database session
        daoSession = ((DaoApp)getApplication()).getDaoSession();
        items = daoSession.getItemDao().loadAll();
        dummyDataManager = new DummyDataManager(daoSession,this);

        if(!dummyDataManager.verifyDataExistence()){
            new InsertDummyData().execute(daoSession);
        }

        //TODO Calcular fechas de disponibilidad, actualizar las que ya se cumplieron

        //UI Elements
        //searchView = (MaterialSearchView) findViewById(R.id.search_view);
        txtError = (TextView) findViewById(R.id.txt_error);
        layoutManager = new GridLayoutManager(this,numberOfRows);
        recyclerItems = (RecyclerView) findViewById(R.id.recycler_items);
        recyclerItems.setLayoutManager(layoutManager);

        //Set the UI
        getSupportActionBar().setTitle(R.string.inventory);
        setRecyclerItems(items);
        //Get Permissions
        permissionsRequest();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main_menu, menu);

        // Search configuration
        final MenuItem searchItem = menu.findItem(R.id.search);
        SearchManager searchManager = (SearchManager) MainActivity.this.getSystemService(Context.SEARCH_SERVICE);

        SearchView searchView = null;
        if (searchItem != null) {
            searchView = (SearchView) searchItem.getActionView();
        }
        if (searchView != null) {
            searchView.setSearchableInfo(searchManager.getSearchableInfo(MainActivity.this.getComponentName()));
            searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
                @Override
                public boolean onQueryTextSubmit(String s) {
                    return true;
                }

                @Override
                public boolean onQueryTextChange(String s) {
                    if(s.isEmpty()){
                        setRecyclerItems(items);
                    }else {
                        List<Item> items = searchItems(s);
                        setRecyclerItems(items);
                    }
                    return false;
                }
            });
        }

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle item selection
        switch (item.getItemId()) {
            case R.id.configuration:
                Intent intentConfig = new Intent(MainActivity.this,ConfigurationActivity.class);
                startActivity(intentConfig);
                return true;
            case R.id.list_orders:
                Intent intent = new Intent(MainActivity.this,OrdersActivity.class);
                startActivity(intent);
                return true;
            case R.id.add_item:
                createItemDialog = new CreateItemDialog(MainActivity.this,new CreateItemCallback() {
                    @Override
                    public void createItemCallback(Item item) {
                        new CreateItemTask(MainActivity.this,daoSession).execute(item);
                    }
                });
                createItemDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                createItemDialog.setContentView(R.layout.dialog_create_item);
                createItemDialog.setCanceledOnTouchOutside(false);
                createItemDialog.getWindow().setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.MATCH_PARENT);
                createItemDialog.show();
                return true;
            case R.id.charts:
                Intent intentToCharts = new Intent(MainActivity.this,ChartsActivity.class);
                startActivity(intentToCharts);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    public void showEditDialog(Item item){
        editItemDialog = new EditItemDialog(this, item, new EditItemCallback() {
            @Override
            public void editItemCallback(Item item, Double value) {
                item.setStock(value);
                daoSession.getItemDao().update(item);
                // Edit from recycler
                items = daoSession.getItemDao().loadAll();
                setRecyclerItems(items);

                editItemDialog.dismiss();
            }
        });
        editItemDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        editItemDialog.setContentView(R.layout.dialog_edit_item);
        editItemDialog.setCanceledOnTouchOutside(false);
        editItemDialog.getWindow().setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.MATCH_PARENT);
        editItemDialog.show();
    }

    /**
     *
     * @param items
     */
    private void setRecyclerItems(List<Item> items){
        if(items.isEmpty()){
            txtError.setVisibility(View.VISIBLE);
            recyclerItems.setVisibility(View.GONE);
        }else{
            recyclerItems.setVisibility(View.VISIBLE);
            txtError.setVisibility(View.GONE);
            //Set adapter
            itemAdapter = new ItemAdapter(items);
            recyclerItems.setAdapter(itemAdapter);
        }
    }

    /**
     * Search items on list
     */
    private List<Item> searchItems(String search){
        List<Item> itemsList = new ArrayList<>();
        for (int i = 0; i < this.items.size(); i++){
            Item item = this.items.get(i);
            if(item.getName().toLowerCase().contains(search.toLowerCase())
                    || item.getReference().toLowerCase().contains(search.toLowerCase())){
                itemsList.add(item);
            }
        }

        return itemsList;
    }

    /**
     *
     * @param item
     */
    public void onDeleteItem(Item item){
        // Remove from database
        daoSession.getItemDao().delete(item);
        // Remove from recycler
        items = daoSession.getItemDao().loadAll();
        setRecyclerItems(items);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode == Activity.RESULT_OK)
            switch (requestCode){
                case RESULT_LOAD_IMG_ITEM:
                    Uri selectedImage = data.getData();
                    try {
                        Bitmap bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), selectedImage);
                        createItemDialog.setImageBitmap(bitmap);
                    } catch (IOException e) {
                        Log.e("TAG", "Some exception " + e);
                    }
                    break;
                case RESULT_LOAD_IMG_ITEM_CAMARA:
                    try {
                        Bitmap bitmap = (Bitmap) data.getExtras().get("data");
                        createItemDialog.setImageBitmap(bitmap);
                    } catch (Exception e) {
                        Log.e("TAG", "Some exception " + e);
                    }
                    break;
            }
    }

    //Permission Request ---------------------------------------------------------------------------
    private void permissionsRequest() {

        if (ContextCompat.checkSelfPermission(this,
                Manifest.permission.CAMERA)
                != PackageManager.PERMISSION_GRANTED) {

            // Permission is not granted
            // Should we show an explanation?
            if (ActivityCompat.shouldShowRequestPermissionRationale(this,
                    Manifest.permission.CAMERA)) {
                // Show an explanation to the user *asynchronously* -- don't block
                // this thread waiting for the user's response! After the user
                // sees the explanation, try again to request the permission.
            } else {
                // No explanation needed; request the permission
                ActivityCompat.requestPermissions(this,
                        new String[]{Manifest.permission.CAMERA,Manifest.permission.READ_EXTERNAL_STORAGE,Manifest.permission.WRITE_EXTERNAL_STORAGE},
                        CAMERA);
            }
        } else {
            // Permission has already been granted
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
        switch (requestCode) {
            case CAMERA: {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    return;
                } else {
                    return;
                }
            }
        }
    }

    //CreateItemTask -------------------------------------------------------------------------------
    private class CreateItemTask extends AsyncTask<Item,Integer,Long> {

        private DaoSession daoSession;
        private Context context;

        private CreateItemTask(Context context, DaoSession daoSession){
            this.context = context;
            this.daoSession = daoSession;
        }

        @Override
        protected Long doInBackground(Item... items) {
            return daoSession.getItemDao().insert(items[0]);
        }

        @Override
        protected void onPostExecute(Long itemId) {
            super.onPostExecute(itemId);
            if(itemId != null){

                items = daoSession.getItemDao().loadAll();
                setRecyclerItems(items);

                if(createItemDialog != null && createItemDialog.isShowing()){
                    createItemDialog.dismiss();
                }
            }
        }
    }

    //Create Dummy data for test -------------------------------------------------------------------
    public class InsertDummyData extends AsyncTask<DaoSession,Integer,List<Long>> {

        private InsertDummyData(){}

        @Override
        protected List<Long> doInBackground(DaoSession... daoSessions) {
            return dummyDataManager.fillDummyData();
        }

        @Override
        protected void onPostExecute(List<Long> aLong) {
            super.onPostExecute(aLong);
            if(!aLong.isEmpty()){
                items = daoSession.getItemDao().loadAll();
                setRecyclerItems(items);
            }

        }
    }
}
