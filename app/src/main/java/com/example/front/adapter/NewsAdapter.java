package com.example.front.adapter;

import android.content.Context;
import android.os.Build;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import com.example.front.R;
import com.example.front.data.News;
import com.example.front.data.Photo;
import com.example.front.data.database.DataBASE;

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
        TextView content, zagal;
        ViewPager viewPager;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            viewPager = itemView.findViewById(R.id.viewPager);
            content = itemView.findViewById(R.id.news_content);
            zagal = itemView.findViewById(R.id.news_title);
            viewPager.setVisibility(View.GONE);
            itemView.setOnLongClickListener(this);
        }

        public void bindView(int position) {
            News news = DataBASE.NEWS_JSON_LIST.get(position);
            zagal.setText("" + news.getTitle().replaceAll("<P>", ""));
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                content.setText(Html.fromHtml(news.getDescription(), Html.FROM_HTML_MODE_COMPACT));
            } else {
                content.setText(Html.fromHtml(news.getDescription()));
            }
            ArrayList<Photo> photos = DataBASE.NEWS_JSON_LIST.get(position).getPhotos();
            if (photos.size()> 0) {
                NewsPhotosViewPager adapter_news = new NewsPhotosViewPager(context, photos);
                viewPager.setAdapter(adapter_news);
                viewPager.setVisibility(View.VISIBLE);
            }
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

    public void setClickListener(ClickListener clickListener) {
        NewsAdapter.clickListener = clickListener;
    }


    public interface ClickListener {
        void onItemClick(int position, View view);

        void onItemLongClick(int position, View view);
    }
}
