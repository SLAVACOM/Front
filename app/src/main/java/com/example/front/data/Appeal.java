package com.example.front.data;

public class Appeal{
    private String name;
    private String content;
    private String theme;
    private String time;



    public Appeal(String theme, String content, String from, String time) {
        this.name =from;
        this.content =content;
        this.time =time;
        this.theme = theme;

    }

    public String getFrom(){return name;}
    public String getTheme(){return theme;}
    public String getContent(){return content;}
    public String getTime(){return time;}
}
