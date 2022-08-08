package com.example.front.data;

public class News {

    private String event;
    private String zagal;
    private String time;

    public News(String event, String zagal,String time) {
        this.event = event;
        this.zagal = zagal;
        this.time = time;
    }

    public String getEvent(){return event;}
    public String getTime(){return time;}
    public String getZagal(){return zagal;}

}