package com.kplo.schoollunchdict;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.ktx.Firebase;

public class UserActivity extends AppCompatActivity implements View.OnClickListener{

    private ImageView home_btn, setting_btn;
    private TextView user_tv_nickname, user_tv_email;
    private TextView user_btn_change_nick, user_btn_evaluation, user_btn_favorites, user_btn_logout;

    private FirebaseAuth firebaseAuth;
    private DatabaseReference database;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user);

        home_btn = (ImageView) findViewById(R.id.home_btn);
        setting_btn = (ImageView) findViewById(R.id.setting_btn);
        user_tv_nickname = (TextView) findViewById(R.id.user_tv_nickname);
        user_tv_email = (TextView) findViewById(R.id.user_tv_email);
        user_btn_change_nick = (TextView) findViewById(R.id.user_btn_change_nick);
        user_btn_evaluation = (TextView) findViewById(R.id.user_btn_evaluation);
        user_btn_favorites = (TextView) findViewById(R.id.user_btn_favorites);
        user_btn_logout = (TextView) findViewById(R.id.user_btn_logout);

        firebaseAuth = FirebaseAuth.getInstance();
        database = FirebaseDatabase.getInstance().getReference();



    }

    @Override
    public void onClick(View v) {
        switch(v.getId()){
            case R.id.home_btn:
                Intent intent = new Intent(this, HomeActivity.class);
                finish();
                startActivity(intent);
                break;
            case R.id.setting_btn:
                break;
            case R.id.user_tv_nickname:
                break;
            case R.id.user_tv_email:
                break;
            case R.id.user_btn_change_nick:
                break;
            case R.id.user_btn_evaluation:
                break;
            case R.id.user_btn_favorites:
                break;
            case R.id.user_btn_logout:
                break;
        }
    }

    public void findNickname(){


    }
}