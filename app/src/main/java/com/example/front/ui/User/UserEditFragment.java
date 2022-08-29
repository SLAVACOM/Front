package com.example.front.ui.User;


import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import com.example.front.R;
import com.example.front.data.database.DataBASE;
import com.example.front.retrofit.RetrofitClient;
import com.example.front.data.User;

import java.util.HashMap;
import java.util.Map;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class UserEditFragment extends Fragment {

    private EditText name, second_name,last_name,email,phone,points,adress,cardnum;
    private Button button;
    private CheckBox checkBox;
    private int curator;
    String stringChange = "";
    Map<String,String> String_map = new HashMap<>();
    Map<String,Integer> integerMap_map = new HashMap<>();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view= inflater.inflate(R.layout.fragment_user_edit, container, false);
        int pos = getArguments().getInt("pos");
        User user = DataBASE.USERS_LIST.get(pos);

        name = view.findViewById(R.id.etv_prof_edit_name);
        second_name = view.findViewById(R.id.etv_prof_edit_second_name);
        last_name = view.findViewById(R.id.etv_prof_edit_last_name);
        email = view.findViewById(R.id.etv_prof_edit_password);
        phone = view.findViewById(R.id.etv_prof_edit_phone);
        points = view.findViewById(R.id.etv_prof_edit_points);
        adress = view.findViewById(R.id.etv_prof_edit_adress);
        cardnum = view.findViewById(R.id.etv_prof_edi_numcard);
        checkBox =view.findViewById(R.id.cb_curator);

        String_map.put("_method","put");
        if (user.isCurator()) {
            checkBox.setChecked(true);
            curator=1;

        } else {
            checkBox.setChecked(false);
            curator = 0;
        }

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
                    Toast.makeText(getContext(),String.valueOf(curator), Toast.LENGTH_SHORT).show();
                } else {
                    curator=0;
                }
                integerMap_map.put("curator",curator);

            }
        });

        name.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (!stringChange.equals(charSequence.toString())){
                    String_map.put("name",name.getText().toString());
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
        last_name.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (!stringChange.equals(charSequence.toString())){
                    String_map.put("last_name",last_name.getText().toString());

                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
        second_name.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (!stringChange.equals(charSequence.toString())){
                    String_map.put("second_name",second_name.getText().toString());
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
        phone.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (!stringChange.equals(charSequence.toString())){
                    String_map.put("phone",phone.getText().toString());
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
        adress.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (!stringChange.equals(charSequence.toString())){
                    String_map.put("address",adress.getText().toString());
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
        email.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (!stringChange.equals(charSequence.toString())){
                    String_map.put("email",email.getText().toString());
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
        points.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (!stringChange.equals(charSequence.toString())){
                    integerMap_map.put("points",Integer.valueOf(points.getText().toString()));
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
        cardnum.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (!stringChange.equals(charSequence.toString())){
                    integerMap_map.put("card_id",Integer.valueOf(cardnum.getText().toString()));
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });



        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                    Call<ResponseBody> setUser = RetrofitClient.getInstance().getApi().editUserList("Bearer " + DataBASE.token,Integer.valueOf(user.getId()),String_map,integerMap_map);
                    setUser.enqueue(new Callback<ResponseBody>() {
                    @Override
                    public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                        if (response.code()==200)
                            Toast.makeText(getContext(), "Успешно", Toast.LENGTH_SHORT).show();
                            FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                            fragmentManager.popBackStack();
                    }

                    @Override
                    public void onFailure(Call<ResponseBody> call, Throwable t) {
                        t.printStackTrace();
                    }
                });
            }
        });

        return view;

    }
}