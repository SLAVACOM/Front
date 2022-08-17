package com.example.front.retrofit.maper;

import android.util.Log;

import com.example.front.CONST.CONST;
import com.example.front.retrofit.HistoryJSON;
import com.example.front.retrofit.MapObject;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;


public class HistoryMapper {
    public static HistoryJSON HistoryFromJSON(JSONObject jsonObject){
        HistoryJSON historyJSON = new HistoryJSON();
        try {
            historyJSON.setId(jsonObject.optInt("id"));
            historyJSON.setUser_id(jsonObject.optInt("user_id"));
            historyJSON.setMap_object_id(jsonObject.optInt("map_object_id"));
            historyJSON.setPoints(jsonObject.optInt("points"));
            historyJSON.setCreated_at(jsonObject.optString("created_at"));
            historyJSON.setUpdated_at(jsonObject.optString("updated_at"));
            Log.d(CONST.SERVER_LOG,historyJSON.toString());

        } catch (Exception e){
            e.printStackTrace();
        }

        return historyJSON;
    }
}
