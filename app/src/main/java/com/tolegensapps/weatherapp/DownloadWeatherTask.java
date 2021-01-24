package com.tolegensapps.weatherapp;

import android.os.AsyncTask;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;

import javax.net.ssl.HttpsURLConnection;

public class DownloadWeatherTask extends AsyncTask<String, Void, String> {

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
            MainActivity.updateUI(mWeather);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}