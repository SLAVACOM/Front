package com.example.front.ui.request;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.example.front.CONST.CONST;
import com.example.front.R;
import com.example.front.adapter.RequestsTypeAdapter;
import com.example.front.data.ListRESPONSE;
import com.example.front.data.RequestTypeJSON;
import com.example.front.data.database.DataBASE;
import com.example.front.retrofit.RetrofitClient;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class RequestTypesFragment extends Fragment {


    FloatingActionButton addTypeBtn;
    RequestsTypeAdapter adapter;
    RecyclerView recyclerView;
    SwipeRefreshLayout swipeRefreshLayout;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view= inflater.inflate(R.layout.fragment_my_appeal, container, false);
        getItems();
        addTypeBtn =view.findViewById(R.id.flBt_my_appeal);
        recyclerView = view.findViewById(R.id.recycler_appeal_byrequest);
        adapter = new RequestsTypeAdapter(getActivity());
        adapter.setOnItemClickListener(new RequestsTypeAdapter.ClickListener() {


            @Override
            public void onItemLongClick(int position, View v) {
                RequestTypeEditFragment editFragment = new RequestTypeEditFragment();
                Bundle bundle = new Bundle();
                bundle.putInt("pos",position);
                editFragment.setArguments(bundle);
                FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(RequestTypesFragment.this.getId(),editFragment).addToBackStack(null);
                fragmentTransaction.commit();

            }
        });
        recyclerView.setAdapter(adapter);
        swipeRefreshLayout = view.findViewById(R.id.swipeRefresh);
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                getItems();
                swipeRefreshLayout.setRefreshing(false);
            }
        });
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(layoutManager);

        addTypeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                RequestTypeEditFragment editFragment = new RequestTypeEditFragment();
                Bundle bundle = new Bundle();
                bundle.putInt("pos",-1);
                editFragment.setArguments(bundle);
                fragmentTransaction.replace(R.id.nav_host_fragment_content_main, editFragment).addToBackStack(null);
                fragmentTransaction.commit();
            }
        });


        return view;
    }

    public void getItems(){
        Call<ListRESPONSE<RequestTypeJSON>> call = RetrofitClient.getInstance().getApi().getRequestTypes();
        call.enqueue(new Callback<ListRESPONSE<RequestTypeJSON>>() {
            @Override
            public void onResponse(Call<ListRESPONSE<RequestTypeJSON>> call, Response<ListRESPONSE<RequestTypeJSON>> response) {
                if(!response.isSuccessful()){
                    Toast.makeText(getActivity(), "Ошибка получения списка типов запроса", Toast.LENGTH_SHORT).show();
                    return;
                }
                DataBASE.REQUEST_TYPEJSON_LIST.clear();
                DataBASE.REQUEST_TYPEJSON_LIST.addAll(response.body().getData());
                Log.d(CONST.SERVER_LOG, DataBASE.REQUEST_TYPEJSON_LIST.toString());
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onFailure(Call<ListRESPONSE<RequestTypeJSON>> call, Throwable t) {
                t.printStackTrace();
            }
        });
    }
}