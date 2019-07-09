package com.ufo.mobile.eapp;

import android.content.Context;
import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.components.Description;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter;
import com.github.mikephil.charting.highlight.Highlight;
import com.github.mikephil.charting.listener.OnChartValueSelectedListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import ModelManager.Area;
import ModelManager.AreaDao;
import ModelManager.DaoSession;
import ModelManager.Item;
import ModelManager.ItemDao;
import ModelManager.Order;
import ModelManager.OrderDao;
import ModelManager.User;
import ModelManager.UserDao;
import Utils.Constants;

public class DetailsChartActivity extends AppCompatActivity {

    private DaoSession daoSession;
    private Area area;
    private List<Item> items = new ArrayList<>();
    private HashMap<Item,Double> itemsMap = new HashMap<>();
    private ArrayList labels = new ArrayList<String>();

    private BarChart barChart;
    private TextView txtItemTitle,txtTotal;
    private ImageView imgItem;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details_chart);
        Constants.setLayoutOrientation(this);

        daoSession = ((DaoApp)getApplication()).getDaoSession();
        Long areaId = getIntent().getLongExtra("areaId",0);
        area = daoSession.getAreaDao().queryBuilder().where(AreaDao.Properties.Id.eq(areaId)).unique();

        //Set back button on action bar
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        barChart = findViewById(R.id.chart_items);
        txtItemTitle = findViewById(R.id.txt_item);
        txtTotal = findViewById(R.id.txt_total);
        imgItem = findViewById(R.id.img_item);

        new GetChartsInfo(this).execute(daoSession);

        //Actions
        barChart.setOnChartValueSelectedListener(new OnChartValueSelectedListener() {
            @Override
            public void onValueSelected(Entry e, Highlight h) {
                int position = (int) e.getX();
                Item selected = items.get(position);
                setUpItemSelected((BarEntry) e,selected);
            }

            @Override
            public void onNothingSelected() {

            }
        });

    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

    /**
     *
     */
    private void setUpItemSelected(BarEntry e, Item item){
        String label = barChart.getXAxis().getValueFormatter().getFormattedValue(e.getX(), barChart.getXAxis());
        txtTotal.setText(e.getY()+"");
        txtItemTitle.setText(label);
        Bitmap bp = Constants.loadImageFromStorage(this,item.getImagePath());
        if (bp != null){
            imgItem.setImageBitmap(bp);
        }
    }

    /**
     * Set the charts of a week
     * @param entries
     **/
    private void setBarChart(List<BarEntry> entries, List<String> labels){
        BarDataSet dataSet = new BarDataSet(entries, "Ordenes por area");
        dataSet.setColor(getResources().getColor(R.color.colorPrimaryDark));
        BarData data = new BarData(dataSet);
        Description description = new Description();
        description.setText("");
        barChart.getAxisLeft().setDrawGridLines(false);
        barChart.getXAxis().setDrawGridLines(false);
        barChart.getXAxis().setPosition(XAxis.XAxisPosition.BOTTOM);
        barChart.getAxisRight().setDrawGridLines(false);
        barChart.getAxisRight().setEnabled(false);
        barChart.setDescription(description);
        barChart.getXAxis().setValueFormatter(new IndexAxisValueFormatter(labels));
        barChart.setData(data);
        barChart.invalidate();
    }

    /** -------------------------------------------------------------------------------
     *
     */
    private class GetChartsInfo extends AsyncTask<DaoSession,Integer,ArrayList<BarEntry>> {

        private Context context;

        private GetChartsInfo(Context context){
            this.context = context;
        }

        @RequiresApi(api = Build.VERSION_CODES.N)
        @Override
        protected ArrayList<BarEntry> doInBackground(DaoSession... session) {
            DaoSession daoSession = session[0];
            ArrayList entries = new ArrayList<BarEntry>();
            List<User> owners = daoSession.getUserDao().queryBuilder()
                    .where(UserDao.Properties.AreaId.eq(area.getId())).list();
            for(int i= 0; i < owners.size();i++){
                User user = owners.get(i);
                List<Order> orders = daoSession.getOrderDao().queryBuilder()
                        .where(OrderDao.Properties.Owner.eq(user.getId())).list();
                for (int j = 0; j < orders.size(); j++){

                    Order order = orders.get(j);
                    Item item = daoSession.getItemDao().queryBuilder()
                            .where(ItemDao.Properties.Id.eq(order.getItem())).unique();

                    if(itemsMap.containsKey(item)){
                        Double qty = itemsMap.get(item).doubleValue() + order.getQty();
                        itemsMap.replace(item,qty);
                    }else{
                        itemsMap.put(item,order.getQty());
                        items.add(item);
                    }
                }
            }

            int i = 0;
            for (Map.Entry<Item, Double> entry : itemsMap.entrySet()) {
                Item item = entry.getKey();
                int qty = entry.getValue().intValue();

                BarEntry entryBar = new BarEntry(i,qty,item.getName());
                entries.add(entryBar);
                labels.add(item.getName());
                i++;
            }

            return entries;
        }

        @Override
        protected void onPostExecute(ArrayList<BarEntry> list) {
            super.onPostExecute(list);
            setBarChart(list,labels);
            setUpItemSelected((BarEntry) list.get(0),items.get(0));
        }
    }
}
