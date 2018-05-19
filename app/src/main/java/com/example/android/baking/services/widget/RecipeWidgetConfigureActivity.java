package com.example.android.baking.services.widget;

import android.app.Activity;
import android.appwidget.AppWidgetManager;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.example.android.baking.R;
import com.example.android.baking.models.Recipe;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.List;

/**
 * The configuration screen for the {@link RecipeWidgetProvider RecipeWidgetProvider} AppWidget.
 */
public class RecipeWidgetConfigureActivity extends Activity {

    // Tag for log messages
    private static final String LOG_TAG = RecipeWidgetConfigureActivity.class.getName();

    private List<Recipe> recipesList;
    private Recipe selectedRecipe;

    int appWidgetId = AppWidgetManager.INVALID_APPWIDGET_ID;

    public RecipeWidgetConfigureActivity() {
        super();
    }

    @Override
    public void onCreate(Bundle icicle) {
        super.onCreate(icicle);

        // Set the result to CANCELED.  This will cause the widget host to cancel
        // out of the widget placement if the user presses the back button.
        setResult(RESULT_CANCELED);

        setContentView(R.layout.recipe_widget_configure);
        findViewById(R.id.widget_add_button).setOnClickListener(mOnClickListener);

        // Find the widget id from the intent.
        Intent intent = getIntent();
        Bundle extras = intent.getExtras();
        if (extras != null) {
            appWidgetId = extras.getInt(
                    AppWidgetManager.EXTRA_APPWIDGET_ID, AppWidgetManager.INVALID_APPWIDGET_ID);
        }
        // If this activity was started with an intent without an app widget ID, finish with an error.
        if (appWidgetId == AppWidgetManager.INVALID_APPWIDGET_ID) {
            finish();
        }
        // Get the list of saved list of recipes from the shared preferences file
        SharedPreferences sharedPref = getSharedPreferences("baking-shared-prefs", MODE_PRIVATE);
        Gson gson = new Gson();
        String json = sharedPref.getString("key_saved_recipes_list", "");
        Type type = new TypeToken<List<Recipe>>(){}.getType();
        recipesList = gson.fromJson(json, type);
    }

    // Handle the button click to add a widget
    View.OnClickListener mOnClickListener = new View.OnClickListener() {
        public void onClick(View v) {
            final Context context = RecipeWidgetConfigureActivity.this;
            // It is the responsibility of the configuration activity to update the app widget
            AppWidgetManager appWidgetManager = AppWidgetManager.getInstance(context);
            RecipeWidgetProvider.updateRecipeWidget(context, appWidgetManager, appWidgetId, selectedRecipe);
            // Make sure we pass back the original appWidgetId
            Intent resultValue = new Intent();
            resultValue.putExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, appWidgetId);
            setResult(RESULT_OK, resultValue);
            finish();
        }
    };

    // Extract the selected recipe from the radio buttons
    public void onRadioButtonClicked(View view) {
        // Is the button now checked?
        boolean checked = ((RadioButton) view).isChecked();
        // Check which radio button was clicked
        switch(view.getId()) {
            case R.id.widget_nutella_pie:
                if (checked)
                    selectedRecipe = recipesList.get(0);
                    break;
            case R.id.widget_brownies:
                if (checked)
                    selectedRecipe = recipesList.get(1);
                    break;
            case R.id.widget_yellow_cake:
                if (checked)
                    selectedRecipe = recipesList.get(2);
                    break;
            case R.id.widget_cheese_cake:
                if (checked)
                    selectedRecipe = recipesList.get(3);
                    break;
        }
    }
}

