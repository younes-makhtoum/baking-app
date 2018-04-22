package com.example.android.baking.ui.steps;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.View;

import com.example.android.baking.R;
import com.example.android.baking.models.Recipe;
import com.example.android.baking.services.SelectionEvent;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

public class DetailActivity extends FragmentActivity {

    // Tag for log messages
    private static final String LOG_TAG = DetailActivity.class.getName();

    // Whether or not we are in dual-pane mode
    public boolean isDualPane = false;

    // Recipe object instance declaration to handle the received parcelable
    private Recipe selectedRecipe;

    // StepDetailsFragment object in case we are in dual pane mode
    StepDetailsFragment stepDetailsFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Collect our intent and get our parcel with the selected Recipe object
        selectedRecipe = getIntent().getParcelableExtra("Recipe");

        // Set title of activity according to the selected recipe.
        setTitle(selectedRecipe.getName());

        setContentView(R.layout.main_layout);

        // Determine whether we are in single-pane or dual-pane mode by testing the visibility
        // of the step details view.
        View stepDetailsView = findViewById(R.id.step_details);
        isDualPane = stepDetailsView != null && stepDetailsView.getVisibility() == View.VISIBLE;
    }

    @Override
    protected void onStart() {
        super.onStart();
        EventBus.getDefault().register(this);

        // If we are in dual pane (tablet mode in landscape), initialize a StepDetailsFragment
        if(isDualPane) {
            stepDetailsFragment = new StepDetailsFragment();
            getSupportFragmentManager().beginTransaction().add(R.id.step_details, stepDetailsFragment).commit();
            getSupportFragmentManager().executePendingTransactions();
            stepDetailsFragment.displayStepVideo(selectedRecipe.getSteps().get(0));
            stepDetailsFragment.displayStepFullDescription(selectedRecipe.getSteps().get(0));
        }
    }

    @Override
    protected void onStop() {
        super.onStop();
        EventBus.getDefault().unregister(this);
    }

    // This method will be called when a SelectionEvent is posted
    @Subscribe
    public void onSelectionEvent(SelectionEvent event) {
        stepDetailsFragment.displayStepVideo(event.getStep());
        stepDetailsFragment.displayStepFullDescription(event.getStep());
    }

    public Recipe getSelectedRecipe(){
        return this.selectedRecipe;
    }
}