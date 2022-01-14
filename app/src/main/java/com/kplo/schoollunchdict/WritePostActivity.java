package com.kplo.schoollunchdict;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.SimpleDateFormat;
import java.util.Date;

public class WritePostActivity extends AppCompatActivity {

    private EditText write_post_et_title, write_post_et_contents;
    private Button write_post_btn;
    private DatabaseReference databaseBoard;
    private DatabaseReference databaseUsers;
    private FirebaseAuth firebaseAuth;
    private int count = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_write_post);

        write_post_et_title = (EditText) findViewById(R.id.write_post_et_title);
        write_post_et_contents = (EditText) findViewById(R.id.write_post_et_contents);
        write_post_btn = (Button) findViewById(R.id.write_post_btn);

        databaseBoard = FirebaseDatabase.getInstance().getReference("Board");
        databaseUsers = FirebaseDatabase.getInstance().getReference("Users");
        firebaseAuth = FirebaseAuth.getInstance();


        write_post_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String title = write_post_et_title.getText().toString().trim();
                String contents = write_post_et_contents.getText().toString().trim();

                if(!isEmpty(title, contents)){
                    writePost(title, contents);
                    Intent intent = new Intent(getApplicationContext(), BoardActivity.class);
                    startActivity(intent);
                    finish();
                }
                else{
                    Toast.makeText(getApplicationContext(), title + ", contents: " + contents, Toast.LENGTH_SHORT).show();
                }
            }
        });

    }

    private void writePost(String title, String contents){
        String uid = firebaseAuth.getUid();
        SimpleDateFormat s = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        String format = s.format(new Date());
        Log.v("error", "title: " + title + ", contents: " + contents);

        Post post = new Post(title, contents, getNickname(uid), format);
        databaseBoard.child(String.valueOf(count)).push().setValue(post);
        count = count + 1;
    }

    private boolean isEmpty(String title, String contents){
        boolean check = false;
        if(title.equals("") || contents.equals("")){
            check = true;
        }
        return check;
    }

    private String getNickname(String uid){
        final String[] nickname = {""};
        databaseUsers.child(uid).child("nickname").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                 nickname[0] = snapshot.getValue(String.class);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.w("TAG", "Failed to read value.", error.toException());
            }
        });

        return nickname[0];
    }
}