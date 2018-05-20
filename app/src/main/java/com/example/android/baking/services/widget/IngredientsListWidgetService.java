package com.example.android.baking.services.widget;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.widget.RemoteViews;
import android.widget.RemoteViewsService;

import com.example.android.baking.R;
import com.example.android.baking.models.Ingredient;
import com.example.android.baking.models.Recipe;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import static android.content.Context.MODE_PRIVATE;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Objects;

public class IngredientsListWidgetService extends RemoteViewsService {

    @Override
    public RemoteViewsFactory onGetViewFactory(Intent intent) {
        return new IngredientsListRemoteFactory(this.getApplicationContext(), intent);
    }
}

class IngredientsListRemoteFactory implements RemoteViewsService.RemoteViewsFactory {
    // Tag for log messages
    private static final String LOG_TAG = IngredientsListRemoteFactory.class.getName();
    // Class fields
    private Context context;
    private ArrayList<Ingredient> ingredientsList;

    public IngredientsListRemoteFactory(Context applicationContext, Intent intent) {
        context = applicationContext;
        int appWidgetId = Integer.valueOf(Objects.requireNonNull(intent.getData()).getSchemeSpecificPart());
        ingredientsList = getIngredientsList(appWidgetId);
    }

    // Gets the ingredients list of the selected recipe
    private ArrayList<Ingredient> getIngredientsList(int widgetId) {
        SharedPreferences sharedPref = context.getSharedPreferences("baking-shared-prefs", MODE_PRIVATE);
        Gson gson = new Gson();
        String json = sharedPref.getString("key_saved_recipe" + widgetId, "");
        Type type = new TypeToken<Recipe>(){}.getType();
        Recipe selectedRecipe = gson.fromJson(json, type);
        return selectedRecipe.getIngredients();
    }

    /**
     * This method acts like the onBindViewHolder method in an Adapter
     * @param position The current position of the item in the ListView to be displayed
     * @return The RemoteViews object to display for the provided position
     */
    @Override
    public RemoteViews getViewAt(int position) {
        Ingredient currentIngredient = ingredientsList.get(position);
        RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.ingredient_item_widget);
        views.setTextViewText(R.id.widget_ingredient_name, currentIngredient.getName());
        views.setTextViewText(R.id.widget_ingredient_quantity, String.valueOf(currentIngredient.getQuantity()));
        views.setTextViewText(R.id.widget_ingredient_unit_of_measure, String.valueOf(currentIngredient.getUnitOfMeasure()));
        return views;
    }

    @Override
    public int getCount() {
        if (ingredientsList == null) {
            return 0;
        } else {
            return ingredientsList.size();
        }
    }

    @Override
    public void onCreate() { }
    @Override
    public void onDestroy() { }
    // called on start and when notifyAppWidgetViewDataChanged is called
    @Override
    public void onDataSetChanged() { }
    @Override
    public RemoteViews getLoadingView() {
        return null;
    }
    @Override
    public int getViewTypeCount() {
        return 1;
    }
    @Override
    public long getItemId(int i) {
        return i;
    }
    @Override
    public boolean hasStableIds() {
        return true;
    }
}