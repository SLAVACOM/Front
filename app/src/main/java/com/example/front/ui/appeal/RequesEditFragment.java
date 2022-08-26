package com.example.front.ui.appeal;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import com.example.front.R;
import com.example.front.data.DataData;
import com.example.front.retrofit.RequestTypeJSON;
import com.example.front.retrofit.RetrofitClient;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class RequesEditFragment extends Fragment {

    TextView textView;
    Button button;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view= inflater.inflate(R.layout.fragment_request_edit, container, false);
        RequestTypeJSON requestTypeJSON = DataData.REQUEST_TYPEJSON_LIST.get(getArguments().getInt("pos"));
        textView=view.findViewById(R.id.etv_fragment_requestedit);
        textView.setText("ID: "+ requestTypeJSON.getId()+"\nОбращение: "+requestTypeJSON.getName());
        button = view.findViewById(R.id.bt_fragment_edit);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Call<ResponseBody> deleteResp = RetrofitClient.getInstance().getApi().deleteRequest("Bearer " + DataData.token,requestTypeJSON.getId());
                deleteResp.enqueue(new Callback<ResponseBody>() {
                    @Override
                    public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                        if(response.code()==200){
                            Toast.makeText(getContext(), "Удалено", Toast.LENGTH_SHORT).show();
                            FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                            fragmentManager.popBackStack();
                        }
                        else {
                            Toast.makeText(getContext(), response.message(), Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onFailure(Call<ResponseBody> call, Throwable t) {

                    }
                });
            }
        });

        return view;
    }


}