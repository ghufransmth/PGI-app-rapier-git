package com.pusatgadaiindonesia.app.Model.BayarListrik;

import com.google.gson.annotations.SerializedName;

public class SendListrik {
    @SerializedName("type")
    private String type;

    @SerializedName("customerId")
    private String customerId;

    @SerializedName("price")
    private String price;

    @SerializedName("description")
    private String description;

    public SendListrik(String type, String customerId, String description, String price) {
        this.type = type;
        this.customerId = customerId;
        this.description = description;
        this.price = price;
    }

}
