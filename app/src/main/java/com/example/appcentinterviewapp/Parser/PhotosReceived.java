package com.example.appcentinterviewapp.Parser;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class PhotosReceived {

    private int page;
    private String pages;
    private int perpage;
    private String total;

    @SerializedName("photo")
    private List<InsidePhoto> photo;

    public PhotosReceived(int page, String pages, int perpage, String total, List<InsidePhoto> photo) {
        this.page = page;
        this.pages = pages;
        this.perpage = perpage;
        this.total = total;
        this.photo = photo;
    }

    public int getPage() {
        return page;
    }

    public String getPages() {
        return pages;
    }

    public int getPerpage() {
        return perpage;
    }

    public String getTotal() {
        return total;
    }

    public List<InsidePhoto> getPhoto() {
        return photo;
    }
}