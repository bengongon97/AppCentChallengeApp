package com.example.appcentinterviewapp.Parser;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

public class FlickrPhotoReceiver {


        @SerializedName("photos")
        private PhotosReceived photos;

        private String stat;

    FlickrPhotoReceiver(PhotosReceived photos, String stat){

            this.photos = photos;
            this.stat = stat;
        }
       //getter-setter.

    public PhotosReceived getPhotos() {
        return photos;
    }

    public String getStat() {
        return stat;
    }
}