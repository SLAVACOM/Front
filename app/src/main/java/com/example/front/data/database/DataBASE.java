package com.example.front.data.database;

import androidx.collection.CircularArray;

import com.example.front.data.Appeal;
import com.example.front.data.BusJSON;
import com.example.front.data.EventJSON;
import com.example.front.data.HistoryJSON;
import com.example.front.data.News;
import com.example.front.data.UserRequestType;
import com.example.front.data.UserRequest;
import com.example.front.data.User;

import java.util.ArrayList;
import java.util.List;

public class DataBASE {

    private DataBASE(){}
    public static final List<Appeal> APPEALS_LIST= new ArrayList<>();


    public static User user = new User();

    public static List<BusJSON> BUS_JSON_LIST = new ArrayList<>();
    public static List<User> USERS_LIST = new ArrayList<>();

    public static List<News> NEWS_JSON_LIST = new ArrayList<>();
    public static List<HistoryJSON> HISTORY_JSON_LIST = new ArrayList<>();
    public static List<EventJSON> EVENT_JSON_LIST = new ArrayList<>();
    public static List<UserRequestType> REQUEST_TYPEJSON_LIST = new ArrayList<>();
    public static List<UserRequestType> REQUEST_TYPEJSON_byID_LIST = new ArrayList<>();
    public static List<UserRequest> REQUEST_LIB_LIST = new ArrayList<>();
    public static List<UserRequest> REQUEST_ADMIN_LIST = new ArrayList<>();
    public static  String token = "";

}
