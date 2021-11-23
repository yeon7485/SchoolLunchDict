package com.kplo.schoollunchdict;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class UserActivity extends AppCompatActivity implements View.OnClickListener {

    private ImageView home_btn, setting_btn;
    private TextView user_tv_nickname, user_tv_email;
    private TextView user_btn_edit_profile, user_btn_evaluation, user_btn_favorites, user_btn_logout;

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
        user_btn_edit_profile = (TextView) findViewById(R.id.user_btn_edit_profile);
        user_btn_evaluation = (TextView) findViewById(R.id.user_btn_evaluation);
        user_btn_favorites = (TextView) findViewById(R.id.user_btn_favorites);
        user_btn_logout = (TextView) findViewById(R.id.user_btn_logout);

        // 데이터베이스 연결
        firebaseAuth = FirebaseAuth.getInstance();
        database = FirebaseDatabase.getInstance().getReference("Users");
        // 닉네임, 비밀번호 표시
        getProfile();

        // 클릭 리스너 연결
        home_btn.setOnClickListener(this);
        setting_btn.setOnClickListener(this);
        user_btn_edit_profile.setOnClickListener(this);
        user_btn_evaluation.setOnClickListener(this);
        user_btn_favorites.setOnClickListener(this);
        user_btn_logout.setOnClickListener(this);


    }

    @SuppressLint("NonConstantResourceId")
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
                finish();
                break;

            case R.id.user_btn_edit_profile:
                intent = new Intent(this, EditProfileActivity.class);
                startActivity(intent);
                finish();
                break;

            case R.id.user_btn_evaluation:
                break;

            case R.id.user_btn_favorites:
                break;

            case R.id.user_btn_logout:
                firebaseAuth.signOut();
                intent = new Intent(this, LoginActivity.class);
                startActivity(intent);
                finish();
                break;
        }
    }

    public void getProfile() {
        String uid = firebaseAuth.getUid();
        database.child(uid).child("nickname").get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DataSnapshot> task) {
                if (!task.isSuccessful()) {
                    Log.e("firebase", "Error getting nickname", task.getException());
                } else {
                    String nickname = String.valueOf(task.getResult().getValue());
                    user_tv_nickname.setText(nickname);
                }
            }
        });

        database.child(uid).child("email").get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DataSnapshot> task) {
                if (!task.isSuccessful()) {
                    Log.e("firebase", "Error getting nickname", task.getException());
                } else {
                    String email = String.valueOf(task.getResult().getValue());
                    user_tv_email.setText(email);
                }
            }
        });
    }
}