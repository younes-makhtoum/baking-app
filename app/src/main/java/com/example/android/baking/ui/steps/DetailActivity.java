package com.example.android.baking.ui.steps;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.example.android.baking.R;
import com.example.android.baking.models.Recipe;

public class DetailActivity extends AppCompatActivity {

    // Tag for log messages
    private static final String LOG_TAG = DetailActivity.class.getName();
    // Recipe object instance declaration to handle the received parcelable
    private Recipe selectedRecipe;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_detail);

        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.detail_view, new RecipeStepsFragment());
        fragmentTransaction.commit();

        // Collect our intent and get our parcel with the selected Recipe object
        Intent intent = getIntent();
        selectedRecipe = intent.getParcelableExtra("Recipe");

        setTitle(selectedRecipe.getName());
    }
    public Recipe getSelectedRecipe(){
        return this.selectedRecipe;
    }
}
