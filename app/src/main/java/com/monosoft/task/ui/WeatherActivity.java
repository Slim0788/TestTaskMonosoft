package com.monosoft.task.ui;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.monosoft.task.R;
import com.monosoft.task.ui.location.LocationFragment;

public class WeatherActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_weather);

        if (savedInstanceState != null) {
            return;
        }

        getSupportFragmentManager()
                .beginTransaction()
                .add(R.id.container, LocationFragment.newInstance())
                .commit();
    }
}