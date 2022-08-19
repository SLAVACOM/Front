package com.example.front.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.RecyclerView;

import com.example.front.R;
import com.example.front.data.DataData;
import com.example.front.retrofit.BusJSON;


public class Adapter_bus extends RecyclerView.Adapter {
    public static ClickListener clickListener;


    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_bus,parent,false);

        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        ((MyViewHolder)holder).bindView(position);

    }

    @Override
    public int getItemCount() {
        return DataData.BUS_JSON_LIST.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener,View.OnLongClickListener{
        TextView time, bus_title,bus_place;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            time = itemView.findViewById(R.id.bus_time);
            bus_title = itemView.findViewById(R.id.tv_event_title);
            bus_place = itemView.findViewById(R.id.bus_place);
            itemView.setOnClickListener(this);
            itemView.setOnLongClickListener(this);
        }

        public void bindView(int position){
            BusJSON bus = DataData.BUS_JSON_LIST.get(position);
            time.setText(bus.getTime());
            bus_title.setText(bus.getTitle());
            bus_place.setText(bus.getPlace());

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
        Adapter_bus.clickListener = clickListener;
    }

    public interface ClickListener {
        void onItemClick(int position, View v);
        void onItemLongClick(int position, View v);
    }

}
