package com.example.front.ui.User;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import com.example.front.R;

public class UserFragment extends Fragment {
    TextView name, ferstname, balance;
    ImageView imageView, qrCode;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_user,container,false);
        name = view.findViewById(R.id.TV_name_prof);
        balance = view.findViewById(R.id.TV_balance);
        ferstname = view.findViewById(R.id.TV_ferstname_prof);
        imageView = view.findViewById(R.id.imageViewProfile);
        qrCode = view.findViewById(R.id.imageQrCode);
        name.setText("Святослав");
        ferstname.setText("Иванов");
        balance.setText("777");
        return view;
    }
}