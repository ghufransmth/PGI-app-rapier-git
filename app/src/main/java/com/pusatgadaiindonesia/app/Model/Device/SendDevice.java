package com.pusatgadaiindonesia.app.Model.Device;

import com.google.gson.annotations.SerializedName;

public class SendDevice {

    @SerializedName("deviceId")
    private String deviceId;

    public SendDevice(String deviceId) {
        this.deviceId = deviceId;
    }

}
