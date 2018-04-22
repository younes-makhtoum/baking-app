package com.example.android.baking.ui.steps;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.android.baking.R;
import com.example.android.baking.databinding.FragmentRecipeStepsBinding;
import com.example.android.baking.models.Recipe;

import java.util.Objects;

public class RecipeStepsFragment extends Fragment {

    // Tag for log messages
    private static final String LOG_TAG = RecipeStepsFragment.class.getName();

    private Recipe selectedRecipe;

    private IngredientsAdapter ingredientsAdapter;
    private StepsAdapter stepsAdapter;

    /**
     * Default constructor required by framework.
     */
    public RecipeStepsFragment() {
        super();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        ingredientsAdapter = new IngredientsAdapter(getContext());
        stepsAdapter = new StepsAdapter(getContext());

        // get selected recipe
        selectedRecipe = ((DetailActivity) Objects.requireNonNull(this.getActivity())).getSelectedRecipe();

        setRecipeDetailData();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        FragmentRecipeStepsBinding binding = FragmentRecipeStepsBinding
                .bind(inflater.inflate(R.layout.fragment_recipe_steps, container, false));

        binding.ingredientsRecycler.setLayoutManager(new LinearLayoutManager(getContext()));
        binding.stepsRecycler.setLayoutManager(new LinearLayoutManager(getContext()));

        // Add items separation
        binding.ingredientsRecycler.addItemDecoration(new DividerItemDecoration(getContext(), LinearLayoutManager.VERTICAL));
        binding.stepsRecycler.addItemDecoration(new DividerItemDecoration(getContext(), LinearLayoutManager.VERTICAL));

        binding.ingredientsRecycler.setAdapter(ingredientsAdapter);
        binding.stepsRecycler.setAdapter(stepsAdapter);

        return binding.getRoot();
    }

    private void setRecipeDetailData(){
        ingredientsAdapter.setIngredientsInfoList(selectedRecipe.getIngredients());
        stepsAdapter.setStepsInfoList(selectedRecipe.getSteps());
    }
}