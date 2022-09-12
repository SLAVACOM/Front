package com.example.front.ui.bus;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.example.front.CONST.CONST;
import com.example.front.R;
import com.example.front.adapter.Adapter_bus;
import com.example.front.data.BusJSON;
import com.example.front.data.ListRESPONSE;
import com.example.front.data.database.DataBASE;
import com.example.front.retrofit.RetrofitClient;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class FragmentBus extends Fragment {
    private Adapter_bus adapterBus;
    private RecyclerView recyclerView;
    private FloatingActionButton addbutton;
    private SwipeRefreshLayout swipeRefreshLayout;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_bus_admin, container, false);
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
                fragmentTransaction.replace(R.id.nav_host_fragment_content_main, new FragmentBusAdd()).addToBackStack(null);
                fragmentTransaction.commit();
            }
        });

        return view;
    }


    @Override
    public void onStart() {
        super.onStart();
        if ((DataBASE.user.getRole() & CONST.CURATOR_ROLE) == 0 && DataBASE.user.getRole() < CONST.ADMIN_ROLE) {
            addbutton.setVisibility(View.INVISIBLE);
            addbutton.setVisibility(View.GONE);
        }

        adapterBus = new Adapter_bus();
        adapterBus.setOnItemClickListener(new Adapter_bus.ClickListener() {
            @Override
            public void onItemClick(int position, View v) {
                BusEditFragment editFragment = new BusEditFragment();
                Bundle bundle = new Bundle();
                bundle.putInt("pos", position);
                editFragment.setArguments(bundle);
                FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.nav_host_fragment_content_main, editFragment).addToBackStack(null);
                fragmentTransaction.commit();
            }

            @Override
            public void onItemLongClick(int position, View v) {
            }
        });
        recyclerView.setAdapter(adapterBus);
        getBus();

    }


    private void getBus() {
        Call<ListRESPONSE<BusJSON>> getBus = RetrofitClient.getInstance().getApi().getBusList();
        getBus.enqueue(new Callback<ListRESPONSE<BusJSON>>() {
            @Override
            public void onResponse(Call<ListRESPONSE<BusJSON>> call, Response<ListRESPONSE<BusJSON>> response) {
                if (response.code() == 200) {
                    try {
                        DataBASE.BUS_JSON_LIST.clear();
                        DataBASE.BUS_JSON_LIST.addAll(response.body().getData());
                        Log.d(CONST.SERVER_LOG, "" + DataBASE.BUS_JSON_LIST);
                        adapterBus.notifyDataSetChanged();

                    } catch (Exception e) {
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