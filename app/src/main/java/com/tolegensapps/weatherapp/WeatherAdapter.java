package com.tolegensapps.weatherapp;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.airbnb.lottie.LottieAnimationView;

import java.util.List;

public class WeatherAdapter extends RecyclerView.Adapter<WeatherAdapter.WeatherHolder> {

    private List<Weather> mWeathers;
    private LayoutInflater mLayoutInflater;

    public WeatherAdapter(List<Weather> weathers, LayoutInflater layoutInflater) {
        mWeathers = weathers;
        mLayoutInflater = layoutInflater;
    }


    @NonNull
    @Override
    public WeatherHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new WeatherHolder(parent);
    }

    @Override
    public void onBindViewHolder(@NonNull WeatherHolder holder, int position) {
        Weather weather = mWeathers.get(position);
        holder.bind(weather);
    }


    @Override
    public int getItemCount() {
        return mWeathers.size();
    }


    public class WeatherHolder extends RecyclerView.ViewHolder {

        private Weather mWeather;

        private TextView mFieldName;
        private TextView mFieldTemp;
        private TextView mFieldTime;
        private LottieAnimationView mLottieView;

        public WeatherHolder(ViewGroup parent) {
            super(mLayoutInflater.inflate(R.layout.item_weather, parent, false));
            mFieldName = itemView.findViewById(R.id.fieldName);
            mFieldTemp = itemView.findViewById(R.id.fieldTemp);
            mFieldTime = itemView.findViewById(R.id.fieldTime);
            mLottieView = (LottieAnimationView) itemView.findViewById(R.id.lottie_view);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(mLayoutInflater.getContext(), WeatherDetailActivity.class);
                    mLayoutInflater.getContext().startActivity(intent);
                }
            });
        }


        public void bind(Weather weather) {
            mWeather = weather;

            mFieldName.setText(mWeather.getName());
            mFieldTemp.setText(String.valueOf(mWeather.getTemp()));
            mFieldTime.setText(String.valueOf(mWeather.getTime()));
            mLottieView.setAnimation(R.raw.partly_cloudy);
        }
    }
}