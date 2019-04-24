package com.ufo.mobile.eapp;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.Description;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;

import java.util.ArrayList;
import java.util.List;

public class ChartsActivity extends AppCompatActivity {

    private LineChart lineChartItems;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_charts);

        ArrayList entries = new ArrayList<Entry>();
        for (int i = 0; i < 10;i++){
            double promedio = 1+i;
            Entry entry = new Entry(i,(float)promedio);
            entries.add(entry);

        }

        setLineChart(entries);
    }

    /**
     * Set the charts of a week
     * @param entries
     */
    private void setLineChart(List<Entry> entries){
        LineDataSet dataSet = new LineDataSet(entries, "Moods");
        dataSet.setMode(LineDataSet.Mode.CUBIC_BEZIER);
        dataSet.setDrawFilled(true);
        dataSet.setColor(getResources().getColor(R.color.colorPrimary));
        dataSet.setValueTextColor(getResources().getColor(R.color.colorAccent));
        LineData lineData = new LineData(dataSet);
        Description description = new Description();
        description.setText("");
        lineChartItems.getAxisLeft().setAxisMaxValue(new Float(5));
        lineChartItems.getAxisLeft().setAxisMinValue(0);
        lineChartItems.getAxisLeft().setDrawGridLines(false);
        lineChartItems.getXAxis().setDrawGridLines(false);
        lineChartItems.getXAxis().setPosition(XAxis.XAxisPosition.BOTTOM);
        lineChartItems.getAxisRight().setDrawGridLines(false);
        lineChartItems.getAxisRight().setEnabled(false);
        lineChartItems.setDescription(description);
        lineChartItems.setData(lineData);
        lineChartItems.invalidate();
    }
}
