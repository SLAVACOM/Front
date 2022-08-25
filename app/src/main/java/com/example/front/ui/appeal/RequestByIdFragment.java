package com.example.front.ui.appeal;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewTreeViewModelKt;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.front.R;
import com.example.front.adapter.AdapterRequest;
import com.example.front.adapter.AdapterRequestByID;


public class RequestByIdFragment extends Fragment {

    AdapterRequestByID adapter;
    RecyclerView recyclerView;
    SwipeRefreshLayout swipeRefreshLayout;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_request_by_id, container, false);
        adapter = new AdapterRequestByID();
        recyclerView = view.findViewById(R.id.swipeRefresh);
        swipeRefreshLayout = view.findViewById(R.id.swipeRefresh);




        return view;
    }
}