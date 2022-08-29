package com.example.front.ui.User;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;

import androidx.fragment.app.Fragment;

import com.example.front.R;
import com.example.front.data.database.DataBASE;


public class UserListEditFragment extends Fragment {

    private EditText name, second_name,last_name,password,password_confirmation;
    private Button button;
    private CheckBox checkBox;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view= inflater.inflate(R.layout.fragment_user_edit, container, false);

        name = view.findViewById(R.id.etv_prof_edit_name);
        second_name = view.findViewById(R.id.etv_prof_edit_second_name);
        last_name = view.findViewById(R.id.etv_prof_edit_last_name);
        password = view.findViewById(R.id.etv_prof_edit_password);
        password_confirmation = view.findViewById(R.id.etv_prof_edit_password_confirmation);
        checkBox = view.findViewById(R.id.cb_curator);

        name.setText(DataBASE.user.getName());
        second_name.setText(DataBASE.user.getSecond_name());
        last_name.setText(DataBASE.user.getLast_name());

        button = view.findViewById(R.id.bt_editProf);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
            }
        });

        return view;
    }
}