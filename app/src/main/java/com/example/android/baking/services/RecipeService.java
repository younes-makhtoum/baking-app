package com.example.android.baking.services;

import com.example.android.baking.models.Recipe;
import java.util.List;
import retrofit2.Call;
import retrofit2.http.GET;

public interface RecipeService {
    /**
     * @return list of recipes
     */
    @GET("topher/2017/May/59121517_baking/baking.json")
    Call<List<Recipe>> getRecipes();
}