package com.example.android.baking.services.widget;

import android.appwidget.AppWidgetManager;
import android.content.Context;
import android.content.Intent;
import android.widget.RemoteViews;
import android.widget.RemoteViewsService;

import com.example.android.baking.R;
import com.example.android.baking.models.Ingredient;
import static com.example.android.baking.services.widget.RecipeWidgetProvider.mSelectedRecipe;

import java.util.ArrayList;

public class IngredientsListWidgetService extends RemoteViewsService {

    @Override
    public RemoteViewsFactory onGetViewFactory(Intent intent) {
        return new IngredientsListRemoteFactory(this.getApplicationContext(), intent);
    }
}

class IngredientsListRemoteFactory implements RemoteViewsService.RemoteViewsFactory {

    private Context context;
    private ArrayList<Ingredient> ingredientsList;

    public IngredientsListRemoteFactory(Context applicationContext, Intent intent) {
        context = applicationContext;
        ingredientsList = mSelectedRecipe.getIngredients();
        int mAppWidgetId = intent.getIntExtra(AppWidgetManager.EXTRA_APPWIDGET_ID,
                AppWidgetManager.INVALID_APPWIDGET_ID);
    }

    @Override
    public void onCreate() {
    }

    // called on start and when notifyAppWidgetViewDataChanged is called
    @Override
    public void onDataSetChanged() {
    }

    @Override
    public void onDestroy() {
    }

    @Override
    public int getCount() {
        if (ingredientsList == null) {
            return 0;
        } else {
            return ingredientsList.size();
        }
    }

    /**
     * This method acts like the onBindViewHolder method in an Adapter
     *
     * @param position The current position of the item in the GridView to be displayed
     * @return The RemoteViews object to display for the provided postion
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