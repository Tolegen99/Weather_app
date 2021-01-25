package com.tolegensapps.weatherapp;

import android.annotation.SuppressLint;
import android.content.Context;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.os.AsyncTask;
import android.view.LayoutInflater;

import androidx.annotation.NonNull;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Date;
import java.util.List;
import java.util.Locale;


import javax.net.ssl.HttpsURLConnection;

public class DownloadWeatherTask extends AsyncTask<String, Void, String> {

    private static Context mContext;

    public DownloadWeatherTask(Context context) {
        mContext = context;
    }

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
            Double tempMin = jsonObject.getJSONObject("main").getDouble("temp_min");
            Double tempMax = jsonObject.getJSONObject("main").getDouble("temp_max");
            int pressure = jsonObject.getJSONObject("main").getInt("pressure");
            WeatherDatabaseHelper myDB = new WeatherDatabaseHelper(mContext);
            myDB.addWeather(city, new Date(), temp, tempMin, tempMax, pressure);
            WeatherListActivity.clearDataFromArray();
            WeatherListActivity.storeDataInArrays();
            WeatherListActivity.updateUI(mContext);

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    @SuppressLint("MissingPermission")
    static void getLocation(Context context) {
        FusedLocationProviderClient fusedLocationClient = LocationServices.getFusedLocationProviderClient(context);
        fusedLocationClient.getLastLocation().addOnCompleteListener(new OnCompleteListener<Location>() {
            @Override
            public void onComplete(@NonNull Task<Location> task) {
                Location location = task.getResult();
                if (location != null) {
                    try {
                        Geocoder geocoder = new Geocoder(context,
                                Locale.getDefault());
                        List<Address> addresses = geocoder.getFromLocation(
                                location.getLatitude(), location.getLongitude(), 1);

                        String coord = "lat=" + addresses.get(0).getLatitude() +
                                "&lon=" + addresses.get(0).getLongitude();
                        DownloadWeatherTask Dtask = new DownloadWeatherTask(context);
                        String url = String.format(WeatherListActivity.WEATHER_URL, coord);
                        Dtask.execute(url);

                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        });
    }
}