package com.example.front.ui.signup;

import android.app.Activity;

import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.annotation.StringRes;
import androidx.appcompat.app.AppCompatActivity;

import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.front.R;
import com.example.front.databinding.ActivitySignUpBinding;
import com.example.front.retrofit.User;
import com.example.front.ui.components.AppEditText;

public class SignUpActivity extends AppCompatActivity {

    private SignUpViewModel viewModel;
    private ActivitySignUpBinding binding;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivitySignUpBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        viewModel = new ViewModelProvider(this, new SignUpViewModelFactory())
                .get(SignUpViewModel.class);
        final AppEditText usernameEditText = (AppEditText) binding.username;
        final AppEditText nameEt = (AppEditText) binding.name;
        final AppEditText secondNameEt = (AppEditText) binding.secondName;
        final AppEditText lastNameEt = (AppEditText) binding.lastName;
        final AppEditText addressEt = (AppEditText) binding.address;
        final AppEditText phoneEt = (AppEditText) binding.phone;
        final Button loginButton = binding.login;
        final ProgressBar loadingProgressBar = binding.loading;

        viewModel.getSignUpFormState().observe(this, new Observer<UserFormState>() {
            @Override
            public void onChanged(@Nullable UserFormState signUpFormState) {
                if (signUpFormState == null) {
                    return;
                }
                loginButton.setEnabled(signUpFormState.isDataValid());
                if (signUpFormState.getUsernameError() != null) {
                    usernameEditText.setError(getString(signUpFormState.getUsernameError()));
                }
                if (signUpFormState.getNameError() != null) {
                    nameEt.setError(getString(signUpFormState.getNameError()));
                }
                if (signUpFormState.getLastNameError() != null) {
                    lastNameEt.setError(getString(signUpFormState.getLastNameError()));
                }
                if (signUpFormState.getSecondNameError() != null) {
                    secondNameEt.setError(getString(signUpFormState.getSecondNameError()));
                }
                if (signUpFormState.getAddressError() != null) {
                    addressEt.setError(getString(signUpFormState.getAddressError()));
                }
                if (signUpFormState.getPhoneError() != null) {
                    phoneEt.setError(getString(signUpFormState.getPhoneError()));
                }
            }
        });

        viewModel.getSignupResult().observe(this, new Observer<SignUpResult>() {
            @Override
            public void onChanged(@Nullable SignUpResult loginResult) {
                if (loginResult == null) {
                    return;
                }
                loadingProgressBar.setVisibility(View.GONE);
                if (loginResult.getError() != null) {
                    showLoginFailed(loginResult.getError());
                }
                if (loginResult.getSuccess() != null) {
                    updateUiWithUser(loginResult.getSuccess());
                }
                User value = viewModel.getUserData().getValue();
                if (value != null)
                    setResult(Activity.RESULT_OK, new Intent().putExtra("email", value.getEmail()));
                else setResult(Activity.RESULT_OK);
                finish();
            }
        });

        TextWatcher afterTextChangedListener = new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                // ignore
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                // ignore
            }

            @Override
            public void afterTextChanged(Editable s) {
                viewModel.loginDataChanged(
                        usernameEditText.getText().toString(),
                        nameEt.getText().toString(),
                        secondNameEt.getText().toString(),
                        lastNameEt.getText().toString(),
                        addressEt.getText().toString(),
                        phoneEt.getText().toString()
                );
            }
        };
        usernameEditText.addTextChangedListener(afterTextChangedListener);
        secondNameEt.addTextChangedListener(afterTextChangedListener);
        lastNameEt.addTextChangedListener(afterTextChangedListener);
        addressEt.addTextChangedListener(afterTextChangedListener);
        phoneEt.addTextChangedListener(afterTextChangedListener);
        nameEt.addTextChangedListener(afterTextChangedListener);

        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loadingProgressBar.setVisibility(View.VISIBLE);
                viewModel.signUp(viewModel.getSignUpFormState().getValue());
            }
        });
        phoneEt.setOnEditorActionListener((v, actionId, event) -> {
            if (actionId == EditorInfo.IME_ACTION_DONE) {
                loadingProgressBar.setVisibility(View.VISIBLE);
                viewModel.signUp(viewModel.getSignUpFormState().getValue());
            }
            return false;
        });
    }

    private void updateUiWithUser(User model) {
        String welcome = model.getName() + ", проверяйте почту и возвращайтесь!";
        Toast.makeText(getApplicationContext(), welcome, Toast.LENGTH_LONG).show();
    }

    private void showLoginFailed(@StringRes Integer errorString) {
        Toast.makeText(getApplicationContext(), errorString, Toast.LENGTH_SHORT).show();
    }
}