package com.kplo.schoollunchdict;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
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
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class EvaluationActivity2 extends AppCompatActivity implements View.OnClickListener {

    private Button eval_btn2;
    private Spinner eval_spinner_menu;
    private RatingBar eval_ratingBar;
    private ImageView home_btn, setting_btn, favorite_btn;

    private DatabaseReference databaseMenu = FirebaseDatabase.getInstance().getReference("Menu");
    private DatabaseReference databaseUsers = FirebaseDatabase.getInstance().getReference("Users");
    private FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
    ArrayAdapter menu_adapter;
    ArrayList<String> menuList;
    ArrayList<String> favoritesList;
    String selectMenu;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_evaluation2);

        home_btn = (ImageView) findViewById(R.id.home_btn);
        setting_btn = (ImageView) findViewById(R.id.setting_btn);
        eval_btn2 = (Button) findViewById(R.id.eval_btn2);
        eval_spinner_menu = (Spinner) findViewById(R.id.eval_spinner_menu);
        eval_ratingBar = (RatingBar) findViewById(R.id.eval_ratingBar);
        favorite_btn = (ImageView) findViewById(R.id.favorite_btn);

        menuList = new ArrayList<>();
        favoritesList = new ArrayList<>();
        getFavorites();

        Intent intent = getIntent();
        String day = intent.getStringExtra("day");
        String restaurant = intent.getStringExtra("restaurant");

        if (restaurant.equals("?????????")) {
            menuList.clear();
        } else if (restaurant.equals("????????????")) {
            menuList.clear();
        } else if (restaurant.equals("????????? ??????")) {
            menuList.clear();
            getTeachersMenu(day);
        }


        menu_adapter = new ArrayAdapter(this, R.layout.menu_spinner, menuList);
        Log.v("size", String.valueOf(menuList));
        menu_adapter.setDropDownViewResource(R.layout.menu_spinner);
        eval_spinner_menu.setAdapter(menu_adapter);


        // ?????? ?????? spinner
        eval_spinner_menu.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                selectMenu = menuList.get(position);
                Log.v("check Favorite", String.valueOf(checkFavorite(selectMenu)));
                if(checkFavorite(selectMenu)){
                    favorite_btn.setBackgroundResource(R.drawable.ic_full_heart);
                }
                else{
                    favorite_btn.setBackgroundResource(R.drawable.ic_empty_heart);
                }
                //Toast.makeText(getApplicationContext(), menuList.get(position), Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        // ?????? ?????? ?????????
        home_btn.setOnClickListener(this);
        setting_btn.setOnClickListener(this);
        eval_btn2.setOnClickListener(this);
        favorite_btn.setOnClickListener(this);

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
                evaluate(selectMenu, rating);
                break;

            case R.id.favorite_btn:
                if (checkFavorite(selectMenu)) {
                    deleteFavorite();
                    favorite_btn.setBackgroundResource(R.drawable.ic_empty_heart);
                    Toast.makeText(getApplicationContext(), selectMenu + " ???????????? ??????", Toast.LENGTH_SHORT).show();
                }
                else{
                    setFavorite();
                    favorite_btn.setBackgroundResource(R.drawable.ic_full_heart);
                    Toast.makeText(getApplicationContext(), selectMenu + " ???????????? ??????!", Toast.LENGTH_SHORT).show();
                }
                break;

        }
    }

    // DB?????? ?????? ????????? ????????????
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

    // ????????? ??? DB??? ??????
    private void evaluate(String menu, int rate) {
        String uid = firebaseAuth.getUid();
        databaseUsers.child(uid).child("Eval").child(menu).setValue(rate);
        Toast.makeText(getApplicationContext(), menu + ": " + rate + "????????? ?????? ??????!", Toast.LENGTH_SHORT).show();
    }


    private void getFavorites() {
        String uid = firebaseAuth.getUid();
        databaseUsers.child(uid).child("Favorites").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                favoritesList.clear();
                for (DataSnapshot data : snapshot.getChildren()) {
                    String f_menu = data.getKey();
                    favoritesList.add(f_menu);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    private void setFavorite() {
        String uid = firebaseAuth.getUid();
        databaseUsers.child(uid).child("Favorites").child(selectMenu).setValue("true");
        getFavorites();
    }

    private void deleteFavorite(){
        String uid = firebaseAuth.getUid();
        databaseUsers.child(uid).child("Favorites").child(selectMenu).removeValue();
        getFavorites();
    }

    private boolean checkFavorite(String menu){
        if(favoritesList.contains(menu)) return true;
        else return false;
    }


}