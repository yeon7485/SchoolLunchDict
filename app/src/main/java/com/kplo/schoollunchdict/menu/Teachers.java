package com.kplo.schoollunchdict.menu;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import com.google.android.material.tabs.TabLayout;
import com.kplo.schoollunchdict.HomeActivity;
import com.kplo.schoollunchdict.R;
import com.kplo.schoollunchdict.SettingActivity;
import com.kplo.schoollunchdict.ViewPagerAdapter;
import com.kplo.schoollunchdict.fragment_Teachers.FridayFragment_T;
import com.kplo.schoollunchdict.fragment_Teachers.MondayFragment_T;
import com.kplo.schoollunchdict.fragment_Teachers.ThursdayFragment_T;
import com.kplo.schoollunchdict.fragment_Teachers.TuesdayFragment_T;
import com.kplo.schoollunchdict.fragment_Teachers.WednesdayFragment_T;

public class Teachers extends AppCompatActivity implements View.OnClickListener{
    private ViewPager mViewPager;
    private TabLayout tabLayout;
    private TextView restaurant;
    private ImageView home_btn, setting_btn;
    ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.menu_teachers);

        mViewPager = (ViewPager) findViewById(R.id.mjd_viewPager);
        setupViewPager(mViewPager);

        tabLayout = (TabLayout) findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(mViewPager);

        restaurant = (TextView)findViewById(R.id.restaurant);
        home_btn = (ImageView)findViewById(R.id.home_btn);
        setting_btn = (ImageView)findViewById(R.id.setting_btn);

        restaurant.setText("교직원 식당");

        home_btn.setOnClickListener(this);
        setting_btn.setOnClickListener(this);
    }

    private void setupViewPager(ViewPager mViewPager) {
        adapter.addFragment(new MondayFragment_T(), "월");
        adapter.addFragment(new TuesdayFragment_T(), "화");
        adapter.addFragment(new WednesdayFragment_T(), "수");
        adapter.addFragment(new ThursdayFragment_T(), "목");
        adapter.addFragment(new FridayFragment_T(), "금");

        mViewPager.setAdapter(adapter);
    }
    @Override
    public void onClick(View v) {
        Intent intent;
        switch(v.getId()){
            case R.id.home_btn:
                intent = new Intent(this, HomeActivity.class);
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
