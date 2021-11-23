package com.kplo.schoollunchdict;

import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class PostAdapter extends RecyclerView.Adapter<PostAdapter.PostViewHolder>{

    @NonNull
    @Override
    public PostViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return null;
    }

    @Override
    public void onBindViewHolder(@NonNull PostViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 0;
    }

    public class PostViewHolder extends RecyclerView.ViewHolder {
        TextView post_tv_title, post_tv_contents, post_tv_nickname, post_tv_time;

        public PostViewHolder(@NonNull View itemView) {
            super(itemView);
        }
    }
}
