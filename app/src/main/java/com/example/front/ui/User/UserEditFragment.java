package com.example.front.ui.User;

import android.graphics.Color;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.os.Parcelable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.front.CONST.CONST;
import com.example.front.LoginActivity;
import com.example.front.R;
import com.example.front.data.DataData;
import com.example.front.retrofit.RetrofitClient;
import com.google.gson.Gson;
import com.google.gson.JsonObject;

import org.json.JSONException;
import org.json.JSONObject;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class UserEditFragment extends Fragment {

    private EditText name, second_name,last_name,password,password_confirmation;
    private Button button;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view= inflater.inflate(R.layout.fragment_user_edit, container, false);

        name = view.findViewById(R.id.etv_prof_edit_name);
        second_name = view.findViewById(R.id.etv_prof_edit_second_name);
        last_name = view.findViewById(R.id.etv_prof_edit_last_name);
        password = view.findViewById(R.id.etv_prof_edit_password);
        password_confirmation = view.findViewById(R.id.etv_prof_edit_password_confirmation);

        name.setText(DataData.user.getName());
        second_name.setText(DataData.user.getSecond_name());
        last_name.setText(DataData.user.getLast_name());

        button = view.findViewById(R.id.bt_editProf);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!name.getText().toString().equals("")|!last_name.getText().toString().equals("")|!second_name.getText().toString().equals("")|
                        !password.getText().toString().equals("")|!password_confirmation.getText().toString().equals("")){
                    if(password.getText().length()>=6){
                        if (password.getText().toString().equals(password_confirmation.getText().toString())){
                            Call<JsonObject> editProf = RetrofitClient.getInstance().getApi().editProfile("Bearer "+ DataData.token,"PUT",name.getText().toString(),second_name.getText().toString(),last_name.getText().toString()
                            ,password.getText().toString(),password_confirmation.getText().toString());
                            editProf.enqueue(new Callback<JsonObject>() {
                                @Override
                                public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                                    Log.d(CONST.SERVER_LOG,response.body().toString());
                                    if(response.code()==200){
                                        Toast.makeText(getContext(), "Успешно", Toast.LENGTH_SHORT).show();
                                        FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                                        fragmentManager.popBackStack();
                                    }
                                    if (response.code()==422){
                                        try {
                                            JSONObject jsonObject = new JSONObject(new Gson().toJson(response.body()));
                                            Log.d(CONST.SERVER_LOG,jsonObject.getString("errors"));
                                        }catch (JSONException e) {
                                            e.printStackTrace();
                                        }
                                    }
                                }

                                @Override
                                public void onFailure(Call<JsonObject> call, Throwable t) {
                                }
                            });
                        } else{
                            Toast.makeText(getContext(), "Пароли не совпадают!", Toast.LENGTH_SHORT).show();
                            password_confirmation.setTextColor(-65536);
                            password.setTextColor(-65536);
                        }
                    }else Toast.makeText(getContext(), "Минимальная длинна пароля 6 сиволов!", Toast.LENGTH_SHORT).show();
                }else Toast.makeText(getContext(), "Не все поля запонены!", Toast.LENGTH_SHORT).show();
            }
        });

        return view;
    }
}