package com.monosoft.task.ui.weather_details;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.monosoft.task.api.OpenWeatherApi;
import com.monosoft.task.api.WeatherNetworkService;

public class WeatherDetailsViewModelFactory implements ViewModelProvider.Factory {

    @NonNull
    @Override
    @SuppressWarnings("unchecked")
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        if (modelClass.isAssignableFrom(WeatherDetailsViewModel.class)) {
            return (T) new WeatherDetailsViewModel(getOpenWeatherApi());
        } else {
            throw new IllegalArgumentException("Unknown ViewModel class");
        }
    }

    private OpenWeatherApi getOpenWeatherApi() {
        return WeatherNetworkService.getInstance().getAuthApi();
    }
}