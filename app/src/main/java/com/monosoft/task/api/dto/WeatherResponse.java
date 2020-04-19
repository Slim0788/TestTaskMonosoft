package com.monosoft.task.api.dto;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class WeatherResponse {
    @Expose
    @SerializedName("name")
    private String name;
    @Expose
    @SerializedName("wind")
    private Wind wind;
    @Expose
    @SerializedName("main")
    private Main main;
    @Expose
    @SerializedName("weather")
    private List<Weather> weather;

    public String getName() {
        return name;
    }

    public Wind getWind() {
        return wind;
    }

    public Main getMain() {
        return main;
    }

    public List<Weather> getWeather() {
        return weather;
    }
}
