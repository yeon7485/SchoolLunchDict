package com.kplo.schoollunchdict;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class SignUpActivity extends AppCompatActivity {

    private EditText signUp_et_email, signUp_et_pw;
    private TextView signUp_tv_error;
    private Button signUp_btn;

    private FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        // id 연결
        signUp_et_email = (EditText) findViewById(R.id.signUp_et_email);
        signUp_et_pw = (EditText) findViewById(R.id.signUp_et_pw);
        signUp_tv_error = (TextView) findViewById(R.id.signUp_tv_error);
        signUp_btn = (Button) findViewById(R.id.signUp_btn);

        //signUp 버튼 클릭 리스너
        signUp_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = signUp_et_email.getText().toString().trim();
                String pw = signUp_et_pw.getText().toString().trim();
                if (email.equals("") || !email.contains("@")) {
                    signUp_tv_error.setText("이메일을 다시 입력하세요!");
                } else if (pw.equals("")) {
                    signUp_tv_error.setText("비밀번호를 다시 입력하세요!");
                } else {
                    createUser(email, pw);
                }
            }
        });
    }

    private void createUser(String email, String pw) {
        firebaseAuth.createUserWithEmailAndPassword(email, pw)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // 회원가입 성공시
                            Toast.makeText(SignUpActivity.this, "회원가입 성공", Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(SignUpActivity.this, LoginActivity.class);
                            startActivity(intent);
                        } else {
                            // 계정이 중복된 경우
                            signUp_tv_error.setText("이미 존재하는 계정입니다.");
                        }
                    }
                });
    }
}
