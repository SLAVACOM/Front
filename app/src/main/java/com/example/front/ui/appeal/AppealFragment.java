package com.example.front.ui.appeal;

import android.os.Bundle;

import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.front.MainActivity;
import com.example.front.R;
import com.example.front.adapter.Adapter_appeal;
import com.example.front.adapter.Adapter_bus;
import com.google.android.material.floatingactionbutton.FloatingActionButton;


public class AppealFragment extends Fragment  {

    Adapter_appeal adapter;
    RecyclerView recyclerView;
    FloatingActionButton button;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_appeal,container,false);
        adapter = new Adapter_appeal();
        recyclerView = view.findViewById(R.id.recycler_appeal);
        recyclerView.setAdapter(adapter);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(layoutManager);
        button = view.findViewById(R.id.floatingActionButton2);


        return view;
    }
}