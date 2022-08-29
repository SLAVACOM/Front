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
import com.example.front.data.database.DataBASE;
import com.example.front.data.HistoryJSON;
import com.example.front.data.ListRESPONSE;
import com.example.front.retrofit.RetrofitClient;

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

        getHistory();

        return view;
    }

    @Override
    public void onStart() {
        super.onStart();
        getHistory();
    }

    public void getHistory(){
        Call<ListRESPONSE<HistoryJSON>> getHistory = RetrofitClient.getInstance().getApi().getEventHistory("Bearer " + DataBASE.token);
        getHistory.enqueue(new Callback<ListRESPONSE<HistoryJSON>>() {
            @Override
            public void onResponse(Call<ListRESPONSE<HistoryJSON>> call, Response<ListRESPONSE<HistoryJSON>> response) {

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
            public void onFailure(Call<ListRESPONSE<HistoryJSON>> call, Throwable t) {
                t.printStackTrace();
            }
        });
    }
}