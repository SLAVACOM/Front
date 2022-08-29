package com.example.front.ui.news;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.front.CONST.CONST;
import com.example.front.R;
import com.example.front.data.database.DataBASE;
import com.example.front.retrofit.RetrofitClient;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class NewsAddFragment extends Fragment {

    private TextView title, content;
    private Button addBt;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view= inflater.inflate(R.layout.fragment_news_add, container, false);
        title = view.findViewById(R.id.etv_news_add_title);
        content = view.findViewById(R.id.etv_news_add_content);
        addBt = view.findViewById(R.id.buttonAdd);


        addBt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (CONST.NEWS_THEM_LENGTH>=title.getText().length()){
                    Toast.makeText(getContext(), "Количество символов в поле заголовок должно быть не менее 10", Toast.LENGTH_SHORT).show();
                    if (CONST.NEWS_DESCRIPTION_LENGTH>=content.getText().length()){
                        Toast.makeText(getContext(), "Слишком короткое описание", Toast.LENGTH_SHORT).show();
                    }
                    return;
                }
                try {
                    Call<ResponseBody> addEvent = RetrofitClient.getInstance().getApi().addNews("Bearer " + DataBASE.token, title.getText().toString(),content.getText().toString());
                    addEvent.enqueue(new Callback<ResponseBody>() {
                        @Override
                        public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                            Toast.makeText(getContext(), response.code(), Toast.LENGTH_SHORT).show();
                            if (response.code()==200){
                                Toast.makeText(getContext(), response.message(), Toast.LENGTH_SHORT).show();
                                FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                                fragmentManager.popBackStack();
                            }
                        }

                        @Override
                        public void onFailure(Call<ResponseBody> call, Throwable t) {
                            t.printStackTrace();
                        }
                    });

                } catch (Exception e){
                    e.printStackTrace();
                }
            }
        });


        return view;
    }
}