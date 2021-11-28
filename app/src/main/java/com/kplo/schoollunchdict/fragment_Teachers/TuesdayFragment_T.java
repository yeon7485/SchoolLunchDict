package com.kplo.schoollunchdict.fragment_Teachers;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.kplo.schoollunchdict.R;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;


public class TuesdayFragment_T extends Fragment {

    ViewPager viewPager;
    TextView restaurant1, restaurant2, menu1, menu2;
    private String URL = "https://www.mju.ac.kr/mjukr/488/subview.do";
    final Bundle bundle = new Bundle();


    public void TuesdayFragment(){
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_monday, container, false);
        restaurant1 = (TextView) view.findViewById(R.id.mjd_mon_restaurant1);
        restaurant2 = (TextView) view.findViewById(R.id.mjd_mon_restaurant2);
        menu1 = (TextView) view.findViewById(R.id.mjd_mon_menu1);
        menu2 = (TextView) view.findViewById(R.id.mjd_mon_menu2);

        restaurant1.setText("점심");
        restaurant2.setText("저녁");


        new Thread() {
            @Override
            public void run() {
                //크롤링 할 구문
                try{
                    Document doc = Jsoup.connect(URL).get();	//URL 웹사이트에 있는 html 코드를 다 끌어오기
                    Elements menuElement = doc.select("td.alignL");	// 메뉴 빼오기
                    boolean isEmpty = menuElement.isEmpty(); //빼온 값 null체크
                    Log.d("Tag", "isNull? : " + isEmpty); //로그캣 출력
                    if(!isEmpty) { //null값이 아니면 크롤링 실행
                        Element lunchMenu = doc.select("td.alignL").get(2);
                        Element dinnerMenu = doc.select("td.alignL").get(3);

                        bundle.putString("lunch", makeLineText(lunchMenu)); //결과값 담아서 main Thread로 보내기
                        bundle.putString("dinner", makeLineText(dinnerMenu)); //결과값 담아서 main Thread로 보내기

                    }
                    else{
                        bundle.putString("lunch", "등록된 식단내용이(가) 없습니다.");
                        bundle.putString("dinner", "등록된 식단내용이(가) 없습니다.");
                    }
                    Message msg = handler.obtainMessage();
                    msg.setData(bundle);
                    handler.sendMessage(msg);
                }catch(IOException e){
                    e.printStackTrace();
                }
            }
        }.start();


        return view;
    }

    private String makeLineText(Element element) {
        String[] list = element.html().split("<br>");

        String result = "";
        for(String l : list){
            result += l + "\n";
        }
        return result;
    }

    Handler handler = new Handler(Looper.getMainLooper()) {
        @Override
        public void handleMessage(@NonNull Message msg) {
            Bundle bundle = msg.getData();    //new Thread에서 작업한 결과물 받기
            menu1.setText(bundle.getString("lunch"));    //받아온 데이터 textView에 출력
            menu2.setText(bundle.getString("dinner"));    //받아온 데이터 textView에 출력
        }
    };
}