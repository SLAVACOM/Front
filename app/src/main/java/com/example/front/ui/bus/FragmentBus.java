package com.example.front.ui.bus;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.front.CONST.CONST;
import com.example.front.R;
import com.example.front.adapter.Adapter_bus;
import com.example.front.data.Bus;
import com.example.front.data.DataData;
import com.example.front.retrofit.Data;
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


public class FragmentBus extends Fragment {
    private Adapter_bus adapterBus;
    public RecyclerView recyclerView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_bus,container,false);
        adapterBus = new Adapter_bus();
        recyclerView = view.findViewById(R.id.recycler_bus);
        recyclerView.setAdapter(adapterBus);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(layoutManager);

        Call<JsonObject> getBus = RetrofitClient.getInstance().getApi().getBusList();
        getBus.enqueue(new Callback<JsonObject>() {
            @Override
            public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                if(response.code()==200){
                    try {
                        JSONObject jsonObject = new JSONObject(new Gson().toJson(response.body()));
                        JSONArray jsonArray = jsonObject.getJSONArray("data");
                        DataData.BUS_JSON_LIST.clear();
                        for (int i = 0; i <jsonArray.length() ; i++) {
                            JSONObject object = jsonArray.optJSONObject(i);
                            DataData.BUS_JSON_LIST.add(BusMapper.BusFromJSON(object));

                        }
                        Log.d(CONST.SERVER_LOG,""+DataData.BUS_JSON_LIST);
                        adapterBus.notifyDataSetChanged();

                    } catch (JSONException e){
                        e.printStackTrace();
                    }
                }
            }

            @Override
            public void onFailure(Call<JsonObject> call, Throwable t) {

            }
        });

        return view;
    }
}