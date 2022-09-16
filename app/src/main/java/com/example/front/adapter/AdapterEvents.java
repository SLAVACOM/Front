package com.example.front.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.front.R;
import com.example.front.data.database.DataBASE;
import com.example.front.data.EventJSON;
import com.example.front.scanner.CaptureAct;
import com.journeyapps.barcodescanner.ScanOptions;

public class AdapterEvents extends RecyclerView.Adapter {
    public static ClickListener clickListener;


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
        TextView time, content, zagal;
        Button button;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            button = itemView.findViewById(R.id.bt_scanner);
            time = itemView.findViewById(R.id.tv_event_time);
            content = itemView.findViewById(R.id.tv_event_content);
            zagal = itemView.findViewById(R.id.tv_event_title);
            itemView.setOnLongClickListener(this);
            itemView.setOnClickListener(this);
        }
        public void bindView(int position){
            EventJSON event = DataBASE.EVENT_JSON_LIST.get(position);
            time.setText(""+event.getDate());
            content.setText(""+event.getPlace()+"\n\n"+event.getPoints()+" баллов благодарности");
            zagal.setText(""+event.getTitle());

            button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                }
            });

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
    public void setOnItemClickListener(ClickListener clickListener){
        AdapterEvents.clickListener = clickListener;
    }

    public abstract static class ClickListener{
        public abstract void onItemClick(int position, View view);
        void onItemLongClick(int position,View view) {};
    }
}
