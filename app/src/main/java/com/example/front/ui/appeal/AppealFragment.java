package com.example.front.ui.appeal;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.front.R;
import com.example.front.adapter.Adapter_appeal;
import com.google.android.material.floatingactionbutton.FloatingActionButton;


public class AppealFragment extends Fragment  {


    FloatingActionButton floatingActionButton;
    Adapter_appeal adapter;
    RecyclerView recyclerView;
    FloatingActionButton button;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_appeal,container,false);

        floatingActionButton =view.findViewById(R.id.flBt_appeal);

        adapter = new Adapter_appeal();
        recyclerView = view.findViewById(R.id.recycler_appeal);
        recyclerView.setAdapter(adapter);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(layoutManager);
        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.nav_host_fragment_content_main,new AppealAddFragment()).addToBackStack(null);
                fragmentTransaction.commit();
            }
        });


        return view;
    }
}