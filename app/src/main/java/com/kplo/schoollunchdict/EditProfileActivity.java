package com.kplo.schoollunchdict;

import static androidx.constraintlayout.helper.widget.MotionEffect.TAG;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class EditProfileActivity extends AppCompatActivity {

    private TextView edit_tv_email, edit_tv_nickname, edit_tv_error;
    private EditText edit_et_nickname, edit_et_currentPw, edit_et_password1, edit_et_password2;
    private Button edit_btn;
    private FirebaseAuth firebaseAuth;
    private DatabaseReference database;
    String pw;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_profile);

        edit_tv_email = (TextView) findViewById(R.id.edit_tv_email);
        edit_tv_nickname = (TextView) findViewById(R.id.edit_tv_nickname);
        edit_tv_error = (TextView) findViewById(R.id.edit_tv_error);
        edit_et_nickname = (EditText) findViewById(R.id.edit_et_nickname);
        edit_et_currentPw = (EditText) findViewById(R.id.edit_et_currentPw);
        edit_et_password1 = (EditText) findViewById(R.id.edit_et_password1);
        edit_et_password2 = (EditText) findViewById(R.id.edit_et_password2);
        edit_btn = (Button) findViewById(R.id.edit_btn);

        firebaseAuth = FirebaseAuth.getInstance();
        database = FirebaseDatabase.getInstance().getReference("Users");
        getProfile();


        edit_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String nickname = edit_et_nickname.getText().toString().trim();
                String currentPw = edit_et_currentPw.getText().toString().trim();
                String password1 = edit_et_password1.getText().toString().trim();
                String password2 = edit_et_password2.getText().toString().trim();

                if (checkPassword(currentPw, password1, password2)) {
                    updateProfile(nickname, password1);
                    Intent intent = new Intent(EditProfileActivity.this, UserActivity.class);
                    startActivity(intent);
                    finish();
                }
            }
        });
    }

    // ????????? ????????????
    public void getProfile() {
        String uid = firebaseAuth.getUid();
        database.child(uid).child("nickname").get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DataSnapshot> task) {
                if (!task.isSuccessful()) {
                    Log.e("firebase", "Error getting nickname", task.getException());
                } else {
                    String nickname = String.valueOf(task.getResult().getValue());
                    edit_tv_nickname.setText("?????? ?????????: " + nickname);
                }
            }
        });

        database.child(uid).child("email").get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DataSnapshot> task) {
                if (!task.isSuccessful()) {
                    Log.e("firebase", "Error getting nickname", task.getException());
                } else {
                    String email = String.valueOf(task.getResult().getValue());
                    edit_tv_email.setText(email);
                }
            }
        });

        database.child(uid).child("password").get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DataSnapshot> task) {
                if (!task.isSuccessful()) {
                    Log.e("firebase", "Error getting nickname", task.getException());
                } else {
                    String password = String.valueOf(task.getResult().getValue());
                    pw = password;
                }
            }
        });
    }

    // ???????????? ??????
    public boolean checkPassword(String currentPw, String password1, String password2) {
        boolean check = true;

        // ?????? ??????????????? ??? ?????? ?????? ???
        if (!currentPw.equals("")) {
            // ????????????1??? 2??? ?????? ???
            if (!password1.equals(password2)) {
                edit_tv_error.setText("?????? ??????????????? ??????????????????!");
                check = false;
            }
            // ?????? ??????????????? ?????? ???
            else if (!currentPw.equals(pw)) {
                edit_tv_error.setText("?????? ??????????????? ????????????!");
                check = false;
            }
        }
        // ?????? ??????????????? ??? ?????? ???
        else if (!password1.equals("")) { // ????????? ??????????????? ???????????? ?????????
            edit_tv_error.setText("?????? ??????????????? ??????????????????!");
            check = false;
        }

        return check;
    }

    //????????? ????????????
    public void updateProfile(String nickname, String password1) {

        String uid = firebaseAuth.getUid();
        String complete = "";
        if (nickname.equals("") && password1.equals("")) {
            Toast.makeText(getApplicationContext(), "?????? ??????", Toast.LENGTH_SHORT).show();
            return;
        } else if (password1.equals("")) {
            database.child(uid).child("nickname").setValue(nickname);
            complete += "?????????";
        } else if (nickname.equals("")) {
            database.child(uid).child("password").setValue(password1);
            updatePassword(password1);
            complete += "????????????";
        } else {
            database.child(uid).child("nickname").setValue(nickname);
            database.child(uid).child("password").setValue(password1);
            updatePassword(password1);
            complete += "?????????, ????????????";
        }

        Toast.makeText(getApplicationContext(), complete + " ?????? ??????", Toast.LENGTH_SHORT).show();

    }


    // auth ???????????? ????????????
    public void updatePassword(String password) {
        FirebaseUser user = firebaseAuth.getCurrentUser();
        user.updatePassword(password).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful()) {
                    Log.d(TAG, "User password updated.");
                }
            }
        });
    }
}