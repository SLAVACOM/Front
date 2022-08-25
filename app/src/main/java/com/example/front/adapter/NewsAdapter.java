package com.example.front.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.front.R;
import com.example.front.data.DataData;
import com.example.front.data.News;
import com.example.front.retrofit.Datum;
import com.example.front.retrofit.NewsJSON;


public class NewsAdapter extends RecyclerView.Adapter {

    private static ClickListener clickListener;
    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_news,parent,false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        ((MyViewHolder)holder).bindView(position);
    }



    @Override
    public int getItemCount() {
        return DataData.NEWS_JSON_LIST.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener,View.OnLongClickListener{
        TextView time, event, zagal;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            time = itemView.findViewById(R.id.time_news);
            event = itemView.findViewById(R.id.tv_appeal1);
            zagal = itemView.findViewById(R.id.news_title);
            itemView.setOnLongClickListener(this);
        }
        public void bindView(int position){
            NewsJSON newsJSON  = DataData.NEWS_JSON_LIST.get(position);

            time.setText(""+newsJSON.getData().getCreated_at().replaceAll("T"," ").replaceAll(".000000Z",""));
            event.setText(newsJSON.getData().getDescription().replaceAll("<p>","").replaceAll("</p>","").replaceAll("&nbsp;",""));
            zagal.setText(""+newsJSON.getData().getTitle().replaceAll("<P>",""));

        }

        @Override
        public void onClick(View view) {
            clickListener.onItemClick(getAdapterPosition(),view);
        }

        @Override
        public boolean onLongClick(View view) {
            clickListener.onItemLongClick(getAdapterPosition(), view);
            return false;
        }
    }

    public void setClickListener(ClickListener clickListener){
        NewsAdapter.clickListener = clickListener;
    }

    public interface ClickListener{
        void onItemClick(int position,View view);
        void onItemLongClick(int position,View view);
    }
}
