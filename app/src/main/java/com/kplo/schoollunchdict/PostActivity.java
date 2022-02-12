package com.kplo.schoollunchdict;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class PostActivity extends AppCompatActivity implements View.OnClickListener {

    private TextView post_tv_title, post_tv_contents, post_tv_nickname, post_tv_date;
    private ImageView post_edit_btn;
    private EditText comment_et;
    private Button comment_btn;
    private Post item;

    private DatabaseReference databaseBoard;
    private FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post);

        post_tv_title = (TextView) findViewById(R.id.post_tv_title);
        post_tv_contents = (TextView) findViewById(R.id.post_tv_contents);
        post_tv_nickname = (TextView) findViewById(R.id.post_tv_nickname);
        post_tv_date = (TextView) findViewById(R.id.post_tv_date);
        post_edit_btn = (ImageView) findViewById(R.id.post_edit_btn);
        comment_et = (EditText) findViewById(R.id.comment_et);
        comment_btn = (Button) findViewById(R.id.comment_btn);

        databaseBoard = FirebaseDatabase.getInstance().getReference("Board");
        firebaseAuth = FirebaseAuth.getInstance();

        getItemDetail();
        setItem();

        post_edit_btn.setOnClickListener(this);
        comment_btn.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        Intent intent;
        switch (v.getId()){
            case R.id.post_edit_btn:
                break;
            case R.id.comment_btn:
                break;
        }
    }

    public void getItemDetail() {
        Intent intent = getIntent();
        if (intent != null) {
            Bundle bld = intent.getExtras();

            Object obj = bld.get("item");
            if (obj != null && obj instanceof Post) {
                this.item = (Post) obj;
            }
        }
    }

    public void setItem() {
        if (this.item != null) {
            String title = this.item.getTitle();
            String contents = this.item.getContents();
            String nickname = this.item.getNickname();
            String date = this.item.getDate();
            if (title != null) {
                post_tv_title.setText(title);
            }
            if (contents != null) {
                post_tv_contents.setText(contents);
            }
            if (nickname != null) {
                post_tv_nickname.setText(nickname);
            }
            if (date != null) {
                post_tv_date.setText(date);
            }
        }
    }


}