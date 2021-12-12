package com.kplo.schoollunchdict;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

public class PostActivity extends AppCompatActivity {

    private TextView post_tv_title, post_tv_contents, post_tv_nickname, post_tv_time;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post);
    }
}