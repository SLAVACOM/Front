package com.example.front.ui.news;

import android.os.Bundle;
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

import com.example.front.R;
import com.example.front.adapter.NewsAdapter;
import com.example.front.data.ServerListResponse;
import com.example.front.data.database.DataBASE;
import com.example.front.data.News;
import com.example.front.retrofit.RetrofitClient;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class NewsFragment extends Fragment {


    NewsAdapter adapter;
    RecyclerView recyclerView;
    SwipeRefreshLayout swipeRefreshLayout;
    FloatingActionButton actionButton;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_news,container,false);
        adapter = new NewsAdapter(getContext());
        actionButton = view.findViewById(R.id.fab_addNews);
        actionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.nav_host_fragment_content_main, new NewsEditFragment()).addToBackStack(null);
                fragmentTransaction.commit();
            }
        });
        adapter.setClickListener(new NewsAdapter.ClickListener() {
            @Override
            public void onItemClick(int position, View view) {
                this.onItemLongClick(position, view);
            }

            @Override
            public void onItemLongClick(int position, View view) {
                NewsEditFragment editFragment = new NewsEditFragment();
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
        recyclerView = view.findViewById(R.id.recycler_appeal_byrequest);
        recyclerView.setAdapter(adapter);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(layoutManager);
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                getNews();
                swipeRefreshLayout.setRefreshing(false);
            }
        });
        return view;
    }

    @Override
    public void onStart() {
        super.onStart();
        getNews();


    }
    private void getNews(){
        Call<ServerListResponse<News>> getNewsList = RetrofitClient.getInstance().getApi().getNewsList();
        getNewsList.enqueue(new Callback<ServerListResponse<News>>() {
            @Override
            public void onResponse(Call<ServerListResponse<News>> call, Response<ServerListResponse<News>> response) {
                if(response.code()==200){
                    DataBASE.NEWS_JSON_LIST.clear();
                    adapter.notifyDataSetChanged();
                    DataBASE.NEWS_JSON_LIST.addAll(response.body().getData());
                    adapter.notifyDataSetChanged();
                } else {
                    Toast.makeText(getActivity(),"Сервер не доступен", Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<ServerListResponse<News>> call, Throwable t) {
                t.printStackTrace();

            }
        });
    }

}