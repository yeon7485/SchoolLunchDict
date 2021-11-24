package com.kplo.schoollunchdict;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class PostAdapter extends RecyclerView.Adapter<PostAdapter.PostViewHolder> {

    private ArrayList<Post> arrayList;
    private Context context;

    public PostAdapter(ArrayList<Post> arrayList, Context context) {
        this.arrayList = arrayList;
        this.context = context;
    }

    @NonNull
    @Override
    public PostViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.post_item, parent, false);
        PostViewHolder holder = new PostViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull PostViewHolder holder, int position) {
        holder.post_tv_title.setText(arrayList.get(position).getTitle());
        holder.post_tv_contents.setText(arrayList.get(position).getContents());
        holder.post_tv_nickname.setText(arrayList.get(position).getNickname());
        holder.post_tv_time.setText(arrayList.get(position).getTime());
    }

    @Override
    public int getItemCount() {
        return (arrayList != null ? arrayList.size() : 0);
    }

    public class PostViewHolder extends RecyclerView.ViewHolder {
        TextView post_tv_title, post_tv_contents, post_tv_nickname, post_tv_time;

        public PostViewHolder(@NonNull View itemView) {

            super(itemView);
            this.post_tv_title = (TextView) itemView.findViewById(R.id.post_tv_title);
            this.post_tv_contents = (TextView) itemView.findViewById(R.id.post_tv_contents);
            this.post_tv_nickname = (TextView) itemView.findViewById(R.id.post_tv_nickname);
            this.post_tv_time = (TextView) itemView.findViewById(R.id.post_tv_time);
        }
    }
}
