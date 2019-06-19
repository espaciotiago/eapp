package com.ufo.mobile.eapp;

import android.app.Application;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.Description;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter;
import com.github.mikephil.charting.highlight.Highlight;
import com.github.mikephil.charting.listener.OnChartValueSelectedListener;
import com.github.mikephil.charting.utils.ColorTemplate;

import java.util.ArrayList;
import java.util.List;

import ModelManager.Area;
import ModelManager.DaoSession;
import ModelManager.Item;
import ModelManager.ItemDao;
import ModelManager.Order;
import ModelManager.OrderDao;
import ModelManager.Section;
import ModelManager.User;
import ModelManager.UserDao;

public class ChartsActivity extends AppCompatActivity {

    private DaoSession daoSession;
    private ArrayList labelsPie = new ArrayList<String>();
    private List<Area> areas = new ArrayList<Area>();
    private Area selectedArea;

    private PieChart pieItems;
    private TextView txtArea,txtQty;
    private Button btnDetails;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_charts);

        daoSession = ((DaoApp)getApplication()).getDaoSession();

        //Set back button on action bar
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        pieItems = findViewById(R.id.piechart_items);
        txtArea = findViewById(R.id.txt_area_selected);
        txtQty = findViewById(R.id.txt_area_request);
        btnDetails = findViewById(R.id.btn_details);
        new GetChartsInfo(this).execute(daoSession);

        //Actions
        pieItems.setOnChartValueSelectedListener(new OnChartValueSelectedListener() {
            @Override
            public void onValueSelected(Entry e, Highlight h) {
                int selectedPosition = (int) h.getX();
                selectedArea = areas.get(selectedPosition);
                setUpSelectedAreaView((PieEntry) e);
            }

            @Override
            public void onNothingSelected() {

            }
        });
        btnDetails.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent goToDetails = new Intent(ChartsActivity.this,DetailsChartActivity.class);
                goToDetails.putExtra("areaId",selectedArea.getId());
                startActivity(goToDetails);
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
     * @param e
     */
    private void setUpSelectedAreaView(PieEntry e){
        txtArea.setText(e.getLabel());
        txtQty.setText(e.getValue()+"");
    }

    /**
     * Set the data for the day pie chart
     * @param entries
     */
    private void setPieChartData(List<PieEntry> entries){
        PieDataSet pieDataSet = new PieDataSet(entries,"");
        pieDataSet.setColors(ColorTemplate.LIBERTY_COLORS);
        PieData pieData = new PieData(pieDataSet);
        Description description = new Description();
        description.setText("");
        pieItems.setDescription(description);
        pieItems.setDrawSliceText(false);
        pieItems.getLegend().setTextColor(getResources().getColor(R.color.colorAccentDark));
        pieItems.getLegend().setFormSize(16);
        pieItems.getLegend().setTextSize(8);
        pieItems.getLegend().setForm(Legend.LegendForm.CIRCLE);
        pieItems.setHoleColor(getResources().getColor(R.color.colorWhite));
        pieItems.setCenterTextColor(getResources().getColor(R.color.colorAccentDark));
        pieItems.setData(pieData);
        pieItems.invalidate();
    }

    /** -------------------------------------------------------------------------------
     *
     */
    private class GetChartsInfo extends AsyncTask<DaoSession,Integer,ArrayList<PieEntry>> {

        private Context context;

        private GetChartsInfo(Context context){
            this.context = context;
        }

        @Override
        protected ArrayList<PieEntry> doInBackground(DaoSession... session) {
            DaoSession daoSession = session[0];
            ArrayList entriesPie = new ArrayList<PieEntry>();
            areas = session[0].getAreaDao().loadAll();
            for(int i = 0; i < areas.size(); i++){
                int areaCounter = 0;
                Area area = areas.get(i);
                List<User> owners = daoSession.getUserDao().queryBuilder()
                        .where(UserDao.Properties.AreaId.eq(area.getId())).list();
                for(int j= 0; j < owners.size();j++){
                    User user = owners.get(j);
                    Long orders = daoSession.getOrderDao().queryBuilder()
                            .where(OrderDao.Properties.Owner.eq(user.getId())).count();
                    areaCounter += orders;
                }
                PieEntry entry = new PieEntry(areaCounter,area.getName());
                entriesPie.add(entry);
                labelsPie.add(area.getName());
            }

            return entriesPie;
        }

        @Override
        protected void onPostExecute(ArrayList<PieEntry> list) {
            super.onPostExecute(list);
            setPieChartData(list);
            if(list != null && list.size() > 0) {
                selectedArea = areas.get(0);
                setUpSelectedAreaView((PieEntry) list.get(0));
            }
        }
    }

}
