package com.example.android.baking.services.events;

import com.example.android.baking.models.Recipe;

public class RecipeSelectionEvent {

    private final Recipe recipe;

    public RecipeSelectionEvent(Recipe recipe) {
        this.recipe = recipe;
    }

    public Recipe getRecipe() {
        return recipe;
    }
}