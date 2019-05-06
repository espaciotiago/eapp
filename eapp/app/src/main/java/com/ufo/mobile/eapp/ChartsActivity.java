package com.ufo.mobile.eapp;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

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

import java.util.ArrayList;
import java.util.List;

public class ChartsActivity extends AppCompatActivity {

    private BarChart chartItems;
    private LineChart lineItems;
    private PieChart pieItems;
    private TextView txtLine,txtBars,txtPie;
    private View viewLine,viewBars,viewPie;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_charts);
        //Set back button on action bar
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        chartItems = (BarChart) findViewById(R.id.barchart_items);
        lineItems = (LineChart) findViewById(R.id.linechart_items);
        pieItems = (PieChart) findViewById(R.id.piechart_items);
        txtLine = (TextView) findViewById(R.id.txt_line);
        txtBars = (TextView) findViewById(R.id.txt_bars);
        txtPie = (TextView) findViewById(R.id.txt_pie);
        viewLine = (View) findViewById(R.id.view_line);
        viewBars = (View) findViewById(R.id.view_bars);
        viewPie = (View) findViewById(R.id.view_pie);

        ArrayList entries = new ArrayList<BarEntry>();
        ArrayList labels = new ArrayList<String>();
        for (int i = 0; i < 24;i++){
            int range = (10 - 1) + 1;
            int qty = (int)(Math.random() * range) + 1;
            BarEntry entry = new BarEntry(i,qty,"Area "+ i);
            entries.add(entry);
            labels.add("Area "+ i);
        }
        setBarChart(entries,labels);

        ArrayList entriesLine = new ArrayList<Entry>();
        ArrayList labelsLine = new ArrayList<String>();
        for (int i = 0; i < 24;i++){
            int range = (10 - 1) + 1;
            int qty = (int)(Math.random() * range) + 1;
            Entry entry = new Entry(i,qty,"Area "+ i);
            entriesLine.add(entry);
            labelsLine.add("Area "+ i);
        }
        setLineChart(entriesLine);

        int[] colors = new int[]{
                R.color.colorYellow,
                R.color.colorGreen,
                R.color.colorPrimary,
                R.color.colorRed,
        };
        ArrayList entriesPie = new ArrayList<PieEntry>();
        ArrayList labelsPie = new ArrayList<String>();
        for (int i = 0; i < 4;i++){
            int range = (10 - 1) + 1;
            int qty = (int)(Math.random() * range) + 1;
            PieEntry entry = new PieEntry(qty,"Area "+ i);
            entriesPie.add(entry);
            labelsPie.add("Area "+ i);
        }
        setPieChartData(entriesPie,colors);

        setupActionTabs();
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

    private void setupActionTabs(){
        txtLine.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                lineItems.setVisibility(View.VISIBLE);
                chartItems.setVisibility(View.GONE);
                pieItems.setVisibility(View.GONE);

                txtLine.setTextColor(getResources().getColor(R.color.colorPrimary));
                txtBars.setTextColor(getResources().getColor(R.color.colorAccentDark));
                txtPie.setTextColor(getResources().getColor(R.color.colorAccentDark));

                viewLine.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
                viewBars.setBackgroundColor(getResources().getColor(R.color.colorAccent));
                viewPie.setBackgroundColor(getResources().getColor(R.color.colorAccent));
            }
        });

        txtBars.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                lineItems.setVisibility(View.GONE);
                chartItems.setVisibility(View.VISIBLE);
                pieItems.setVisibility(View.GONE);

                txtLine.setTextColor(getResources().getColor(R.color.colorAccentDark));
                txtBars.setTextColor(getResources().getColor(R.color.colorPrimary));
                txtPie.setTextColor(getResources().getColor(R.color.colorAccentDark));

                viewLine.setBackgroundColor(getResources().getColor(R.color.colorAccent));
                viewBars.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
                viewPie.setBackgroundColor(getResources().getColor(R.color.colorAccent));
            }
        });

        txtPie.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                lineItems.setVisibility(View.GONE);
                chartItems.setVisibility(View.GONE);
                pieItems.setVisibility(View.VISIBLE);

                txtLine.setTextColor(getResources().getColor(R.color.colorAccentDark));
                txtBars.setTextColor(getResources().getColor(R.color.colorAccentDark));
                txtPie.setTextColor(getResources().getColor(R.color.colorPrimary));

                viewLine.setBackgroundColor(getResources().getColor(R.color.colorAccent));
                viewBars.setBackgroundColor(getResources().getColor(R.color.colorAccent));
                viewPie.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
            }
        });

    }

    /**
     * Set the charts of a week
     * @param entries
     */
    private void setBarChart(List<BarEntry> entries, List<String> labels){
        BarDataSet dataSet = new BarDataSet(entries, "Ordenes por area");
        dataSet.setColor(getResources().getColor(R.color.colorPrimaryDark));
        BarData data = new BarData(dataSet);
        Description description = new Description();
        description.setText("");
        chartItems.getAxisLeft().setDrawGridLines(false);
        chartItems.getXAxis().setDrawGridLines(false);
        chartItems.getXAxis().setPosition(XAxis.XAxisPosition.BOTTOM);
        chartItems.getAxisRight().setDrawGridLines(false);
        chartItems.getAxisRight().setEnabled(false);
        chartItems.setDescription(description);
        chartItems.getXAxis().setValueFormatter(new IndexAxisValueFormatter(labels));
        chartItems.setData(data);
        chartItems.invalidate();
    }

    /**
     * Set the charts of a week
     * @param entries
     */
    private void setLineChart(List<Entry> entries){
        LineDataSet dataSet = new LineDataSet(entries, "Pedidos por area");
        dataSet.setMode(LineDataSet.Mode.CUBIC_BEZIER);
        dataSet.setDrawFilled(true);
        dataSet.setColor(getResources().getColor(R.color.colorPrimary));
        dataSet.setFillColor(getResources().getColor(R.color.colorSoftGray));
        dataSet.setValueTextColor(getResources().getColor(R.color.colorAccent));
        LineData lineData = new LineData(dataSet);
        Description description = new Description();
        description.setText("");
        //lineItems.getAxisLeft().setAxisMaxValue(new Float(5));
        lineItems.getAxisLeft().setAxisMinValue(0);
        lineItems.getAxisLeft().setDrawGridLines(false);
        lineItems.getXAxis().setDrawGridLines(false);
        lineItems.getXAxis().setPosition(XAxis.XAxisPosition.BOTTOM);
        lineItems.getAxisRight().setDrawGridLines(false);
        lineItems.getAxisRight().setEnabled(false);
        lineItems.setDescription(description);
        lineItems.setData(lineData);
        lineItems.invalidate();
    }

    /**
     * Set the data for the day pie chart
     * @param entries
     */
    private void setPieChartData(List<PieEntry> entries,int[] colors){
        PieDataSet pieDataSet = new PieDataSet(entries,"");
        pieDataSet.setColors(colors,this);
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

}
