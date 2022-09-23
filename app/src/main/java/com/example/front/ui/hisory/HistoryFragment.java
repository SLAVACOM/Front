package com.example.front.ui.hisory;

import android.graphics.Color;
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
import com.example.front.data.HistoryJSON;
import com.example.front.data.ServerListResponse;
import com.example.front.data.database.DataBASE;
import com.example.front.retrofit.Retrofit;

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
        recyclerView.setBackgroundColor(Color.WHITE);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(layoutManager);

        getHistory();

        return view;
    }

    @Override
    public void onStart() {
        super.onStart();
        getHistory();
    }

    public void getHistory(){
        Call<ServerListResponse<HistoryJSON>> getHistory = Retrofit.getInstance().getApi().getEventHistory("Bearer " + DataBASE.token);
        getHistory.enqueue(new Callback<ServerListResponse<HistoryJSON>>() {
            @Override
            public void onResponse(Call<ServerListResponse<HistoryJSON>> call, Response<ServerListResponse<HistoryJSON>> response) {

                if(response.code()==200){
                    try {

                        DataBASE.HISTORY_JSON_LIST.clear();
                        DataBASE.HISTORY_JSON_LIST.addAll(response.body().getData());
                        adapter.notifyDataSetChanged();
                        Log.d(CONST.SERVER_LOG,response.body().getData().toString());
                    }catch (Exception e){
                        e.printStackTrace();
                    }
                }
            }

            @Override
            public void onFailure(Call<ServerListResponse<HistoryJSON>> call, Throwable t) {
                t.printStackTrace();
            }
        });
    }
}