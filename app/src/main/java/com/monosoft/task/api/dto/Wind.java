package com.monosoft.task.api.dto;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Wind {
    @Expose
    @SerializedName("speed")
    private double speed;

    public double getSpeed() {
        return speed;
    }
}
