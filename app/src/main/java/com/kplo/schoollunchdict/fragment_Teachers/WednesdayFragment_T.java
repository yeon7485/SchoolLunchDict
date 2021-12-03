package com.kplo.schoollunchdict.fragment_Teachers;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.kplo.schoollunchdict.R;

import java.util.ArrayList;


public class WednesdayFragment_T extends Fragment {

    ViewPager viewPager;
    TextView restaurant1, restaurant2, menu1, menu2;
    public ArrayList<String> menuList = new ArrayList<>();
    private DatabaseReference database = FirebaseDatabase.getInstance().getReference("Menu/Teachers/4");

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_day, container, false);
        restaurant1 = (TextView) view.findViewById(R.id.day_restaurant1);
        restaurant2 = (TextView) view.findViewById(R.id.day_restaurant2);
        menu1 = (TextView) view.findViewById(R.id.day_menu1);
        menu2 = (TextView) view.findViewById(R.id.day_menu2);

        restaurant1.setText("점심");
        restaurant2.setText("저녁");

        showLunchMenu();
        showDinnerMenu();


        return view;
    }


    private void showLunchMenu(){
        database.child("Lunch").get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DataSnapshot> task) {
                if (!task.isSuccessful()) {
                    Log.e("firebase", "Error getting data", task.getException());
                }
                else {
                    menu1.setText(String.valueOf(task.getResult().getValue()));
                }
            }
        });
    }

    private void showDinnerMenu(){
        database.child("Dinner").get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DataSnapshot> task) {
                if (!task.isSuccessful()) {
                    Log.e("firebase", "Error getting data", task.getException());
                }
                else {
                    menu2.setText(String.valueOf(task.getResult().getValue()));
                }
            }
        });
    }
}
