package com.example.front.ui.request;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.example.front.CONST.CONST;
import com.example.front.R;
import com.example.front.adapter.RequestsAdminAdapter;
import com.example.front.data.ServerListResponse;
import com.example.front.data.ResponsLibrary;
import com.example.front.data.database.DataBASE;
import com.example.front.retrofit.RetrofitClient;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AdminREQFragment extends Fragment {

    FloatingActionButton flbt_add;
    RequestsAdminAdapter adapter;
    RecyclerView recyclerView;
    SwipeRefreshLayout swipeRefreshLayout;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view= inflater.inflate(R.layout.fragment_library, container, false);
        getREQUEST();
        flbt_add =view.findViewById(R.id.flBt_my_appeal);
        recyclerView = view.findViewById(R.id.recycler_appeal_byrequest);
        adapter = new RequestsAdminAdapter(getActivity());
        adapter.setOnItemClickListener(new RequestsAdminAdapter.ClickListener() {
              @Override
            public void onItemLongClick(int position, View v) {
                Bundle bundle = new Bundle();
                bundle.putInt("pos",position);

            }
        });
        recyclerView.setAdapter(adapter);
        swipeRefreshLayout = view.findViewById(R.id.swipeRefresh);
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                getREQUEST();
                swipeRefreshLayout.setRefreshing(false);
            }
        });
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(layoutManager);

        flbt_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getActivity(), "AdminREQFragment no action", Toast.LENGTH_SHORT).show();
            }
        });


        return view;
    }

    public void getREQUEST(){
        Call<ServerListResponse<ResponsLibrary>> getREQUEST = RetrofitClient.getInstance().getApi().getAdminRespons("Bearer "+DataBASE.token);
        getREQUEST.enqueue(new Callback<ServerListResponse<ResponsLibrary>>() {
            @Override
            public void onResponse(Call<ServerListResponse<ResponsLibrary>> call, Response<ServerListResponse<ResponsLibrary>> response) {
                if(response.code()==200){
                    try {
                        DataBASE.REQUEST_LIB_LIST.clear();
                        DataBASE.REQUEST_LIB_LIST.addAll(response.body().getData());
                        Log.d(CONST.SERVER_LOG, DataBASE.REQUEST_LIB_LIST.toString());
                        adapter.notifyDataSetChanged();
                    } catch (Exception e){
                        e.printStackTrace();
                    }
                }
            }

            @Override
            public void onFailure(Call<ServerListResponse<ResponsLibrary>> call, Throwable t) {
                t.printStackTrace();
            }
        });
    }

}