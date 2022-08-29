package com.example.front.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.front.R;
import com.example.front.data.database.DataBASE;
import com.example.front.data.HistoryJSON;

public class AdapterHistory extends RecyclerView.Adapter<AdapterHistory.MyViewHolder> {


    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_history,parent,false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        ((MyViewHolder)holder).bindView(position);

    }

    @Override
    public int getItemCount() {
        return DataBASE.HISTORY_JSON_LIST.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        TextView time, points,mapId;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            time = itemView.findViewById(R.id.tv_history_time);
            points = itemView.findViewById(R.id.tv_history_points);
            mapId = itemView.findViewById(R.id.tv_history_mapObjectId);
        }

        public void bindView(int position){
            HistoryJSON historyJSON = DataBASE.HISTORY_JSON_LIST.get(position);
            time.setText(historyJSON.getCreated_at());
            points.setText(historyJSON.getPoints()+" баллов благодарности");
            mapId.setText("Место: "+historyJSON.getMap_object().getName());

        }

        @Override
        public void onClick(View view) {

        }
    }
}
