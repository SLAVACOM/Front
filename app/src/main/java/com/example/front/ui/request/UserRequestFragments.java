package com.example.front.ui.request;

import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.example.front.CONST.CONST;
import com.example.front.R;
import com.example.front.adapter.UserRequestsAdapter;
import com.example.front.data.ServerListResponse;
import com.example.front.data.UserRequest;
import com.example.front.data.database.DataBASE;
import com.example.front.helpers.SwipeHelper;
import com.example.front.retrofit.Retrofit;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class UserRequestFragments extends Fragment {

    FloatingActionButton addBtn;
    UserRequestsAdapter adapter;
    RecyclerView recyclerView;
    SwipeRefreshLayout swipeRefreshLayout;
    private int role = CONST.LIBRARIAN_ROLE;
    private String bearer;;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        bearer  = "Bearer " + DataBASE.token;
        View view = inflater.inflate(R.layout.fragment_library, container, false);
        if (getArguments() != null) role = getArguments().getInt("role");
        loadItems();
        addBtn = view.findViewById(R.id.add_request_btn);
        addBtn.setVisibility(DataBASE.user.isUser() ? View.VISIBLE : View.GONE);
        recyclerView = view.findViewById(R.id.recycler_appeal_byrequest);
        adapter = new UserRequestsAdapter(getActivity(), getItemsList());
        adapter.setOnItemClickListener(new UserRequestsAdapter.ClickListener() {
            @Override
            public void onItemLongClick(int position, View v) {
                Bundle bundle = new Bundle();
                bundle.putInt("pos", position);
            }
        });
        SwipeHelper swh = new SwipeHelper(getActivity(), recyclerView) {
            @Override
            public void instantiateUnderlayButton(RecyclerView.ViewHolder viewHolder, List<UnderlayButton> underlayButtons) {
                underlayButtons.add(new SwipeHelper.UnderlayButton(
                        "Удалить",
                        0,
                        Color.parseColor("#FF8585"),
                        new SwipeHelper.UnderlayButtonClickListener() {
                            @Override
                            public void onClick(int pos) {
                                deleteItem(getItemsList().get(pos));
                            }
                        }
                ));
            }
        };
        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(swh);
        itemTouchHelper.attachToRecyclerView(recyclerView);
        recyclerView.setAdapter(adapter);
        recyclerView.setBackgroundColor(Color.WHITE);
        swipeRefreshLayout = view.findViewById(R.id.swipeRefresh);
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                loadItems();
                swipeRefreshLayout.setRefreshing(false);
            }
        });
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(layoutManager);

        addBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getActivity(), "AdminREQFragment no action", Toast.LENGTH_SHORT).show();
            }
        });


        return view;
    }

    private void deleteItem(UserRequest userRequest) {
        Call<ResponseBody> call = Retrofit.getApi().deleteUserRequest(bearer, userRequest.getId() + "");
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if (response.isSuccessful()) {
                    getItemsList().remove(userRequest);
                    adapter.notifyDataSetChanged();
                } else onError();
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                t.printStackTrace();
                onError();
            }
        });
    }

    public void loadItems() {
        Call<ServerListResponse<UserRequest>> call = Retrofit.getApi().getUserRequests(bearer, role);
        System.out.println(call.request().url().toString()+bearer);
        call.enqueue(new Callback<ServerListResponse<UserRequest>>() {
            @Override
            public void onResponse(Call<ServerListResponse<UserRequest>> call, Response<ServerListResponse<UserRequest>> response) {
                if (response.isSuccessful()) {
                    getItemsList().clear();
                    getItemsList().addAll(response.body().getData());
                    adapter.notifyDataSetChanged();
                    Log.d(CONST.SERVER_LOG, getItemsList().toString());
                } else onError();
            }

            @Override
            public void onFailure(Call<ServerListResponse<UserRequest>> call, Throwable t) {
                t.printStackTrace();
                onError();
            }
        });
    }

    private List<UserRequest> getItemsList() {
        return role == CONST.LIBRARIAN_ROLE ? DataBASE.REQUEST_LIB_LIST : DataBASE.REQUEST_ADMIN_LIST;
    }

    public void onError() {
        Toast.makeText(getContext(), "Ошибка сервера", Toast.LENGTH_SHORT).show();
    }

}