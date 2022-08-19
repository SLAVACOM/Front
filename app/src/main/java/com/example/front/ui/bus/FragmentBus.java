package com.example.front.ui.bus;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.example.front.CONST.CONST;
import com.example.front.R;
import com.example.front.adapter.Adapter_bus;
import com.example.front.data.DataData;
import com.example.front.retrofit.BusJSON;
import com.example.front.retrofit.Data;
import com.example.front.retrofit.ListRESPONSE;
import com.example.front.retrofit.RetrofitClient;
import com.example.front.retrofit.maper.BusMapper;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.squareup.picasso.Downloader;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;
import java.util.zip.DataFormatException;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class FragmentBus extends Fragment  {
    private Adapter_bus adapterBus;
    private RecyclerView recyclerView;
    private FloatingActionButton addbutton;
    private SwipeRefreshLayout swipeRefreshLayout;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_bus_admin,container,false);
        swipeRefreshLayout = view.findViewById(R.id.swipeRefresh);
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                getBus();
                swipeRefreshLayout.setRefreshing(false);

            }
        });
        addbutton = view.findViewById(R.id.floatingActionButton_addBus);
        recyclerView = view.findViewById(R.id.recycler_bus);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(layoutManager);
        addbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.nav_host_fragment_content_main,new FragmentBusAdd()).addToBackStack(null);
                fragmentTransaction.commit();
            }
        });

        return view;
    }


    @Override
    public void onStart() {
        super.onStart();
        if (DataData.user.isCurator()){
            addbutton.setVisibility(View.INVISIBLE);
        }

            adapterBus = new Adapter_bus();
            adapterBus.setOnItemClickListener(new Adapter_bus.ClickListener() {
            @Override
            public void onItemClick(int position, View v) {


            }

            @Override
            public void onItemLongClick(int position, View v) {
                BusEditFragment editFragment = new BusEditFragment();
                Bundle bundle = new Bundle();
                bundle.putInt("pos",position);
                editFragment.setArguments(bundle);
                Toast.makeText(getActivity(), "sdad"+DataData.BUS_JSON_LIST.get(position).getTime(), Toast.LENGTH_SHORT).show();
                FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.nav_host_fragment_content_main,editFragment).addToBackStack(null);
                fragmentTransaction.commit();

            }
        });
        recyclerView.setAdapter(adapterBus);
        getBus();

    }


    private void getBus(){
        Call<ListRESPONSE<BusJSON>> getBus = RetrofitClient.getInstance().getApi().getBusList();
        getBus.enqueue(new Callback<ListRESPONSE<BusJSON>>() {
            @Override
            public void onResponse(Call<ListRESPONSE<BusJSON>> call, Response<ListRESPONSE<BusJSON>> response) {
                if(response.code()==200){
                    try {
                        DataData.BUS_JSON_LIST.clear();
                        DataData.BUS_JSON_LIST.addAll(response.body().getData());
                        Log.d(CONST.SERVER_LOG,""+DataData.BUS_JSON_LIST);
                        adapterBus.notifyDataSetChanged();

                    } catch (Exception e){
                        e.printStackTrace();
                    }

                }
            }

            @Override
            public void onFailure(Call<ListRESPONSE<BusJSON>> call, Throwable t) {
                t.printStackTrace();
            }
        });
    }

}