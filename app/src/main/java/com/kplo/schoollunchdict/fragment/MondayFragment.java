package com.kplo.schoollunchdict.fragment;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.kplo.schoollunchdict.R;
import com.kplo.schoollunchdict.menu.MyongJinDang;

public class MondayFragment extends Fragment {
    ViewPager viewPager;

    public void MondayFragment(){
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_monday, container, false);

        return view;
    }
}