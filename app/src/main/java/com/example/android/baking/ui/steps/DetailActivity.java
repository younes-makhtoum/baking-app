package com.example.android.baking.ui.steps;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.example.android.baking.R;
import com.example.android.baking.models.Recipe;
import com.example.android.baking.models.Step;
import com.example.android.baking.services.events.RecipeSelectionEvent;
import com.example.android.baking.services.events.StepSelectionEvent;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

public class DetailActivity extends AppCompatActivity {

    boolean savedInstanceStateEnabled = false;

    // Tag for log messages
    private static final String LOG_TAG = DetailActivity.class.getName();

    // Whether or not we are in dual-pane mode
    private boolean isDualPane = false;

    // Recipe object instance declaration to handle the received parcelable
    private Recipe selectedRecipe;

    // Step object instance declaration to populate right pane accordingly if relevant
    private Step selectedStep;

    // StepDetailsFragment object in case we are in dual pane mode
    private StepDetailsFragment stepDetailsFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (savedInstanceState != null) {
            savedInstanceStateEnabled = true;
        }

        selectedRecipe = getIntent().getParcelableExtra("Recipe");

        EventBus.getDefault().register(this);

        // Set title of activity according to the selected recipe.
        setTitle(selectedRecipe.getName());

        isDualPane = getResources().getBoolean(R.bool.has_two_panes);

        // If we are in dual pane mode (device is tablet in landscape orientation),
        // initialize a StepDetailsFragment
        if (isDualPane) {
            // Initiate new step details fragment
            stepDetailsFragment = new StepDetailsFragment();
            getSupportFragmentManager().beginTransaction().add(R.id.step_details, stepDetailsFragment).commit();
            getSupportFragmentManager().executePendingTransactions();
        }

        // If we are coming from the RecipesListActivity, initiate a new recipe steps fragment
        if (!savedInstanceStateEnabled) {
            getSupportFragmentManager().beginTransaction().add(R.id.recipe_steps, new RecipeStepsFragment()).commit();
        }

        setContentView(R.layout.main_layout);
    }

    @Override
    protected void onStart() {
        super.onStart();
        // Populate steps details data with first step only if dual pane mode is effective.
        // If we are coming from the RecipesListActivity (savedInstanceStateEnabled is false),
        // display data related to first step.
        if (isDualPane) {
            if (!savedInstanceStateEnabled) {
                EventBus.getDefault().postSticky(new StepSelectionEvent(selectedRecipe.getSteps().get(0)));
            } else {
                StepSelectionEvent stickyEvent = EventBus.getDefault().getStickyEvent(StepSelectionEvent.class);
                // Better check that an event was actually posted before
                if(stickyEvent != null) {
                    // Now do something with it
                    EventBus.getDefault().postSticky(stickyEvent);
                } else {
                    EventBus.getDefault().postSticky(new StepSelectionEvent(selectedStep));
                }
            }
        }
    }

    @Override
    protected void onStop() {
        super.onStop();
        EventBus.getDefault().unregister(this);
    }

    @Subscribe(sticky = true)
    public void onStepSelectionEvent(StepSelectionEvent event) {
        // Update steps details data only if dual pane mode is effective
        selectedStep = event.getStep();
        if (isDualPane) {
        stepDetailsFragment.displayStepVideo(selectedStep);
        stepDetailsFragment.displayStepFullDescription(selectedStep);
        }
    }

    @Subscribe(sticky = true)
    public void onRecipeSelectionEvent(RecipeSelectionEvent event) {
        selectedRecipe = event.getRecipe();
    }

    public Recipe getSelectedRecipe(){
        return this.selectedRecipe;
    }
}