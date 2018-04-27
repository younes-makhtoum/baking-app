package com.example.android.baking.ui.steps;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.example.android.baking.R;
import com.example.android.baking.models.Recipe;
import com.example.android.baking.models.Step;
import com.example.android.baking.services.events.RecipeSelectionEvent;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

public class StepDetailsActivity extends AppCompatActivity {

    // Tag for log messages
    private static final String LOG_TAG = StepDetailsActivity.class.getName();

    private StepDetailsFragment stepDetailsFragment;

    private Recipe selectedRecipe;
    private Step selectedStep;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // If we are in two-pane layout mode, this activity is no longer necessary
        if (getResources().getBoolean(R.bool.has_two_panes)) {
            finish();
            return;
        }

        EventBus.getDefault().register(this);

        // Collect our intent and get our parcel with the selected Step object
        Intent intent = getIntent();
        selectedStep = intent.getParcelableExtra("Step");

        // Set title of activity according to the selected recipe.
        setTitle(selectedRecipe.getName() + " : " + selectedStep.getShortDescription());

        // Place an StepDetailsFragment as our content pane
        stepDetailsFragment = new StepDetailsFragment();
        getSupportFragmentManager().beginTransaction().add(android.R.id.content, stepDetailsFragment).commit();
        getSupportFragmentManager().executePendingTransactions();
    }

    @Override
    protected void onStart() {
        super.onStart();
        // Populate fragment views with selectedStep's data
        stepDetailsFragment.displayStepVideo(selectedStep);
        stepDetailsFragment.displayStepFullDescription(selectedStep);
    }

    @Override
    protected void onStop() {
        super.onStop();
        EventBus.getDefault().unregister(this);
    }

    // This method will be called when a RecipeSelectionEvent is posted
    @Subscribe(sticky = true)
    public void onRecipeSelectionEvent(RecipeSelectionEvent event) {
        selectedRecipe = event.getRecipe();
    }
}