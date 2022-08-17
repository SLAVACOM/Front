package com.example.front.retrofit;

import java.util.ArrayList;

public class NewsJSON {

    private int current_page;
    private ArrayList<Datum> data;
    private String first_page_url;
    private int from;
    private int last_page;
    private String last_page_url;
    private ArrayList<Link> links;
    private Object next_page_url;
    private String path;
    private int per_page;
    private Object prev_page_url;

    private int myto;
    private int total;

    public NewsJSON() {
    }


    public int getCurrent_page() {
        return current_page;
    }

    public void setCurrent_page(int current_page) {
        this.current_page = current_page;
    }

    public ArrayList<Datum> getData() {
        return data;
    }

    public void setData(ArrayList<Datum> data) {
        this.data = data;
    }

    public String getFirst_page_url() {
        return first_page_url;
    }

    public void setFirst_page_url(String first_page_url) {
        this.first_page_url = first_page_url;
    }

    public int getFrom() {
        return from;
    }

    public void setFrom(int from) {
        this.from = from;
    }

    public int getLast_page() {
        return last_page;
    }

    public void setLast_page(int last_page) {
        this.last_page = last_page;
    }

    public String getLast_page_url() {
        return last_page_url;
    }

    public void setLast_page_url(String last_page_url) {
        this.last_page_url = last_page_url;
    }

    public ArrayList<Link> getLinks() {
        return links;
    }

    public void setLinks(ArrayList<Link> links) {
        this.links = links;
    }

    public Object getNext_page_url() {
        return next_page_url;
    }

    public void setNext_page_url(Object next_page_url) {
        this.next_page_url = next_page_url;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public int getPer_page() {
        return per_page;
    }

    public void setPer_page(int per_page) {
        this.per_page = per_page;
    }

    public Object getPrev_page_url() {
        return prev_page_url;
    }

    public void setPrev_page_url(Object prev_page_url) {
        this.prev_page_url = prev_page_url;
    }

    public int getMyto() {
        return myto;
    }

    public void setMyto(int myto) {
        this.myto = myto;
    }

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    @Override
    public String toString() {
        return "NewsJSON{" +
                "message='" + '\'' +
                ", result=" +
                ", current_page=" + current_page +
                ", data=" + data +
                ", first_page_url='" + first_page_url + '\'' +
                ", from=" + from +
                ", last_page=" + last_page +
                ", last_page_url='" + last_page_url + '\'' +
                ", links=" + links +
                ", next_page_url=" + next_page_url +
                ", path='" + path + '\'' +
                ", per_page=" + per_page +
                ", prev_page_url=" + prev_page_url +
                ", myto=" + myto +
                ", total=" + total +
                '}';
    }
}
