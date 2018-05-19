package com.example.android.baking.services.widget;

import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.widget.RemoteViews;

import com.example.android.baking.R;
import com.example.android.baking.models.Recipe;

/**
 * Implementation of App Widget functionality.
 * App Widget Configuration implemented in {@link RecipeWidgetConfigureActivity RecipeWidgetConfigureActivity}
 */
public class RecipeWidgetProvider extends AppWidgetProvider {

    // Tag for log messages
    private static final String LOG_TAG = RecipeWidgetProvider.class.getName();

    public static Recipe mSelectedRecipe;

    static void updateRecipeWidget(Context context, AppWidgetManager appWidgetManager,
                                int appWidgetId, Recipe selectedRecipe) {

        // Retrieve the list of recipes
        mSelectedRecipe = selectedRecipe;
        // Construct the RemoteViews object
        RemoteViews remoteViews = getIngredientsListRemoteViews(context);
        // Instruct the widget manager to update the widget
        appWidgetManager.updateAppWidget(appWidgetId, remoteViews);
    }

    /**
     * Creates and returns the RemoteViews to be displayed in the widget
     *
     * @param context The context
     * @return The RemoteViews for the widget
     */
    private static RemoteViews getIngredientsListRemoteViews(Context context) {

        RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.recipe_widget_provider);

        // Set the IngredientsListWidgetService intent to act as the adapter for the ListView
        Intent intent = new Intent(context, IngredientsListWidgetService.class);

        views.setTextViewText(R.id.widget_recipe_name, mSelectedRecipe.getName());
        views.setRemoteAdapter(R.id.widget_recipe_ingredients, intent);

        return views;
    }

    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        // Enter relevant functionality for when widgets instances are updated
    }

    @Override
    public void onEnabled(Context context) {
        // Enter relevant functionality for when the first widget is created
    }

    @Override
    public void onDisabled(Context context) {
        // Enter relevant functionality for when the last widget is disabled
    }
}