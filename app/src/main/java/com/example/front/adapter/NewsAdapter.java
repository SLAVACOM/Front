package com.example.front.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import com.example.front.R;
import com.example.front.data.database.DataBASE;
import com.example.front.data.NewsJSON;
import com.example.front.data.Photo;

import java.util.ArrayList;


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
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_news_photo, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        ((MyViewHolder) holder).bindView(position);
    }


    @Override
    public int getItemCount() {
        return DataBASE.NEWS_JSON_LIST.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener, View.OnLongClickListener {
        TextView event, zagal;
        ViewPager viewPager;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            viewPager = itemView.findViewById(R.id.viewPager);
            event = itemView.findViewById(R.id.tv_appeal1);
            zagal = itemView.findViewById(R.id.news_title);

            itemView.setOnLongClickListener(this);
        }

        public void bindView(int position) {
            NewsJSON newsJSON = DataBASE.NEWS_JSON_LIST.get(position);
            ArrayList<Photo> photos= DataBASE.NEWS_JSON_LIST.get(position).getPhotos();
            ViewPagerAdapter_News adapter_news = new ViewPagerAdapter_News(context, photos);
            event.setText(newsJSON.getDescription().replaceAll("<p>", "").replaceAll("</p>", "").replaceAll("&nbsp;", ""));
            zagal.setText("" + newsJSON.getTitle().replaceAll("<P>", ""));
            viewPager.setAdapter(adapter_news);

        }

        @Override
        public void onClick(View view) {
            clickListener.onItemClick(getAdapterPosition(), view);
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
