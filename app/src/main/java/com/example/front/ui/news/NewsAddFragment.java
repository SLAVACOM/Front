package com.example.front.ui.news;

import android.content.Context;
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
import com.example.front.retrofit.call.ValidateCallback;
import com.example.front.retrofit.responses.ValidationResponse;

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
                try {
                    Call<ResponseBody> addEvent = RetrofitClient.getInstance().getApi().addNews("Bearer " + DataBASE.token, title.getText().toString(),content.getText().toString());
                    addEvent.enqueue(new ValidateCallback<ResponseBody>() {
                        @Override
                        public void on422(Call<ResponseBody> call, Response<ResponseBody> response, ValidationResponse errors) {
                            String error = errors.getError("title");
                            if (error != null) title.setError(error);
                            error = errors.getError("description");
                            if (error != null) content.setError(error);
                        }

                        @Override
                        public void onSuccess(Call<ResponseBody> call, Response<ResponseBody> response) {
                            FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                            fragmentManager.popBackStack();
                        }

                        @Override
                        public Context getContext() {
                            return getActivity();
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