package com.example.front.data;

import com.example.front.retrofit.BusJSON;
import com.example.front.retrofit.Datum;
import com.example.front.retrofit.EventJSON;
import com.example.front.retrofit.HistoryJSON;
import com.example.front.retrofit.MapObject;
import com.example.front.retrofit.NewsJSON;
import com.example.front.retrofit.User;
import com.example.front.ui.hisory.HistoryFragment;

import java.util.ArrayList;
import java.util.List;

public class DataData {
    private DataData(){}
    public static final List<News> NEWS_LIST= new ArrayList<>();
    public static final List<Appeal> APPEALS_LIST= new ArrayList<>();

    public static final List<Event> EVENT_LIST= new ArrayList<>();

    public static final List<MapObject> MAP_OBJECTS = new ArrayList<>();

    public static MapObject MAP_OBJECT = new MapObject();

    public static User user = new User();

    public static List<BusJSON> BUS_JSON_LIST = new ArrayList<>();
    public static List<Datum> NEWS_JSON_LIST = new ArrayList<>();
    public static List<HistoryJSON> HISTORY_JSON_LIST = new ArrayList<>();
    public static List<EventJSON> EVENT_JSON_LIST = new ArrayList<>();
    public static  String token = "";

    public static String BearerToken = "Bearer "+token;
}
