package com.kplo.schoollunchdict;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.kplo.schoollunchdict.menu.MyongJinDang;
import com.kplo.schoollunchdict.menu.StudentsHall;
import com.kplo.schoollunchdict.menu.Teachers;

public class MenuActivity extends AppCompatActivity implements View.OnClickListener{

    private TextView menu_btn_myongJinDang, menu_btn_studentsHall, menu_btn_teachers;
    private ImageView home_btn, setting_btn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);

        home_btn = (ImageView) findViewById(R.id.home_btn);
        setting_btn = (ImageView) findViewById(R.id.setting_btn);
        menu_btn_myongJinDang = (TextView) findViewById(R.id.menu_btn_myongJinDang);
        menu_btn_studentsHall = (TextView) findViewById(R.id.menu_btn_studentsHall);
        menu_btn_teachers = (TextView) findViewById(R.id.menu_btn_teachers);

        home_btn.setOnClickListener(this);
        setting_btn.setOnClickListener(this);
        menu_btn_myongJinDang.setOnClickListener(this);
        menu_btn_studentsHall.setOnClickListener(this);
        menu_btn_teachers.setOnClickListener(this);
    }

    @SuppressLint("NonConstantResourceId")
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
            case R.id.menu_btn_myongJinDang:
                intent = new Intent(this, MyongJinDang.class);
                startActivity(intent);
                break;
            case R.id.menu_btn_studentsHall:
                intent = new Intent(this, StudentsHall.class);
                startActivity(intent);
                break;
            case R.id.menu_btn_teachers:
                intent = new Intent(this, Teachers.class);
                startActivity(intent);
                break;
        }
    }
}