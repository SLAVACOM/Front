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
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.example.front.CONST.CONST;
import com.example.front.R;
import com.example.front.adapter.AdapterEvents;
import com.example.front.data.Event;
import com.example.front.data.ServerListResponse;
import com.example.front.data.database.DataBASE;
import com.example.front.helpers.LastItemListener;
import com.example.front.helpers.SwipeHelper;
import com.example.front.retrofit.Retrofit;
import com.example.front.ui.components.AppButton;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class EventFragment extends AddEventFragment {

    private RecyclerView recyclerView;
    private FloatingActionButton addBtn;
    AlertDialog dialog;

    private SwipeRefreshLayout swipeRefreshLayout;
    private int page = 0;
    private int last_page = -1;


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
        adapter.setLastItemListener(new LastItemListener() {
            @Override
            public void onLastItemOpened(int position) {
                loadEvents(page + 1);
            }
        });
        if (DataBASE.user.isAdmin()) {
            ItemTouchHelper ih = new ItemTouchHelper(new SwipeHelper(getContext(), recyclerView) {
                @Override
                public void instantiateUnderlayButton(RecyclerView.ViewHolder viewHolder, List<UnderlayButton> underlayButtons) {
                    underlayButtons.add(new SwipeHelper.UnderlayButton(
                            "??????????????",
                            0,
                            Color.parseColor("#FF8585"),
                            new SwipeHelper.UnderlayButtonClickListener() {
                                @Override
                                public void onClick(int pos) {
                                    EventFragment.this.deleteItem(DataBASE.EVENT_JSON_LIST.get(pos));
                                    adapter.notifyDataSetChanged();
                                }
                            }
                    ));
                }
            });
            ih.attachToRecyclerView(recyclerView);
        }
        return view;
    }

    private void addPeople(int position) {
        LayoutInflater inflater = ((AppCompatActivity) getContext()).getLayoutInflater();
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        LinearLayout dialoglayout = (LinearLayout) inflater.inflate(R.layout.dialog_comment, null);
        AppButton nfc = new AppButton(getContext());
        nfc.setText("?????????????????????? ??????????");
        dialoglayout.addView(nfc);
        nfc.setOnClickListener((v) -> startNfc(position, () -> dialog.cancel()));
        AppButton qr = new AppButton(getContext());
        qr.setOnClickListener((v) -> startQr(position, () -> dialog.cancel()));
        qr.setText("?????????????????????? QR-??????");
        dialoglayout.addView(qr);
        ViewGroup.MarginLayoutParams lp = (ViewGroup.MarginLayoutParams) qr.getLayoutParams();
        lp.topMargin = 20;
        builder.setView(dialoglayout);
        builder.setTitle("???????????????????? ??????????????????");
        builder.setNegativeButton("????????????", new DialogInterface.OnClickListener() {
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
        loadEvents(1);
    }
    public void loadEvents(int page) {
        if (last_page >0 && last_page< page) return;
        Call<ServerListResponse<Event>> getEventList = Retrofit.getInstance().getApi().getEventList(page);
        if(swipeRefreshLayout != null) swipeRefreshLayout.setRefreshing(true);
        getEventList.enqueue(new Callback<ServerListResponse<Event>>() {
            @Override
            public void onResponse(Call<ServerListResponse<Event>> call, Response<ServerListResponse<Event>> response) {
                if (response.isSuccessful() && EventFragment.this.page == page - 1) {
                    EventFragment.this.page = page;
                    EventFragment.this.last_page = response.body().getLast_page();
                    if (page == 1) DataBASE.EVENT_JSON_LIST.clear();
                    DataBASE.EVENT_JSON_LIST.addAll(response.body().getData());
                    Log.d(CONST.SERVER_LOG, DataBASE.EVENT_JSON_LIST.toString());
                    adapter.notifyDataSetChanged();
                }
                swipeRefreshLayout.setRefreshing(false);
            }

            @Override
            public void onFailure(Call<ServerListResponse<Event>> call, Throwable t) {
                t.printStackTrace();
                swipeRefreshLayout.setRefreshing(false);
            }
        });

    }
}