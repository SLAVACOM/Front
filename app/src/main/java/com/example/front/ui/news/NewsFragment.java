package com.example.front.ui.news;

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
import com.example.front.adapter.NewsAdapter;
import com.example.front.data.DataData;
import com.example.front.retrofit.Data;
import com.example.front.retrofit.ListRESPONSE;
import com.example.front.retrofit.NewsJSON;
import com.example.front.retrofit.RetrofitClient;
import com.example.front.retrofit.maper.EventMaper;
import com.example.front.retrofit.maper.NewsMapper;
import com.google.gson.Gson;
import com.google.gson.JsonObject;

import org.json.JSONException;
import org.json.JSONObject;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class NewsFragment extends Fragment {


    NewsAdapter adapter;
    RecyclerView recyclerView;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_news,container,false);
        adapter = new NewsAdapter();
        recyclerView = view.findViewById(R.id.recycler_my_appeal);
        recyclerView.setAdapter(adapter);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(layoutManager);
        return view;
    }

    @Override
    public void onStart() {
        super.onStart();

        Call<JsonObject> getNewsList = RetrofitClient.getInstance().getApi().getNewsList();
        getNewsList.enqueue(new Callback<JsonObject>() {
            @Override
            public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                if(response.code()==200){
                    DataData.EVENT_JSON_LIST.clear();
                    try {
                        JSONObject jsonObject = new JSONObject(new Gson().toJson(response.body()));
//                        jsonObject = jsonObject.getJSONObject("data");
                        NewsMapper.NewsFromJson(jsonObject);
                        adapter.notifyDataSetChanged();
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }

            @Override
            public void onFailure(Call<JsonObject> call, Throwable t) {
                t.printStackTrace();

            }
        });
    }
}