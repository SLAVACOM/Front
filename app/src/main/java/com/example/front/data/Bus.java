package com.example.front.data;

public class Bus {

    private String time_1;
    private String time_2;
    private String name_1;
    private String name_2;

    public Bus(String time_1, String time_2, String name_1,String name_2) {
        this.time_1 = time_1;
        this.time_2 = time_2;
        this.name_1 = name_1;
        this.name_2 = name_2;
    }

    public String getTime1(){return time_1;}
    public String getTime_2(){return time_2;}
    public String getName_1(){return name_1;}
    public String getName_2(){return name_2;}

    @Override
    public String toString() {
        return "Bus{" +
                "time_1='" + time_1 + '\'' +
                ", time_2='" + time_2 + '\'' +
                ", name_1='" + name_1 + '\'' +
                ", name_2='" + name_2 + '\'' +
                '}';
    }
}
