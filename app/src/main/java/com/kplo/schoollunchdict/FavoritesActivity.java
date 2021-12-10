package com.kplo.schoollunchdict;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class FavoritesActivity extends AppCompatActivity implements View.OnClickListener {

    private TextView favorites_tv_menu;
    private DatabaseReference databaseUsers = FirebaseDatabase.getInstance().getReference("Users");
    private FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_favorites);

        ImageView home_btn = (ImageView) findViewById(R.id.home_btn);
        ImageView setting_btn = (ImageView) findViewById(R.id.setting_btn);
        favorites_tv_menu = (TextView) findViewById(R.id.favorites_tv_menu);

        getFavoriteMenu();

        home_btn.setOnClickListener(this);
        setting_btn.setOnClickListener(this);

    }

    private void getFavoriteMenu(){
        String uid = firebaseAuth.getUid();
        databaseUsers.child(uid).child("Favorites").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                String result = "";
                for (DataSnapshot data : snapshot.getChildren()) {
                    result += data.getKey() + "\n";
                }
                favorites_tv_menu.setText(result);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.v("error", "getFavoritesMenu error");
            }
        });
    }

    @Override
    public void onClick(View v) {
        Intent intent;
        switch (v.getId()) {
            case R.id.home_btn:
                intent = new Intent(this, HomeActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
                finish();
                break;

            case R.id.setting_btn:
                intent = new Intent(this, SettingActivity.class);
                startActivity(intent);
                break;
        }
    }
}