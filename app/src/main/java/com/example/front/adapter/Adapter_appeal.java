package com.example.front.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager2.widget.ViewPager2;

import com.example.front.R;
import com.example.front.data.Appeal;
import com.example.front.data.database.DataBASE;


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
        return DataBASE.APPEALS_LIST.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        TextView name,content,time,theme,status;
        ViewPager2 viewPager2;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            theme = itemView.findViewById(R.id.tv_appeal_theme);
            name = itemView.findViewById(R.id.tv_appeal_name);
            content = itemView.findViewById(R.id.tv_appeal_content);
            time = itemView.findViewById(R.id.tv_my_appeal_time);
            status = itemView.findViewById(R.id.tv_appeal_status);
        }

        public void bindView(int position){
            Appeal appeal = DataBASE.APPEALS_LIST.get(position);
            name.setText("От кого: "+appeal.getAuthor().getFull_name());
            content.setText("Обращение: "+ appeal.getDescription());
            time.setText(appeal.getDate());
            theme.setText("Тема: " + appeal.getComment());
            status.setText(appeal.getState()+"");
        }

        @Override
        public void onClick(View view) {

        }
    }
}
