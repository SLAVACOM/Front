package com.example.front.ui.bus;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.front.CONST.CONST;
import com.example.front.R;
import com.example.front.adapter.Adapter_bus;
import com.example.front.data.DataData;
import com.example.front.retrofit.RetrofitClient;
import com.example.front.retrofit.maper.BusMapper;
import com.google.gson.Gson;
import com.google.gson.JsonObject;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class FragmentBusAdd extends Fragment {

    private Button button;
    private EditText title,place, time;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_bus_add, container, false);
        button = view.findViewById(R.id.bt_fragment_busAdd);
        title = view.findViewById(R.id.etv_fragment_busAdd_title);
        place = view.findViewById(R.id.etv_fragment_busAdd_place);
        time = view.findViewById(R.id.etv_fragment_busAdd_time);


        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Call<JsonObject> addBus = RetrofitClient.getInstance().getApi().addBusEvent("Bearer " + DataData.token,title.getText().toString(),place.getText().toString(),time.getText().toString());
                addBus.enqueue(new Callback<JsonObject>() {
                    @Override
                    public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                        if (response.code()==200){
                            Toast.makeText(getContext(), "Успешно добавлено", Toast.LENGTH_SHORT).show();
                            FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                            fragmentManager.popBackStack();
                        }
                    }

                    @Override
                    public void onFailure(Call<JsonObject> call, Throwable t) {

                    }
                });
            }
        });



    return view;
    }

}