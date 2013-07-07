package com.tlenclos.weatherforecast;

import java.io.BufferedReader;
import java.io.InputStreamReader;

import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.os.AsyncTask;
import android.util.Log;

public class WeatherWebservice extends AsyncTask<Void, Void, Weather> {
	MainActivity activityRef;
	String apiUrl = "http://api.openweathermap.org/data/2.1/find/city?&lat=48.853409&lon=2.3488&cnt=1&APPID=5d2eef1e303470228dcf653b4f989499";
	
	public WeatherWebservice(MainActivity activityRef) {
		this.activityRef = activityRef;
	}
	
	@Override
	protected Weather doInBackground(Void... arg0) {
		Log.d("AppWeather", "Starting Async Weather");
		
		DefaultHttpClient httpClient = new DefaultHttpClient();
		HttpGet request = new HttpGet(apiUrl);
		StringBuilder builder;
		
		// Get Response
		try {
			HttpResponse response = httpClient.execute(request);
			BufferedReader reader = new BufferedReader(new InputStreamReader(response.getEntity().getContent(), "UTF-8"));
			builder = new StringBuilder();
			for (String line = null; (line = reader.readLine()) != null;) {
				builder.append(line).append("\n");
			}
		} catch (Exception e) {
			Log.d("AppWeather", e.toString());
            return null;
        }
		
		String response = builder.toString();
		Weather dayWeather = new Weather();
		
		// Parse JSON
		JSONObject jsonObject = null;
		JSONArray list = null;
		try {
			jsonObject = new JSONObject(response);
			list = jsonObject.getJSONArray("list");
			
			if (list.length() > 0) {
				JSONObject weatherData = list.getJSONObject(0);
				dayWeather.place = weatherData.getString("name");
				dayWeather.temperature = weatherData.getJSONObject("main").getDouble("temp");
				dayWeather.humidity = weatherData.getJSONObject("main").getDouble("humidity");
				dayWeather.pressure = weatherData.getJSONObject("main").getInt("pressure");
				dayWeather.windSpeed = weatherData.getJSONObject("wind").getDouble("speed");
				dayWeather.iconName = weatherData.getJSONArray("weather").getJSONObject(0).getString("icon")+".jpg";
			}
			
		} catch (JSONException e) {
			e.printStackTrace();
		}
		
		return dayWeather;
	}
	
	protected void onPostExecute(Weather result) {
		activityRef.updateWeatherData(result); // TODO : Ckeck to see if the activity is still available.
	}
}
