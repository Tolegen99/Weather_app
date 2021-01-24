package com.tolegensapps.weatherapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.icu.text.DateFormat;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.airbnb.lottie.LottieAnimationView;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ThreadFactory;

import javax.net.ssl.HttpsURLConnection;

public class MainActivity extends AppCompatActivity {

    private static RecyclerView mRecyclerView;
    private static WeatherAdapter mAdapter;
    private static LayoutInflater mLayoutInflater;


    private EditText mEditTextCity;
    private Button mbtnSearch;

    static List<Weather> mWeathers = new ArrayList<>();

    private final String WEATHER_URL =
            "https://api.openweathermap.org/data/2.5/weather?q=%s&appid=fb65e5fbd82b86c13341170565524eea&lang=ru&units=metric";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mLayoutInflater = getLayoutInflater();

        mRecyclerView = findViewById(R.id.recyclerView);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        mEditTextCity = findViewById(R.id.editTextCityName);
        mbtnSearch = findViewById(R.id.btnSearch);



        mbtnSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String city = mEditTextCity.getText().toString();

                DownloadWeatherTask task = new DownloadWeatherTask();
                String url = String.format(WEATHER_URL, city);
                task.execute(url);
            }
        });
    }

    public static void updateUI(Weather weather) {
        mWeathers.add(weather);
        mAdapter = new WeatherAdapter(mWeathers, mLayoutInflater);
        mRecyclerView.setAdapter(mAdapter);

    }

}