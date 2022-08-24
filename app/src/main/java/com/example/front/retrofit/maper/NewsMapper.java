package com.example.front.retrofit.maper;

import com.example.front.data.DataData;
import com.example.front.retrofit.Data;
import com.example.front.retrofit.Datum;
import com.example.front.retrofit.NewsJSON;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class NewsMapper {
    public static NewsJSON NewsFromJson(JSONObject jsonObject){
    try {
        NewsJSON newsJSON = new NewsJSON();
        JSONArray DataArray = jsonObject.getJSONArray("data");
        DataData.NEWS_JSON_LIST.clear();
        for (int i = 0; i < DataArray.length() ; i++) {
            Data data = new Data();
            JSONObject object = DataArray.getJSONObject(i);
            data.setId(object.optInt("id"));
            data.setTitle(object.optString("title"));
            data.setDescription(object.optString("description"));
            data.setUser_id(object.optInt("user_id"));
            data.setCreated_at(object.optString("created_at"));
            data.setUpdated_at(object.optString("updated_at"));
            newsJSON.setData(data);
            DataData.NEWS_JSON_LIST.add(newsJSON);
        }


    }catch (Exception e){
        e.printStackTrace();
    }


        return null;
    }

}
