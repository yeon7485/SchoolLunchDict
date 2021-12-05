package com.kplo.schoollunchdict;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.media.Image;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class UserEvalActivity extends AppCompatActivity implements View.OnClickListener{


    private ImageView home_btn, setting_btn, search_btn;
    private EditText user_eval_et_search;
    private RecyclerView user_eval_recyclerView;
    private RecyclerView.LayoutManager layoutManager;
    ArrayList<String> menuList, searchList;
    UserEvalAdapter adapter;

    private DatabaseReference database = FirebaseDatabase.getInstance().getReference("Users");
    private FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_eval);

        home_btn = (ImageView) findViewById(R.id.home_btn);
        setting_btn = (ImageView) findViewById(R.id.setting_btn);
        search_btn = (ImageView) findViewById(R.id.search_btn);
        user_eval_et_search = (EditText) findViewById(R.id.user_eval_et_search);

        user_eval_recyclerView = (RecyclerView) findViewById(R.id.user_eval_recyclerView);
        user_eval_recyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(this);
        user_eval_recyclerView.setLayoutManager(layoutManager);


        menuList = new ArrayList<>();

        getUserEvalMenu();


        adapter = new UserEvalAdapter(menuList, this);
        user_eval_recyclerView.setAdapter(adapter);



    }

    private void getUserEvalMenu(){
        String uid = firebaseAuth.getUid();
        database.child(uid).child("Eval").addListenerForSingleValueEvent(new ValueEventListener() {
            int i;
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                menuList.clear();
                for(DataSnapshot data : snapshot.getChildren()){
                    String menuRate = "";
                    String menu = data.getKey();
                    String rating = data.getValue().toString();
                    menuRate += menu + "  :  " + rating + "점";
                    menuList.add(menuRate);
                }
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

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

            case R.id.search_btn:

                break;
        }
    }


}