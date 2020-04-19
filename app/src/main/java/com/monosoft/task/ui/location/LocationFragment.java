package com.monosoft.task.ui.location;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.monosoft.task.R;
import com.monosoft.task.ui.weather_details.WeatherDetailsFragment;

public class LocationFragment extends Fragment {

    private static final int LOCATION_PERMISSION_RC = 100;
    private static final String[] LOCATION_PERMISSIONS =
            new String[]{
                    Manifest.permission.ACCESS_COARSE_LOCATION,
                    Manifest.permission.ACCESS_FINE_LOCATION
            };

    private MaterialButton locationButton;
    private MaterialButton weatherButton;
    private ProgressBar locationProgressBar;
    private TextInputLayout cityInputLayout;
    private TextInputEditText cityEditText;

    private double latitude;
    private double longitude;
    private String city;

    private LocationViewModel viewModel;

    public static LocationFragment newInstance() {
        return new LocationFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.location_fragment, container, false);
        locationButton = root.findViewById(R.id.btn_location);
        weatherButton = root.findViewById(R.id.btn_weather);
        locationProgressBar = root.findViewById(R.id.progressBar_location);
        cityInputLayout = root.findViewById(R.id.til_city);
        cityEditText = root.findViewById(R.id.et_city);
        return root;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        viewModel = new ViewModelProvider(this).get(LocationViewModel.class);
        getLifecycle().addObserver(viewModel);

        setObservers();
        setListeners();
    }

    private void setObservers() {
        viewModel.getLocation().observe(getViewLifecycleOwner(), location -> {
            latitude = location.getLatitude();
            longitude = location.getLongitude();
        });

        viewModel.getCity().observe(getViewLifecycleOwner(), cityLD -> city = cityLD);

        viewModel.isButtonEnabled().observe(getViewLifecycleOwner(), isButtonEnabled ->
                weatherButton.setEnabled(isButtonEnabled));

        viewModel.isProgressBarEnabled().observe(getViewLifecycleOwner(), isEnabled ->
                locationProgressBar.setVisibility(isEnabled ? View.VISIBLE : View.GONE));
    }

    private void setListeners() {
        cityEditText.addTextChangedListener(viewModel.getTextWatcher());

        locationButton.setOnClickListener(v -> {
            if (!viewModel.checkLocationPermissions()) {
                requestPermissions(LOCATION_PERMISSIONS, LOCATION_PERMISSION_RC);
            } else {
                viewModel.start();
            }
        });

        weatherButton.setOnClickListener(v -> goToWeatherDetails());
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode == LOCATION_PERMISSION_RC) {
            for (int i = 0, len = permissions.length; i < len; i++) {
                if (grantResults[i] == PackageManager.PERMISSION_GRANTED) {
                    viewModel.start();
                } else if (grantResults[i] == PackageManager.PERMISSION_DENIED) {

                    boolean showRationale = shouldShowRequestPermissionRationale(permissions[i]);
                    if (!showRationale) {
                        // user also CHECKED "never ask again"
                        // you can either enable some fall back,
                        // disable features of your app
                        // or open another dialog explaining
                        // again the permission and directing to
                        // the app setting
                    } else if (Manifest.permission.ACCESS_COARSE_LOCATION.equals(permissions[i])) {
                        // user did NOT check "never ask again"
                        // this is a good place to explain the user
                        // why you need the permission and ask if he wants
                        // to accept it (the rationale)
                    } else if (Manifest.permission.ACCESS_FINE_LOCATION.equals(permissions[i])) {
                        // ...
                    }
                }
            }

            if (grantResults.length != 0 &&
                    grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                viewModel.start();
            }
        }
    }

    private void goToWeatherDetails() {
        WeatherDetailsFragment fragment;
        if (latitude != 0 && longitude != 0) {
            fragment = WeatherDetailsFragment.newInstance(latitude, longitude);
        } else {
            fragment = WeatherDetailsFragment.newInstance(city);
        }
        getParentFragmentManager().beginTransaction()
                .addToBackStack(null)
                .replace(R.id.container, fragment)
                .commit();
    }

}