package com.example.front.ui.news;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.front.R;
import com.example.front.data.DataData;
import com.example.front.retrofit.NewsJSON;
import com.example.front.retrofit.RetrofitClient;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class NewsEditFragment extends Fragment {

    private EditText title,content;
    private Button button,deleteBt;
    private NewsJSON newsJSON;



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view =  inflater.inflate(R.layout.fragment_news_edit, container, false);
        title = view.findViewById(R.id.etv_news_edit_title);
        content = view.findViewById(R.id.etv_news_edit_content);
        button = view.findViewById(R.id.buttonEdit);
        newsJSON= DataData.NEWS_JSON_LIST.get(getArguments().getInt("pos"));
        deleteBt = view.findViewById(R.id.buttonDel);
        deleteBt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Call<ResponseBody> delete = RetrofitClient.getInstance().getApi().deleteNews("Bearer " +DataData.token,Integer.valueOf(newsJSON.getData().getId()));
                delete.enqueue(new Callback<ResponseBody>() {
                    @Override
                    public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                        Toast.makeText(getContext(),String.valueOf(response.message()), Toast.LENGTH_SHORT).show();
                        if (response.code()==200) {
                            FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                            fragmentManager.popBackStack();
                        }


                    }

                    @Override
                    public void onFailure(Call<ResponseBody> call, Throwable t) {
                        t.printStackTrace();
                    }
                });
            }
        });


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