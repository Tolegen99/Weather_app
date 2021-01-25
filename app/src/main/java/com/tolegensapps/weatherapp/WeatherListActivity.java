package com.tolegensapps.weatherapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;


import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class WeatherListActivity extends AppCompatActivity {

    private static RecyclerView mRecyclerView;
    private static LayoutInflater mLayoutInflater;
    private static List<Weather> mWeathers = new ArrayList<>();
    private FloatingActionButton mBtnCurrentLocation;
    private static WeatherDatabaseHelper mMyDB;
    private static ArrayList<String> mId, mCityName, mTime, mTemperature, mTempMin, mTempMax, mPressure;
    private static WeatherAdapter mAdapter;

    private final String WEATHER_URL =
            "https://api.openweathermap.org/data/2.5/weather?%s&appid=fb65e5fbd82b86c13341170565524eea&lang=ru&units=metric";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_weather_list);

        mLayoutInflater = getLayoutInflater();
        mRecyclerView = findViewById(R.id.recyclerView);
        mBtnCurrentLocation = findViewById(R.id.btnCurrentLocation);

        mMyDB = new WeatherDatabaseHelper(WeatherListActivity.this);
        mId = new ArrayList<>();
        mCityName = new ArrayList<>();
        mTime = new ArrayList<>();
        mTemperature = new ArrayList<>();
        mTempMin = new ArrayList<>();
        mTempMax = new ArrayList<>();
        mPressure = new ArrayList<>();

        storeDataInArrays();
        updateUI(this);

        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        hideFloatingButtonOnScroll();

    }

    static void clearDataFromArray() {
        mId.clear();
        mCityName.clear();
        mTime.clear();
        mTemperature.clear();
        mTempMin.clear();
        mTempMax.clear();
        mPressure.clear();
    }

    static void storeDataInArrays() {
        Cursor cursor = mMyDB.readAllData();
        while (cursor.moveToNext()) {
            mId.add(cursor.getString(0));
            mCityName.add(cursor.getString(1));
            mTime.add(cursor.getString(2));
            mTemperature.add(cursor.getString(3));
            mTempMin.add(cursor.getString(4));
            mTempMax.add(cursor.getString(5));
            mPressure.add(cursor.getString(6));

        }
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
                        DownloadWeatherTask Dtask = new DownloadWeatherTask(WeatherListActivity.this);
                        String url = String.format(WEATHER_URL, coord);
                        Dtask.execute(url);

                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        });
    }

    public static void updateUI(Context context) {
        mAdapter = new WeatherAdapter(context, mId, mCityName, mTime,
                mTemperature, mTempMin, mTempMax, mPressure);
        mRecyclerView.setAdapter(mAdapter);
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