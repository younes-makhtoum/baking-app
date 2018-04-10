package com.example.android.baking.models;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.List;

public class Recipe implements Parcelable {

    private int id;
    private String name;
    private List<Ingredient> ingredients;
    private List<Step> steps;
    private int servings;
    private String image;

    public Recipe(int id, String name, List<Ingredient> ingredients, List<Step> steps, int servings, String image) {
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
    public List<Ingredient> getIngredients() {
        return ingredients;
    }
    public List<Step> getSteps() {
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
        parcel.readTypedList(ingredients, Ingredient.CREATOR);
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

