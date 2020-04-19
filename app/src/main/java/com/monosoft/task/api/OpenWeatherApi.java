package com.monosoft.task.api;

import com.monosoft.task.api.dto.WeatherResponse;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface OpenWeatherApi {
    @GET("data/2.5/weather?")
    Call<WeatherResponse> getWeatherByCity(
            @Query("q") String city,
            @Query("units") String units,
            @Query("APPID") String app_id
    );

    @GET("data/2.5/weather?")
    Call<WeatherResponse> getWeatherByCoordinates(
            @Query("lat") double latitude,
            @Query("lon") double longitude,
            @Query("units") String units,
            @Query("APPID") String app_id
    );
}
