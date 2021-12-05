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
import android.widget.RatingBar;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

public class EvaluationActivity2 extends AppCompatActivity implements View.OnClickListener {

    private Button eval_btn2;
    private Spinner eval_spinner_menu;
    private RatingBar eval_ratingBar;
    private ImageView home_btn, setting_btn;

    private DatabaseReference databaseMenu = FirebaseDatabase.getInstance().getReference("Menu");
    private DatabaseReference databaseRate = FirebaseDatabase.getInstance().getReference("Users");
    private FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
    ArrayAdapter menu_adapter;
    ArrayList<String> menuList;
    String menu;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_evaluation2);

        home_btn = (ImageView) findViewById(R.id.home_btn);
        setting_btn = (ImageView) findViewById(R.id.setting_btn);
        eval_btn2 = (Button) findViewById(R.id.eval_btn2);
        eval_spinner_menu = (Spinner) findViewById(R.id.eval_spinner_menu);
        eval_ratingBar = (RatingBar) findViewById(R.id.eval_ratingBar);

        menuList = new ArrayList<>();


        Intent intent = getIntent();
        String day = intent.getStringExtra("day");
        String restaurant = intent.getStringExtra("restaurant");

        if (restaurant.equals("명진당")) {
            menuList.clear();
        } else if (restaurant.equals("학생회관")) {
            menuList.clear();
        } else if (restaurant.equals("교직원 식당")) {
            menuList.clear();
            getTeachersMenu(day);
        }


        menu_adapter = new ArrayAdapter(this, R.layout.menu_spinner, menuList);
        Log.v("size", String.valueOf(menuList));
        menu_adapter.setDropDownViewResource(R.layout.menu_spinner);
        eval_spinner_menu.setAdapter(menu_adapter);


        // 메뉴 선택 spinner
        eval_spinner_menu.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                menu = menuList.get(position);
                //Toast.makeText(getApplicationContext(), menuList.get(position), Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        // 버튼 클릭 리스너
        home_btn.setOnClickListener(this);
        setting_btn.setOnClickListener(this);
        eval_btn2.setOnClickListener(this);

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

            case R.id.eval_btn2:
                int rating = (int) eval_ratingBar.getRating();
                evaluate(menu, rating);
                break;
        }
    }

    // DB에서 메뉴 리스트 가져오기
    private void getTeachersMenu(String dayCode) {
        databaseMenu.child("Teachers").child(dayCode).child("Lunch").get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DataSnapshot> task) {
                if (!task.isSuccessful()) {
                    Log.e("firebase", "Error getting data", task.getException());
                } else {
                    String result = String.valueOf(task.getResult().getValue());
                    String[] list = result.split("\\n");
                    for (String l : list) {
                        if (!menuList.contains(l)) {
                            menuList.add(l.trim());
                        }
                        Log.v("lunch", l);
                    }
                }
                menu_adapter.notifyDataSetChanged();
            }
        });

        databaseMenu.child("Teachers").child(dayCode).child("Dinner").get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DataSnapshot> task) {
                if (!task.isSuccessful()) {
                    Log.e("firebase", "Error getting data", task.getException());
                } else {
                    String result = String.valueOf(task.getResult().getValue());
                    String[] list = result.split("\\n");
                    for (String l : list) {
                        if (!menuList.contains(l)) {
                            menuList.add(l.trim());
                        }
                    }
                }
                menu_adapter.notifyDataSetChanged();
            }
        });
    }

    // 평가한 것 DB에 저장
    public void evaluate(String menu, int rate) {
        String uid = firebaseAuth.getUid();
        databaseRate.child(uid).child("Eval").child(menu).setValue(rate);
        Toast.makeText(getApplicationContext(), menu + ": " + rate + "점으로 평가 완료!", Toast.LENGTH_SHORT).show();
    }

}