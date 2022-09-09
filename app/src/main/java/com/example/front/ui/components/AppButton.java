package com.example.front.ui.components;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Color;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.widget.Button;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.res.ResourcesCompat;

import com.example.front.R;


public class AppButton extends androidx.appcompat.widget.AppCompatButton {
    public AppButton(@NonNull Context context) {
        this(context, null);
    }

    public AppButton(@NonNull Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, com.google.android.material.R.attr.buttonStyle);
    }

    public AppButton(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        setTypeface(ResourcesCompat.getFont(context,R.font.vela_sans_bold));
        setBackgroundResource(R.drawable.blue_btn_small);
        setTextColor(Color.WHITE);
        setPadding(0,0,0,0);

    }
}
