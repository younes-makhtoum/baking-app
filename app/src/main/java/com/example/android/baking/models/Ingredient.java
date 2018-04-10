package com.example.android.baking.models;

import android.os.Parcel;
import android.os.Parcelable;

public class Ingredient implements Parcelable {

    private String name;
    private String unitOfMeasure;
    private float quantity;

    public Ingredient(String name, String unitOfMeasure, int quantity) {
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
    public float getQuantity() {
        return quantity;
    }

    // Write object values to parcel for storage
    public void writeToParcel(Parcel dest, int flags){
        dest.writeString(name);
        dest.writeString(unitOfMeasure);
        dest.writeFloat(quantity);
    }

    // Constructor used for parcel
    private Ingredient(Parcel parcel){
        name = parcel.readString();
        unitOfMeasure = parcel.readString();
        quantity = parcel.readInt();
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