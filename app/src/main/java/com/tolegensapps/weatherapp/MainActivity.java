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

    private EditText mEditTextCity;
    private Button mbtnSearch;

    static List<Weather> mWeathers = new ArrayList<>();

    private final String WEATHER_URL =
            "https://api.openweathermap.org/data/2.5/weather?q=%s&appid=fb65e5fbd82b86c13341170565524eea&lang=ru&units=metric";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

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

    public void updateUI(Weather weather) {
        mWeathers.add(weather);
        mAdapter = new WeatherAdapter(mWeathers);
        mRecyclerView.setAdapter(mAdapter);

    }

    private class DownloadWeatherTask extends AsyncTask<String, Void, String> {

        Weather mWeather;

        @Override
        protected String doInBackground(String... strings) {
            URL url = null;
            HttpsURLConnection urlConnection = null;
            StringBuilder result = new StringBuilder();
            try {
                url = new URL(strings[0]);
                urlConnection = (HttpsURLConnection) url.openConnection();
                InputStream inputStream = urlConnection.getInputStream();
                InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
                BufferedReader reader = new BufferedReader(inputStreamReader);
                String line = reader.readLine();
                while (line != null) {
                    result.append(line);
                    line = reader.readLine();
                }
                return result.toString();
            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                if (urlConnection != null) {
                    urlConnection.disconnect();
                }
            }
            return null;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            try {
                JSONObject jsonObject = new JSONObject(s);
                String city = jsonObject.getString("name");
                Double temp = jsonObject.getJSONObject("main").getDouble("temp");
                mWeather = new Weather(city, temp);
                updateUI(mWeather);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }


    private class WeatherHolder extends RecyclerView.ViewHolder {

        private Weather mWeather;

        private TextView mFieldName;
        private TextView mFieldTemp;
        private TextView mFieldTime;
        private LottieAnimationView mLottieView;

        public WeatherHolder(LayoutInflater layoutInflater, ViewGroup parent) {
            super(layoutInflater.inflate(R.layout.item_weather, parent, false));
            mFieldName = itemView.findViewById(R.id.fieldName);
            mFieldTemp = itemView.findViewById(R.id.fieldTemp);
            mFieldTime = itemView.findViewById(R.id.fieldTime);
            mLottieView = (LottieAnimationView) itemView.findViewById(R.id.lottie_view);

        }

        public void bind(Weather weather) {
            mWeather = weather;

            mFieldName.setText(mWeather.getName());
            mFieldTemp.setText(String.valueOf(mWeather.getTemp()));
            mFieldTime.setText(String.valueOf(mWeather.getTime()));
            mLottieView.setAnimation(R.raw.storm);
        }
    }

    private class WeatherAdapter extends RecyclerView.Adapter<WeatherHolder> {

        private List<Weather> mWeathers;

        public WeatherAdapter(List<Weather> weathers) {
            mWeathers = weathers;
        }


        @NonNull
        @Override
        public WeatherHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            return new WeatherHolder(getLayoutInflater(), parent);
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
    }

}