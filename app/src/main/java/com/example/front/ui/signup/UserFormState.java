package com.example.front.ui.signup;

import android.content.Context;
import android.widget.TextView;

import androidx.annotation.Nullable;

import com.example.front.ui.components.AppEditText;

/**
 * Data validation state of the login form.
 */
public class UserFormState {

    @Nullable
    private Object passwordError;
    @Nullable
    private Object password_confirmationError;
    @Nullable
    private Object usernameError;
    @Nullable
    private Object secondNameError;
    @Nullable
    private Object lastNameError;
    @Nullable
    private Object nameError;
    @Nullable
    private Object phoneError;
    @Nullable
    private Object addressError;
    @Nullable
    private Object formError;
    @Nullable
    private Object cardIdError;
    @Nullable
    private Object curatorError;
    @Nullable
    private Object pointsError;

    private boolean isDataValid;

    UserFormState(@Nullable String field, @Nullable Object error) {
        this.addError(field, error);
        this.isDataValid = false;
    }


    UserFormState(boolean isDataValid) {
        this.isDataValid = isDataValid;
    }

    public boolean isDataValid() {
        return isDataValid;
    }

    public void addError(String field, Object error) {
        if (field != null) switch (field) {
            case "name" :
                this.nameError = error;
                break;
            case "second_name" :
                this.secondNameError = error;
                break;
            case "last_name" :
                this.lastNameError = error;
                break;
            case "address" :
                this.addressError = error;
                break;
            case "phone" :
                this.phoneError = error;
                break;
            case "card_id" :
                this.cardIdError = error;
                break;
            case "points" :
                this.pointsError = error;
                break;
            case "password" :
                this.passwordError = error;
                break;
            case "password_confirmation" :
                this.password_confirmationError = error;
                break;
            case "curator" :
                this.curatorError = error;
                break;
            case "email" :
            case "username" :
                this.usernameError = error;
                break;
            default:
                this.formError = error;
                break;
        }
    }

    @Nullable
    public Object getCardIdError() {
        return cardIdError;
    }

    @Nullable
    public Object getCuratorError() {
        return curatorError;
    }

    @Nullable
    public Object getPointsError() {
        return pointsError;
    }

    @Nullable
    public Object getUsernameError() {
        return usernameError;
    }

    @Nullable
    public Object getPasswordError() {
        return passwordError;
    }

    public Object getSecondNameError() {
        return secondNameError;
    }

    public Object getLastNameError() {
        return lastNameError;
    }

    public Object getNameError() {
        return nameError;
    }

    public Object getPhoneError() {
        return phoneError;
    }

    public Object getAddressError() {
        return addressError;
    }

    public String setError(Object error, AppEditText view) {
        String err;
        Context baseContext = view.getContext();
        if (error == null) {
            view.setError(null);
            return null;
        }
        if (error instanceof Integer) {
            err = baseContext.getString((Integer) error);
        }
        else {
            err = error.toString();
        }
        view.setError(err);
        return err;
    }
    public String setError(Object error, TextView view) {
        String err;
        Context baseContext = view.getContext();
        if (error == null) {
            view.setError(null);
            return null;
        }
        if (error instanceof Integer) {
            err = baseContext.getString((Integer) error);
        }
        else {
            err = error.toString();
        }
        view.setError(err);
        return err;
    }

    @Nullable
    public Object getFormError() {
        return formError;
    }

    @Nullable
    public Object getPassword_confirmationError() {
        return password_confirmationError;
    }
}