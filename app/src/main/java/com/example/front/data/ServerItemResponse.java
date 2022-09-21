package com.example.front.data;

import java.util.List;

public class ServerItemResponse<T> {
    private T data;
    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

}
