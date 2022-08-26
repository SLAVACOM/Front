package com.example.front.data;

import com.example.front.retrofit.Photo;
import com.example.front.retrofit.User;

import java.util.ArrayList;

public class Appeal{
    private String title;
    private String description;
    private String comment;
    private String date;
    public int state;
    public int likes;
    public int user_like;
    public User author;
    public ArrayList<Photo> photos;



    public Appeal(String theme, String content, String from, String time) {
        this.title =from;
        this.description =content;
        this.date =time;
        this.comment = theme;

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

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public int getState() {
        return state;
    }

    public void setState(int state) {
        this.state = state;
    }

    public int getLikes() {
        return likes;
    }

    public void setLikes(int likes) {
        this.likes = likes;
    }

    public int getUser_like() {
        return user_like;
    }

    public void setUser_like(int user_like) {
        this.user_like = user_like;
    }

    public User getAuthor() {
        return author;
    }

    public void setAuthor(User author) {
        this.author = author;
    }

    public ArrayList<Photo> getPhotos() {
        return photos;
    }

    public void setPhotos(ArrayList<Photo> photos) {
        this.photos = photos;
    }
}
