package com.example.front.ui.components;

import android.content.Context;
import android.util.AttributeSet;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.res.ResourcesCompat;

import com.example.front.R;


public class AppEditText extends androidx.appcompat.widget.AppCompatEditText {
    public AppEditText(@NonNull Context context) {
        this(context, null);
    }

    public AppEditText(@NonNull Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, com.google.android.material.R.attr.editTextStyle);
    }

    public AppEditText(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        setTypeface(ResourcesCompat.getFont(context,R.font.vela_sans_bold));
    }
}
