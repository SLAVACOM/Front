package com.example.front.ui.User;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import com.example.front.R;
import com.example.front.data.UserEdit;
import com.example.front.data.database.DataBASE;
import com.example.front.retrofit.RetrofitClient;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class UserProfileEditFragment extends Fragment {

    private EditText name, second_name,last_name,password,password_confirmation,address;
    private Button button;
    private CheckBox checkBox;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view= inflater.inflate(R.layout.fragment_userlist_edit, container, false);

        name = view.findViewById(R.id.etv_prof_edit_name);
        second_name = view.findViewById(R.id.etv_prof_edit_second_name);
        last_name = view.findViewById(R.id.etv_prof_edit_last_name);
        password = view.findViewById(R.id.etv_prof_edit_password);
        password_confirmation = view.findViewById(R.id.etv_prof_edit_password_confirmation);
        checkBox = view.findViewById(R.id.cb_curator);
        address= view.findViewById(R.id.etv_prof_edit_adress);
        name.setText(DataBASE.user.getName());
        second_name.setText(DataBASE.user.getSecond_name());
        last_name.setText(DataBASE.user.getLast_name());

        button = view.findViewById(R.id.bt_editProf);
        if (password.getText().toString().length()<=6){
            button.setEnabled(false);
        }
        String stringChange="";

        password_confirmation.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (password_confirmation.getText().toString().length() < 6) {
                    button.setEnabled(false);
                }else {
                    button.setEnabled(true);
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {
            }
        });

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (password.getText().toString().equals(password_confirmation.getText().toString())) {

                    UserEdit userEdit = new UserEdit();
                    userEdit.setName(name.getText().toString());
                    userEdit.setLast_name(last_name.getText().toString());
                    userEdit.setSecond_name(second_name.getText().toString());
                    userEdit.setPassword(password.getText().toString());
                    userEdit.getPassword_confirmation(password_confirmation.getText().toString());
                    Map<String,String> user= new HashMap<>();
                    user.put("password",password.getText().toString());
                    user.put("password_confirmation",password_confirmation.getText().toString());
                    user.put("last_name",last_name.getText().toString());
                    user.put("second_name",second_name.getText().toString());
                    user.put("name",name.getText().toString());
                    user.put("_method","PUT");
                    user.put("address",address.getText().toString());

                    Call<ResponseBody> editProfile = RetrofitClient.getInstance().getApi().editProfile("Bearer " + DataBASE.token, user);
                    editProfile.enqueue(new Callback<ResponseBody>() {
                        @Override

                        public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                            if (response.code()==200){
                                Toast.makeText(getContext(), "Успешно", Toast.LENGTH_SHORT).show();
                                FragmentManager manager = getActivity().getSupportFragmentManager();
                                manager.popBackStack();
                            }
                            else {
                                Toast.makeText(getContext(), response.errorBody().toString(), Toast.LENGTH_SHORT).show();
                            }
                        }
                        @Override
                        public void onFailure(Call<ResponseBody> call, Throwable t) {
                            t.printStackTrace();
                        }
                    });
                }
                else {
                    Toast.makeText(getContext(), "Пароли не совпадают", Toast.LENGTH_SHORT).show();
                }
            }
        });

        return view;
    }
}