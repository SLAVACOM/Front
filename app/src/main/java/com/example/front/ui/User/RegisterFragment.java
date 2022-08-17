package com.example.front.ui.User;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;

import com.example.front.R;


public class RegisterFragment extends Fragment {

    private EditText name,lastname,ferstname,phone,email;
    private CheckBox checkBox;
    private Button button;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view =  inflater.inflate(R.layout.fragment_register, container, false);
        name = view.findViewById(R.id.etv_register_name);
        lastname = view.findViewById(R.id.etv_register_lastname);
        ferstname = view.findViewById(R.id.etv_register_ferstname);
        phone = view.findViewById(R.id.etv_register_phone);
        email = view.findViewById(R.id.etv_register_email);
        checkBox = view.findViewById(R.id.register_checkBox);
        button = view.findViewById(R.id.bt_login);

        checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                button.setEnabled(b);
            }
        });
        return view;
    }
}