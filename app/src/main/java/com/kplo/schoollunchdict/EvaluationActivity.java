package com.kplo.schoollunchdict;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.kplo.schoollunchdict.fragment_Teachers.FridayFragment_T;
import com.kplo.schoollunchdict.fragment_Teachers.MondayFragment_T;
import com.kplo.schoollunchdict.fragment_Teachers.ThursdayFragment_T;
import com.kplo.schoollunchdict.fragment_Teachers.TuesdayFragment_T;
import com.kplo.schoollunchdict.fragment_Teachers.WednesdayFragment_T;

import java.lang.reflect.Array;
import java.util.ArrayList;

public class EvaluationActivity extends AppCompatActivity {

    private TextView tv_item_spinner;
    private Spinner eval_spinner_day, eval_spinner_menu;
    private ArrayList<String> mon_list, tue_list, wed_list, thu_list, fri_list;
    MondayFragment_T MondayFragment;
    TuesdayFragment_T TuesdayFragment;
    WednesdayFragment_T WednesdayFragment;
    ThursdayFragment_T ThursdayFragment;
    FridayFragment_T FridayFragment;
    ArrayAdapter menu_adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_evaluation);

        String[] days ={"월","화","수","목","금"};
        eval_spinner_day = (Spinner) findViewById(R.id.eval_spinner_day);
        eval_spinner_menu = (Spinner) findViewById(R.id.eval_spinner_menu);

        getMenu();

        ArrayAdapter day_adapter = new ArrayAdapter(this, R.layout.day_spinner, days);
        day_adapter.setDropDownViewResource(R.layout.day_spinner);
        eval_spinner_day.setAdapter(day_adapter);


        eval_spinner_day.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                Toast.makeText(getApplicationContext(), "selected day: " + days[position], Toast.LENGTH_SHORT).show();
                switch(days[position]){
                    case "월":
                        menu_adapter = new ArrayAdapter(getApplicationContext(), R.layout.day_spinner, mon_list);
                        break;
                    case "화":
                        menu_adapter = new ArrayAdapter(getApplicationContext(), R.layout.day_spinner, tue_list);
                        break;
                    case "수":
                        menu_adapter = new ArrayAdapter(getApplicationContext(), R.layout.day_spinner, wed_list);
                        break;
                    case "목":
                        menu_adapter = new ArrayAdapter(getApplicationContext(), R.layout.day_spinner, thu_list);
                        break;
                    case "금":
                        menu_adapter = new ArrayAdapter(getApplicationContext(), R.layout.day_spinner, fri_list);
                        break;
                }
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        menu_adapter.setDropDownViewResource(R.layout.day_spinner);
        eval_spinner_menu.setAdapter(menu_adapter);
    }

    private void getMenu(){
        MondayFragment = (MondayFragment_T)getSupportFragmentManager().findFragmentById(R.id.fragment_day);
        if (MondayFragment != null) {
            mon_list = MondayFragment.menuList;
        }
        TuesdayFragment = (TuesdayFragment_T) getSupportFragmentManager().findFragmentById(R.id.fragment_day);
        if (TuesdayFragment != null) {
            tue_list = TuesdayFragment.menuList;
        }
        WednesdayFragment = (WednesdayFragment_T) getSupportFragmentManager().findFragmentById(R.id.fragment_day);
        if (WednesdayFragment != null) {
            wed_list = WednesdayFragment.menuList;
        }
        ThursdayFragment = (ThursdayFragment_T) getSupportFragmentManager().findFragmentById(R.id.fragment_day);
        if (ThursdayFragment != null) {
            thu_list = ThursdayFragment.menuList;
        }
        FridayFragment = (FridayFragment_T) getSupportFragmentManager().findFragmentById(R.id.fragment_day);
        if (FridayFragment != null) {
            fri_list = FridayFragment.menuList;
        }
    }
}