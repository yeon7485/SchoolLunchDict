package com.kplo.schoollunchdict;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;

public class HomeActivity extends AppCompatActivity implements View.OnClickListener {

    private ImageView home_btn, setting_btn, home_btn_menu, home_btn_evaluation, home_btn_ranking, home_btn_community, home_btn_user;
    private DatabaseReference database;
    String URL_T = "https://www.mju.ac.kr/mjukr/488/subview.do";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        home_btn = (ImageView) findViewById(R.id.home_btn);
        setting_btn = (ImageView) findViewById(R.id.setting_btn);
        home_btn_menu = (ImageView) findViewById(R.id.home_btn_menu);
        home_btn_evaluation = (ImageView) findViewById(R.id.home_btn_evaluation);
        home_btn_ranking = (ImageView) findViewById(R.id.home_btn_ranking);
        home_btn_community = (ImageView) findViewById(R.id.home_btn_community);
        home_btn_user = (ImageView) findViewById(R.id.home_btn_user);

        new Thread(){
            @Override
            public void run() {
                try {
                    getTeachersMenu();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }.start();

        home_btn.setOnClickListener(this);
        setting_btn.setOnClickListener(this);
        home_btn_menu.setOnClickListener(this);
        home_btn_evaluation.setOnClickListener(this);
        home_btn_ranking.setOnClickListener(this);
        home_btn_community.setOnClickListener(this);
        home_btn_user.setOnClickListener(this);

    }

    @SuppressLint("NonConstantResourceId")
    @Override
    public void onClick(View v) {
        Intent intent;
        switch (v.getId()) {
            case R.id.home_btn:
                break;
            case R.id.setting_btn:
                intent = new Intent(this, SettingActivity.class);
                startActivity(intent);
                break;
            case R.id.home_btn_menu:
                intent = new Intent(this, MenuActivity.class);
                startActivity(intent);
                break;
            case R.id.home_btn_evaluation:
                intent = new Intent(this, EvaluationActivity1.class);
                startActivity(intent);
                break;
            case R.id.home_btn_ranking:
                break;
            case R.id.home_btn_community:
                break;
            case R.id.home_btn_user:
                intent = new Intent(this, UserActivity.class);
                startActivity(intent);
                break;
        }
    }

    public void getTeachersMenu() throws IOException {

        database = FirebaseDatabase.getInstance().getReference("Menu");

        Document doc = Jsoup.connect(URL_T).get();    //URL 웹사이트에 있는 html 코드를 다 끌어오기
        Elements menuElement = doc.select("td.alignL");    // 메뉴 빼오기
        boolean isEmpty = menuElement.isEmpty(); //빼온 값 null체크
        Log.d("Tag", "isNull? : " + isEmpty); //로그캣 출력
        if (!isEmpty) { //null값이 아니면 크롤링 실행
            for(int i = 0; i <= 8; i += 2){
                Element lunchMenu = doc.select("td.alignL").get(i);
                Element dinnerMenu = doc.select("td.alignL").get(i + 1);

                makeLineText(lunchMenu, i, "Lunch"); //결과값
                makeLineText(dinnerMenu, i, "Dinner"); //결과값
            }


        } else {
            String noResult = "등록된 식단내용이(가) 없습니다.";
            for(int i = 0; i <= 8; i += 2){
                database.child("Teachers").child(String.valueOf(i)).child("Lunch").setValue(noResult);
                database.child("Teachers").child(String.valueOf(i)).child("Dinner").setValue(noResult);
            }
        }
    }

    private void makeLineText(Element element, int i, String time) {
        String[] list = element.html().split("<br>");
        String result = "";
        for(String l : list){
            l = l.replaceAll("&amp;", "&");
            result += l + "\n";
        }
        database.child("Teachers").child(String.valueOf(i)).child(time).setValue(result);
    }
}