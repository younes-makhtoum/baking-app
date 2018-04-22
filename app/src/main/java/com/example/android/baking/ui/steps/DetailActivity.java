package com.example.android.baking.ui.steps;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.View;

import com.example.android.baking.R;
import com.example.android.baking.models.Recipe;

public class DetailActivity extends FragmentActivity {

    // Tag for log messages
    private static final String LOG_TAG = DetailActivity.class.getName();

    // Whether or not we are in dual-pane mode
    public boolean isDualPane = false;

    // Recipe object instance declaration to handle the received parcelable
    private Recipe selectedRecipe;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Collect our intent and get our parcel with the selected Recipe object
        Intent intent = getIntent();
        selectedRecipe = intent.getParcelableExtra("Recipe");

        setContentView(R.layout.main_layout);

        // Determine whether we are in single-pane or dual-pane mode by testing the visibility
        // of the step details view.
        View stepDetailsView = findViewById(R.id.step_details);
        isDualPane = stepDetailsView != null && stepDetailsView.getVisibility() == View.VISIBLE;

        setTitle(selectedRecipe.getName());
    }

    @Override
    protected void onStart() {
        super.onStart();
        if(isDualPane) {
            StepDetailsFragment stepDetailsFragment = new StepDetailsFragment();
            getSupportFragmentManager().beginTransaction().add(android.R.id.content, stepDetailsFragment).commit();
            getSupportFragmentManager().executePendingTransactions();
        }
    }

    public Recipe getSelectedRecipe(){
        return this.selectedRecipe;
    }
}
