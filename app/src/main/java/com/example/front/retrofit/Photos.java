package com.example.front.retrofit;

public class Photos {
    private int id;
    private String file;
    private int post_id;
    private Data created_at;
    private Data updated_ay;

    public Photos() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getFile() {
        return file;
    }

    public void setFile(String file) {
        this.file = file;
    }

    public int getPost_id() {
        return post_id;
    }

    public void setPost_id(int post_id) {
        this.post_id = post_id;
    }

    public Data getCreated_at() {
        return created_at;
    }

    public void setCreated_at(Data created_at) {
        this.created_at = created_at;
    }

    public Data getUpdated_ay() {
        return updated_ay;
    }

    public void setUpdated_ay(Data updated_ay) {
        this.updated_ay = updated_ay;
    }
}
