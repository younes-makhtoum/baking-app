package com.example.android.baking.ui.recipes;

import android.databinding.DataBindingUtil;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.util.Log;

import com.example.android.baking.R;
import com.example.android.baking.databinding.ActivityRecipesListBinding;
import com.example.android.baking.services.RecipeService;
import com.example.android.baking.services.RemoteClient;
import com.example.android.baking.services.Utils;
import com.example.android.baking.models.Recipe;

import com.novoda.merlin.Merlin;
import com.novoda.merlin.MerlinsBeard;
import com.novoda.merlin.registerable.connection.Connectable;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RecipesListActivity extends AppCompatActivity {

    // Tag for log messages
    private static final String LOG_TAG = RecipesListActivity.class.getName();
    // Store the binding
    private ActivityRecipesListBinding binding;
    // RecyclerView adapter instance
    private RecipesListAdapter recipesListAdapter;
    // Used to check the internet connection changes
    private Merlin merlin;
    // Recipes loaded checker
    private boolean recipesAreLoaded = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        // Inflate the content view
        binding = DataBindingUtil.setContentView(this, R.layout.activity_recipes_list);
        binding.recyclerMain.recyclerView.setLayoutManager(new LinearLayoutManager(this));
        // Add items separation
        binding.recyclerMain.recyclerView.addItemDecoration(new DividerItemDecoration(this, LinearLayoutManager.VERTICAL));
        // Set the RecyclerView adapter to the correspondent view
        recipesListAdapter = new RecipesListAdapter(this);
        binding.recyclerMain.recyclerView.setAdapter(recipesListAdapter);

        // Initialize the internet connection listeners
        merlin = new Merlin.Builder().withConnectableCallbacks().build(getApplicationContext());
        MerlinsBeard merlinsBeard = MerlinsBeard.from(getApplicationContext());

        // Register the internet status activation listener
        merlin.registerConnectable(new Connectable() {
            @Override
            public void onConnect() {
                // Only load the recipes if they are not currently loaded
                if (!recipesAreLoaded) {
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            getRecipes();
                        }
                    });
                }
            }
        });

        // If no internet connection is detected at app launch, then show the issue disclaimer
        if (!merlinsBeard.isConnected()) {
            Utils.showIssueDisclaimer(binding.recyclerMain.recyclerView,
                    binding.recyclerMain.issueView, R.drawable.no_internet_connection);
        } else {
            getRecipes();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        // Bind the merlin listener so that internet status changes are monitored
        merlin.bind();
    }

    @Override
    protected void onPause() {
        // Unbind the merlin listener as the activity is on pause
        merlin.unbind();
        super.onPause();
    }

    private void getRecipes() {
        Utils.showLoadingSpinner(binding.recyclerMain.issueView, binding.recyclerMain.loadingSpinner,
                binding.recyclerMain.recyclerView);
        RecipeService recipeService = RemoteClient.getClient().create(RecipeService.class);
        final Call<List<Recipe>> callResponse = recipeService.getRecipes();

        callResponse.enqueue(new Callback<List<Recipe>>() {
            @Override
            public void onResponse(@NonNull Call<List<Recipe>> call, @NonNull Response<List<Recipe>> response) {
                if (response.isSuccessful()) {
                    recipesListAdapter.setRecipeInfoList(response.body());
                    recipesListAdapter.notifyDataSetChanged();
                    Utils.showResults(binding.recyclerMain.loadingSpinner,
                            binding.recyclerMain.recyclerView);
                    recipesAreLoaded = true;
                } else {
                    Log.v(LOG_TAG, "LOG// response.failed() reached");
                    Utils.showIssueDisclaimer(binding.recyclerMain.recyclerView,
                            binding.recyclerMain.issueView, R.drawable.error_avatar);
                }
            }
            @Override
            public void onFailure(@NonNull Call<List<Recipe>> call, @NonNull Throwable t) {
                Log.v(LOG_TAG, "LOG// onFailure reached");
                Utils.showIssueDisclaimer(binding.recyclerMain.recyclerView,
                        binding.recyclerMain.issueView, R.drawable.error_avatar);
            }
        });
    }
}