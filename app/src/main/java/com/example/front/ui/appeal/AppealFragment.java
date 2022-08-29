package com.example.front.ui.appeal;

import android.os.Bundle;

import androidx.annotation.NonNull;
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
import com.example.front.LoginActivity;
import com.example.front.R;
import com.example.front.adapter.AppealsAdapter;
import com.example.front.adapter.LoadMoreAdapter;
import com.example.front.data.Appeal;
import com.example.front.data.User;
import com.example.front.data.database.DataBASE;
import com.example.front.data.ListRESPONSE;
import com.example.front.retrofit.RetrofitClient;
import com.example.front.ui.news.NewsAddFragment;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.gson.Gson;
import com.google.gson.JsonObject;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class AppealFragment extends Fragment  {


    public static final int MODE_MY = 1;
    AppealsAdapter adapter;
    RecyclerView recyclerView;
    SwipeRefreshLayout swipeRefreshLayout;
    FloatingActionButton actionButton;
    int mode = 0;
    int page = 1;

    public AppealFragment() {
    }
    public AppealFragment(int mode) {
        this.mode = mode;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_appeals,container,false);
        recyclerView = view.findViewById(R.id.recycler_appeal_byrequest);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(layoutManager);
        adapter = new AppealsAdapter(getContext(), recyclerView);
        recyclerView = view.findViewById(R.id.recycler_appeal_byrequest);
        recyclerView.setAdapter(adapter);
        actionButton = view.findViewById(R.id.fab_addNews);
        actionButton.setVisibility(View.INVISIBLE);
        if (mode == MODE_MY) {
            actionButton.setVisibility(View.VISIBLE);
            actionButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                    FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                    fragmentTransaction.replace(R.id.nav_host_fragment_content_main,new NewsAddFragment()).addToBackStack(null);
                    fragmentTransaction.commit();
                }
            });
        } else if ((DataBASE.user.getRole() & User.VIILAGE_USER_ROLE) > 1) {
            actionButton.setVisibility(View.INVISIBLE);
        }
        adapter.setClickListener(new AppealsAdapter.ClickListener() {
            @Override
            public void onItemClick(int position, View view) {

            }

            @Override
            public void onItemLongClick(int position, View view) {
                AppealEditFragment editFragment = new AppealEditFragment();
                Bundle bundle = new Bundle();
                bundle.putInt("pos",position);
                editFragment.setArguments(bundle);
                FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.nav_host_fragment_content_main,editFragment).addToBackStack(null);
                fragmentTransaction.commit();

            }
        });
        swipeRefreshLayout = view.findViewById(R.id.swipeRefresh);
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                swipeRefreshLayout.setRefreshing(true);
                getAppeals();
                swipeRefreshLayout.setRefreshing(false);
            }
        });
        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        getAppeals();
    }

    private void getAppeals() {
        getAppeals(1);
    }
    private void getAppeals(int page) {
        Call<ListRESPONSE<Appeal>> getNewsList = RetrofitClient.getInstance().getApi().getAppeals(
                "Bearer " + LoginActivity.userToken(getActivity().getBaseContext()), mode == MODE_MY ? "me" : null, page + "");
        getNewsList.enqueue(new Callback<ListRESPONSE<Appeal>>() {
            @Override
            public void onResponse(Call<ListRESPONSE<Appeal>> call, Response<ListRESPONSE<Appeal>> response) {
                if (response.code() == 200) {
                    if (page == 1) DataBASE.APPEALS_LIST.clear();
                    List<Appeal> data = response.body().getData();
                    DataBASE.APPEALS_LIST.addAll(data);
                    adapter.notifyDataSetChanged();
                    Log.d(CONST.SERVER_LOG, data.toString());
                    AppealFragment.this.page = page;
                }
                swipeRefreshLayout.setRefreshing(false);
            }

            @Override
            public void onFailure(Call<ListRESPONSE<Appeal>> call, Throwable t) {
                t.printStackTrace();
                swipeRefreshLayout.setRefreshing(false);
            }
        });
    }
}