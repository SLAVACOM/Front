package com.example.front.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.front.R;
import com.example.front.data.Appeal;
import com.example.front.data.Data;


public class Adapter_my_appeal extends RecyclerView.Adapter {


    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_my_appeal,parent,false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        ((MyViewHolder)holder).bindView(position);

    }

    @Override
    public int getItemCount() {
        return Data.APPEALS_LIST.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        TextView theme,content,time;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            time = itemView.findViewById(R.id.tv_my_appeal_time);
            content = itemView.findViewById(R.id.tv_my_appeal_content);
            theme = itemView.findViewById(R.id.tv_my_appeal_theme);

        }

        public void bindView(int position){
            Appeal appeal = Data.APPEALS_LIST.get(position);
            theme.setText("Тема: "+ appeal.getTheme());
            content.setText("Обращение: "+appeal.getContent());
            time.setText(appeal.getTime());

        }

        @Override
        public void onClick(View view) {

        }
    }
}
