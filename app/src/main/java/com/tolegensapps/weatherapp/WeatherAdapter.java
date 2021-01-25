package com.tolegensapps.weatherapp;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class WeatherAdapter extends RecyclerView.Adapter<WeatherAdapter.WeatherViewHolder> {

    public static final String EXTRA_ID = "_id";
    public static final String EXTRA_NAME = "city_name";
    public static final String EXTRA_TIME = "time";
    public static final String EXTRA_TEMP = "temperature";
    public static final String EXTRA_TEMP_MIN = "temp_min";
    public static final String EXTRA_TEMP_MAX = "temp_max";
    public static final String EXTRA_PRESSURE = "pressure";

    private Context mContext;
    private ArrayList mId, mCityName, mTime, mTemperature, mTempMin, mTempMax, mPressure;

    public WeatherAdapter(Context context,
                          ArrayList id, ArrayList cityName, ArrayList time, ArrayList temperature,
                          ArrayList tempMin, ArrayList tempMax, ArrayList pressure) {
        mContext = context;
        mId = id;
        mCityName = cityName;
        mTime = time;
        mTemperature = temperature;
        mTempMin = tempMin;
        mTempMax = tempMax;
        mPressure = pressure;
    }

    @NonNull
    @Override
    public WeatherViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(mContext);
        View view = inflater.inflate(R.layout.item_weather, parent, false);
        return new WeatherViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull WeatherViewHolder holder, int position) {
        holder.mFieldName.setText(String.valueOf(mCityName.get(position)));
        holder.mFieldTemp.setText(String.valueOf(mTemperature.get(position)));
        holder.mFieldTime.setText(String.valueOf(mTime.get(position)));
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(mContext, WeatherDetailActivity.class);
                intent.putExtra(EXTRA_NAME, String.valueOf(mCityName.get(position)));
                intent.putExtra(EXTRA_TIME, String.valueOf(mTime.get(position)));
                intent.putExtra(EXTRA_TEMP, String.valueOf(mTemperature.get(position)));
                intent.putExtra(EXTRA_TEMP_MIN, String.valueOf(mTempMin.get(position)));
                intent.putExtra(EXTRA_TEMP_MAX, String.valueOf(mTempMax.get(position)));
                intent.putExtra(EXTRA_PRESSURE, String.valueOf(mPressure.get(position)));
                mContext.startActivity(intent);
            }
        });

    }

    @Override
    public int getItemCount() {
        return mId.size();
    }

    public class WeatherViewHolder extends RecyclerView.ViewHolder {

        private TextView mFieldName, mFieldTemp, mFieldTime;

        public WeatherViewHolder(@NonNull View itemView) {
            super(itemView);

            mFieldName = itemView.findViewById(R.id.fieldName);
            mFieldTemp = itemView.findViewById(R.id.fieldTemp);
            mFieldTime = itemView.findViewById(R.id.fieldTime);

        }
    }
}