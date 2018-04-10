package com.example.android.baking.ui;

import android.databinding.DataBindingUtil;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.view.View;

import com.example.android.baking.R;
import com.example.android.baking.databinding.ActivityMasterBinding;
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

public class MasterActivity extends AppCompatActivity {

    // Tag for log messages
    public static final String LOG_TAG = MasterActivity.class.getName();
    // Store the binding
    private ActivityMasterBinding binding;
    // RecyclerView adapter instance
    private MasterAdapter masterAdapter;
    // List of favorite movies
    List<Recipe> recipesList;
    // Used to check the internet connection changes
    Merlin merlin;
    // Used to check the instant internet connection status
    MerlinsBeard merlinsBeard;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        // Inflate the content view
        binding = DataBindingUtil.setContentView(this, R.layout.activity_master);
        binding.recyclerMain.recyclerView.setLayoutManager(new LinearLayoutManager(this));
        // Set the RecyclerView adapter to the correspondent view
        masterAdapter = new MasterAdapter(this);
        binding.recyclerMain.recyclerView.setAdapter(masterAdapter);

        // Initialize the internet connection listeners
        merlin = new Merlin.Builder().withConnectableCallbacks().build(getApplicationContext());
        merlinsBeard = MerlinsBeard.from(getApplicationContext());

        // Register the internet status activation listener
        merlin.registerConnectable(new Connectable() {
            @Override
            public void onConnect() {
                // Only load the recipes if they are not currently loaded
                if (binding.recyclerMain.recyclerView.getVisibility()!= View.VISIBLE) {
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            getRecipes();
                        }
                    });
                }
            }
        });

        // Depending on the network status, show the appropriate view
        if (!merlinsBeard.isConnected()) {
            Utils.showIssueDisclaimer(binding.recyclerMain.recyclerView,
                    binding.recyclerMain.issueView, R.drawable.no_internet_connection);
        }
        else {
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
                    masterAdapter.setRecipeInfoList(response.body());
                    masterAdapter.notifyDataSetChanged();
                    Utils.showResults(binding.recyclerMain.loadingSpinner,
                            binding.recyclerMain.recyclerView);
                } else {
                    Utils.showIssueDisclaimer(binding.recyclerMain.recyclerView,
                            binding.recyclerMain.issueView, R.drawable.error_avatar);
                }
            }
            @Override
            public void onFailure(@NonNull Call<List<Recipe>> call, @NonNull Throwable t) {
                Utils.showIssueDisclaimer(binding.recyclerMain.recyclerView,
                        binding.recyclerMain.issueView, R.drawable.error_avatar);
            }
        });
    }
}