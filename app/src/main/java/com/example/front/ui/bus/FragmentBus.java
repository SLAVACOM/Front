package com.example.front.ui.bus;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.front.MainActivity;
import com.example.front.R;
import com.example.front.adapter.Adapter_bus;
import com.example.front.data.Bus;
import com.example.front.data.Data;


public class FragmentBus extends Fragment {
    private Adapter_bus adapterBus;
    public RecyclerView recyclerView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        Bus bus = new Bus("21:21","22:21","Канаш","Чебоксары");
        Data.BUS_LIST.add(bus);
        Data.BUS_LIST.add(bus);
        Data.BUS_LIST.add(bus);
        Data.BUS_LIST.add(bus);
        Data.BUS_LIST.add(bus);
        Data.BUS_LIST.add(bus);
        View view = inflater.inflate(R.layout.fragment_bus,container,false);
        adapterBus = new Adapter_bus();
        recyclerView = view.findViewById(R.id.recycler_bus);
        recyclerView.setAdapter(adapterBus);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(layoutManager);
        return view;
    }
}