package com.example.front.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.example.front.R;
import com.example.front.data.EventJSON;
import com.example.front.data.database.DataBASE;

public class AdapterEvents extends RecyclerView.Adapter {
    public static onEventClickListener clickListener;


    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_event,parent,false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        ((MyViewHolder) holder).bindView(position);
    }

    @Override
    public int getItemCount() {
        return DataBASE.EVENT_JSON_LIST.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener,View.OnLongClickListener{
        TextView dateTv, placeTv, titleTv, pointsTv;
        ConstraintLayout addPeopleBtn;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            addPeopleBtn = itemView.findViewById(R.id.bt_scanner);
            addPeopleBtn.setVisibility(DataBASE.user.isAdmin() || DataBASE.user.isCurator() ? View.VISIBLE : View.GONE);
            dateTv = itemView.findViewById(R.id.tv_event_time);
            placeTv = itemView.findViewById(R.id.tv_event_content);
            titleTv = itemView.findViewById(R.id.tv_event_title);
            pointsTv = itemView.findViewById(R.id.tv_event_points);
            if (DataBASE.user.isAdmin()) {
                itemView.setOnLongClickListener(this);
                itemView.setOnClickListener(this);
            }
        }
        public void bindView(int position){
            EventJSON event = DataBASE.EVENT_JSON_LIST.get(position);
            dateTv.setText(""+event.getDate());
            placeTv.setText(""+event.getPlace());
            pointsTv.setText(""+event.getPoints());
            titleTv.setText(""+event.getTitle());
            addPeopleBtn.setOnClickListener(view -> clickListener.onAddPeople(position));
        }

        @Override
        public void onClick(View view) {
            clickListener.onItemClick(getAdapterPosition(),view);
        }

        @Override
        public boolean onLongClick(View view) {
            clickListener.onItemLongClick(getAdapterPosition(),view);
            return false;
        }

    }
    public void setOnItemClickListener(onEventClickListener clickListener){
        AdapterEvents.clickListener = clickListener;
    }

    public abstract static class onEventClickListener {
        public abstract void onItemClick(int position, View view);
        public void onItemLongClick(int position,View view) {};
        public void onAddPeople(int position) {};
    }
}
