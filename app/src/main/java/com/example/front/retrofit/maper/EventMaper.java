package com.example.front.retrofit.maper;

import android.util.Log;

import com.example.front.CONST.CONST;
import com.example.front.data.database.DataBASE;
import com.example.front.data.EventJSON;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class EventMaper {
    public static EventJSON EventFromJSON(JSONObject jsonObject){
        EventJSON eventJSON = null;
        try {
            eventJSON = new EventJSON();
            DataBASE.EVENT_JSON_LIST.clear();
            JSONArray array = jsonObject.optJSONArray("data");
            for (int i = 0; i <array.length() ; i++) {
                EventJSON eventJSON1 = new EventJSON();
                JSONObject object = array.optJSONObject(i);
                eventJSON1.setId(object.getInt("id"));
                eventJSON1.setTitle(object.optString("title"));
                eventJSON1.setPlace(object.optString("place"));
                eventJSON1.setDate(object.optString("date"));
                eventJSON1.setUser_id(object.optInt("user_id"));
                eventJSON1.setCreated_at(object.optString("created_at"));
                eventJSON1.setUpdated_at(object.optString("updated_at"));
                eventJSON1.setPoints(object.getInt("points"));
                DataBASE.EVENT_JSON_LIST.add(eventJSON1);
            }                Log.d(CONST.SERVER_LOG, DataBASE.EVENT_JSON_LIST.toString());


        } catch (JSONException e){
            e.printStackTrace();
        }


        return eventJSON;
    }






}
