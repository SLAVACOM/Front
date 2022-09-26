package com.example.front.ui.event;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.example.front.CONST.CONST;
import com.example.front.R;
import com.example.front.adapter.AdapterEvents;
import com.example.front.data.EventJSON;
import com.example.front.data.ServerListResponse;
import com.example.front.data.database.DataBASE;
import com.example.front.retrofit.Retrofit;
import com.example.front.ui.components.AppButton;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class EventFragment extends AddEventFragment {

    private AdapterEvents adapter;
    private RecyclerView recyclerView;
    private FloatingActionButton addBtn;
    AlertDialog dialog;

    private SwipeRefreshLayout swipeRefreshLayout;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_event, container, false);
        adapter = new AdapterEvents();
        addBtn = view.findViewById(R.id.fab_event_add);
        swipeRefreshLayout = view.findViewById(R.id.swipeRefresh);
        recyclerView = view.findViewById(R.id.recycler_event);
        adapter.setOnItemClickListener(new AdapterEvents.onEventClickListener() {
            public void onItemClick(int position, View view) {
                if (!DataBASE.user.isCurator() && !DataBASE.user.isAdmin()) return;
                AddEventFragment fragment = new AddEventFragment();
                Bundle bundle = new Bundle();
                bundle.putInt("pos", position);
                fragment.setArguments(bundle);
                FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.nav_host_fragment_content_main, fragment).addToBackStack(null);
                fragmentTransaction.commit();
            }

            @Override
            public void onAddPeople(int position) {
                addPeople(position);
            }
        });
        recyclerView.setAdapter(adapter);
        recyclerView.setBackgroundColor(Color.WHITE);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(layoutManager);

        addBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.nav_host_fragment_content_main, new AddEventFragment()).addToBackStack(null);
                fragmentTransaction.commit();
            }
        });
        addBtn.setVisibility(DataBASE.user.isAdmin() ? View.VISIBLE : View.GONE);
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                loadEvents();
            }
        });
        return view;
    }

    private void addPeople(int position) {
        LayoutInflater inflater = ((AppCompatActivity) getContext()).getLayoutInflater();
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        LinearLayout dialoglayout = (LinearLayout) inflater.inflate(R.layout.dialog_comment, null);
        AppButton nfc = new AppButton(getContext());
        nfc.setText("Сканировать метку");
        dialoglayout.addView(nfc);
        nfc.setOnClickListener((v) -> startNfc(position, () -> dialog.cancel()));
        AppButton qr = new AppButton(getContext());
        qr.setOnClickListener((v) -> startQr(position, () -> dialog.cancel()));
        qr.setText("Сканировать QR-код");
        dialoglayout.addView(qr);
        ViewGroup.MarginLayoutParams lp = (ViewGroup.MarginLayoutParams) qr.getLayoutParams();
        lp.topMargin = 20;
        builder.setView(dialoglayout);
        builder.setTitle("Добавление участника");
        builder.setNegativeButton("Отмена", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.cancel();
            }
        });
        dialog = builder.create();
        dialog.show();
    }

    @Override
    public void onStart() {
        loadEvents();
        super.onStart();
    }


    public void loadEvents() {
        Call<ServerListResponse<EventJSON>> getEventList = Retrofit.getInstance().getApi().getEventList();
        getEventList.enqueue(new Callback<ServerListResponse<EventJSON>>() {
            @Override
            public void onResponse(Call<ServerListResponse<EventJSON>> call, Response<ServerListResponse<EventJSON>> response) {
                if (response.isSuccessful()) {
                    DataBASE.EVENT_JSON_LIST.clear();
                    DataBASE.EVENT_JSON_LIST.addAll(response.body().getData());
                    Log.d(CONST.SERVER_LOG, DataBASE.EVENT_JSON_LIST.toString());
                    adapter.notifyDataSetChanged();
                }
                swipeRefreshLayout.setRefreshing(false);
            }

            @Override
            public void onFailure(Call<ServerListResponse<EventJSON>> call, Throwable t) {
                t.printStackTrace();
                swipeRefreshLayout.setRefreshing(false);
            }
        });

    }
}