package com.tlenclos.weatherforecast;

import java.io.BufferedReader;
import java.io.InputStreamReader;

import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;

import android.os.AsyncTask;
import android.util.Log;

public class WeatherWebservice extends AsyncTask<Void, Void, String> {
	MainActivity activityRef;
	String apiUrl = "http://api.openweathermap.org/data/2.1/find/city?&lat=48.853409&lon=2.3488&cnt1&APPID=5d2eef1e303470228dcf653b4f989499";
	
	public WeatherWebservice(MainActivity activityRef) {
		this.activityRef = activityRef;
	}
	
	@Override
	protected String doInBackground(Void... arg0) {
		Log.d("Weather", "Starting Async Weather");
		
		DefaultHttpClient httpClient = new DefaultHttpClient();
		HttpGet request = new HttpGet(apiUrl);
		StringBuilder builder;
		
		try {
			HttpResponse response = httpClient.execute(request);
			BufferedReader reader = new BufferedReader(new InputStreamReader(response.getEntity().getContent(), "UTF-8"));
			builder = new StringBuilder();
			for (String line = null; (line = reader.readLine()) != null;) {
				builder.append(line).append("\n");
			}
		} catch (Exception e) {
			Log.d("Weather", e.toString());
            return null;
        }
		
		return builder.toString();
	}
	
	@Override
	protected void onPostExecute(String result) {
		activityRef.updateWeatherData(result); // TODO : Ckeck to see if the activity is still available.
	}
}
