package com.example.front.ui.signup;

import android.util.Patterns;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.front.R;
import com.example.front.retrofit.RetrofitClient;
import com.example.front.retrofit.User;
import com.google.gson.JsonObject;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SignUpViewModel extends ViewModel {

    private MutableLiveData<UserFormState> signUpFormState = new MutableLiveData<>();
    private MutableLiveData<SignUpResult> signupResult = new MutableLiveData<>();
    private MutableLiveData<User> userData = new MutableLiveData<>();

    SignUpViewModel() {
    }

    LiveData<UserFormState> getSignUpFormState() {
        return signUpFormState;
    }

    LiveData<SignUpResult> getSignupResult() {
        return signupResult;
    }
    LiveData<User> getUserData() {
        return userData;
    }

    public void signUp(UserFormState form) {
        Call<ResponseBody> call = RetrofitClient.getInstance().getApi().registration(userData.getValue());
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if (response.isSuccessful()) {
                    signupResult.setValue(new SignUpResult(userData.getValue()));
                } else {
                    signupResult.setValue(new SignUpResult(R.string.signup_failed));
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                signupResult.setValue(new SignUpResult(R.string.signup_failed));
            }
        });
    }

    public void loginDataChanged(String username, String name, String second, String last, String address, String phone) {
        if (!isUserNameValid(username)) {
            signUpFormState.setValue(new UserFormState("email", R.string.invalid_username));
        } else if (!isNameValid(name)) {
            signUpFormState.setValue(new UserFormState("name", R.string.invalid_name));
        } else if (!isNameValid(second)) {
            signUpFormState.setValue(new UserFormState("second_name", R.string.invalid_second_name));
        } else if (!isLastValid(last)) {
            signUpFormState.setValue(new UserFormState("last_name", R.string.invalid_last_name));
        } else if (!isLastValid(address)) {
            signUpFormState.setValue(new UserFormState("address", R.string.invalid_address));
        } else if (!isPhoneValid(phone)) {
            signUpFormState.setValue(new UserFormState("phone", R.string.invalid_phone));
        } else {
            User value = new User();
            value.setEmail(username);
            value.setAddress(address);
            value.setPhone(phone);
            value.setName(name);
            value.setLast_name(last);
            value.setSecond_name(second);
            userData.setValue(value);
            signUpFormState.setValue(new UserFormState(true));
        }
    }

    // A placeholder username validation check
    private boolean isUserNameValid(String username) {
        if (username == null) {
            return false;
        }
        return Patterns.EMAIL_ADDRESS.matcher(username).matches();
    }

    // A placeholder password validation check
    private boolean isPasswordValid(String password) {
        return password != null && password.trim().length() > 6;
    }// A placeholder password validation check
    private boolean isNameValid(String name) {
        return name != null && name.trim().length() > 2;
    }
    private boolean isLastValid(String name) {
        return name == null || name.isEmpty() ||  name.trim().length() > 2;
    }
    private boolean isPhoneValid(String phone) {
        return phone != null && phone.trim().length() == 10;
    }
}