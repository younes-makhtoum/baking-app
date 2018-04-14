package com.example.android.baking.ui.steps;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.android.baking.R;
import com.example.android.baking.models.Ingredient;

import java.util.ArrayList;
import java.util.List;

public class IngredientsAdapter extends RecyclerView.Adapter<IngredientsAdapter.MyViewHolder> {

    // Tag for log messages
    public static final String LOG_TAG = IngredientsAdapter.class.getName();

    private Context context;
    private ArrayList<Ingredient> ingredientsList;

    public IngredientsAdapter(Context context) {
        this.context = context;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        private TextView ingredientName, ingredientQuantity, ingredientUnitOfMeasure;

        private MyViewHolder(View itemView) {
            super(itemView);
            ingredientName = itemView.findViewById(R.id.ingredient_name);
            ingredientQuantity = itemView.findViewById(R.id.ingredient_quantity);
            ingredientUnitOfMeasure = itemView.findViewById(R.id.ingredient_unit_of_measure);
        }
    }

    @NonNull
    @Override
    public IngredientsAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // Inflate the item layout
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.ingredient_item, parent, false);
        return new IngredientsAdapter.MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull IngredientsAdapter.MyViewHolder holder, final int position) {
        Ingredient currentIngredient = ingredientsList.get(position);
        Log.v(LOG_TAG, "LOG// currentIngredient's name is " + currentIngredient.getName());
        holder.ingredientName.setText(currentIngredient.getName());
        holder.ingredientQuantity.setText((int) currentIngredient.getQuantity());
        holder.ingredientUnitOfMeasure.setText(currentIngredient.getUnitOfMeasure());
    }

    @Override
    public int getItemCount() {
        if (ingredientsList == null) {
            return 0;
        } else {
            return ingredientsList.size();
        }
    }

    // Helper method to set the current ingredients list into the ingredients RecyclerView of the activity
    public void setIngredientsInfoList(ArrayList<Ingredient> ingredientsList) {
        this.ingredientsList = ingredientsList;
    }
}