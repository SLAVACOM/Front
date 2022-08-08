package com.example.front.data;

public class Appeal{
    private String name;
    private String content;
    private String time;



    public Appeal(String content, String from, String time) {
        this.name =from;
        this.content =content;
        this.time =time;

    }

    public String getFrom(){return name;}
    public String getContent(){return content;}
    public String getTime(){return time;}
}
