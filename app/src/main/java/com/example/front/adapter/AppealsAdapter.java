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
import com.example.front.data.Appeal;
import com.example.front.data.database.DataBASE;


public class AppealsAdapter extends LoadMoreAdapter<Appeal> {

    public AppealsAdapter(Context context, RecyclerView rv) {
        super(context, rv, DataBASE.APPEALS_LIST);
    }


    private static ClickListener clickListener;


    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        RecyclerView.ViewHolder holder = super.onCreateViewHolder(parent, viewType);
        if (holder != null) return holder;
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_appeal,parent,false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        super.onBindViewHolder(holder, position);
        if (holder instanceof MyViewHolder) {
            ((MyViewHolder)holder).bindView(position);
        }
    }

    @Override
    public int getItemCount() {
        return DataBASE.APPEALS_LIST.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        TextView name,content,theme;
        ViewPager viewPager;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            theme = itemView.findViewById(R.id.tv_appeal_theme);
            name = itemView.findViewById(R.id.tv_appeal_name);
            content = itemView.findViewById(R.id.tv_appeal_content);
            viewPager = itemView.findViewById(R.id.viewPager);
        }

        public void bindView(int position){
            Appeal appeal = DataBASE.APPEALS_LIST.get(position);
            theme.setText("Автор: "+appeal.getAuthor().getFull_name());
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                content.setText(Html.fromHtml(appeal.getDescription(), Html.FROM_HTML_MODE_COMPACT));
            } else {
                content.setText(Html.fromHtml(appeal.getDescription()));
            }
            name.setText("Опубликовано: " + appeal.getDate());
//            status.setText(appeal.getState()+"");
            NewsPhotosViewPager adapter_news = new NewsPhotosViewPager(context, appeal.getPhotos());
            viewPager.setAdapter(adapter_news);
        }

        @Override
        public void onClick(View view) {
            AppealsAdapter.clickListener.onItemClick(getAdapterPosition(), view);
        }
    }


    public void setClickListener(AppealsAdapter.ClickListener clickListener){
        AppealsAdapter.clickListener = clickListener;
    }

    public interface ClickListener{
        void onItemClick(int position,View view);
        void onItemLongClick(int position,View view);
    }
}
