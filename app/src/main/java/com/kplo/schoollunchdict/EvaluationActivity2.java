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
import android.widget.Spinner;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class EvaluationActivity2 extends AppCompatActivity {

    private Button eval_btn2;
    private Spinner eval_spinner_menu;

    private DatabaseReference database = FirebaseDatabase.getInstance().getReference("Menu");
    ArrayAdapter menu_adapter;
    ArrayList<String> menuList;
    String result = "";
    String[] menu;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_evaluation2);

        eval_btn2 = (Button) findViewById(R.id.eval_btn2);
        eval_spinner_menu = (Spinner) findViewById(R.id.eval_spinner_menu);

        menuList = new ArrayList<>();


        Intent intent = getIntent();
        String day = intent.getStringExtra("day");
        String restaurant = intent.getStringExtra("restaurant");

        if(restaurant.equals("명진당")){
            menuList.clear();
        }
        else if(restaurant.equals("학생회관")){
            menuList.clear();
        }
        else if(restaurant.equals("교직원 식당")){
            menuList.clear();
            getTeachersMenu(day);
        }


        menu_adapter = new ArrayAdapter(this, R.layout.menu_spinner, menuList);
        Log.v("size", String.valueOf(menuList));
        menu_adapter.setDropDownViewResource(R.layout.menu_spinner);
        eval_spinner_menu.setAdapter(menu_adapter);

        eval_spinner_menu.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                Toast.makeText(getApplicationContext(), menuList.get(position), Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

    }


    private void getTeachersMenu(String dayCode){

        database.child("Teachers").child(dayCode).child("Lunch").get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DataSnapshot> task) {
                if (!task.isSuccessful()) {
                    Log.e("firebase", "Error getting data", task.getException());
                }
                else {
                    String result = String.valueOf(task.getResult().getValue());
                    String[] list = result.split("\\n");
                    for (String l : list) {
                        if(!menuList.contains(l)){
                            menuList.add(l.trim());
                        }
                        Log.v("lunch", l);
                    }
                }
                menu_adapter.notifyDataSetChanged();
            }
        });

        database.child("Teachers").child(dayCode).child("Dinner").get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DataSnapshot> task) {
                if (!task.isSuccessful()) {
                    Log.e("firebase", "Error getting data", task.getException());
                }
                else {
                    String result = String.valueOf(task.getResult().getValue());
                    String[] list = result.split("\\n");
                    for (String l : list) {
                        if(!menuList.contains(l)){
                            menuList.add(l.trim());
                        }
                    }
                }
                menu_adapter.notifyDataSetChanged();
            }
        });
    }
}