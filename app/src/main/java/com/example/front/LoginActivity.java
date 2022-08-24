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
import com.example.front.retrofit.ObjectResponse;
import com.example.front.retrofit.User;
import com.example.front.retrofit.RetrofitClient;
import com.example.front.retrofit.maper.UserMapper;
import com.google.gson.Gson;
import com.google.gson.JsonObject;

import org.json.JSONException;
import org.json.JSONObject;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginActivity extends AppCompatActivity  {

    SharedPreferences sharedPreferences;
    EditText login, password;
    Button button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_activity);
        password = findViewById(R.id.etv_login_password);
        login = findViewById(R.id.etv_login_login);
        login.setText("a@mail.ru");
        password.setText("admin1");
        button = findViewById(R.id.bt_login);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                button.setEnabled(false);
                getToken();
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        getProfile(true);
    }

    private  void getToken() {
        Call<JsonObject> call = RetrofitClient.getInstance().getApi().login(login.getText().toString(), password.getText().toString());
        call.enqueue(new Callback<JsonObject>() {
            @Override
            public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                if (response.code() == 500) {
                    Toast.makeText(LoginActivity.this, "Ошибка сервера", Toast.LENGTH_LONG).show();
                    button.setEnabled(true);
                    return;
                } else if (response.code() >= 400 && response.code() < 500) {
                    Toast.makeText(LoginActivity.this, "Неправильный логин или пароль", Toast.LENGTH_LONG).show();
                    button.setEnabled(true);
                    return;
                }
                try {
                    JSONObject jsonObject = new JSONObject(new Gson().toJson(response.body()));
                    String token = jsonObject.getJSONObject("data").getString("access_token");
                    saveUserToken(DataData.token = token);
                } catch (JSONException e) {
                    Toast.makeText(LoginActivity.this, "Неправильный логин или пароль", Toast.LENGTH_LONG).show();
                    e.printStackTrace();
                }finally {
                    getProfile();
                }
            }

            @Override
            public void onFailure(Call<JsonObject> call, Throwable t) {
                Toast.makeText(LoginActivity.this, "Для продолжения работы с профилем, необходимо обновить приложение", Toast.LENGTH_LONG).show();
                Log.d(CONST.SERVER_LOG, "ERROR: " + t.getMessage());
                button.setEnabled(true);
            }
        });
    }
    private void getProfile() {
        getProfile(false);
    }
    private void getProfile(boolean resume) {
        String token = getPreferences(0).getString(CONST.USER_TOKEN, null);
        if (token == null && resume) return;
        Call<ObjectResponse<User>> getProfileData = RetrofitClient.getInstance().getApi().getProfile("Bearer " + token);
        getProfileData.enqueue(new Callback<ObjectResponse<User>>() {
            @Override
            public void onResponse(Call<ObjectResponse<User>> call, Response<ObjectResponse<User>> response) {
                DataData.user = response.body().getData();
                Log.d(CONST.SERVER_LOG, "USERS " + DataData.user);
                startActivity(new Intent(getApplicationContext(), MainActivity.class));
            }

            @Override
            public void onFailure(Call<ObjectResponse<User>> call, Throwable t) {
                if (resume) Toast.makeText(LoginActivity.this, "Не удалось загрузить данные о профиле", Toast.LENGTH_LONG).show();
                Log.d(CONST.SERVER_LOG, "TOKEN " + token);
                button.setEnabled(true);
                t.printStackTrace();
            }
        });
    }

    private void saveUserToken(String userToken) {
        sharedPreferences = getPreferences(0);
        sharedPreferences.edit().putString(CONST.USER_TOKEN, userToken).commit();
        Log.d(CONST.SERVER_LOG, "Токен cохранён: " + userToken);
    }


}