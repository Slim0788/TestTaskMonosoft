package com.monosoft.task.api.dto;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Main {
    @Expose
    @SerializedName("humidity")
    private int humidity;
    @Expose
    @SerializedName("pressure")
    private int pressure;
    @Expose
    @SerializedName("temp")
    private double temp;

    public int getHumidity() {
        return humidity;
    }

    public int getPressure() {
        return pressure;
    }

    public double getTemp() {
        return temp;
    }
}
