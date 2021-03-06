package com.example.android.baking.models;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class Recipe implements Parcelable {

    @SerializedName("id")
    private final int id;

    @SerializedName("name")
    private final String name;

    @SerializedName("ingredients")
    private final ArrayList<Ingredient> ingredients;

    @SerializedName("steps")
    private final ArrayList<Step> steps;

    @SerializedName("servings")
    private final int servings;

    @SerializedName("image")
    private final String image;

    public Recipe(int id, String name, ArrayList<Ingredient> ingredients, ArrayList<Step> steps, int servings, String image) {
        this.id = id;
        this.name = name;
        this.ingredients = ingredients;
        this.steps = steps;
        this.servings = servings;
        this.image = image;
    }

    public int getId() {
        return id;
    }
    public String getName() {
        return name;
    }
    public ArrayList<Ingredient> getIngredients() {
        return ingredients;
    }
    public ArrayList<Step> getSteps() {
        return steps;
    }
    public int getServings() {
        return servings;
    }
    public String getImage() {
        return image;
    }


    // Write object values to parcel for storage
    public void writeToParcel(Parcel dest, int flags){
        dest.writeInt(id);
        dest.writeString(name);
        dest.writeTypedList(ingredients);
        dest.writeTypedList(steps);
        dest.writeInt(servings);
        dest.writeString(image);
    }

    // Constructor used for parcel
    private Recipe(Parcel parcel){
        id = parcel.readInt();
        name = parcel.readString();
        ingredients = new ArrayList<>();
        parcel.readTypedList(ingredients, Ingredient.CREATOR);
        steps = new ArrayList<>();
        parcel.readTypedList(steps, Step.CREATOR);
        servings = parcel.readInt();
        image = parcel.readString();
    }

    // Creator - used when un-parceling a parcel (creating a Recipe object)
    public static final Parcelable.Creator<Recipe> CREATOR = new Parcelable.Creator<Recipe>(){

        @Override
        public Recipe createFromParcel(Parcel parcel) {
            return new Recipe(parcel);
        }

        @Override
        public Recipe[] newArray(int size) {
            return new Recipe[size];
        }
    };

    // Return hashcode of object
    public int describeContents() {
        return hashCode();
    }
}

