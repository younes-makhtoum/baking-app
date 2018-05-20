package com.example.android.baking.services.widgets;

import android.appwidget.AppWidgetManager;
import android.content.Context;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.RadioGroup;

import com.example.android.baking.R;
import com.example.android.baking.databinding.RecipeWidgetConfigureBinding;
import com.example.android.baking.models.Recipe;

import java.util.List;

/**
 * The configuration screen for the {@link RecipeWidgetProvider} AppWidget.
 */
public class RecipeWidgetConfigureActivity extends AppCompatActivity {

    // Tag for log messages
    private static final String LOG_TAG = RecipeWidgetConfigureActivity.class.getName();

    private RecipeWidgetConfigureBinding binding;
    private List<Recipe> recipesList;
    private Recipe selectedRecipe;

    int appWidgetId = AppWidgetManager.INVALID_APPWIDGET_ID;

    public RecipeWidgetConfigureActivity() {
        super();
    }

    @Override
    public void onCreate(Bundle icicle) {
        super.onCreate(icicle);

        Log.v(LOG_TAG, "LOG// RecipeWidgetConfigureActivity");

        // Inflate the content view
        binding = DataBindingUtil.setContentView(this, R.layout.recipe_widget_configure);

        // Set the result to CANCELED.  This will cause the widget host to cancel
        // out of the widget placement if the user presses the back button.
        setResult(RESULT_CANCELED);

/*        if (RecipesListActivity.recipesList != null) {
            recipesList = RecipesListActivity.recipesList;
        } else {
            Toast.makeText(this, R.string.widget_empty_recipes_list_error_message, Toast.LENGTH_SHORT).show();
        }*/

        // Find the widget id from the intent.
        Intent intent = getIntent();

        Bundle extras = intent.getExtras();
        if (extras != null) {
            appWidgetId = extras.getInt(
                    AppWidgetManager.EXTRA_APPWIDGET_ID, AppWidgetManager.INVALID_APPWIDGET_ID);
        }

        Intent resultValue = new Intent();
        resultValue.putExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, appWidgetId);
        setResult(RESULT_CANCELED, resultValue);

        // If this activity was started with an intent without an app widget ID, finish with an error.
        if (appWidgetId == AppWidgetManager.INVALID_APPWIDGET_ID) {
            finish();
        }
        binding.widgetAddButton.setOnClickListener(mOnClickListener);
    }

    View.OnClickListener mOnClickListener = new View.OnClickListener() {
        public void onClick(View v) {
            final Context context = RecipeWidgetConfigureActivity.this;
            final RadioGroup radioGroup = binding.widgetRecipesRadioButtons;
            radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(RadioGroup radioGroup, int i) {
                    int id = radioGroup.getCheckedRadioButtonId();
                    switch (id) {
                        case R.id.widget_nutella_pie:
                            selectedRecipe = recipesList.get(0);
                            break;
                        case R.id.widget_brownies:
                            selectedRecipe = recipesList.get(1);
                            break;
                        case R.id.widget_yellow_cake:
                            selectedRecipe = recipesList.get(2);
                            break;
                        case R.id.widget_cheese_cake:
                            selectedRecipe = recipesList.get(3);
                            break;
                        default:
                            selectedRecipe = recipesList.get(0);
                            break;
                    }
                }
            });

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
}