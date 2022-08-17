package com.example.front.ui.event;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.front.R;
import com.example.front.adapter.AdapterEvents;
import com.example.front.data.DataData;
import com.example.front.retrofit.RetrofitClient;
import com.example.front.retrofit.maper.EventMaper;
import com.google.gson.Gson;
import com.google.gson.JsonObject;

import org.json.JSONException;
import org.json.JSONObject;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class EventFragment extends Fragment {

    AdapterEvents adapter;
    RecyclerView recyclerView;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_event,container,false);
        adapter = new AdapterEvents();
        recyclerView = view.findViewById(R.id.recycler_event);
        recyclerView.setAdapter(adapter);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(layoutManager);
        return view;
    }

    @Override
    public void onStart() {
        Call<JsonObject> getEventList = RetrofitClient.getInstance().getApi().getEventList();
        getEventList.enqueue(new Callback<JsonObject>() {
            @Override
            public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                if(response.code()==200){
                    DataData.EVENT_JSON_LIST.clear();
                    try {
                        JSONObject jsonObject = new JSONObject(new Gson().toJson(response.body()));
                        EventMaper.EventFromJSON(jsonObject);
                        adapter.notifyDataSetChanged();
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }

            @Override
            public void onFailure(Call<JsonObject> call, Throwable t) {

            }
        });
        super.onStart();
    }

    @Override
    public void onStop() {
        super.onStop();
    }
}