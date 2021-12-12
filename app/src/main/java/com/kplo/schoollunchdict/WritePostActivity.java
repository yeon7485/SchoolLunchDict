package com.kplo.schoollunchdict;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class WritePostActivity extends AppCompatActivity {

    private EditText write_post_et_title, write_post_et_contents;
    private Button write_post_btn;
    private DatabaseReference databaseReference;
    private FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_write_post);

        write_post_et_title = (EditText) findViewById(R.id.write_post_et_title);
        write_post_et_contents = (EditText) findViewById(R.id.write_post_et_contents);
        write_post_btn = (Button) findViewById(R.id.write_post_btn);

        databaseReference = FirebaseDatabase.getInstance().getReference("Users");
        firebaseAuth = FirebaseAuth.getInstance();

        String title = write_post_et_title.getText().toString().trim();
        String content = write_post_et_contents.getText().toString().trim();

        writePost(title, content);

    }

    private void writePost(String title, String content){

    }
}