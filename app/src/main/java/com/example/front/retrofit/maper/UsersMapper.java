package com.example.front.retrofit.maper;

import com.example.front.retrofit.User;

import org.json.JSONObject;

public class UsersMapper {
    public static User UserFullFromJson(JSONObject jsonObject){
        User user = new User();
        try {
            user.setId(jsonObject.optInt("id"));
            user.setEmail(jsonObject.optString("email"));
            user.setBlocked(jsonObject.optInt("blocked"));
            user.setPhone(jsonObject.optString("phone"));
            user.setPoints(jsonObject.optInt("points"));
            user.setFull_name(jsonObject.optString("full_name"));
            user.setAddress(jsonObject.optString("address"));

        } catch (Exception e) {
            e.printStackTrace();
        }
        return user;
    }

}
