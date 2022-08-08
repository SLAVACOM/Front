package com.example.front.data;

public class Event {

    private String zagal;
    private String content;
    private String time;


    public Event(String zagal, String context, String time) {
        this.zagal = zagal;
        this.content = context;
        this.time = time;
    }

    public String getZagal(){return zagal;}
    public String getTime(){return time;}
    public String getContent(){return content;}
}
