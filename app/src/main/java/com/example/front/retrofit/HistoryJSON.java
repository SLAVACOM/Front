package com.example.front.retrofit;

public class HistoryJSON {

    private int id;
    private int user_id;
    private int map_object_id;
    private int points;
    private String created_at;
    private String updated_at;

    @Override
    public String toString() {
        return "HistoryJSON{" +
                "id=" + id +
                ", user_id=" + user_id +
                ", map_object_id=" + map_object_id +
                ", points=" + points +
                ", created_at='" + created_at + '\'' +
                ", updated_at='" + updated_at + '\'' +
                '}';
    }



    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getUser_id() {
        return user_id;
    }

    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }

    public int getMap_object_id() {
        return map_object_id;
    }

    public void setMap_object_id(int map_object_id) {
        this.map_object_id = map_object_id;
    }

    public int getPoints() {
        return points;
    }

    public void setPoints(int points) {
        this.points = points;
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
}
