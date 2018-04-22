package com.example.android.baking.ui.steps;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;

import com.example.android.baking.R;
import com.example.android.baking.models.Step;

public class StepDetailsActivity extends FragmentActivity {

    // Tag for log messages
    private static final String LOG_TAG = StepDetailsActivity.class.getName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // If we are in two-pane layout mode, this activity is no longer necessary
        if (getResources().getBoolean(R.bool.has_two_panes)) {
            finish();
            return;
        }

        // Collect our intent and get our parcel with the selected Step object
        Intent intent = getIntent();
        Step selectedStep = intent.getParcelableExtra("Step");

        // Place an StepDetailsFragment as our content pane
        StepDetailsFragment stepDetailsFragment = new StepDetailsFragment();
        getSupportFragmentManager().beginTransaction().add(android.R.id.content, stepDetailsFragment).commit();

        // Display the detail data about the step in the corresponding views
        stepDetailsFragment.displayStepVideo(selectedStep);
        stepDetailsFragment.displayStepFullDescription(selectedStep);
    }
}