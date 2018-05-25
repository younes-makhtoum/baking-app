package com.example.android.baking;

import android.app.Activity;
import android.app.Instrumentation;
import android.content.Intent;
import android.support.test.espresso.contrib.RecyclerViewActions;
import android.support.test.espresso.intent.rule.IntentsTestRule;
import android.support.test.runner.AndroidJUnit4;

import com.example.android.baking.ui.recipes.RecipesListActivity;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.intent.Intents.intending;
import static android.support.test.espresso.intent.matcher.IntentMatchers.toPackage;
import static android.support.test.espresso.matcher.ViewMatchers.hasDescendant;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withParent;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.Matchers.allOf;

/*
 * Reference :
 * https://spin.atomicobject.com/2016/04/15/espresso-testing-recyclerviews/
 * */
@RunWith(AndroidJUnit4.class)
public class RecipesListActivityRecyclerViewTest {
    @Rule
    public IntentsTestRule<RecipesListActivity> mIntentTestRule
            = new IntentsTestRule<>(RecipesListActivity.class);

    @Test
    public void clickRecyclerView_OpenCorrectRecipe(){

        // Click on the RecyclerView item at position 1 : "Brownies" Recipe
        onView(allOf(withId(R.id.recycler_view), withParent(withId(R.id.recycler_main))))
                .perform(RecyclerViewActions.actionOnItemAtPosition(1, click()));

        // Build the result to return when the detail activity is launched.
        Intent resultData = new Intent();
        Instrumentation.ActivityResult result =
                new Instrumentation.ActivityResult(Activity.RESULT_OK, resultData);

        // Set up result stubbing when an intent sent to "step details" is seen.
        intending(toPackage("com.example.android.baking.ui.steps")).respondWith(result);

        String firstIngredient = "Bittersweet chocolate (60-70% cacao)";

        // Check that the first ingredient listed is the one expected
        onView(withRecyclerView(R.id.ingredients_recycler).atPosition(0))
                .check(matches(hasDescendant(withText(firstIngredient))));
    }

    // Convenience helper
    public static RecyclerViewMatcher withRecyclerView(final int recyclerViewId) {
        return new RecyclerViewMatcher(recyclerViewId);
    }
}
