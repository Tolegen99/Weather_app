package com.tolegensapps.weatherapp;

import java.util.Date;

public class Weather {
    private String mName;
    private Date mTime;
    private Double mTemp;
    private String mDescription;
    private float mTempMin;
    private float mTempMax;
    private int mPressure;
    private int mLottieID;

    public Weather(String name, Double temp) {
        mName = name;
        mTemp = temp;
//        mDescription = description;
//        mTempMin = tempMin;
//        mTempMax = tempMax;
//        mPressure = pressure;
        mTime = new Date();
    }

    public String getName() {
        return mName;
    }

    public void setName(String name) {
        mName = name;
    }

    public Date getTime() {
        return mTime;
    }

    public Double getTemp() {
        return mTemp;
    }

    public String getDescription() {
        return mDescription;
    }

    public float getTempMin() {
        return mTempMin;
    }

    public float getTempMax() {
        return mTempMax;
    }

    public int getPressure() {
        return mPressure;
    }

    public int getLottieID() {
        return mLottieID;
    }

}
