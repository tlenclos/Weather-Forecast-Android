package com.tlenclos.weatherforecast;

import java.util.Date;

import android.R.bool;
import android.util.Log;

public class Weather {
	public String place;
	public double temperature;
	public double humidity;
	public double windSpeed;
	public int pressure;
	public String iconName;
	public Date day;
	public boolean isFetched;
	
	public Weather() {
		// Default values
		day = new Date();
		temperature = humidity = windSpeed = pressure = 0;
		isFetched = false;
	}
}
