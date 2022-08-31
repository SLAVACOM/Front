package com.example.front.ui.User;

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
import com.example.front.adapter.AdapterUserList;
import com.example.front.data.ListRESPONSE;
import com.example.front.data.User;
import com.example.front.data.database.DataBASE;
import com.example.front.retrofit.RetrofitClient;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class UsersListFragment extends Fragment {

    AdapterUserList adapterUserList ;
    RecyclerView recyclerView;
    SwipeRefreshLayout swipeRefreshLayout;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_users_list, container, false);
        recyclerView = view.findViewById(R.id.recycler_userList);
        swipeRefreshLayout = view.findViewById(R.id.swipeRefresh);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(layoutManager);
        adapterUserList = new AdapterUserList();
        recyclerView.setAdapter(adapterUserList);
        adapterUserList.setOnItemClickListener(new AdapterUserList.ClickListener() {
            @Override
            public void onItemClick(int position, View v) {

            }

            @Override
            public void onItemLongClick(int position, View v) {
                UserEditFragment fragment = new UserEditFragment();
                Bundle bundle = new Bundle();
                bundle.putInt("pos",position);
                fragment.setArguments(bundle);
                FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.nav_host_fragment_content_main,fragment).addToBackStack(null);
                fragmentTransaction.commit();
            }
        });
        getUsers();

        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                getUsers();
                swipeRefreshLayout.setRefreshing(false);
            }
        });
        return view;
    }

    private void getUsers(){
        Call<ListRESPONSE<User>> getUsers = RetrofitClient.getInstance().getApi().getUsers("Bearer "+ DataBASE.token);
        getUsers.enqueue(new Callback<ListRESPONSE<User>>() {
            @Override
            public void onResponse(Call<ListRESPONSE<User>> call, Response<ListRESPONSE<User>> response) {
                if(response.code()==200){
                    try {
                        DataBASE.USERS_LIST.clear();
                        DataBASE.USERS_LIST.addAll(response.body().getData());
                        Log.d(CONST.SERVER_LOG,DataBASE.USERS_LIST.toString());
                    } catch (Exception e){
                        e.printStackTrace();
                    }

                }
            }

            @Override
            public void onFailure(Call<ListRESPONSE<User>> call, Throwable t) {
                t.printStackTrace();
            }
        });
    }
}