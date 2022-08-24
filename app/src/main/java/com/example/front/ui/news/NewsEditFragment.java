package com.example.front.ui.news;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.example.front.R;
import com.example.front.data.DataData;
import com.example.front.retrofit.NewsJSON;


public class NewsEditFragment extends Fragment {

    private EditText title,content;
    private Button button;
    private NewsJSON newsJSON;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view =  inflater.inflate(R.layout.fragment_news_edit, container, false);
        title = view.findViewById(R.id.etv_news_edit_title);
        content = view.findViewById(R.id.etv_news_edit_content);
        button = view.findViewById(R.id.buttonEdit);

        newsJSON= DataData.NEWS_JSON_LIST.get(getArguments().getInt("pos"));
        title.setText(newsJSON.getData().getTitle().replaceAll("<P>",""));
        content.setText(newsJSON.getData().getDescription().replaceAll("<p>","").replaceAll("</p>","").replaceAll("&nbsp;",""));

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });



        return view;
    }
}