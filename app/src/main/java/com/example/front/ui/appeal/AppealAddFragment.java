package com.example.front.ui.appeal;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import com.example.front.R;
import com.example.front.data.Appeal;
import com.example.front.data.DataData;


public class AppealAddFragment extends Fragment {
    public static final int THEM_LENGTH = 10;
    public static final int CONTENT_LENGTH = 20;

    EditText appeal_theme, appeal_content;
    Button bt_send_appeal;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View  view= inflater.inflate(R.layout.fragment_appeal_add, container, false);

        appeal_content = view.findViewById(R.id.etv_add_appeal_content);
        appeal_theme = view.findViewById(R.id.etv_add_appeal_theme);
        bt_send_appeal = view.findViewById(R.id.bt_add_appeal);

        bt_send_appeal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (appeal_content.getText().toString().length()>=CONTENT_LENGTH && appeal_theme.getText().toString().length()>=THEM_LENGTH){

                DataData.APPEALS_LIST.add(new Appeal(appeal_theme.getText().toString(),appeal_content.getText().toString(),"SLAVACOM","18:00"));
                FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                fragmentManager.popBackStack();
                }else{
                    if (appeal_theme.getText().toString().length()<THEM_LENGTH)
                        Toast.makeText(getActivity(), "Заполните тему", Toast.LENGTH_SHORT).show();

                    else if (appeal_content.getText().toString().length()<CONTENT_LENGTH){
                        Toast.makeText(getActivity(), "Заполните обращение", Toast.LENGTH_SHORT).show();

                    }
            }}
        });
        return view;
    }
}