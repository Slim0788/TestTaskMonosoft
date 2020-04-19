package com.monosoft.task.model;

public class WeatherData {

    private String city;
    private String weatherIcon;
    private double temperature;
    private double wind;
    private int humidity;
    private int pressure;

    public WeatherData(String city, String weatherIcon, double temperature, double wind, int humidity, int pressure) {
        this.city = city;
        this.weatherIcon = weatherIcon;
        this.temperature = temperature;
        this.wind = wind;
        this.humidity = humidity;
        this.pressure = pressure;
    }

    public String getCity() {
        return city;
    }

    public String getWeatherIcon() {
        return weatherIcon;
    }

    public double getTemperature() {
        return temperature;
    }

    public double getWind() {
        return wind;
    }

    public int getHumidity() {
        return humidity;
    }

    public int getPressure() {
        return pressure;
    }
}
