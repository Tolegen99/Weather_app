package com.tolegensapps.weatherapp;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.tolegensapps.weatherapp.databinding.ItemWeatherBinding;

import java.util.ArrayList;
import java.util.List;
import java.util.zip.Inflater;

public class WeatherAdapter extends RecyclerView.Adapter<WeatherAdapter.WeatherViewHolder> {

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

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(mContext, WeatherDetailActivity.class);
                    mContext.startActivity(intent);
                }
            });
        }
    }
}