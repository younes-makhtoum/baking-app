package com.example.android.baking.services;

import com.example.android.baking.models.Step;

public class SelectionEvent {

    private final Step step;

    public SelectionEvent(Step step) {
        this.step = step;
    }

    public Step getStep() {
        return step;
    }
}