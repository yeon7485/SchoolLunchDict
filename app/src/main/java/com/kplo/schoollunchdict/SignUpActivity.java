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
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;
import java.util.Map;

public class SignUpActivity extends AppCompatActivity {

    private EditText signUp_et_email, signUp_et_password1, signUp_et_password2;
    private TextView signUp_tv_error;
    private Button signUp_btn;

    private FirebaseAuth firebaseAuth;
    private DatabaseReference database;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        // id 연결
        signUp_et_email = (EditText) findViewById(R.id.signUp_et_email);
        signUp_et_password1 = (EditText) findViewById(R.id.signUp_et_password1);
        signUp_et_password2 = (EditText) findViewById(R.id.signUp_et_password2);
        signUp_tv_error = (TextView) findViewById(R.id.signUp_tv_error);
        signUp_btn = (Button) findViewById(R.id.signUp_btn);

        // 데이터 접근 권한
        firebaseAuth = FirebaseAuth.getInstance();
        database = FirebaseDatabase.getInstance().getReference("Users");


        //signUp 버튼 클릭 리스너
        signUp_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = signUp_et_email.getText().toString().trim();
                String password1 = signUp_et_password1.getText().toString().trim();
                String password2 = signUp_et_password2.getText().toString().trim();

                if (checkEmailAndPassword(email, password1, password2)) {
                    createUser(email, password1);
                }
            }
        });
    }

    private void createUser(String email, String password) {
        Toast.makeText(SignUpActivity.this, firebaseAuth.getLanguageCode(), Toast.LENGTH_SHORT).show();
        firebaseAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(SignUpActivity.this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // 회원가입 성공시

                            FirebaseUser user = firebaseAuth.getCurrentUser();
                            String uid = user.getUid();
                            String email = user.getEmail();
                            String password = signUp_et_password1.getText().toString().trim();
                            String nickname = email;

                            //해쉬맵 테이블을 파이어베이스 데이터베이스에 저장
                            HashMap<String, Object> map = new HashMap<>();
                            map.put("uid", uid);
                            map.put("email", email);
                            map.put("password", password);
                            map.put("nickname", nickname);
                            map.put("postNum", "0");

                            database.child(uid).setValue(map);

                            Toast.makeText(SignUpActivity.this, "회원가입 성공", Toast.LENGTH_SHORT).show();
                            finish();
                            Intent intent = new Intent(SignUpActivity.this, LoginActivity.class);
                            startActivity(intent);
                        } else {
                            // 계정이 중복된 경우
                            //Toast.makeText(SignUpActivity.this, email + " / " + password, Toast.LENGTH_SHORT).show();
                            Log.w(TAG, "createUserWithEmail:failure", task.getException());
                            signUp_tv_error.setText("이미 존재하는 계정입니다!");
                        }
                    }
                });
    }

    public boolean checkEmailAndPassword(String email, String password1, String password2){
        boolean check = true;
        if (email.equals("") || !email.contains("@")) {
            signUp_tv_error.setText("이메일을 다시 입력하세요!");
            check = false;
        } else if (password1.equals("")) {
            signUp_tv_error.setText("비밀번호를 다시 입력하세요!");
            check = false;
        } else if(!password1.equals(password2)){
            signUp_tv_error.setText("같은 비밀번호를 입력해주세요!");
            check = false;
        }
        return check;
    }
}
