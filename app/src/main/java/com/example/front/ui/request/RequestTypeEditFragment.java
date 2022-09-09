package com.example.front.ui.request;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import com.example.front.MainActivity;
import com.example.front.R;
import com.example.front.data.database.DataBASE;
import com.example.front.data.RequestTypeJSON;
import com.example.front.retrofit.RetrofitClient;
import com.example.front.retrofit.call.ValidateCallback;
import com.example.front.retrofit.responses.ObjectResponse;
import com.example.front.retrofit.responses.ValidationResponse;
import com.example.front.ui.components.AppEditText;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class RequestTypeEditFragment extends Fragment implements View.OnClickListener {

    TextView textView;
    AppEditText et;
    Button button;
    private RequestTypeJSON typeItem;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_request_edit, container, false);
        int pos = getArguments().getInt("pos");
        typeItem = pos == -1 ? null : DataBASE.REQUEST_TYPEJSON_LIST.get(pos);
        textView = view.findViewById(R.id.type_tv);
        et = (AppEditText) view.findViewById(R.id.type_et);
        if (typeItem != null) {
            textView.setText("ID: " + typeItem.getId() + "\nТип: " + typeItem.getName());
            et.setVisibility(View.INVISIBLE);
            textView.setVisibility(View.VISIBLE);
        } else {
            textView.setVisibility(View.GONE);
            textView.setVisibility(View.INVISIBLE);
            et.setVisibility(View.VISIBLE);
        }
        button = view.findViewById(R.id.bt_fragment_edit);
        button.setOnClickListener(this);
        if (typeItem != null) {
            button.setText(R.string.remove_title);
        } else {
            button.setText(R.string.save_title);
        }

        return view;
    }
    public void onClick(View view) {
        if (typeItem != null) {
            delete();
        } else {
            create();
        }
    }

    private void create() {
        Call<ObjectResponse<RequestTypeJSON>> call = RetrofitClient.getInstance().getApi().addRequestType("Bearer " + MainActivity.userToken(getActivity()), et.getText().toString());
        call.enqueue(new ValidateCallback<ObjectResponse<RequestTypeJSON>>() {
            @Override
            public void on422(Call<ObjectResponse<RequestTypeJSON>> call, Response<ObjectResponse<RequestTypeJSON>> response, ValidationResponse errors) {
                String error = errors.getError("name");
                if (error != null) et.setError(error);
            }

            @Override
            public void onSuccess(Call<ObjectResponse<RequestTypeJSON>> call, Response<ObjectResponse<RequestTypeJSON>> response) {
                FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                fragmentManager.popBackStack();
            }

            @Override
            public Context getContext() {
                return getActivity();
            }
        });
    }

    private void delete() {
        Call<ResponseBody> deleteResp = RetrofitClient.getInstance().getApi().deleteRequest("Bearer " + MainActivity.userToken(getActivity()), typeItem.getId());
        deleteResp.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if (response.code() == 200) {
                    Toast.makeText(getContext(), "Удалено", Toast.LENGTH_SHORT).show();
                    FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                    fragmentManager.popBackStack();
                } else {
                    Toast.makeText(getContext(), response.message(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                t.printStackTrace();
            }
        });
    }


}