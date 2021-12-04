package com.kplo.schoollunchdict;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.io.IOException;
import java.util.ArrayList;

public class EvaluationActivity1 extends AppCompatActivity implements View.OnClickListener{

    private TextView tv_item_spinner;
    private Spinner eval_spinner_day, eval_spinner_restaurant;
    private Button eval_btn1;
    private ImageView home_btn, setting_btn;
    ArrayAdapter day_adapter, restaurant_adapter;
    String[] menu = new String[6];

    String selectDay = "월";
    String selectRestaurant = "교직원 식당";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_evaluation1);

        String[] days = {"월","화","수","목","금"};
        String[] restaurant = {"명진당", "학생회관","교직원 식당"};

        home_btn = (ImageView) findViewById(R.id.home_btn);
        setting_btn = (ImageView) findViewById(R.id.setting_btn);

        eval_spinner_day = (Spinner) findViewById(R.id.eval_spinner_day);
        eval_spinner_restaurant = (Spinner) findViewById(R.id.eval_spinner_restaurant);
        eval_btn1 = (Button) findViewById(R.id.eval_btn1);

        day_adapter = new ArrayAdapter(this, R.layout.day_spinner, days);
        day_adapter.setDropDownViewResource(R.layout.day_spinner);
        eval_spinner_day.setAdapter(day_adapter);

        restaurant_adapter = new ArrayAdapter(this, R.layout.restaurant_spinner, restaurant);
        restaurant_adapter.setDropDownViewResource(R.layout.restaurant_spinner);
        eval_spinner_restaurant.setAdapter(restaurant_adapter);

        // 버튼 클릭 리스너
        home_btn.setOnClickListener(this);
        setting_btn.setOnClickListener(this);
        eval_btn1.setOnClickListener(this);


        // 요일 선택 spinner
        eval_spinner_day.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                selectDay = days[position];
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                Toast.makeText(getApplicationContext(), "요일이 선택되지 않았습니다.", Toast.LENGTH_SHORT).show();
            }
        });


        // 식당 선택 spinner
        eval_spinner_restaurant.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                selectRestaurant = restaurant[position];
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                Toast.makeText(getApplicationContext(), "식당이 선택되지 않았습니다.", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private String getDayCode(String selectDay){
        switch(selectDay){
            case "월":
                return "0";
            case "화":
                return "2";
            case "수":
                return "4";
            case "목":
                return "6";
            case "금":
                return "8";
            default:
                return "-1";
        }
    }

    @Override
    public void onClick(View v) {
        Intent intent;
        switch (v.getId()) {
            case R.id.home_btn:
                intent = new Intent(this, HomeActivity.class);
                startActivity(intent);
                finish();
                break;

            case R.id.setting_btn:
                intent = new Intent(this, SettingActivity.class);
                startActivity(intent);
                break;

            case R.id.eval_btn1:
                intent = new Intent(getApplicationContext(), EvaluationActivity2.class);
                intent.putExtra("day", getDayCode(selectDay));
                intent.putExtra("restaurant", selectRestaurant);
                startActivity(intent);
                break;
        }
    }
}