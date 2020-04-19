package com.monosoft.task.ui.location;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Application;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.LifecycleObserver;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.OnLifecycleEvent;

public class LocationViewModel extends AndroidViewModel implements LifecycleObserver {

    private static final long LOCATION_UPDATE_MIN_TIME = 5000L;
    private static final float LOCATION_UPDATE_MIN_DISTANCE = 10f;

    private MutableLiveData<Location> locationLiveData = new MutableLiveData<>();
    private MutableLiveData<String> cityLiveData = new MutableLiveData<>();
    private MutableLiveData<Boolean> isButtonEnabled = new MutableLiveData<>(false);
    private MutableLiveData<Boolean> isProgress = new MutableLiveData<>(false);

    private LocationManager locationManager;

    public LocationViewModel(@NonNull Application application) {
        super(application);
        locationManager = (LocationManager) getApplication().getSystemService(Context.LOCATION_SERVICE);
    }

    public LiveData<Location> getLocation() {
        return locationLiveData;
    }

    public LiveData<String> getCity() {
        return cityLiveData;
    }

    public LiveData<Boolean> isButtonEnabled() {
        return isButtonEnabled;
    }

    public LiveData<Boolean> isProgressBarEnabled() {
        return isProgress;
    }

    @SuppressLint("MissingPermission")
    public void start() {
        isProgress.setValue(true);
        if (checkLocationPermissions()) {
            locationManager.requestLocationUpdates(
                    LocationManager.GPS_PROVIDER,
                    LOCATION_UPDATE_MIN_TIME,
                    LOCATION_UPDATE_MIN_DISTANCE,
                    locationListener);

            locationManager.requestLocationUpdates(
                    LocationManager.NETWORK_PROVIDER,
                    1000 * 10,
                    10,
                    locationListener);
        }
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_STOP)
    public void stop() {
        isProgress.setValue(false);
        locationManager.removeUpdates(locationListener);
    }

    public boolean checkLocationPermissions() {
        return ActivityCompat.checkSelfPermission(
                getApplication(),
                Manifest.permission.ACCESS_COARSE_LOCATION
        ) == PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
                getApplication(),
                Manifest.permission.ACCESS_FINE_LOCATION
        ) == PackageManager.PERMISSION_GRANTED;
    }

    private LocationListener locationListener = new LocationListener() {

        @Override
        public void onLocationChanged(Location location) {
            if (location == null) {
                return;
            }
            locationLiveData.setValue(location);
            isButtonEnabled.setValue(true);
            stop();
        }

        @Override
        public void onStatusChanged(String provider, int status, Bundle extras) {
        }

        @Override
        public void onProviderEnabled(String provider) {
            if (checkLocationPermissions()) {
                @SuppressLint("MissingPermission")
                Location location = locationManager.getLastKnownLocation(provider);
                if (location != null) {
                    locationLiveData.setValue(location);
                    isButtonEnabled.setValue(true);
                    stop();
                }
            }
        }

        @Override
        public void onProviderDisabled(String provider) {
        }
    };

    public TextWatcher getTextWatcher() {
        return new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence str, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence str, int start, int before, int count) {
                if (str.length() > 2) {
                    cityLiveData.setValue(str.toString());
                    isButtonEnabled.setValue(true);
                } else {
                    cityLiveData.setValue("");
                    isButtonEnabled.setValue(false);
                }
            }

            @Override
            public void afterTextChanged(Editable str) {

            }
        };
    }

}
