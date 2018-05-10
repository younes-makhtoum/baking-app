package com.example.android.baking.ui.steps;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Display;
import android.view.MenuItem;
import android.view.WindowManager;

import com.example.android.baking.R;
import com.example.android.baking.models.Recipe;
import com.example.android.baking.models.Step;
import com.example.android.baking.services.events.RecipeSelectionEvent;
import com.example.android.baking.services.events.StepSelectionEvent;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.util.Objects;

public class StepDetailsActivity extends AppCompatActivity {

    // Tag for log messages
    private static final String LOG_TAG = StepDetailsActivity.class.getName();

    private boolean savedInstanceStateEnabled = false;

    private StepDetailsFragment stepDetailsFragment;

    private static final String TAG_STEP_DETAIL_FRAGMENT = "StepDetailsFragment";

    private Recipe selectedRecipe;
    private Step selectedStep;
    private int orientation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // If we are in two-pane layout mode, this activity is no longer necessary
        if (getResources().getBoolean(R.bool.has_two_panes)) {
            finish();
            return;
        }
        // Check and get the current orientation of the device
        Display display = ((WindowManager) Objects.requireNonNull(this.getSystemService(Context.WINDOW_SERVICE))).getDefaultDisplay();
        orientation = display.getRotation();

        if (savedInstanceState != null) {
            savedInstanceStateEnabled = true;
        }

        android.support.v4.app.FragmentManager fragmentManager = getSupportFragmentManager();

        // Collect our intent and get our parcel with the selected Step object
        selectedStep = getIntent().getParcelableExtra("Step");

        // A configuration change occurred, we just need to retrieve our previously created fragment
        if (!savedInstanceStateEnabled) {
            // Place an StepDetailsFragment as our content pane
            stepDetailsFragment = new StepDetailsFragment();
            fragmentManager.beginTransaction().add(android.R.id.content, stepDetailsFragment, TAG_STEP_DETAIL_FRAGMENT).commit();
        } else {
            stepDetailsFragment = (StepDetailsFragment) fragmentManager.findFragmentByTag(TAG_STEP_DETAIL_FRAGMENT);
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        // Make full screen experience if the orientation is landscape
        if(orientation == 1 || orientation == 3) {
            stepDetailsFragment.hideSystemUi();
        }
        // Register the EventBus to handle events and react accordingly
        EventBus.getDefault().register(this);
        // Set title of activity according to the selected recipe.
        setTitle(selectedRecipe.getName() + " : " + selectedStep.getShortDescription());
        // Initial flow from previous activity : display data related to selected step.
        if (!savedInstanceStateEnabled) {
            EventBus.getDefault().postSticky(new StepSelectionEvent(selectedStep));
        }
    }

    @Override
    protected void onStop() {
        super.onStop();
        EventBus.getDefault().unregister(this);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                return true;
        }
        return(super.onOptionsItemSelected(item));
    }

    @Subscribe(sticky = true)
    public void onStepSelectionEvent(StepSelectionEvent event) {
        selectedStep = event.getStep();
        stepDetailsFragment.releasePlayer();
        stepDetailsFragment.displayStepVideo(selectedStep);
        stepDetailsFragment.displayStepFullDescription(selectedStep);
    }

    // This method will be called when a RecipeSelectionEvent is posted
    @Subscribe(sticky = true)
    public void onRecipeSelectionEvent(RecipeSelectionEvent event) {
        selectedRecipe = event.getRecipe();
    }
}