package com.example.front.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.HorizontalScrollView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.front.R;
import com.example.front.data.DataData;
import com.example.front.data.News;
import com.example.front.retrofit.Data;
import com.example.front.retrofit.Datum;
import com.example.front.retrofit.NewsJSON;
import com.squareup.picasso.Picasso;


public class NewsAdapter extends RecyclerView.Adapter {
    public Context context;
    public NewsAdapter(Context context) {
        this.context = context;
    }

    public Context getContext() {
        return context;
    }

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
        TextView  event, zagal;
        HorizontalScrollView  layout;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            event = itemView.findViewById(R.id.tv_appeal1);
            zagal = itemView.findViewById(R.id.news_title);
            layout = itemView.findViewById(R.id.layoutNews);

            itemView.setOnLongClickListener(this);
        }
        public void bindView(int position){
            NewsJSON newsJSON  = DataData.NEWS_JSON_LIST.get(position);

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


        public void addView(ImageView imageView,int width,int height){
            LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(width,height);
            layoutParams.setMargins(0,100,0,100);
            imageView.setLayoutParams(layoutParams);
            layout.addView(imageView);

        }
    }

    public void setClickListener(ClickListener clickListener){
        NewsAdapter.clickListener = clickListener;
    }

    public interface ClickListener{
        void onItemClick(int position,View view);
        void onItemLongClick(int position,View view);
    }
    private void loadQrCode(String url){
        ImageView imageView= new ImageView(getContext());

        Picasso.with(context).load(url).placeholder(R.mipmap.ic_launcher).error(R.mipmap.ic_launcher).into(imageView, new com.squareup.picasso.Callback(){

            @Override
            public void onSuccess() {

            }

            @Override
            public void onError() {

            }
        });
    }


}
