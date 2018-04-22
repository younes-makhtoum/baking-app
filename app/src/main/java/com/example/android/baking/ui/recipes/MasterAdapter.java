package com.example.android.baking.ui.recipes;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.android.baking.R;
import com.example.android.baking.models.Recipe;
import com.example.android.baking.ui.steps.DetailActivity;

import java.util.List;

public class MasterAdapter extends RecyclerView.Adapter<MasterAdapter.MyViewHolder> {

    // Tag for log messages
    public static final String LOG_TAG = MasterAdapter.class.getName();

    private final Context context;
    private List<Recipe> recipesList;

    public MasterAdapter(Context context) {
        this.context = context;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        private TextView recipeTitleTextView;

        private MyViewHolder(View itemView) {
            super(itemView);
            recipeTitleTextView = itemView.findViewById(R.id.recipe_title);
        }
    }

    @NonNull
    @Override
    public MasterAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // Inflate the item layout
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.recipe_item, parent, false);
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull MasterAdapter.MyViewHolder holder, final int position) {

        Recipe currentRecipe = recipesList.get(position);
        holder.recipeTitleTextView.setText(currentRecipe.getName());

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, DetailActivity.class);
                intent.putExtra("Recipe", recipesList.get(position));
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        if (recipesList == null) {
            return 0;
        } else {
            return recipesList.size();
        }
    }

    // Helper method to set the current recipes list into the RecyclerView of the activity
    public void setRecipeInfoList(List<Recipe> recipesList) {
        this.recipesList = recipesList;
    }
}