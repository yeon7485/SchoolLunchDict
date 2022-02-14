package com.kplo.schoollunchdict;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class PostActivity extends AppCompatActivity implements View.OnClickListener {

    private TextView post_tv_title, post_tv_contents, post_tv_nickname, post_tv_date, comment_nickname, comment_tv;
    private ImageView post_edit_btn;
    private EditText comment_et;
    private Button comment_btn;
    private Post item;
    private String key;

    private DatabaseReference databaseBoard;
    private DatabaseReference databaseUsers;
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
        comment_nickname = (TextView) findViewById(R.id.comment_nickname);
        comment_tv = (TextView) findViewById(R.id.comment_tv);

        databaseBoard = FirebaseDatabase.getInstance().getReference("Board");
        databaseUsers = FirebaseDatabase.getInstance().getReference("Users");
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
                addComment();
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
            Object bldKey = bld.get("key");
            this.key = String.valueOf(bldKey);
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

    public void addComment(){
        // post처럼 comment item 만들어야됨
        getNickname();
        String comment = comment_et.getText().toString().trim();
        String nickname = comment_nickname.getText().toString();
        comment_tv.setText(comment);
        databaseBoard.child(key).child("Comment").child(nickname).setValue(comment);

    }

    public void getNickname(){
        String uid = firebaseAuth.getUid();

        databaseUsers.child(uid).child("nickname").addListenerForSingleValueEvent(new ValueEventListener() {
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                String nickname = snapshot.getValue(String.class);
                comment_nickname.setText(nickname);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.w("TAG", "Failed to read value.", error.toException());
            }
        });
    }


}