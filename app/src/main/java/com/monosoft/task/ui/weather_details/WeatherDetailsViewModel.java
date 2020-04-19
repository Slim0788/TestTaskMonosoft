package com.monosoft.task.ui.weather_details;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.monosoft.task.api.OpenWeatherApi;
import com.monosoft.task.api.dto.WeatherResponse;
import com.monosoft.task.model.WeatherData;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class WeatherDetailsViewModel extends ViewModel {

    private MutableLiveData<WeatherData> weatherData = new MutableLiveData<>();

    OpenWeatherApi openWeatherApi;

    public WeatherDetailsViewModel(OpenWeatherApi openWeatherApi) {
        this.openWeatherApi = openWeatherApi;
    }

    public LiveData<WeatherData> getWeatherData() {
        return weatherData;
    }

    public void getWeather(String city) {
        openWeatherApi.getWeatherByCity(city, "metric", "82bf1ae11d0d79169c84285f5ffb9057")
                .enqueue(new Callback<WeatherResponse>() {
                    @Override
                    public void onResponse(@NonNull Call<WeatherResponse> call, @NonNull Response<WeatherResponse> response) {
                        if (response.isSuccessful()) {
                            WeatherResponse weatherResponse = response.body();
                            if (weatherResponse != null) {
                                parsWeatherData(weatherResponse);
                            }
                        }
                    }

                    @Override
                    public void onFailure(@NonNull Call<WeatherResponse> call, @NonNull Throwable t) {

                    }
                });
    }

    public void getWeather(double latitude, double longitude) {
        openWeatherApi.getWeatherByCoordinates(latitude, longitude, "metric", "82bf1ae11d0d79169c84285f5ffb9057")
                .enqueue(new Callback<WeatherResponse>() {
                    @Override
                    public void onResponse(@NonNull Call<WeatherResponse> call, @NonNull Response<WeatherResponse> response) {
                        if (response.isSuccessful()) {
                            WeatherResponse weatherResponse = response.body();
                            if (weatherResponse != null) {
                                parsWeatherData(weatherResponse);
                            }
                        }
                    }

                    @Override
                    public void onFailure(@NonNull Call<WeatherResponse> call, @NonNull Throwable t) {

                    }
                });
    }

    private void parsWeatherData(WeatherResponse weatherResponse) {
        WeatherData data = new WeatherData(
                weatherResponse.getName(),
                weatherResponse.getWeather().get(0).getIcon(),
                weatherResponse.getMain().getTemp(),
                weatherResponse.getWind().getSpeed(),
                weatherResponse.getMain().getHumidity(),
                weatherResponse.getMain().getPressure()
        );
        weatherData.setValue(data);
    }
}