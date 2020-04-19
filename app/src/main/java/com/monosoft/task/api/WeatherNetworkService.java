package com.monosoft.task.api;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class WeatherNetworkService {

    private static final String BASE_URL = "https://api.openweathermap.org/";

    private static WeatherNetworkService instance;

    private Retrofit retrofit;

    private WeatherNetworkService() {
        retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
    }

    public static WeatherNetworkService getInstance() {
        if (instance == null) {
            instance = new WeatherNetworkService();
        }
        return instance;
    }

    public OpenWeatherApi getAuthApi() {
        return retrofit.create(OpenWeatherApi.class);
    }

}
