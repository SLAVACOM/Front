package com.example.front.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.front.R;
import com.example.front.data.Bus;
import com.example.front.data.Data;


public class Adapter_bus extends RecyclerView.Adapter {


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
        return Data.BUS_LIST.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        TextView time_1,time_2, bus_st1,bus_st2;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            time_1 = itemView.findViewById(R.id.bus_time1);
            time_2 = itemView.findViewById(R.id.bus_time2);
            bus_st1 = itemView.findViewById(R.id.event_zag);
            bus_st2 = itemView.findViewById(R.id.bus_st2);
        }

        public void bindView(int position){
            Bus bus = Data.BUS_LIST.get(position);
            time_1.setText(bus.getTime1());
            time_2.setText(bus.getTime_2());
            bus_st1.setText(bus.getName_1());
            bus_st2.setText(bus.getName_2());

        }

        @Override
        public void onClick(View view) {

        }
    }
}
