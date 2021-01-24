package com.tolegensapps.weatherapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Locale;

public class WeatherListActivity extends AppCompatActivity {

    private static RecyclerView mRecyclerView;
    private static LayoutInflater mLayoutInflater;
    private static List<Weather> mWeathers = new ArrayList<>();
    private FloatingActionButton mBtnCurrentLocation;
    private final String WEATHER_URL =
            "https://api.openweathermap.org/data/2.5/weather?%s&appid=fb65e5fbd82b86c13341170565524eea&lang=ru&units=metric";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mLayoutInflater = getLayoutInflater();
        mRecyclerView = findViewById(R.id.recyclerView);
        mBtnCurrentLocation = findViewById(R.id.btnCurrentLocation);

        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        hideFloatingButtonOnScroll();

    }

    public void onBtnPressed(View view) {
        if (ActivityCompat.checkSelfPermission(WeatherListActivity.this,
                Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            getLocation();
        } else {
            ActivityCompat.requestPermissions(WeatherListActivity.this,
                    new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 44);
        }
    }


    @SuppressLint("MissingPermission")
    private void getLocation() {
        FusedLocationProviderClient fusedLocationClient = LocationServices.getFusedLocationProviderClient(this);
        fusedLocationClient.getLastLocation().addOnCompleteListener(new OnCompleteListener<Location>() {
            @Override
            public void onComplete(@NonNull Task<Location> task) {
                Location location = task.getResult();
                if (location != null) {
                    try {
                        Geocoder geocoder = new Geocoder(WeatherListActivity.this,
                                Locale.getDefault());
                        List<Address> addresses = geocoder.getFromLocation(
                                location.getLatitude(), location.getLongitude(), 1);

                        String coord = "lat=" + addresses.get(0).getLatitude() +
                                "&lon=" + addresses.get(0).getLongitude();
                        DownloadWeatherTask Dtask = new DownloadWeatherTask();
                        String url = String.format(WEATHER_URL, coord);
                        Dtask.execute(url);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        });
    }

    public static void updateUI(Weather weather) {
        mWeathers.add(weather);
        WeatherAdapter adapter = new WeatherAdapter(mWeathers, mLayoutInflater);
        mRecyclerView.setAdapter(adapter);
    }

    public void hideFloatingButtonOnScroll() {
        mRecyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                if (dy > 0) {
                    mBtnCurrentLocation.hide();
                } else {
                    mBtnCurrentLocation.show();
                }

                super.onScrolled(recyclerView, dx, dy);
            }
        });
    }


}