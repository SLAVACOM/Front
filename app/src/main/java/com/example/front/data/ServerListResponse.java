package com.example.front.data;

import java.util.List;

public class ServerListResponse<T> {
    private List<T> data;
    public List<T> getData() {
        return data;
    }

    public void setData(List<T> data) {
        this.data = data;
    }

}