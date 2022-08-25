package com.example.front.ui.User;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

import com.example.front.R;
import com.example.front.data.DataData;
import com.example.front.retrofit.RequestTypeJSON;
import com.example.front.retrofit.RetrofitClient;
import com.example.front.retrofit.User;

import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class UserEditFragment extends Fragment {

    private EditText name, second_name,last_name,email,phone,points,adress,cardnum;
    private Button button;
    private CheckBox checkBox;
    private int curator;

    private int pos;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        int pos= getArguments().getInt("pos");
        View view= inflater.inflate(R.layout.fragment_user_edit, container, false);

        name = view.findViewById(R.id.etv_prof_edit_name);
        second_name = view.findViewById(R.id.etv_prof_edit_second_name);
        last_name = view.findViewById(R.id.etv_prof_edit_last_name);
        email = view.findViewById(R.id.etv_prof_edit_password);
        phone = view.findViewById(R.id.etv_prof_edit_phone);
        points = view.findViewById(R.id.etv_prof_edit_points);
        adress = view.findViewById(R.id.etv_prof_edit_adress);
        cardnum = view.findViewById(R.id.etv_prof_edi_numcard);
        checkBox =view.findViewById(R.id.cb_curator);

        User user = DataData.USERS_LIST.get(pos);
        name.setText(user.getName());
        second_name.setText(user.getSecond_name());

        last_name.setText(user.getLast_name());
        email.setText(user.getEmail());
        phone.setText(String.valueOf(user.getPhone()));
        points.setText(String.valueOf(user.getPoints()));
        adress.setText(user.getAddress());
        cardnum.setText(String.valueOf(user.getCard_id()));
        button = view.findViewById(R.id.bt_editProf);
        checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (checkBox.isChecked()){
                    curator  = 1;
                } else curator=0;
            }
        });

        if (user.isCurator()) {
            checkBox.setChecked(true);
        } else checkBox.setChecked(false);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Call<RequestBody> setUser = RetrofitClient.getInstance().getApi().editUserList("Bearer " +DataData.token,Integer.valueOf(user.getId()),"put",email.getText().toString(),name.getText().toString(),second_name.getText().toString(),phone.getText().toString(),last_name.getText().toString(),user.getBlocked(),curator,Integer.parseInt(points.getText().toString()),Integer.parseInt(cardnum.getText().toString()));
                setUser.enqueue(new Callback<RequestBody>() {
                    @Override
                    public void onResponse(Call<RequestBody> call, Response<RequestBody> response) {
                        if (response.code()==200)
                            Toast.makeText(getContext(), "Успешно", Toast.LENGTH_SHORT).show();
                        else Toast.makeText(getContext(), response.message(), Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onFailure(Call<RequestBody> call, Throwable t) {

                    }
                });
            }
        });

        return view;

    }
}