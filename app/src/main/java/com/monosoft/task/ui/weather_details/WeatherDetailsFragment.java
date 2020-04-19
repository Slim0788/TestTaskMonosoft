package com.monosoft.task.ui.weather_details;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.bumptech.glide.Glide;
import com.monosoft.task.R;
import com.monosoft.task.model.WeatherData;

public class WeatherDetailsFragment extends Fragment {

    private static final String ARG_LATITUDE = "latitude";
    private static final String ARG_LONGITUDE = "longitude";
    private static final String ARG_CITY = "city";

    private TextView cityTextView;
    private ImageView weatherIcon;
    private TextView temperatureTextView;
    private TextView windTextView;
    private TextView humidityTextView;
    private TextView pressureTextView;
    private ProgressBar progressBar;

    private WeatherDetailsViewModel viewModel;

    public static WeatherDetailsFragment newInstance(double latitude, double longitude) {
        WeatherDetailsFragment fragment = new WeatherDetailsFragment();
        Bundle args = new Bundle();
        args.putDouble(ARG_LATITUDE, latitude);
        args.putDouble(ARG_LONGITUDE, longitude);
        fragment.setArguments(args);
        return fragment;
    }

    public static WeatherDetailsFragment newInstance(String city) {
        WeatherDetailsFragment fragment = new WeatherDetailsFragment();
        Bundle args = new Bundle();
        args.putString(ARG_CITY, city);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.weather_details_fragment, container, false);
        progressBar = root.findViewById(R.id.progressBar);
        cityTextView = root.findViewById(R.id.tv_city);
        weatherIcon = root.findViewById(R.id.iv_image);
        temperatureTextView = root.findViewById(R.id.tv_temperature);
        windTextView = root.findViewById(R.id.tv_wind);
        humidityTextView = root.findViewById(R.id.tv_humidity);
        pressureTextView = root.findViewById(R.id.tv_pressure);
        return root;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        viewModel = new ViewModelProvider(this, new WeatherDetailsViewModelFactory())
                .get(WeatherDetailsViewModel.class);

        progressBar.setVisibility(View.VISIBLE);

        Bundle args = getArguments();
        if (args != null) {
            if (args.getString(ARG_CITY) != null) {
                String city = args.getString(ARG_CITY);
                progressBar.setVisibility(View.VISIBLE);
                viewModel.getWeather(city);
            } else {
                double latitude = args.getDouble(ARG_LATITUDE);
                double longitude = args.getDouble(ARG_LONGITUDE);
                progressBar.setVisibility(View.VISIBLE);
                viewModel.getWeather(latitude, longitude);
            }
        }

        viewModel.getWeatherData().observe(getViewLifecycleOwner(), this::updateUI);

    }

    private void updateUI(WeatherData weatherData) {
        progressBar.setVisibility(View.GONE);
        cityTextView.setText(weatherData.getCity());
        temperatureTextView.setText(String.valueOf(weatherData.getTemperature()));
        windTextView.setText(String.valueOf(weatherData.getWind()));
        humidityTextView.setText(String.valueOf(weatherData.getHumidity()));
        pressureTextView.setText(String.valueOf(weatherData.getPressure()));

        Glide.with(weatherIcon)
                .load("https://openweathermap.org/img/wn/" + weatherData.getWeatherIcon() + ".png")
                .into(weatherIcon);
    }

}