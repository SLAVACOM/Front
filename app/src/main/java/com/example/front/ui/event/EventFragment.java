package com.example.front.ui.event;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.front.CONST.CONST;
import com.example.front.R;
import com.example.front.adapter.AdapterEvents;
import com.example.front.data.EventJSON;
import com.example.front.data.ServerListResponse;
import com.example.front.data.database.DataBASE;
import com.example.front.retrofit.RetrofitClient;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class EventFragment extends Fragment {

    private AdapterEvents adapter;
    private RecyclerView recyclerView;
    private FloatingActionButton add;

    private SwipeRefreshLayout swipeRefreshLayout;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_event,container,false);
        adapter = new AdapterEvents();
        add = view.findViewById(R.id.fab_event_add);
        swipeRefreshLayout = view.findViewById(R.id.swipeRefresh);
        recyclerView = view.findViewById(R.id.recycler_event);
        adapter.setOnItemClickListener(new AdapterEvents.ClickListener(){
            public void onItemClick(int position, View view) {
                AddEventFragment fragment = new AddEventFragment();
                Bundle bundle = new Bundle();
                bundle.putInt("pos",position);
                fragment.setArguments(bundle);
                FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.nav_host_fragment_content_main,fragment).addToBackStack(null);
                fragmentTransaction.commit();
            }
        });
        recyclerView.setAdapter(adapter);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(layoutManager);

        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.nav_host_fragment_content_main,new AddEventFragment()).addToBackStack(null);
                fragmentTransaction.commit();
            }
        });
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                getEvent();
            }
        });
        return view;
    }

    @Override
    public void onStart() {

        getEvent();

        super.onStart();
    }

    @Override
    public void onStop() {
        super.onStop();
    }


    public void getEvent(){
        Call<ServerListResponse<EventJSON>> getEventList = RetrofitClient.getInstance().getApi().getEventList();
        getEventList.enqueue(new Callback<ServerListResponse<EventJSON>>() {
            @Override
            public void onResponse(Call<ServerListResponse<EventJSON>> call, Response<ServerListResponse<EventJSON>> response) {
                if(response.isSuccessful()){
                    DataBASE.EVENT_JSON_LIST.clear();
                    DataBASE.EVENT_JSON_LIST.addAll(response.body().getData());
                    Log.d(CONST.SERVER_LOG,DataBASE.EVENT_JSON_LIST.toString());
                    adapter.notifyDataSetChanged();
                }
                swipeRefreshLayout.setRefreshing(false);
            }

            @Override
            public void onFailure(Call<ServerListResponse<EventJSON>> call, Throwable t) {
                t.printStackTrace();
                swipeRefreshLayout.setRefreshing(false);
            }
        });

    }
}