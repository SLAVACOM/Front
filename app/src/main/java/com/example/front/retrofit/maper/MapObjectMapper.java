package com.example.front.retrofit.maper;


import android.util.Log;

import com.example.front.CONST.CONST;
import com.example.front.retrofit.Cords;
import com.example.front.retrofit.MapObject;
import com.yandex.mapkit.geometry.Point;
import com.yandex.mapkit.geometry.Polygon;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import retrofit2.Call;

public class MapObjectMapper {
    public static MapObject MapObjectFromJson(JSONObject jsonObject){
        MapObject mapObject = null;
        ArrayList<Cords> pointArrayList=new ArrayList<>();
        try {
            mapObject = new MapObject();
            mapObject.setId(jsonObject.optInt("id"));
            mapObject.setName(jsonObject.optString("name"));

            mapObject.setType(jsonObject.optString("type"));
            mapObject.setApp_type(jsonObject.optInt("app_type"));
            mapObject.setColor(jsonObject.optString("color"));
            mapObject.setCreated_at(jsonObject.optString("created_at"));
            mapObject.setUpdated_at(jsonObject.optString("updated_at"));

            mapObject.setClient_id(jsonObject.optInt("client_id"));

            JSONArray array = jsonObject.optJSONArray("coords");
            for (int i = 0; i < array.length(); i++) {
                JSONArray array1 = array.optJSONArray(i);
                for (int j = 0; j < array1.length(); j++) {
                    Cords point = new Cords();
                    point.setLat(array1.getJSONObject(i).optDouble("lat"));
                    point.setLng(array1.getJSONObject(i).optDouble("lng"));
                    pointArrayList.add(point);
                    mapObject.setCoords(pointArrayList);

                }
            }

        }catch (Exception e){
            e.printStackTrace();
        }



        return mapObject;
    }
}
