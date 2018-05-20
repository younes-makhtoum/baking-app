package com.example.android.baking.services.widgets;

import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.widget.RemoteViews;

import com.example.android.baking.R;
import com.example.android.baking.models.Recipe;

import java.util.Objects;

/**
 * Implementation of App Widget functionality.
 */
public class RecipeWidgetProvider extends AppWidgetProvider {

    // Tag for log messages
    private static final String LOG_TAG = RecipeWidgetProvider.class.getName();

    private static Recipe mSelectedRecipe;

    static void updateRecipeWidget(Context context, AppWidgetManager appWidgetManager,
                                int appWidgetId, Recipe selectedRecipe) {

        Log.v(LOG_TAG, "LOG// RecipeWidgetProvider > updateRecipeWidget");

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

        Log.v(LOG_TAG, "LOG// RecipeWidgetProvider > getIngredientsListRemoteViews");

        RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.recipe_widget_provider);

        // Set the IngredientsListWidgetService intent to act as the adapter for the ListView
        Intent intent = new Intent(context, IngredientsListWidgetService.class);
        intent.putExtra("Recipe", mSelectedRecipe);

        views.setTextViewText(R.id.widget_recipe_name, mSelectedRecipe.getName());
        views.setRemoteAdapter(R.id.widget_recipe_ingredients, intent);

/*        // Set the DetailActivity intent to launch when clicked
        Intent appIntent = new Intent(context, DetailActivity.class);
        PendingIntent appPendingIntent = PendingIntent.getActivity(context, 0, appIntent, PendingIntent.FLAG_UPDATE_CURRENT);
        views.setPendingIntentTemplate(R.id.widget_recipe_ingredients, appPendingIntent);*/

        return views;
    }

    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        // There may be multiple widgets active, so update all of them
        Log.v(LOG_TAG, "LOG// RecipeWidgetProvider > onUpdate");
        for (int appWidgetId : appWidgetIds) {
            updateRecipeWidget(context, appWidgetManager, appWidgetId, mSelectedRecipe);
        }
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

