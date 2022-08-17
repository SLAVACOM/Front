package com.example.front.ui.hisory;

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
import com.example.front.adapter.AdapterHistory;
import com.example.front.data.DataData;
import com.example.front.retrofit.Data;
import com.example.front.retrofit.HistoryJSON;
import com.example.front.retrofit.RetrofitClient;
import com.example.front.retrofit.maper.HistoryMapper;
import com.google.gson.Gson;
import com.google.gson.JsonObject;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class HistoryFragment extends Fragment {

    private AdapterHistory adapter;
    private RecyclerView recyclerView;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view= inflater.inflate(R.layout.fragment_history, container, false);
        adapter = new AdapterHistory();
        recyclerView = view.findViewById(R.id.recycler_history);
        recyclerView.setAdapter(adapter);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(layoutManager);


        return view;
    }

    @Override
    public void onStart() {
        super.onStart();
        Call<JsonObject> getHistory = RetrofitClient.getInstance().getApi().getEventHistory("Bearer " + DataData.token);
        getHistory.enqueue(new Callback<JsonObject>() {
            @Override
            public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                if(response.code()==200){
                    try {
                        JSONObject jsonObject = new JSONObject(new Gson().toJson(response.body()));
                        JSONArray array = jsonObject.getJSONArray("data");
                        DataData.HISTORY_JSON_LIST.clear();
                        for (int i = 0; i <array.length() ; i++) {
                            DataData.HISTORY_JSON_LIST.add(HistoryMapper.HistoryFromJSON(array.getJSONObject(i)));
                        }
                        adapter.notifyDataSetChanged();
                        Log.d(CONST.SERVER_LOG,response.body().toString());
                    }catch (JSONException e){
                        e.printStackTrace();
                    }
                }
            }

            @Override
            public void onFailure(Call<JsonObject> call, Throwable t) {

            }
        });


    }
}