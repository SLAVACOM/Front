package com.example.front.helpers;

import android.view.View;

public class CurrentToTopHelper extends CurrentViewAnimateHelper{

    public CurrentToTopHelper(float excludedPosition, float leftOffset) {
        this.excludedPosition = excludedPosition;
        this.leftOffset = leftOffset;
    }

    private float excludedPosition;
    private float leftOffset;

    @Override
    public void onUpdate(View view, int visiblePosition, int adapterPosition, double progress) {
        if (adapterPosition != excludedPosition) {
            view.setTranslationX((float) (-progress * leftOffset));
        }
    }
}
