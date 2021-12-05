package com.kplo.schoollunchdict;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;

import java.util.ArrayList;

public class RankingActivity extends AppCompatActivity {

    private PieChart ranking_pie_chart;
    //int[] colorArray = new int[] {R.color.main_color_red, R.color.main_color_orange, R.color.main_color_yellow};
    int[] colorArray = new int[] { 0xFFD62828, 0xFFF77F00, 0xFFFCBF49 };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ranking);



        PieDataSet pieDataSet = new PieDataSet(putData(), "BEST 3");
        pieDataSet.setColors(colorArray);

        PieData pieData = new PieData(pieDataSet);

        ranking_pie_chart.setUsePercentValues(true);
        pieData.setValueTextSize(20);
        pieData.setValueTextColor(0xFF4A0C0C);

        ranking_pie_chart.getLegend().setEnabled(false);
        ranking_pie_chart.getDescription().setEnabled(false);

        ranking_pie_chart.setCenterText("BEST 3");
        ranking_pie_chart.setCenterTextSize(20);
        ranking_pie_chart.setCenterTextColor(0xFF4A0C0C);
        ranking_pie_chart.setData(pieData);
        ranking_pie_chart.invalidate();
    }

    private ArrayList<PieEntry> putData(){
        ArrayList<PieEntry> data = new ArrayList<>();

        data.add(new PieEntry(40, "메뉴1"));
        data.add(new PieEntry(30, "메뉴2"));
        data.add(new PieEntry(30, "메뉴3"));
        return data;
    }
}