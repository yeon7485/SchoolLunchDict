package com.kplo.schoollunchdict;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class PostAdapter extends RecyclerView.Adapter<PostAdapter.PostViewHolder> {

    private ArrayList<Post> arrayList;
    private ArrayList<String> keyList;
    private Context context;

    public PostAdapter(ArrayList<Post> arrayList, ArrayList<String> keyList, Context context) {
        this.arrayList = arrayList;
        this.keyList = keyList;
        this.context = context;
    }

    @NonNull
    @Override
    public PostViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_post, parent, false);
        PostViewHolder holder = new PostViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull PostViewHolder holder, int position) {
        holder.post_tv_title.setText(arrayList.get(position).getTitle());
        holder.post_tv_contents.setText(arrayList.get(position).getContents());
        holder.post_tv_nickname.setText(arrayList.get(position).getNickname());
        holder.post_tv_date.setText(arrayList.get(position).getDate());
    }

    @Override
    public int getItemCount() {
        return (arrayList != null ? arrayList.size() : 0);
    }

    public Post getItem(int position){
        return arrayList != null ? arrayList.get(position) : null;
    }


    public class PostViewHolder extends RecyclerView.ViewHolder{
        TextView post_tv_title, post_tv_contents, post_tv_nickname, post_tv_date;

        public PostViewHolder(@NonNull View itemView) {

            super(itemView);
            this.post_tv_title = (TextView) itemView.findViewById(R.id.post_item_tv_title);
            this.post_tv_contents = (TextView) itemView.findViewById(R.id.post_item_tv_contents);
            this.post_tv_nickname = (TextView) itemView.findViewById(R.id.post_item_tv_nickname);
            this.post_tv_date = (TextView) itemView.findViewById(R.id.post_item_tv_date);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int pos = getAdapterPosition();
                    Log.v("position", String.valueOf(pos));
                    Intent intent = new Intent(v.getContext(), PostActivity.class);
                    intent.putExtra("item", getItem(pos));
                    intent.putExtra("key", keyList.get(pos));
                    v.getContext().startActivity(intent);
                }
            });
        }

    }
}
