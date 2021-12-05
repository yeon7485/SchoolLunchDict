package com.kplo.schoollunchdict;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class UserEvalAdapter extends RecyclerView.Adapter<UserEvalAdapter.UserEvalViewHolder>{


    private ArrayList<String> menuList;
    private Context context;

    public UserEvalAdapter(ArrayList<String> menuRatingList, Context context) {
        this.menuList = menuRatingList;
        this.context = context;
    }

    @NonNull
    @Override
    public UserEvalViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_user_eval, parent, false);
        UserEvalViewHolder holder = new UserEvalViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull UserEvalViewHolder holder, int position) {
        holder.user_eval_tv_menu.setText(menuList.get(position));
    }

    @Override
    public int getItemCount() {

        return (menuList != null ? menuList.size() : 0);
    }

    public class UserEvalViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        TextView user_eval_tv_menu;

        public UserEvalViewHolder(@NonNull View itemView) {

            super(itemView);
            this.user_eval_tv_menu = (TextView) itemView.findViewById(R.id.user_eval_tv_menu);
        }

        //아이템 클릭시 화면 전환
        @Override
        public void onClick(View v) {
            int pos = getAdapterPosition();
//
//            Intent intent = new Intent(v.getContext(), StoreActivity.class);
//            intent.putExtra("item", getItem(pos));
//            intent.putExtra("uid", uidList.get(pos));
//            v.getContext().startActivity(intent);

            Toast.makeText(v.getContext(), menuList.get(pos), Toast.LENGTH_SHORT).show();
        }
    }
}
