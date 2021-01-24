package com.tolegensapps.weatherapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


import android.Manifest;
import android.annotation.SuppressLint;
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
import java.util.List;
import java.util.Locale;

public class MainActivity extends AppCompatActivity {

    private String mCoord;

    private static RecyclerView mRecyclerView;
    private static WeatherAdapter mAdapter;
    private static LayoutInflater mLayoutInflater;

    private FusedLocationProviderClient mFusedLocationClient;

    private EditText mEditTextCity;
    private Button mbtnSearch;
    private FloatingActionButton mBtnCurrentLocation;

    static List<Weather> mWeathers = new ArrayList<>();

    private final String WEATHER_URL =
            "https://api.openweathermap.org/data/2.5/weather?%s&appid=fb65e5fbd82b86c13341170565524eea&lang=ru&units=metric";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mLayoutInflater = getLayoutInflater();

        mRecyclerView = findViewById(R.id.recyclerView);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
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

        mEditTextCity = findViewById(R.id.editTextCityName);
        mbtnSearch = findViewById(R.id.btnSearch);
        mBtnCurrentLocation = findViewById(R.id.btnCurrentLocation);

        mbtnSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String city = mEditTextCity.getText().toString();

                DownloadWeatherTask task = new DownloadWeatherTask();
                String url = String.format(WEATHER_URL, "q=" + city);
                task.execute(url);
            }
        });

        mFusedLocationClient = LocationServices.getFusedLocationProviderClient(this);

        mBtnCurrentLocation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (ActivityCompat.checkSelfPermission(MainActivity.this,
                        Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
                    getLocation();
                } else {
                    ActivityCompat.requestPermissions(MainActivity.this,
                            new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 44);
                }
            }
        });
    }

    @SuppressLint("MissingPermission")
    private void getLocation() {
        mFusedLocationClient.getLastLocation().addOnCompleteListener(new OnCompleteListener<Location>() {
            @Override
            public void onComplete(@NonNull Task<Location> task) {
                Location location = task.getResult();
                if(location != null) {
                    try {
                        Geocoder geocoder = new Geocoder(MainActivity.this,
                                Locale.getDefault());
                        List<Address> addresses = geocoder.getFromLocation(
                                location.getLatitude(), location.getLongitude(), 1);

                        mCoord =  "lat=" + addresses.get(0).getLatitude() +
                                "&lon=" + addresses.get(0).getLongitude();
                        DownloadWeatherTask Dtask = new DownloadWeatherTask();
                        String url = String.format(WEATHER_URL, mCoord);
                        Log.d("result", "onClick: " + url);
                        Log.d("result", "onClick: " + mCoord);
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
        mAdapter = new WeatherAdapter(mWeathers, mLayoutInflater);
        mRecyclerView.setAdapter(mAdapter);

    }

}