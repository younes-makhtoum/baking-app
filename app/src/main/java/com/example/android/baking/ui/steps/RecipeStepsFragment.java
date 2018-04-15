package com.example.android.baking.ui.steps;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.android.baking.R;
import com.example.android.baking.databinding.FragmentRecipeStepsBinding;
import com.example.android.baking.models.Recipe;

import java.util.Objects;

public class RecipeStepsFragment extends Fragment {

    private Recipe selectedRecipe;

    private IngredientsAdapter ingredientsAdapter;
    private StepsAdapter stepsAdapter;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        FragmentRecipeStepsBinding binding = FragmentRecipeStepsBinding.bind(inflater.inflate(R.layout.fragment_recipe_steps, container, false));

        View rootView = binding.getRoot();

        binding.ingredientsRecycler.setLayoutManager(new LinearLayoutManager(getContext()));
        binding.stepsRecycler.setLayoutManager(new LinearLayoutManager(getContext()));

        // Add items separation
        binding.ingredientsRecycler.addItemDecoration(new DividerItemDecoration(getContext(), LinearLayoutManager.VERTICAL));
        binding.stepsRecycler.addItemDecoration(new DividerItemDecoration(getContext(), LinearLayoutManager.VERTICAL));

        ingredientsAdapter = new IngredientsAdapter(getContext());
        stepsAdapter = new StepsAdapter(getContext());

        binding.ingredientsRecycler.setAdapter(ingredientsAdapter);
        binding.stepsRecycler.setAdapter(stepsAdapter);

        selectedRecipe = ((DetailActivity) Objects.requireNonNull(this.getActivity())).getSelectedRecipe();

        setRecipeDetailData();

        return rootView;
    }

    private void setRecipeDetailData(){
        ingredientsAdapter.setIngredientsInfoList(selectedRecipe.getIngredients());
        stepsAdapter.setStepsInfoList(selectedRecipe.getSteps());
    }
}