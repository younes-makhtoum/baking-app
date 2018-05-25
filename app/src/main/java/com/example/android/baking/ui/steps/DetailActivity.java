package com.example.android.baking.ui.steps;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.example.android.baking.R;
import com.example.android.baking.models.Recipe;
import com.example.android.baking.services.events.RecipeSelectionEvent;
import com.example.android.baking.services.events.StepSelectionEvent;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

public class DetailActivity extends AppCompatActivity {

    boolean savedInstanceStateEnabled = false;

    // Tag for log messages
    private static final String LOG_TAG = DetailActivity.class.getName();

    // Recipe object instance declaration to handle the received parcelable
    private Recipe selectedRecipe;

    private RecipeStepsFragment recipeStepsFragment;
    private StepDetailsFragment stepDetailsFragment;

    private static final String TAG_RECIPE_STEPS_FRAGMENT = "RecipeStepsFragment";
    private static final String TAG_STEP_DETAIL_FRAGMENT = "StepDetailsFragment";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        boolean isDualPane = getResources().getBoolean(R.bool.has_two_panes);

        android.support.v4.app.FragmentManager fragmentManager = getSupportFragmentManager();

        if (savedInstanceState != null) {
            savedInstanceStateEnabled = true;
        }

        selectedRecipe = getIntent().getParcelableExtra("Recipe");

        // Set title of activity according to the selected recipe.
        setTitle(selectedRecipe.getName());

        // Initial flow from previous activity
        if (!savedInstanceStateEnabled) {
            // Create new RecipeStepsFragment
            recipeStepsFragment = new RecipeStepsFragment();
            fragmentManager.beginTransaction().add(R.id.recipe_steps, recipeStepsFragment, TAG_RECIPE_STEPS_FRAGMENT).commit();
            // If we are in DualPane, then create also a new StepDetailsFragment
            if(isDualPane){
                stepDetailsFragment = new StepDetailsFragment();
                fragmentManager.beginTransaction().add(R.id.step_details, stepDetailsFragment, TAG_STEP_DETAIL_FRAGMENT).commit();
            }
        }

        // A configuration change occurred, when just need to retrieve our previously created fragments
        if (savedInstanceStateEnabled) {
            recipeStepsFragment = (RecipeStepsFragment) fragmentManager.findFragmentByTag(TAG_RECIPE_STEPS_FRAGMENT);
            stepDetailsFragment = (StepDetailsFragment) fragmentManager.findFragmentByTag(TAG_STEP_DETAIL_FRAGMENT);
        }

        setContentView(R.layout.main_layout);
    }

    @Override
    protected void onStart() {
        super.onStart();

        // Register the EventBus to handle events and react accordingly
        EventBus.getDefault().register(this);
        // Initial flow from previous activity : display data related to first step.
        if (!savedInstanceStateEnabled) {
            EventBus.getDefault().postSticky(new StepSelectionEvent(selectedRecipe.getSteps().get(0)));
        }
    }

    @Override
    protected void onStop() {
        super.onStop();
        EventBus.getDefault().unregister(this);
    }

    @Subscribe(sticky = true)
    public void onStepSelectionEvent(StepSelectionEvent event) {
        stepDetailsFragment.releasePlayer();
        stepDetailsFragment.displayStepVideo(event.getStep());
        stepDetailsFragment.displayStepFullDescription(event.getStep());
    }

    @Subscribe(sticky = true)
    public void onRecipeSelectionEvent(RecipeSelectionEvent event) {
        selectedRecipe = event.getRecipe();
    }

    public Recipe getSelectedRecipe(){
        return this.selectedRecipe;
    }
}