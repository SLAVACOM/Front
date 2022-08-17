package com.example.front.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.front.R;
import com.example.front.data.DataData;
import com.example.front.data.Event;
import com.example.front.retrofit.EventJSON;
import com.example.front.retrofit.HistoryJSON;

public class AdapterEvents extends RecyclerView.Adapter<AdapterEvents.MyViewHolder> {

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_event,parent,false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {

        ((MyViewHolder) holder).bindView(position);

    }



    @Override
    public int getItemCount() {
        return DataData.EVENT_JSON_LIST.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder{
        TextView time, content, zagal;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            time = itemView.findViewById(R.id.tv_event_time);
            content = itemView.findViewById(R.id.tv_event_content);
            zagal = itemView.findViewById(R.id.tv_event_title);
        }
        public void bindView(int position){
             EventJSON event = DataData.EVENT_JSON_LIST.get(position);
            time.setText(""+event.getCreated_at());
            content.setText(""+event.getPlace());
            zagal.setText(""+event.getTitle());

        }
    }
}
