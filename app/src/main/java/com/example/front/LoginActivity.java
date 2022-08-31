package com.example.front;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.front.CONST.CONST;
import com.example.front.data.database.DataBASE;
import com.example.front.retrofit.responses.ObjectResponse;
import com.example.front.data.User;
import com.example.front.retrofit.RetrofitClient;
import com.example.front.ui.signup.SignUpActivity;
import com.google.gson.Gson;
import com.google.gson.JsonObject;

import org.json.JSONException;
import org.json.JSONObject;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginActivity extends AppCompatActivity  {

    public static final String LOGIN_PREFS = "login";
    EditText login, password;
    TextView signup, reset;
    Button button;
    boolean loginMode;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_activity);
        loginMode = true;
        password = findViewById(R.id.etv_login_password);
        login = findViewById(R.id.etv_login_login);
        signup = findViewById(R.id.signup);
        reset = findViewById(R.id.reset);
        login.setText("a@sugai.ru");
        password.setText("admin1");
        button = findViewById(R.id.bt_login);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                button.setEnabled(false);
                if(loginMode)  getToken();
                else resetPassword();
            }
        });
        reset.setOnClickListener((view) -> {
            loginMode = !loginMode;
            password.setText("");
            if (loginMode) {
                button.setText("Войти");
                reset.setText("Забыли пароль?");
                password.setVisibility(View.VISIBLE);
            } else {
                reset.setText("Войти с паролем");
                button.setText("Восстановить");
                password.setVisibility(View.INVISIBLE);
            }

        });
        signup.setOnClickListener((view) -> {
            startActivityForResult(new Intent(getBaseContext(), SignUpActivity.class), Activity.RESULT_OK);
        });
        password.setOnEditorActionListener((v, actionId, event) -> {
            if (actionId == EditorInfo.IME_ACTION_DONE) {
                if (loginMode) getToken();
            }
            return false;
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (loginMode) getProfile(true);
    }

    private  void getToken() {
        Call<JsonObject> call = RetrofitClient.getInstance().getApi().login(login.getText().toString(), password.getText().toString());
        call.enqueue(new LoginCallback<JsonObject>() {
            @Override
            public void onSuccess(Call<JsonObject> call, Response<JsonObject> response) {
                try {
                    JSONObject jsonObject = new JSONObject(new Gson().toJson(response.body()));
                    String token = jsonObject.getJSONObject("data").getString("access_token");
                    saveUserToken(getBaseContext(), DataBASE.token = token);
                } catch (JSONException e) {
                    Toast.makeText(LoginActivity.this, "Неправильный логин или пароль", Toast.LENGTH_LONG).show();
                    e.printStackTrace();
                }finally {
                    getProfile();
                }
            }
        });
    }

    private  void resetPassword() {
        Call<ResponseBody> call = RetrofitClient.getInstance().getApi().resetPassword(login.getText().toString());
        call.enqueue(new LoginCallback<ResponseBody>() {
            @Override
            public void onSuccess(Call<ResponseBody> call, Response<ResponseBody> response) {
                if (response.code() == 200) {
                    Toast.makeText(getBaseContext(), "Пароль для входа был отправлен на вашу почту", Toast.LENGTH_LONG).show();
                }
                button.setEnabled(true);
            }
        });
    }
    public User getProfile() {
        return getProfile(false);
    }
    public User getProfile(boolean resume) {
        String token = userToken(getBaseContext());
        if (token == null) return null;
        Call<ObjectResponse<User>> getProfileData = RetrofitClient.getInstance().getApi().getProfile("Bearer " + token);
        getProfileData.enqueue(new Callback<ObjectResponse<User>>() {
            @Override
            public void onResponse(Call<ObjectResponse<User>> call, Response<ObjectResponse<User>> response) {
                if (response.isSuccessful()) {
                    DataBASE.user = response.body().getData();
                    Log.d(CONST.SERVER_LOG, "USERS " + DataBASE.user);
                    startActivity(new Intent(getApplicationContext(), MainActivity.class));
                }
                button.setEnabled(true);
            }

            @Override
            public void onFailure(Call<ObjectResponse<User>> call, Throwable t) {
                if (!resume) Toast.makeText(LoginActivity.this, "Не удалось загрузить данные о профиле", Toast.LENGTH_LONG).show();
                Log.d(CONST.SERVER_LOG, "TOKEN " + token);
                button.setEnabled(true);
                t.printStackTrace();
            }
        });
        return  DataBASE.user;
    }
    public static String userToken(Context context) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(LoginActivity.LOGIN_PREFS, 0);
        return sharedPreferences.getString(CONST.USER_TOKEN, null);
    }

    public static void saveUserToken(Context context, String token) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(LoginActivity.LOGIN_PREFS, 0);
        if (token != null){
            sharedPreferences.edit().putString(CONST.USER_TOKEN, token).commit();
            Log.d(CONST.SERVER_LOG, "Токен cохранён: " + token);
        }
        else{
            sharedPreferences.edit().remove(CONST.USER_TOKEN).commit();
            Log.d(CONST.SERVER_LOG, "Токен удален: ");
        }
    }

    abstract class LoginCallback<T> implements Callback<T> {
        abstract public void onSuccess(Call<T> call, Response<T> response);
        @Override
        public void onResponse(Call<T> call, Response<T> response) {
            if (response.code() >= 500) {
                Toast.makeText(LoginActivity.this, "Ошибка сервера", Toast.LENGTH_LONG).show();
                button.setEnabled(true);
                return;
            } else if (response.code() >= 400) {
                Toast.makeText(LoginActivity.this,
                        loginMode
                                ? "Неправильный логин или пароль"
                                : "Такого пользователя не существует",
                        Toast.LENGTH_LONG).show();
                button.setEnabled(true);
                return;
            }
            this.onSuccess(call, response);
        }

        @Override
        public void onFailure(Call<T> call, Throwable t) {
            Toast.makeText(LoginActivity.this, "Не могу связаться с сервером. Проверьте включен ли у вас инетернет.", Toast.LENGTH_LONG).show();
            Log.d(CONST.SERVER_LOG, "ERROR: " + t.getMessage());
            button.setEnabled(true);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        String email = data.getStringExtra("email");
        if (email != null && !email.isEmpty()) {
            login.setText(email);
        }
    }
}