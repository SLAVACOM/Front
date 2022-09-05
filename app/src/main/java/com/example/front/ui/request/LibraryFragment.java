package com.example.front.ui.request;

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
import com.example.front.adapter.RequestsLibAdapter;
import com.example.front.adapter.RequestsTypeAdapter;
import com.example.front.data.ListRESPONSE;
import com.example.front.data.RequestTypeJSON;
import com.example.front.data.ResponsLibrary;
import com.example.front.data.database.DataBASE;
import com.example.front.retrofit.RetrofitClient;
import com.example.front.ui.appeal.AppealEditFragment;
import com.example.front.ui.appeal.RequesEditFragment;
import com.example.front.ui.appeal.RequestByIdFragment;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LibraryFragment extends Fragment {

    FloatingActionButton flbt_add;
    RequestsLibAdapter adapter;
    RecyclerView recyclerView;
    SwipeRefreshLayout swipeRefreshLayout;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view= inflater.inflate(R.layout.fragment_library, container, false);
        getREQUEST();
        flbt_add =view.findViewById(R.id.flBt_my_appeal);
        recyclerView = view.findViewById(R.id.recycler_appeal_byrequest);
        adapter = new RequestsLibAdapter(getActivity());
        adapter.setOnItemClickListener(new RequestsLibAdapter.ClickListener() {
              @Override
            public void onItemLongClick(int position, View v) {
                RequesEditFragment editFragment = new RequesEditFragment();
                Bundle bundle = new Bundle();
                bundle.putInt("pos",position);
                editFragment.setArguments(bundle);
                FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.nav_host_fragment_content_main,editFragment).addToBackStack(null);
                fragmentTransaction.commit();

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
                FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.nav_host_fragment_content_main, new AppealEditFragment(AppealEditFragment.MODE_ADDLIB)).addToBackStack(null);
                fragmentTransaction.commit();
            }
        });


        return view;
    }

    public void getREQUEST(){
        Call<ListRESPONSE<ResponsLibrary>> getREQUEST = RetrofitClient.getInstance().getApi().getLibRespons("Bearer "+DataBASE.token);
        getREQUEST.enqueue(new Callback<ListRESPONSE<ResponsLibrary>>() {
            @Override
            public void onResponse(Call<ListRESPONSE<ResponsLibrary>> call, Response<ListRESPONSE<ResponsLibrary>> response) {
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
            public void onFailure(Call<ListRESPONSE<ResponsLibrary>> call, Throwable t) {
                t.printStackTrace();
            }
        });
    }

}