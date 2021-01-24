package com.tolegensapps.weatherapp;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.tolegensapps.weatherapp.databinding.ItemWeatherBinding;

import java.util.List;

public class WeatherAdapter extends RecyclerView.Adapter<WeatherAdapter.WeatherViewHolder> {

    private List<Weather> mWeathers;
    private LayoutInflater mLayoutInflater;


    public WeatherAdapter(List<Weather> weathers) {
        mWeathers = weathers;
    }


    @NonNull
    @Override
    public WeatherViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        mLayoutInflater = LayoutInflater.from(parent.getContext());
        ItemWeatherBinding itemWeatherBinding = ItemWeatherBinding.inflate(mLayoutInflater, parent, false);
        return new WeatherViewHolder(itemWeatherBinding);
    }

    @Override
    public void onBindViewHolder(@NonNull WeatherViewHolder holder, int position) {
        Weather weather = mWeathers.get(position);
        holder.mItemWeatherBinding.setWeather(weather);
        holder.mItemWeatherBinding.executePendingBindings();
    }


    @Override
    public int getItemCount() {
        return mWeathers.size();
    }


    public class WeatherViewHolder extends RecyclerView.ViewHolder {

        ItemWeatherBinding mItemWeatherBinding;

        public WeatherViewHolder(@NonNull ItemWeatherBinding itemWeatherBinding) {
            super(itemWeatherBinding.getRoot());
            mItemWeatherBinding = itemWeatherBinding;
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(mLayoutInflater.getContext(), WeatherDetailActivity.class);
                    mLayoutInflater.getContext().startActivity(intent);
                }
            });
        }
    }
}