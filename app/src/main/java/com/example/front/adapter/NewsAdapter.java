package com.example.front.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.front.R;
import com.example.front.data.Bus;
import com.example.front.data.Data;
import com.example.front.data.News;

import java.util.List;


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
        return Data.NEWS_LIST.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder{
        TextView time, event, zagal;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            time = itemView.findViewById(R.id.time_news);
            event = itemView.findViewById(R.id.news_news);
            zagal = itemView.findViewById(R.id.news_zag);
        }
        public void bindView(int position){
            News news = Data.NEWS_LIST.get(position);
            time.setText(news.getTime());
            event.setText(news.getEvent());
            zagal.setText(news.getZagal());

        }
    }
}
