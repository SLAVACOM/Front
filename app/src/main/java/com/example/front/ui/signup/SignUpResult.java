package com.example.front.ui.signup;

import androidx.annotation.Nullable;

import com.example.front.retrofit.User;

/**
 * Authentication result : success (user details) or error message.
 */
class SignUpResult {
    @Nullable
    private User success;
    @Nullable
    private Integer error;

    SignUpResult(@Nullable Integer error) {
        this.error = error;
    }

    SignUpResult(@Nullable User success) {
        this.success = success;
    }

    @Nullable
    User getSuccess() {
        return success;
    }

    @Nullable
    Integer getError() {
        return error;
    }
}