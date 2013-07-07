package com.tlenclos.weatherforecast;

import android.app.ActionBar;
import android.app.ActionBar.Tab;
import android.app.Activity;
import android.content.Context;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.widget.Toast;

public class MainActivity extends Activity implements LocationListener   {
	private Tab tab;
	private LocationManager locationManager;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
        
	    // Create the actionbar
        ActionBar actionBar = getActionBar();
        actionBar.setDisplayShowHomeEnabled(false);
        actionBar.setDisplayShowTitleEnabled(false);
        actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);
 
        // Create first Tab
        tab = actionBar.newTab().setTabListener(new HomeTab());
        tab.setText("Today");
        actionBar.addTab(tab);
 
        // Create Second Tab
        tab = actionBar.newTab().setTabListener(new WeekTab());
        // Set Tab Title
        tab.setText("Week");
        actionBar.addTab(tab);
        
     	// Geoloc user
 		locationManager = (LocationManager) this.getSystemService(Context.LOCATION_SERVICE);
	}
	
	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		Log.v("AppWeather", "Starting app");
		
		if (locationManager != null) {
			locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 1000, 1000, this);
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.activity_main, menu);
		return true;
	}
	
	public void updateWeatherData(Weather result) {
		if (result != null && result.isFetched) {
			// Update UI
			Log.d("AppWeather",  "Place & Temperature : " + result.place +", "+ result.temperature);
			
		} else {
			Log.d("AppWeather", "Webservice returned empty result");
			Toast.makeText(this.getApplicationContext(), "Error while getting weather data", Toast.LENGTH_SHORT).show();
		}
	}

	public boolean isOnline() {
	    ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
	    NetworkInfo netInfo = cm.getActiveNetworkInfo();
	    if (netInfo != null && netInfo.isConnectedOrConnecting()) {
	        return true;
	    }
	    return false;
	}

	@Override
	public void onLocationChanged(Location location) {
		Log.v("AppWeather", location.getLatitude() + " - " + location.getLongitude());
		locationManager.removeUpdates(this);
		
		// Get weather data
		if (isOnline()) {
			WeatherWebservice weatherWS = new WeatherWebservice(this, location);
			weatherWS.execute();
		} else {
			Log.d("AppWeather", "Device is offline");
		}
	}

	@Override
	public void onProviderDisabled(String provider) {
		// TODO Auto-generated method stub
		Log.v("AppWeather", provider);
		
	}

	@Override
	public void onProviderEnabled(String provider) {
		// TODO Auto-generated method stub
		Log.v("AppWeather", provider);
	}

	@Override
	public void onStatusChanged(String provider, int status, Bundle extras) {
		// TODO Auto-generated method stub
		Log.v("AppWeather", provider+ " changed");
		
	}
}