package com.example.android.baking.models;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

public class Ingredient implements Parcelable {

    @SerializedName("ingredient")
    private final String name;

    @SerializedName("measure")
    private final String unitOfMeasure;

    @SerializedName("quantity")
    private final double quantity;

    public Ingredient(String name, String unitOfMeasure, double quantity) {
        this.name = name;
        this.unitOfMeasure = unitOfMeasure;
        this.quantity = quantity;
    }

    public String getName() {
        return name;
    }
    public String getUnitOfMeasure() {
        return unitOfMeasure;
    }
    public double getQuantity() {
        return quantity;
    }

    // Write object values to parcel for storage
    public void writeToParcel(Parcel dest, int flags){
        dest.writeString(name);
        dest.writeString(unitOfMeasure);
        dest.writeDouble(quantity);
    }

    // Constructor used for parcel
    private Ingredient(Parcel parcel){
        name = parcel.readString();
        unitOfMeasure = parcel.readString();
        quantity = parcel.readDouble();
    }

    // Creator - used when un-parceling a parcel (creating a Ingredient object)
    public static final Parcelable.Creator<Ingredient> CREATOR = new Parcelable.Creator<Ingredient>(){

        @Override
        public Ingredient createFromParcel(Parcel parcel) {
            return new Ingredient(parcel);
        }

        @Override
        public Ingredient[] newArray(int size) {
            return new Ingredient[size];
        }
    };

    // Return hashcode of object
    public int describeContents() {
        return hashCode();
    }
}