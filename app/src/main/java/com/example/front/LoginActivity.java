package com.example.front;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.front.CONST.CONST;
import com.example.front.ui.main.LoginFragment;
import com.example.front.ui.news.NewsFragment;
import com.yandex.mapkit.MapKitFactory;

public class LoginActivity extends AppCompatActivity {

    SharedPreferences sharedPreferences;

    String UserId = "";




    Button button;
    FragmentManager fragmentManager;
    FragmentTransaction fragmentTransaction;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_activity);
        fragmentManager = getSupportFragmentManager();
        fragmentTransaction = getSupportFragmentManager().beginTransaction();


        button= findViewById(R.id.bt_login);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(),MainActivity.class);
                startActivity(intent);
                finish();


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