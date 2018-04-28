package com.example.android.baking.services.events;

import com.example.android.baking.models.Step;

public class StepSelectionEvent {

    private final Step step;

    public StepSelectionEvent(Step step) {
        this.step = step;
    }

    public Step getStep() {
        return step;
    }
}