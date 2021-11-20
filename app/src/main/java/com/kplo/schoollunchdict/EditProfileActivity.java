package com.kplo.schoollunchdict;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class EditProfileActivity extends AppCompatActivity {

    private TextView edit_tv_email, edit_tv_nickname, edit_tv_error;
    private EditText edit_et_nickname, edit_et_password1, edit_et_password2;
    private Button edit_btn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_profile);

        edit_tv_email = (TextView) findViewById(R.id.edit_tv_email);
        edit_tv_nickname = (TextView) findViewById(R.id.edit_tv_nickname);
        edit_tv_error = (TextView) findViewById(R.id.edit_tv_error);
        edit_et_nickname = (EditText) findViewById(R.id.edit_et_nickname);
        edit_et_password1 = (EditText) findViewById(R.id.edit_et_password1);
        edit_et_password2 = (EditText) findViewById(R.id.edit_et_password2);
        edit_btn = (Button) findViewById(R.id.edit_btn);



    }
}