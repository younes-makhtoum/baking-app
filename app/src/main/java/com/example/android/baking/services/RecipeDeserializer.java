package com.example.android.baking.services;

import com.example.android.baking.models.Ingredient;
import com.google.gson.Gson;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;

import java.lang.reflect.Type;

public class RecipeDeserializer<T> implements JsonDeserializer<T> {

    @Override
    public T deserialize(JsonElement je, Type type, JsonDeserializationContext jdc)
            throws JsonParseException {

        String nestedJson;

        if (type == Ingredient.class) {
            nestedJson = "ingredients";
        } else { nestedJson = "steps"; }

        JsonElement content = je.getAsJsonObject().get(nestedJson);

        return new Gson().fromJson(content, type);
    }
}