package com.example.front.ui.appeal;

import static com.example.front.data.DataData.token;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.example.front.CONST.CONST;
import com.example.front.R;
import com.example.front.data.Appeal;
import com.example.front.data.DataData;
import com.example.front.retrofit.RetrofitClient;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class AppealEditFragment extends Fragment {


    EditText appeal_content;
    Button bt_send_appeal;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View  view= inflater.inflate(R.layout.fragment_appeal_add, container, false);

        appeal_content = view.findViewById(R.id.etv_add_appeal_content);
        bt_send_appeal = view.findViewById(R.id.bt_add_appeal);


        bt_send_appeal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (appeal_content.getText().toString().length()>= CONST.CONTENT_LENGTH){
                    Call<ResponseBody> addRequest = RetrofitClient.getInstance().getApi().addRequestType("Bearer " + DataData.token,appeal_content.getText().toString());
                    addRequest.enqueue(new Callback<ResponseBody>() {
                        @Override
                        public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                            if(response.code()==200){
                                Toast.makeText(getContext(), "Добавлено", Toast.LENGTH_SHORT).show();
                                FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                                fragmentManager.popBackStack();
                            }
                        }

                        @Override
                        public void onFailure(Call<ResponseBody> call, Throwable t) {

                        }
                    });

                }else{
                    Toast.makeText(getActivity(), "Количество символов в поле имя должно быть не менее 10", Toast.LENGTH_SHORT).show();
                }
            }
        });
        return view;
    }
}