package com.tolegensapps.weatherapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.airbnb.lottie.LottieAnimationView;

import java.util.ArrayList;

public class WeatherDetailActivity extends AppCompatActivity {

    private String mCityName, mTime, mTemperature, mTempMin, mTempMax, mPressure;
    private TextView mFieldName, mFieldTime, mFieldTemp, mFieldTempMin, mFieldTempMax, mFieldPressure;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_weather_detail);

        getExtraFromIntent();
        initUI();
        bind();
    }

    private void getExtraFromIntent() {
        mCityName = getIntent().getStringExtra(WeatherAdapter.EXTRA_NAME);
        mTime = getIntent().getStringExtra(WeatherAdapter.EXTRA_TIME);
        mTemperature = getIntent().getStringExtra(WeatherAdapter.EXTRA_TEMP);
        mTempMin = getIntent().getStringExtra(WeatherAdapter.EXTRA_TEMP_MIN);
        mTempMax = getIntent().getStringExtra(WeatherAdapter.EXTRA_TEMP_MAX);
        mPressure = getIntent().getStringExtra(WeatherAdapter.EXTRA_PRESSURE);
    }

    private void initUI() {
        mFieldName = findViewById(R.id.field_name);
        mFieldTime = findViewById(R.id.fieldTime);
        mFieldTemp = findViewById(R.id.fieldTemp);
        mFieldTempMin = findViewById(R.id.fiedTempMin);
        mFieldTempMax = findViewById(R.id.fieldTempMax);
        mFieldPressure = findViewById(R.id.fieldPressure);

    }

    private void bind() {
        mFieldName.setText(mCityName);
        mFieldTime.setText(mTime);
        mFieldTemp.setText(mTemperature);
        mFieldTempMin.setText(mTempMin);
        mFieldTempMax.setText(mTempMax);
        mFieldPressure.setText(mPressure);

    }

    public void onBack(View view) {
        onBackPressed();
    }
}