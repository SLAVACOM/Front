package com.example.front.retrofit.maper;

import com.example.front.retrofit.BusJSON;

import org.json.JSONException;
import org.json.JSONObject;

public class BusMapper {
    public static BusJSON BusFromJSON(JSONObject jsonObject){
        BusJSON busJSON = new BusJSON();
        try {

            busJSON.setId(jsonObject.optInt("id"));
            busJSON.setTitle(jsonObject.optString("title"));
            busJSON.setPlace(jsonObject.optString("place"));
            busJSON.setTime(jsonObject.optString("time"));
            busJSON.setUser_id(jsonObject.optInt("user_id"));
            busJSON.setCreated_at(jsonObject.optString("created_at"));
            busJSON.setUpdated_at(jsonObject.optString("updated_at"));
            busJSON.setColor(jsonObject.optString("color"));
            busJSON.setSkip(jsonObject.optBoolean("skip"));


        }catch (Exception e){
            e.printStackTrace();
        }
        return busJSON;
    }
}
