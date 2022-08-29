package com.example.front.retrofit.maper;

import android.util.Log;

import com.example.front.CONST.CONST;
import com.example.front.data.database.DataBASE;
import com.example.front.data.Data;
import com.example.front.data.NewsJSON;
import com.example.front.data.Photo;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

public class NewsMapper {
    public static NewsJSON NewsFromJson(JSONObject jsonObject){
    try {
        NewsJSON newsJSON = new NewsJSON();
        JSONArray DataArray = jsonObject.getJSONArray("data");
        DataBASE.NEWS_JSON_LIST.clear();
        for (int i = 0; i < DataArray.length() ; i++) {
            Data data = new Data();
            JSONObject object  = DataArray.getJSONObject(i);
            data.setId(object.optInt("id"));
            data.setTitle(object.optString("title"));
            data.setDescription(object.optString("description"));
            data.setUser_id(object.optInt("user_id"));
            data.setCreated_at(object.optString("created_at"));
            data.setUpdated_at(object.optString("updated_at"));

            ArrayList<Photo> photos= new ArrayList<>();
            JSONArray photoarray = object.getJSONArray("photos");
            for (int j = 0; j < photoarray.length(); j++) {
                Photo photo =new Photo();
                JSONObject object1 = photoarray.getJSONObject(j);
                photo.setId(object1.getInt("id"));
                photo.setFile(object1.optString("file"));
                photo.setUser_post_id(object1.getInt("post_id"));
                photo.setCreated_at(object1.getString("created_at"));
                photo.setUpdated_at(object1.getString("updated_at"));
                photos.add(photo);

            }
            data.setPhotos(photos);
//            newsJSON.setData(data);
            DataBASE.NEWS_JSON_LIST.add(newsJSON);
        }
        Log.d(CONST.SERVER_LOG, DataBASE.NEWS_JSON_LIST.toString());


    }catch (Exception e){
        e.printStackTrace();
    }
        return null;
    }

}
