package com.example.front.ui.event;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.front.R;
import com.example.front.adapter.AdapterEvents;
import com.example.front.adapter.Adapter_bus;


public class EventFragment extends Fragment {

    AdapterEvents adapter;
    RecyclerView recyclerView;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_event,container,false);
        adapter = new AdapterEvents();
        recyclerView = view.findViewById(R.id.recycler_event);
        recyclerView.setAdapter(adapter);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(layoutManager);
        return view;
    }

    @Override
    public void onStop() {
        super.onStop();
    }
}