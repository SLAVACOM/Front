package com.example.front.ui.User;

import android.os.Bundle;
import android.os.Handler;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
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
import com.example.front.data.ServerListResponse;
import com.example.front.data.User;
import com.example.front.data.database.DataBASE;
import com.example.front.retrofit.Retrofit;
import com.example.front.ui.components.AppEditText;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class UsersListFragment extends Fragment {

    AdapterUserList adapterUserList ;
    RecyclerView recyclerView;
    AppEditText search;
    SwipeRefreshLayout swipeRefreshLayout;
    Handler handler;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_users_list, container, false);
        recyclerView = view.findViewById(R.id.recycler_userList);
        search = view.findViewById(R.id.user_search);
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
        TextWatcher w = new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                if (handler != null) handler.removeCallbacksAndMessages(null);
                handler = new Handler();
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        getUsers();
                    }
                }, 2000);
            }
        };
        search.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                final int DRAWABLE_RIGHT = 2;

                if(event.getAction() == MotionEvent.ACTION_UP) {
                    if(event.getRawX() >= (search.getRight() - search.getEt().getCompoundDrawables()[DRAWABLE_RIGHT].getBounds().width())) {
                        if (handler!=null) handler.removeCallbacksAndMessages(null);
                       getUsers();
                        return true;
                    }
                }
                return false;
            }
        });
        search.addTextChangedListener(w);
        return view;
    }

    @Override
    public void onStart() {
        super.onStart();
        getUsers();
    }

    private void getUsers(){
        Call<ServerListResponse<User>> getUsers = Retrofit.getInstance().getApi().getUsers("Bearer "+ DataBASE.token, search.getText().toString());
        getUsers.enqueue(new Callback<ServerListResponse<User>>() {
            @Override
            public void onResponse(Call<ServerListResponse<User>> call, Response<ServerListResponse<User>> response) {
                System.out.println(response.code());
                DataBASE.USERS_LIST.clear();
                DataBASE.USERS_LIST.addAll(response.body().getData());
                Log.d(CONST.SERVER_LOG,DataBASE.USERS_LIST.toString());
                adapterUserList.notifyDataSetChanged();
            }

            @Override
            public void onFailure(Call<ServerListResponse<User>> call, Throwable t) {
                t.printStackTrace();
            }
        });
    }
}