package com.kplo.schoollunchdict;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

public class HomeActivity extends AppCompatActivity implements View.OnClickListener {

    private ImageView home_btn, setting_btn, home_btn_menu, home_btn_evaluation, home_btn_rank, home_btn_community, home_btn_user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        home_btn = (ImageView) findViewById(R.id.home_btn);
        setting_btn = (ImageView) findViewById(R.id.setting_btn);
        home_btn_menu = (ImageView) findViewById(R.id.home_btn_menu);
        home_btn_evaluation = (ImageView) findViewById(R.id.home_btn_evaluation);
        home_btn_rank = (ImageView) findViewById(R.id.home_btn_rank);
        home_btn_community = (ImageView) findViewById(R.id.home_btn_community);
        home_btn_user = (ImageView) findViewById(R.id.home_btn_user);

        home_btn.setOnClickListener(this);
        setting_btn.setOnClickListener(this);
        home_btn_menu.setOnClickListener(this);
        home_btn_evaluation.setOnClickListener(this);
        home_btn_rank.setOnClickListener(this);
        home_btn_community.setOnClickListener(this);
        home_btn_user.setOnClickListener(this);

    }

    @SuppressLint("NonConstantResourceId")
    @Override
    public void onClick(View v) {
        Intent intent;
        switch (v.getId()) {
            case R.id.home_btn:
                break;
            case R.id.setting_btn:
                intent = new Intent(this, SettingActivity.class);
                startActivity(intent);
                break;
            case R.id.home_btn_menu:
                intent = new Intent(this, MenuActivity.class);
                startActivity(intent);
                break;
            case R.id.home_btn_evaluation:
                intent = new Intent(this, EvaluationActivity.class);
                startActivity(intent);
                break;
            case R.id.home_btn_rank:
                break;
            case R.id.home_btn_community:
                break;
            case R.id.home_btn_user:
                intent = new Intent(this, UserActivity.class);
                startActivity(intent);
                break;
        }
    }

}