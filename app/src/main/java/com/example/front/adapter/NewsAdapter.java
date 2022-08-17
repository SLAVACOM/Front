package com.example.front.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.front.R;
import com.example.front.data.DataData;
import com.example.front.retrofit.Datum;
import com.example.front.retrofit.NewsJSON;


public class NewsAdapter extends RecyclerView.Adapter<NewsAdapter.MyViewHolder> {


    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_news,parent,false);
        return new MyViewHolder(view);
    }



    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        ((MyViewHolder)holder).bindView(position);
    }



    @Override
    public int getItemCount() {
        return DataData.NEWS_JSON_LIST.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder{
        TextView time, event, zagal;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            time = itemView.findViewById(R.id.time_news);
            event = itemView.findViewById(R.id.tv_appeal1);
            zagal = itemView.findViewById(R.id.news_title);
        }
        public void bindView(int position){
            Datum datum = DataData.NEWS_JSON_LIST.get(position);

            time.setText(datum.getCreated_at().replaceFirst(".000000Z","").replaceFirst("T"," "));
            event.setText(datum.getDescription().replaceAll("<p>","").replaceAll("</p>","").replaceAll("&","").replaceAll("nbsp;",""));
            zagal.setText(datum.getTitle());

        }
    }
}
