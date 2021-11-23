package com.kplo.schoollunchdict.fragment;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.kplo.schoollunchdict.R;
import com.kplo.schoollunchdict.menu.MyongJinDang;

public class MondayFragment extends Fragment {
    ViewPager viewPager;
    TextView restaurant1, restaurant2, menu1, menu2;

    public void MondayFragment(){
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_monday, container, false);
        restaurant1 = (TextView) view.findViewById(R.id.mjd_mon_restaurant1);
        restaurant2 = (TextView) view.findViewById(R.id.mjd_mon_restaurant2);
        menu1 = (TextView) view.findViewById(R.id.mjd_mon_menu1);
        menu2 = (TextView) view.findViewById(R.id.mjd_mon_menu2);



        return view;
    }
}