package com.kplo.schoollunchdict;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.media.Image;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;

import java.util.ArrayList;

public class UserEvalActivity extends AppCompatActivity implements View.OnClickListener{


    private ImageView home_btn, setting_btn;


    private PieChart user_eval_chart;
    //int[] colorArray = new int[] {R.color.main_color_red, R.color.main_color_orange, R.color.main_color_yellow};
    int[] colorArray = new int[] { 0xFFD62828, 0xFFF77F00, 0xFFFCBF49 };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_eval);

        home_btn = (ImageView) findViewById(R.id.home_btn);
        setting_btn = (ImageView) findViewById(R.id.setting_btn);
        user_eval_chart = (PieChart) findViewById(R.id.user_eval_chart);

        PieDataSet pieDataSet = new PieDataSet(putData(), "BEST 3");
        pieDataSet.setColors(colorArray);

        PieData pieData = new PieData(pieDataSet);

        user_eval_chart.setUsePercentValues(true);
        pieData.setValueTextSize(20);
        pieData.setValueTextColor(0xFF4A0C0C);

        user_eval_chart.getLegend().setEnabled(false);
        user_eval_chart.getDescription().setEnabled(false);

        user_eval_chart.setCenterText("BEST 3");
        user_eval_chart.setCenterTextSize(20);
        user_eval_chart.setCenterTextColor(0xFF4A0C0C);
        user_eval_chart.setData(pieData);
        user_eval_chart.invalidate();



    }
    private ArrayList<PieEntry> putData(){
        ArrayList<PieEntry> data = new ArrayList<>();

        data.add(new PieEntry(40, "메뉴1"));
        data.add(new PieEntry(30, "메뉴2"));
        data.add(new PieEntry(30, "메뉴3"));
        return data;
    }

    @Override
    public void onClick(View v) {
        Intent intent;
        switch (v.getId()) {
            case R.id.home_btn:
                intent = new Intent(this, HomeActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
                finish();
                break;

            case R.id.setting_btn:
                intent = new Intent(this, SettingActivity.class);
                startActivity(intent);
                break;
        }
    }
}