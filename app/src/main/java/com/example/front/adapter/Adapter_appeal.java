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


public class Adapter_appeal extends RecyclerView.Adapter {


    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_appeal,parent,false);
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
        TextView name,content,time;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.TV_name_Appeal);
            content = itemView.findViewById(R.id.TV_content_Appeal);
            time = itemView.findViewById(R.id.time_appeal);

        }

        public void bindView(int position){
            Appeal appeal = Data.APPEALS_LIST.get(position);
            name.setText(appeal.getFrom());
            content.setText(appeal.getContent());
            time.setText(appeal.getTime());

        }

        @Override
        public void onClick(View view) {

        }
    }
}
