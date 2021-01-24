package com.tolegensapps.weatherapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.airbnb.lottie.LottieAnimationView;

public class WeatherDetailActivity extends AppCompatActivity {

    private TextView mFieldName, mFieldTemp, mFieldTempMin, mFieldTempMax, mFieldPressure, mFieldTime;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_weather_detail);

        mFieldName = findViewById(R.id.fieldName);
        mFieldTemp = findViewById(R.id.fieldTemp);
        mFieldTempMin = findViewById(R.id.fiedTempMin);
        mFieldTempMax = findViewById(R.id.fieldTempMax);
        mFieldPressure = findViewById(R.id.fieldPressure);
        mFieldTime = findViewById(R.id.fieldTime);
    }

    public void onBack(View view) {
        onBackPressed();
    }
}