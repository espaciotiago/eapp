package com.ufo.mobile.eapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
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
import com.github.mikephil.charting.highlight.Highlight;
import com.github.mikephil.charting.listener.OnChartValueSelectedListener;
import com.github.mikephil.charting.utils.ColorTemplate;

import java.util.ArrayList;
import java.util.List;

import ModelManager.Area;

public class ChartsActivity extends AppCompatActivity {

    private PieChart pieItems;
    private TextView txtArea,txtQty;
    private Button btnDetails;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_charts);

        //Set back button on action bar
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        pieItems = findViewById(R.id.piechart_items);
        txtArea = findViewById(R.id.txt_area_selected);
        txtQty = findViewById(R.id.txt_area_request);
        btnDetails = findViewById(R.id.btn_details);

        /*
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
        */

        ArrayList entriesPie = new ArrayList<PieEntry>();
        ArrayList labelsPie = new ArrayList<String>();
        for (int i = 0; i < 10;i++){
            int range = (10 - 1) + 1;
            int qty = (int)(Math.random() * range) + 1;
            PieEntry entry = new PieEntry(qty,"Area "+ i);
            entriesPie.add(entry);
            labelsPie.add("Area "+ i);
        }
        setPieChartData(entriesPie);
        setUpSelectedAreaView((PieEntry) entriesPie.get(0));

        //Actions
        pieItems.setOnChartValueSelectedListener(new OnChartValueSelectedListener() {
            @Override
            public void onValueSelected(Entry e, Highlight h) {
                //TODO Obtener el area seleccionada
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
     * Set the charts of a week
     * @param entries
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
     */

    /**
     * Set the charts of a week
     * @param entries
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
     */

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

}
