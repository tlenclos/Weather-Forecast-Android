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
        tab.setText("Home");
        actionBar.addTab(tab);
 
        // Create Second Tab
        tab = actionBar.newTab().setTabListener(new WeekTab());
        // Set Tab Title
        tab.setText("Week");
        actionBar.addTab(tab);
        
     	// Geoloc user
 		locationManager = (LocationManager) this.getSystemService(Context.LOCATION_SERVICE);
 		
		// Get weather data
		if (isOnline()) {
			WeatherWebservice weatherWS = new WeatherWebservice(this);
			weatherWS.execute();
		} else {
			Log.d("AppWeather", "Device is offline");
		}
	}
	
	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		
		Log.v("AppWeather", "Starting app");
		locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 1000, 1000, this);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.activity_main, menu);
		return true;
	}
	
	public void updateWeatherData(String result) {
		if (result != null) {
			Log.d("AppWeather", result);
		} else {
			Log.d("AppWeather", "Webservice returned empty result");
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
	}

	@Override
	public void onProviderDisabled(String provider) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onProviderEnabled(String provider) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onStatusChanged(String provider, int status, Bundle extras) {
		// TODO Auto-generated method stub
		
	}
}