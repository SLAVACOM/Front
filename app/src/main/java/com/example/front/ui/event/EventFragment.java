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
import android.widget.Toast;

import com.example.front.CONST.CONST;
import com.example.front.R;
import com.example.front.adapter.AdapterEvents;
import com.example.front.data.EventJSON;
import com.example.front.data.ListRESPONSE;
import com.example.front.data.database.DataBASE;
import com.example.front.retrofit.RetrofitClient;
import com.example.front.retrofit.maper.EventMaper;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.gson.Gson;
import com.google.gson.JsonObject;

import org.json.JSONException;
import org.json.JSONObject;

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
        adapter.setOnItemClickListener(new AdapterEvents.ClickListener() {
            @Override
            public void onItemClick(int position, View view) {
            }

            @Override
            public void onItemLongClick(int position, View view) {
                EventEditFragment fragment = new EventEditFragment();
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
                swipeRefreshLayout.setRefreshing(false);

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
        Call<ListRESPONSE<EventJSON>> getEventList = RetrofitClient.getInstance().getApi().getEventList();
        getEventList.enqueue(new Callback<ListRESPONSE<EventJSON>>() {
            @Override
            public void onResponse(Call<ListRESPONSE<EventJSON>> call, Response<ListRESPONSE<EventJSON>> response) {
                if(response.code()==200){
                    try {
                        DataBASE.EVENT_JSON_LIST.clear();
                        DataBASE.EVENT_JSON_LIST.addAll(response.body().getData());
                        Log.d(CONST.SERVER_LOG,DataBASE.EVENT_JSON_LIST.toString());
                        adapter.notifyDataSetChanged();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }

            @Override
            public void onFailure(Call<ListRESPONSE<EventJSON>> call, Throwable t) {
                t.printStackTrace();
            }
        });

    }
}