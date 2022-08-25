package com.example.front.ui.User;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import com.example.front.CONST.CONST;
import com.example.front.R;
import com.example.front.data.DataData;
import com.example.front.retrofit.RetrofitClient;
import com.google.gson.Gson;
import com.google.gson.JsonObject;

import org.json.JSONException;
import org.json.JSONObject;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class UserListEditFragment extends Fragment {

    private EditText name, second_name,last_name,password,password_confirmation;
    private Button button;
    private CheckBox checkBox;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view= inflater.inflate(R.layout.fragment_user_edit, container, false);

        name = view.findViewById(R.id.etv_prof_edit_name);
        second_name = view.findViewById(R.id.etv_prof_edit_second_name);
        last_name = view.findViewById(R.id.etv_prof_edit_last_name);
        password = view.findViewById(R.id.etv_prof_edit_password);
        password_confirmation = view.findViewById(R.id.etv_prof_edit_password_confirmation);
        checkBox = view.findViewById(R.id.cb_curator);

        name.setText(DataData.user.getName());
        second_name.setText(DataData.user.getSecond_name());
        last_name.setText(DataData.user.getLast_name());

        button = view.findViewById(R.id.bt_editProf);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
            }
        });

        return view;
    }
}