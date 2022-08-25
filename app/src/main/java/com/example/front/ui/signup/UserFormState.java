package com.example.front.ui.signup;

import androidx.annotation.Nullable;

/**
 * Data validation state of the login form.
 */
class UserFormState {

    @Nullable
    private Integer passwordError;
    @Nullable
    private Integer usernameError;
    @Nullable
    private Integer secondNameError;
    @Nullable
    private Integer lastNameError;
    @Nullable
    private Integer nameError;
    @Nullable
    private Integer phoneError;
    @Nullable
    private Integer addressError;

    private boolean isDataValid;

    UserFormState(@Nullable String field, @Nullable Integer error) {
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
            case "email" :
            case "username" :
                this.usernameError = error;
                break;
        }
        this.isDataValid = false;
    }

    UserFormState(boolean isDataValid) {
        this.isDataValid = isDataValid;
    }

    boolean isDataValid() {
        return isDataValid;
    }

    @Nullable
    public Integer getUsernameError() {
        return usernameError;
    }

    @Nullable
    public Integer getPasswordError() {
        return passwordError;
    }

    public Integer getSecondNameError() {
        return secondNameError;
    }

    public Integer getLastNameError() {
        return lastNameError;
    }

    public Integer getNameError() {
        return nameError;
    }

    public Integer getPhoneError() {
        return phoneError;
    }

    public Integer getAddressError() {
        return addressError;
    }
}