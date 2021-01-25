package com.tolegensapps.weatherapp;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Weather {

    private String mCityName;
    private Date mTime;
    private Double mTemp;
    private Double mTempMin;
    private Double mTempMax;
    private int mPressure;

    public Weather(String cityName, Double temp, Double tempMin, Double tempMax, int pressure) {
        mCityName = cityName;
        mTemp = temp;
        mTempMin = tempMin;
        mTempMax = tempMax;
        mPressure = pressure;
        mTime = new Date();
    }

    public String getCityName() {
        return mCityName;
    }

    public void setCityName(String cityName) {
        mCityName = cityName;
    }

    public Date getTime() {
        return mTime;
    }

    public Double getTemp() {
        return mTemp;
    }

    public Double getTempMin() {
        return mTempMin;
    }

    public Double getTempMax() {
        return mTempMax;
    }

    public int getPressure() {
        return mPressure;
    }


}
