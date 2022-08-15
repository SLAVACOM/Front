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
import com.example.front.retrofit.RetrofitClient;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginActivity extends AppCompatActivity {

    SharedPreferences sharedPreferences;

    String UserId = "";

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
                Call<ResponseBody> call = RetrofitClient.getInstance().getApi().login(login.getText().toString(),password.getText().toString());
                call.enqueue(new Callback<ResponseBody>() {
                    @Override
                    public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                        Log.d(CONST.SERVER_LOG,response.message());
                        Log.d(CONST.SERVER_LOG,""+response.code());
                        if (response.code()==200){
                            Toast.makeText(getApplicationContext(), "Успешно", Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(getApplicationContext(),MainActivity.class);
                            startActivity(intent);
                            finish();
                        }else {
                            Toast.makeText(LoginActivity.this, "Неверные данные!", Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onFailure(Call<ResponseBody> call, Throwable t) {
                        Log.d(CONST.SERVER_LOG,"ERROR: "+t.getMessage());
                    }
                });
            }
        });
    }
    private void saveUserId(String userId){
        sharedPreferences = getPreferences(0);
        sharedPreferences.edit().putString(CONST.USER_ID,userId).commit();
    }

    private void loadUserId(){
        sharedPreferences = getPreferences(0);
        UserId =sharedPreferences.getString(CONST.USER_ID,"");

    }


}