package com.example.front.retrofit.responses;


public class ObjectResponse<T> {
    private T data;

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

}
