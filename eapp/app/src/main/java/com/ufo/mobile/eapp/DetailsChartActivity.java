package com.ufo.mobile.eapp;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.components.Description;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter;

import java.util.ArrayList;
import java.util.List;

public class DetailsChartActivity extends AppCompatActivity {

    private BarChart barChart;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details_chart);

        //Set back button on action bar
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        barChart = findViewById(R.id.chart_items);

        ArrayList entries = new ArrayList<BarEntry>();
        ArrayList labels = new ArrayList<String>();
        for (int i = 0; i < 24;i++){
            int range = (10 - 1) + 1;
            int qty = (int)(Math.random() * range) + 1;
            BarEntry entry = new BarEntry(i,qty,"Item "+ i);
            entries.add(entry);
            labels.add("Item "+ i);
        }
        setBarChart(entries,labels);

    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
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
}
