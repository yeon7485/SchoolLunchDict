package com.kplo.schoollunchdict.menu;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.View;
import android.widget.TableLayout;

import com.google.android.material.tabs.TabLayout;
import com.kplo.schoollunchdict.R;
import com.kplo.schoollunchdict.ViewPagerAdapter;
import com.kplo.schoollunchdict.fragment.FridayFragment;
import com.kplo.schoollunchdict.fragment.MondayFragment;
import com.kplo.schoollunchdict.fragment.ThursdayFragment;
import com.kplo.schoollunchdict.fragment.TuesdayFragment;
import com.kplo.schoollunchdict.fragment.WednesdayFragment;

public class MyongJinDang extends AppCompatActivity {

    private ViewPager mViewPager;
    private TabLayout tabLayout;
    ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.menu_myong_jin_dang);

        mViewPager = (ViewPager) findViewById(R.id.mjd_viewPager);
        setupViewPager(mViewPager);

        tabLayout = (TabLayout) findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(mViewPager);
    }

    private void setupViewPager(ViewPager mViewPager) {
        adapter.addFragment(new MondayFragment(), "월");
        adapter.addFragment(new TuesdayFragment(), "화");
        adapter.addFragment(new WednesdayFragment(), "수");
        adapter.addFragment(new ThursdayFragment(), "목");
        adapter.addFragment(new FridayFragment(), "금");

        mViewPager.setAdapter(adapter);
    }
}