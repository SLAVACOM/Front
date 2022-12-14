package com.example.front.adapter;

import android.graphics.Color;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.example.front.CONST.CONST;
import com.example.front.R;
import com.example.front.data.database.DataBASE;
import com.example.front.helpers.LastItemListener;
import com.example.front.retrofit.Retrofit;
import com.example.front.data.User;
import com.google.gson.JsonObject;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AdapterUserList extends RecyclerView.Adapter<AdapterUserList.MyViewHolder> {

    public static ClickListener clickListener;
    public LastItemListener lastItemListener;

    AppCompatActivity a;
    public AdapterUserList(AppCompatActivity activity) {
        a = activity;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_userlist,parent,false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        ((MyViewHolder)holder).bindView(position);

    }

    @Override
    public int getItemCount() {
        return DataBASE.USERS_LIST.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener,View.OnLongClickListener{
        TextView id, points,fullname,email,phone,adress,status;
        ImageView button;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            id = itemView.findViewById(R.id.tv_user_id);
            points = itemView.findViewById(R.id.textView13);
            fullname = itemView.findViewById(R.id.textView10);
            email = itemView.findViewById(R.id.textView11);
            phone = itemView.findViewById(R.id.textView12);
            adress = itemView.findViewById(R.id.textView14);
            button =  itemView.findViewById(R.id.bt_userlist_block);
            itemView.setOnLongClickListener(this);
            itemView.setOnClickListener(this);
        }

        public void bindView(int position){
            if (position ==  DataBASE.USERS_LIST.size()-1 && lastItemListener != null) lastItemListener.onLastItemOpened(position);
            User user = DataBASE.USERS_LIST.get(position);
            id.setText("ID ????????????????????????: "+user.getId());
            points.setText(user.getPoints()+" ????????????");
            points.setTextColor(a.getColor(R.color.default_chip));
            if (user.getPoints() > 0) {
                points.setTextColor(a.getColor(R.color.accept));
            }
            email.setText("??????????: "+user.getEmail());
            fullname.setText("??????: "+user.getFull_name());
            phone.setText("??????????????: "+user.getPhone());
            adress.setText("??????????: "+user.getAddress());
            if (user.isAdmin()) button.setVisibility(View.INVISIBLE);
            if (user.getBlocked()==0) {
                button.setImageDrawable(a.getDrawable(R.drawable.ic_lock_open_24));
                button.setColorFilter(ContextCompat.getColor(a, R.color.accept), android.graphics.PorterDuff.Mode.MULTIPLY);
            } else {
                button.setImageDrawable(a.getDrawable(R.drawable.ic_lock_24));
                button.setColorFilter(ContextCompat.getColor(a, R.color.like), android.graphics.PorterDuff.Mode.MULTIPLY);

            }
            button.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View view) {
                    int blok;
                    if (user.getBlocked()==0){
                        blok=1;
                    } else {
                        blok=0;
                    }
                    Call<JsonObject> blocket = Retrofit.getInstance().getApi().editProfile("Bearer "+ DataBASE.token, user.getId(),"put", blok);
                    blocket.enqueue(new Callback<JsonObject>() {
                        @Override
                        public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                            if (response.code()==200){
                                DataBASE.USERS_LIST.get(position).setBlocked(blok);
                                notifyDataSetChanged();
                            }
//                            Toast.makeText(status.getContext(), response.code(), Toast.LENGTH_SHORT).show();
                            Log.d(CONST.SERVER_LOG, response.code() + response.message().toString());

                        }

                        @Override
                        public void onFailure(Call<JsonObject> call, Throwable t) {
                            t.printStackTrace();
                        }
                    });

                }

            });
        }

        @Override
        public void onClick(View view) {
            clickListener.onItemClick(getAdapterPosition(),view);
        }

        @Override
        public boolean onLongClick(View v) {
            clickListener.onItemLongClick(getAdapterPosition(), v);
            return false;
        }


    }
    public void setOnItemClickListener(ClickListener clickListener) {
        AdapterUserList.clickListener = clickListener;
    }

    public void setLastItemListener(LastItemListener lastItemListener) {
        this.lastItemListener = lastItemListener;
    }

    public interface ClickListener {
        void onItemClick(int position, View v);
        void onItemLongClick(int position, View v);
    }



}
