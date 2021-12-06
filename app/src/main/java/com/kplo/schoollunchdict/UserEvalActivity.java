package com.kplo.schoollunchdict;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.media.Image;
import android.os.Bundle;
import android.text.Editable;
import android.text.InputType;
import android.text.TextWatcher;
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
import java.util.List;

public class UserEvalActivity extends AppCompatActivity implements View.OnClickListener {


    private EditText user_eval_et_search;
    private RecyclerView user_eval_recyclerView;
    private RecyclerView.LayoutManager layoutManager;
    ArrayList<String> menuList, searchList;
    UserEvalAdapter adapter;

    private final DatabaseReference database = FirebaseDatabase.getInstance().getReference("Users");
    private final FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_eval);

        ImageView home_btn = (ImageView) findViewById(R.id.home_btn);
        ImageView setting_btn = (ImageView) findViewById(R.id.setting_btn);
        user_eval_et_search = (EditText) findViewById(R.id.user_eval_et_search);
        user_eval_et_search.setInputType(InputType.TYPE_TEXT_FLAG_NO_SUGGESTIONS);

        user_eval_recyclerView = (RecyclerView) findViewById(R.id.user_eval_recyclerView);
        user_eval_recyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(this);
        user_eval_recyclerView.setLayoutManager(layoutManager);


        menuList = new ArrayList<>();
        searchList = new ArrayList<>();

        getUserEvalMenu();

        home_btn.setOnClickListener(this);
        setting_btn.setOnClickListener(this);

        adapter = new UserEvalAdapter(menuList, this);
        user_eval_recyclerView.setAdapter(adapter);

        user_eval_et_search.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                String searchText = user_eval_et_search.getText().toString().trim();
                search(searchText);
            }
        });


    }

    private void getUserEvalMenu() {
        String uid = firebaseAuth.getUid();
        database.child(uid).child("Eval").addListenerForSingleValueEvent(new ValueEventListener() {
            int i;

            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                menuList.clear();
                for (DataSnapshot data : snapshot.getChildren()) {
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

    //검색기능(순차 탐색)
    private void search(String searchText) {
        searchList.clear();
        // 문자 입력이 없을때는 모든 데이터를 보여준다
        if (searchText.length() == 0) {
            searchList.addAll(menuList);
            // 문자 입력을 할때
        } else {
            for (String str : menuList) {
                if (str.contains(searchText)) {
                    searchList.add(str);
                }
            }
        }

        adapter.searchList(searchList);
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