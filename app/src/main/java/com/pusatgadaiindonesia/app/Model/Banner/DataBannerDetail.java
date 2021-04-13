package com.pusatgadaiindonesia.app.Model.Banner;

import com.google.gson.annotations.SerializedName;

public class DataBannerDetail {
    @SerializedName("id")
    private String id;

    @SerializedName("name")
    private String name;

    @SerializedName("imgUrl")
    private String imgUrl;

    @SerializedName("imgThumb")
    private String imgThumb;

    @SerializedName("description")
    private String description;

    @SerializedName("type")
    private String type;

    public DataBannerDetail(String id, String name, String imgUrl, String imgThumb, String description, String type) {
        this.id = id;
        this.name = name;
        this.imgUrl = imgUrl;
        this.imgThumb = imgThumb;
        this.description = description;
        this.type = type;
    }

    public String getid() {
        return id;
    }

    public String getname() {
        return name;
    }

    public String getimgUrl() {
        return imgUrl;
    }

    public String getimgThumb() {
        return imgThumb;
    }

    public String getdescription() {
        return description;
    }

    public String getType() {
        return type;
    }

}
