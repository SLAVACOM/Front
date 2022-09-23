package com.example.front.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.front.R;
import com.example.front.data.UserRequest;

import java.util.ArrayList;
import java.util.List;


public class UserRequestsAdapter extends RecyclerView.Adapter {

    public static ClickListener clickListener;
    public Context context;
    public List<UserRequest> items = new ArrayList<>();

    public UserRequestsAdapter(Context context, List<UserRequest> items) {
        this.context = context;
        this.items = items;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_user_request,parent,false);

        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        ((MyViewHolder)holder).bindView(position);

    }

    @Override
    public int getItemCount() {
        return items.size();
    }


    public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnLongClickListener{
        TextView authorTv, typeTv;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            authorTv = itemView.findViewById(R.id.from_name);
            typeTv = itemView.findViewById(R.id.type_name);
            itemView.setOnLongClickListener(this);
        }

        public void bindView(int position){
            UserRequest ur = items.get(position);
            authorTv.setText(ur.getUser().getFull_name());
            typeTv.setText(ur.getType().getName()+ur.getType().getName()+ur.getType().getName()+ur.getType().getName());

        }



        @Override
        public boolean onLongClick(View view) {
            clickListener.onItemLongClick(getAdapterPosition(),view);

            return false;
        }
    }

    public void setOnItemClickListener(ClickListener clickListener) {
        UserRequestsAdapter.clickListener = clickListener;
    }

    public interface ClickListener {
        void onItemLongClick(int position, View v);
    }
}