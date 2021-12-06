package com.kplo.schoollunchdict;

import android.app.AlertDialog;
import android.content.Context;
import android.util.Log;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

public class UserEvalAdapter extends RecyclerView.Adapter<UserEvalAdapter.UserEvalViewHolder> {


    private ArrayList<String> menuList;
    private Context context;
    private final DatabaseReference database = FirebaseDatabase.getInstance().getReference("Users");
    private final FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();


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

    public void searchList(ArrayList<String> searchList) {
        menuList = searchList;
        notifyDataSetChanged();
    }

    public class UserEvalViewHolder extends RecyclerView.ViewHolder implements View.OnCreateContextMenuListener {
        TextView user_eval_tv_menu;

        public UserEvalViewHolder(@NonNull View itemView) {

            super(itemView);
            this.user_eval_tv_menu = (TextView) itemView.findViewById(R.id.user_eval_tv_menu);

            itemView.setOnCreateContextMenuListener(this);

        }

        @Override
        public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {

            MenuItem edit = menu.add(Menu.NONE, 1001, 1, "수정");
            MenuItem delete = menu.add(Menu.NONE, 1002, 2, "삭제");
            edit.setOnMenuItemClickListener(onEditMenu);
            delete.setOnMenuItemClickListener(onEditMenu);
        }

        private final MenuItem.OnMenuItemClickListener onEditMenu = new MenuItem.OnMenuItemClickListener() {

            @Override
            public boolean onMenuItemClick(MenuItem item) {
                String uid = firebaseAuth.getUid();

                int i = menuList.get(getAdapterPosition()).indexOf(" ");
                String menu = menuList.get(getAdapterPosition()).substring(0, i);

                switch (item.getItemId()) {
                    // 수정
                    case 1001:
                        AlertDialog.Builder builder = new AlertDialog.Builder(context);
                        View view = LayoutInflater.from(context).inflate(R.layout.edit_box, null, false);
                        builder.setView(view);
                        final Button edit_btn = (Button) view.findViewById(R.id.dialog_edit_btn);
                        final EditText dialog_et_rating = (EditText) view.findViewById(R.id.dialog_et_rating);
                        final TextView dialog_tv_menu = (TextView) view.findViewById(R.id.dialog_tv_menu);

                        int rating = menuList.get(getAdapterPosition()).indexOf("점");
                        dialog_tv_menu.setText(menu);
                        dialog_et_rating.setText(menuList.get(getAdapterPosition()).substring(rating - 1, rating));
                        final AlertDialog dialog = builder.create();

                        edit_btn.setOnClickListener(new View.OnClickListener() {

                            @Override
                            public void onClick(View v) {
                                String s_rating = dialog_et_rating.getText().toString().trim().replaceAll("[^0-9]", "");
                                String result = menuList.get(getAdapterPosition()).substring(0, rating - 1);

                                database.child(uid).child("Eval").child(menu).setValue(Integer.parseInt(s_rating));

                                result += s_rating + "점";
                                menuList.set(getAdapterPosition(), result);
                                notifyItemChanged(getAdapterPosition());

                                dialog.dismiss();
                            }
                        });

                        dialog.show();
                        break;

                    // 삭제
                    case 1002:
                        menuList.remove(getAdapterPosition());
                        notifyItemRemoved(getAdapterPosition());
                        notifyItemRangeChanged(getAdapterPosition(), menuList.size());
                        database.child(uid).child("Eval").child(menu).removeValue();
                        break;

                }
                return true;
            }
        };

    }
}
