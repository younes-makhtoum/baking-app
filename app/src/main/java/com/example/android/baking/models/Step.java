package com.example.android.baking.models;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

public class Step implements Parcelable {

    @SerializedName("id")
    private int id;

    @SerializedName("shortDescription")
    private String shortDescription;

    @SerializedName("description")
    private String fullDescription;

    @SerializedName("videoURL")
    private String videoURL;

    @SerializedName("thumbnailURL")
    private String thumbnailURL;

    public Step(int id, String shortDescription, String fullDescription, String videoURL, String thumbnailURL) {
        this.id = id;
        this.shortDescription = shortDescription;
        this.fullDescription = fullDescription;
        this.videoURL = videoURL;
        this.thumbnailURL = thumbnailURL;
    }

    public int getId() {
        return id;
    }
    public String getShortDescription() {
        return shortDescription;
    }
    public String getFullDescription() {
        return fullDescription;
    }
    public String getVideoURL() {
        return videoURL;
    }
    public String getThumbnailURL() {
        return thumbnailURL;
    }

    // Write object values to parcel for storage
    public void writeToParcel(Parcel dest, int flags){
        dest.writeInt(id);
        dest.writeString(shortDescription);
        dest.writeString(fullDescription);
        dest.writeString(videoURL);
        dest.writeString(thumbnailURL);
    }

    // Constructor used for parcel
    private Step(Parcel parcel){
        id = parcel.readInt();
        shortDescription = parcel.readString();
        fullDescription = parcel.readString();
        videoURL = parcel.readString();
        thumbnailURL = parcel.readString();
    }

    // Creator - used when un-parceling a parcel (creating a Step object)
    public static final Parcelable.Creator<Step> CREATOR = new Parcelable.Creator<Step>(){

        @Override
        public Step createFromParcel(Parcel parcel) {
            return new Step(parcel);
        }

        @Override
        public Step[] newArray(int size) {
            return new Step[size];
        }
    };

    // Return hashcode of object
    public int describeContents() {
        return hashCode();
    }
}