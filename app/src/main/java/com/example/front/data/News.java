package com.example.front.data;

import android.content.Context;

import androidx.annotation.NonNull;

import com.example.front.adapter.PhotosPagerAdapter;

import java.util.ArrayList;

public class News {
    private int id;
    private String title;
    private String description;
    private int user_id;
    private String created_at;
    private String updated_at;
    private ArrayList<Photo> photos;
    private User author;
    private String date;
    PhotosPagerAdapter adapter;

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public User getAuthor() {
        return author;
    }

    public void setAuthor(User author) {
        this.author = author;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getUser_id() {
        return user_id;
    }

    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }

    public String getCreated_at() {
        return created_at;
    }

    public void setCreated_at(String created_at) {
        this.created_at = created_at;
    }

    public String getUpdated_at() {
        return updated_at;
    }

    public void setUpdated_at(String updated_at) {
        this.updated_at = updated_at;
    }

    public ArrayList<Photo> getPhotos() {
        return photos;
    }

    public void setPhotos(ArrayList<Photo> photos) {
        this.photos = photos;
    }

    public void initAdapter(Context context){
        adapter = new PhotosPagerAdapter(context, getPhotos());
    }

    public PhotosPagerAdapter getAdapter() {
        return adapter;
    }

    @NonNull
    @Override
    public String toString() {
        return "Data{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", description='" + description + '\'' +
                ", user_id=" + user_id +
                ", created_at='" + created_at + '\'' +
                ", updated_at='" + updated_at + '\'' +
                ", photos=" + photos +
                ", author=" + author +
                ", date='" + date + '\'' +
                '}';
    }
}



