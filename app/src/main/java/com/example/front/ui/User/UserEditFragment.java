package com.example.front.ui.User;


import static com.example.front.data.database.DataBASE.user;

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

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.example.front.R;
import com.example.front.data.database.DataBASE;
import com.example.front.retrofit.Retrofit;
import com.example.front.ui.signup.UserFormResult;
import com.example.front.ui.signup.UserFormViewModel;
import com.example.front.ui.signup.SignUpViewModelFactory;
import com.example.front.ui.signup.UserFormState;

import okhttp3.ResponseBody;
import retrofit2.Call;


public class UserEditFragment extends Fragment implements View.OnClickListener {

    private EditText name, second_name, last_name, email, phone, points, adress, cardnum;
    private Button saveBtn;
    private CheckBox checkBox;
    private UserFormViewModel viewModel;

    public void init(View view) {
        viewModel = new ViewModelProvider(this, new SignUpViewModelFactory())
                .get(UserFormViewModel.class);
        viewModel.setContext(getActivity());
        name = view.findViewById(R.id.etv_prof_edit_name);
        second_name = view.findViewById(R.id.etv_prof_edit_second_name);
        last_name = view.findViewById(R.id.etv_prof_edit_last_name);
        email = view.findViewById(R.id.etv_prof_edit_password);
        phone = view.findViewById(R.id.etv_prof_edit_phone);
        points = view.findViewById(R.id.etv_prof_edit_points);
        adress = view.findViewById(R.id.etv_prof_edit_adress);
        cardnum = view.findViewById(R.id.etv_prof_edi_numcard);
        checkBox = view.findViewById(R.id.cb_curator);
        saveBtn = view.findViewById(R.id.bt_editProf);
        viewModel.getSignUpFormState().observe(getActivity(), new Observer<UserFormState>() {
            @Override
            public void onChanged(@Nullable UserFormState userForm) {
                if (userForm == null) {
                    return;
                }
                saveBtn.setEnabled(userForm.isDataValid());
                userForm.setError(userForm.getUsernameError(), email);
                userForm.setError(userForm.getNameError(), name);
                userForm.setError(userForm.getLastNameError(), last_name);
                userForm.setError(userForm.getSecondNameError(), second_name);
                userForm.setError(userForm.getAddressError(), adress);
                userForm.setError(userForm.getPhoneError(), phone);
                userForm.setError(userForm.getCardIdError(), cardnum);
                userForm.setError(userForm.getPointsError(), points);
                userForm.setError(userForm.getCuratorError(), checkBox);
            }
        });
        viewModel.getUserEditResult().observe(getActivity(), new Observer<UserFormResult>() {
            @Override
            public void onChanged(UserFormResult userFormResult) {
                if (userFormResult == null) {
                    return;
                }
                if (userFormResult.getSuccess() != null) {
                    Toast.makeText(getActivity(), "Данные обновлены", Toast.LENGTH_LONG).show();
                }
            }
        });
        TextWatcher watcher = new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {}

            @Override
            public void afterTextChanged(Editable s) {
                viewModel.loginDataChanged(
                        email.getText().toString(),
                        name.getText().toString(),
                        second_name.getText().toString(),
                        last_name.getText().toString(),
                        adress.getText().toString(),
                        phone.getText().toString(),
                        cardnum.getText().toString(),
                        points.getText().toString(),
                        checkBox.isChecked()
                );
            }
        };
        email.addTextChangedListener(watcher);
        name.addTextChangedListener(watcher);
        last_name.addTextChangedListener(watcher);
        second_name.addTextChangedListener(watcher);
        points.addTextChangedListener(watcher);
        cardnum.addTextChangedListener(watcher);
        phone.addTextChangedListener(watcher);
        adress.addTextChangedListener(watcher);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_user_edit, container, false);
        init(view);
        user = DataBASE.USERS_LIST.get(getArguments().getInt("pos"));
        checkBox.setChecked(user.isCurator());
        name.setText(user.getName());
        second_name.setText(user.getSecond_name());
        last_name.setText(user.getLast_name());
        email.setText(user.getEmail());
        phone.setText(String.valueOf(user.getPhone()));
        points.setText(String.valueOf(user.getPoints()));
        adress.setText(user.getAddress());
        String card_id = user.getCard_id();
        cardnum.setText(card_id == null ? "" :card_id);
        checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                viewModel.getUserData().getValue().setCurator(b);
            }
        });
        saveBtn.setOnClickListener(this);
        return view;
    }

    @Override
    public void onClick(View view) {
        Call<ResponseBody> setUser = Retrofit.getInstance().getApi().editUser("Bearer " + DataBASE.token, Integer.valueOf(user.getId()), viewModel.getUserData().getValue());
        viewModel.sendRequest(setUser);
    }
}