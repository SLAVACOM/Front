package com.example.front.retrofit.maper;

import com.example.front.retrofit.User;

import org.json.JSONException;
import org.json.JSONObject;

public class UserMapper {
    public static User UserFullFromJson(JSONObject jsonObject){
        User user = null;
        try {
            user = new User();
            user.setId(jsonObject.optInt("id"));
            user.setName(jsonObject.optString("name"));
            user.setSecond_name(jsonObject.optString("second_name"));
            user.setEmail(jsonObject.optString("email"));
            user.setEmail_verified_at(jsonObject.optString("email_verified_at"));
            user.setPassword_reset_at(jsonObject.optString("password_reset_at"));
            user.setRole(jsonObject.optInt("role"));
            user.setBlocked(jsonObject.optInt("blocked"));
            user.setCreated_at(jsonObject.optInt("created_at"));
            user.setPhone(jsonObject.optString("phone"));
            user.setPoints(jsonObject.optInt("points"));
            user.setFull_name(jsonObject.optString("full_name"));
            user.setCurator(jsonObject.optBoolean("curator"));

        } catch (Exception e) {
            e.printStackTrace();
        }
        return user;
    }

}
