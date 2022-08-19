package com.example.front;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.front.CONST.CONST;
import com.example.front.data.DataData;
import com.example.front.retrofit.User;
import com.example.front.retrofit.RetrofitClient;
import com.example.front.retrofit.maper.UserMapper;
import com.google.gson.Gson;
import com.google.gson.JsonObject;

import org.json.JSONException;
import org.json.JSONObject;

import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginActivity extends AppCompatActivity {

    SharedPreferences sharedPreferences;


    EditText login,password;

    Button button;
    FragmentManager fragmentManager;
    FragmentTransaction fragmentTransaction;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_activity);
        fragmentManager = getSupportFragmentManager();
        fragmentTransaction = getSupportFragmentManager().beginTransaction();

        password = findViewById(R.id.etv_login_password);
        login = findViewById(R.id.etv_login_login);
        button= findViewById(R.id.bt_login);
        



        button.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                password.setText("admin1");
                login.setText("a@mail.ru");

                Call<JsonObject> call = RetrofitClient.getInstance().getApi().login(login.getText().toString(),password.getText().toString());
                call.enqueue(new Callback<JsonObject>() {
                    @Override
                    public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {

                        if (response.code()==200){
                            try {
                                JSONObject jsonObject = new JSONObject(new Gson().toJson(response.body()));
                                saveUserToken(jsonObject.getJSONObject("data").getString("access_token"));
                                DataData.token=(jsonObject.getJSONObject("data").getString("access_token"));
                                Call<JsonObject> getProfileData = RetrofitClient.getInstance().getApi().view_profil_data("Bearer " +DataData.token);
                                getProfileData.enqueue(new Callback<JsonObject>() {
                                    @Override
                                    public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                                        if (response.code()==200){
                                            try {
                                                JSONObject jsonObject = new JSONObject(new Gson().toJson(response.body()));
                                                JSONObject jsonObject1 = jsonObject.getJSONObject("data");
                                                User user = UserMapper.UserFullFromJson(jsonObject1);
                                                DataData.user = user;
                                                Log.d(CONST.SERVER_LOG,"USERS " + DataData.user);
                                            }catch (JSONException e){

                                            }
                                        }
                                    }

                                    @Override
                                    public void onFailure(Call<JsonObject> call, Throwable t) {

                                    }
                                });
                                startActivity(new Intent(getApplicationContext(),MainActivity.class));

                            }catch (JSONException e){

                            }


                        }else {
                            Toast.makeText(LoginActivity.this, "Неверные данные!", Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onFailure(Call<JsonObject> call, Throwable t) {
                        Log.d(CONST.SERVER_LOG,"ERROR: "+t.getMessage());
                    }
                });
            }
        });
    }
    private void saveUserToken(String userToken){
        sharedPreferences = getPreferences(0);
        sharedPreferences.edit().putString(CONST.USER_ID,userToken).commit();
        Log.d(CONST.SERVER_LOG,"Токен cохранён: "+userToken);
    }



}