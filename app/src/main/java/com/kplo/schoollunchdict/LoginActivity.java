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
import com.google.firebase.auth.FirebaseUser;

public class LoginActivity extends AppCompatActivity {

    private EditText login_et_email, login_et_pw;
    private TextView login_tv_error, login_btn_signUp;
    private Button login_btn;

    private FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
    private FirebaseAuth.AuthStateListener firebaseAuthListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        // id 연결
        login_et_email = (EditText) findViewById(R.id.login_et_email);
        login_et_pw = (EditText) findViewById(R.id.login_et_pw);
        login_tv_error = (TextView) findViewById(R.id.login_tv_error);
        login_btn_signUp = (TextView) findViewById(R.id.login_btn_signUp);
        login_btn = (Button) findViewById(R.id.login_btn);

        // 클릭 리스너 연결
        login_btn.setOnClickListener(clickListener);
        login_btn_signUp.setOnClickListener(clickListener);

        // 홈 화면 이동
        firebaseAuthListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = firebaseAuth.getCurrentUser();
                if (user != null) {
                    Intent intent = new Intent(LoginActivity.this, HomeActivity.class);
                    startActivity(intent);
                    finish();
                }
            }
        };

    }

    // 클릭 리스너
    View.OnClickListener clickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.login_btn:
                    String email = login_et_email.getText().toString().trim();
                    String pw = login_et_pw.getText().toString().trim();
                    if (email.equals("") || pw.equals("") || !email.contains("@")) {
                        login_tv_error.setText("이메일 또는 비밀번호를 입력하세요!");
                    } else {
                        loginUser(email, pw);
                    }
                    break;
                case R.id.login_btn_signUp:
                    Intent intent = new Intent(LoginActivity.this, SignUpActivity.class);
                    startActivity(intent);
                    break;
            }
        }
    };

    // 로그인 메소드
    private void loginUser(String email, String pw) {
        firebaseAuth.signInWithEmailAndPassword(email, pw)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // 로그인 성공
                            Toast.makeText(LoginActivity.this, "로그인 성공", Toast.LENGTH_SHORT).show();
                            firebaseAuth.addAuthStateListener(firebaseAuthListener);
                        } else {
                            // 로그인 실패
                            login_tv_error.setText("이메일 또는 비밀번호가 틀렸습니다!");
                        }
                    }
                });
    }
}