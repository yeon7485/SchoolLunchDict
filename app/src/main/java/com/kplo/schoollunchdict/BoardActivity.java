package com.kplo.schoollunchdict;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class BoardActivity extends AppCompatActivity implements View.OnClickListener{

    private RecyclerView board_recyclerView;
    private RecyclerView.Adapter adapter;
    private RecyclerView.LayoutManager layoutManager;
    private ArrayList<Post> postList;
    private ArrayList<Post> resultList;
    private DatabaseReference databaseReference;

    private TextView post_tv_title, post_tv_contents, post_tv_nickname, post_tv_time;
    private ImageView home_btn, write_btn, search_btn;
    private EditText board_et_search;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_board);

        board_recyclerView = (RecyclerView) findViewById(R.id.board_recyclerView);
        board_recyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(this);
        board_recyclerView.setLayoutManager(layoutManager);
        postList = new ArrayList<>();

        post_tv_title = (TextView) findViewById(R.id.post_item_tv_title);
        post_tv_contents = (TextView) findViewById(R.id.post_item_tv_contents);
        post_tv_nickname = (TextView) findViewById(R.id.post_item_tv_nickname);
        post_tv_time = (TextView) findViewById(R.id.post_item_tv_date);
        home_btn = (ImageView) findViewById(R.id.home_btn);
        write_btn = (ImageView) findViewById(R.id.write_btn);
        search_btn = (ImageView) findViewById(R.id.search_btn);
        board_et_search = (EditText) findViewById(R.id.board_et_search);

        databaseReference = FirebaseDatabase.getInstance().getReference("Board");

        getBoard();

        adapter = new PostAdapter(postList, BoardActivity.this);
        board_recyclerView.setAdapter(adapter);
        board_recyclerView.invalidate();

        home_btn.setOnClickListener(this);
        write_btn.setOnClickListener(this);
        search_btn.setOnClickListener(this);

    }

    // 클릭 이벤트 처리
    @Override
    public void onClick(View v) {
        Intent intent;
        switch (v.getId()){
            case R.id.home_btn:
                intent = new Intent(this, HomeActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
                finish();
                break;
            case R.id.write_btn:
                intent = new Intent(this, WritePostActivity.class);
                startActivity(intent);
                break;
            case R.id.search_btn:
                String searchText = board_et_search.getText().toString().trim();
                search(searchText);
                break;
        }
    }


    private void getBoard(){
        Toast.makeText(this, "get board!", Toast.LENGTH_SHORT).show();
        databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @SuppressLint("NotifyDataSetChanged")
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                //파이어베이스 데이터베이스의 데이터를 받아오는곳
                postList.clear();
                for (DataSnapshot snapshot1 : snapshot.getChildren()) {
                    Post post = snapshot1.getValue(Post.class); //만들어뒀던 list 객체에 데이터 담기
                    if(post != null){
                        postList.add(post);
                    }
                    else{
                        Log.e("BoardActivity", "post is null.");
                    }
                }
                adapter.notifyDataSetChanged(); // 리스트 저장 및 새로고침

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.e("BoardActivity", String.valueOf(error.toException()));
            }
        });

    }

    private void search(String searchText){
        Toast.makeText(getApplicationContext(), "search", Toast.LENGTH_SHORT).show();
        resultList = new ArrayList<>();
        if (searchText.length() == 0) {
            resultList.addAll(postList);
        } else {
            for (Post post : postList) {
                if (post.getTitle().contains(searchText)) {
                    resultList.add(post);
                }
                else if(post.getContents().contains(searchText)){
                    resultList.add(post);
                }
                else if(post.getNickname().contains(searchText)){
                    resultList.add(post);
                }
            }
        }

        PostAdapter resultAdapter = new PostAdapter(resultList, this);
        board_recyclerView.removeAllViewsInLayout();
        board_recyclerView.setAdapter(resultAdapter);
    }


}