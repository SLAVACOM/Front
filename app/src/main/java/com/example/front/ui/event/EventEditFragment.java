package com.example.front.ui.event;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import com.example.front.R;
import com.example.front.data.DataData;
import com.example.front.retrofit.EventJSON;

public class EventEditFragment extends Fragment {
    private EditText title,content;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_event_edit, container, false);
        EventJSON busJSON = DataData.EVENT_JSON_LIST.get(getArguments().getInt("pos"));
        title = view.findViewById(R.id.etv_edit_event_title);
        content = view.findViewById(R.id.etv_edit_event_content);
        title.setText(busJSON.getTitle());
        content.setText(busJSON.getPlace());


        return view;
    }
}