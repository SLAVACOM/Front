package com.example.front.data;

import com.example.front.retrofit.BusJSON;
import com.example.front.retrofit.EventJSON;
import com.example.front.retrofit.HistoryJSON;
import com.example.front.retrofit.NewsJSON;
import com.example.front.retrofit.User;
import com.example.front.retrofit.RequestTypeJSON;

import java.util.ArrayList;
import java.util.List;

public class DataData {
    private DataData(){}
    public static final List<Appeal> APPEALS_LIST= new ArrayList<>();


    public static User user = new User();

    public static List<BusJSON> BUS_JSON_LIST = new ArrayList<>();
    public static List<User> USERS_LIST = new ArrayList<>();
    public static BusJSON deleteBus = new BusJSON();

    public static List<NewsJSON> NEWS_JSON_LIST = new ArrayList<>();
    public static List<HistoryJSON> HISTORY_JSON_LIST = new ArrayList<>();
    public static List<EventJSON> EVENT_JSON_LIST = new ArrayList<>();
    public static List<RequestTypeJSON> REQUEST_TYPEJSON_LIST = new ArrayList<>();
    public static List<RequestTypeJSON> REQUEST_TYPEJSON_byID_LIST = new ArrayList<>();
    public static  String token = "";

}
