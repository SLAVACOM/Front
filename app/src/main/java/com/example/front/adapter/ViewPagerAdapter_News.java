package com.example.front.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.viewpager.widget.PagerAdapter;

import com.example.front.data.Photo;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class ViewPagerAdapter_News extends PagerAdapter {
    private Context context;
    private ArrayList<Photo> imageUrls;


    public ViewPagerAdapter_News(Context context, ArrayList<Photo> imageUrls) {
        this.context = context;
        this.imageUrls = imageUrls;
    }
        @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return view ==object;
    }

    @Override
    public int getCount() {
        return imageUrls.size();
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        container.removeView((View) object);
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {

        ImageView imageView = new ImageView(context);
        Picasso.get().load("http://sugai.ru" +imageUrls.get(position).getFile()).fit().centerCrop().into(imageView);
        container.addView(imageView);
        return imageView;


    }
}
