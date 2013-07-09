package com.tlenclos.weatherforecast;

import java.util.ArrayList;

import com.tlenclos.weatherforecast.HomeTab.FragmentCallback;
import com.tlenclos.weatherforecast.models.User;
import com.tlenclos.weatherforecast.models.Weather;

import android.app.ActionBar.Tab;
import android.app.ActionBar.TabListener;
import android.app.Fragment;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;
 
public class WeekTab extends Fragment implements TabListener {
 
    private Fragment mFragment;
    private ListView daysListView;
    private CustomListAdapter adapter;
    ArrayList<Weather> weathers;
    
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Get the view from fragment1.xml
        getActivity().setContentView(R.layout.week_tab);

        weathers = new ArrayList<Weather>();
        daysListView = (ListView) this.getActivity().findViewById(R.id.days_list);
        adapter = new CustomListAdapter(this.getActivity(), weathers);
        daysListView.setAdapter(adapter);
        
        // Get weather data
        if (User.getInstance().location != null) {
    		if (((MainActivity) this.getActivity()).isOnline()) {
    			Toast.makeText(this.getActivity().getApplicationContext(), "Fetching weather data...", Toast.LENGTH_SHORT).show();
    			
    			WeatherWebservice weatherWS = new WeatherWebservice(new FragmentCallback() {
    	            @Override
    	            public void onTaskDone(ArrayList<Weather> result) {
    	                weathers.clear();
    	                weathers.addAll(result);
    	                adapter.notifyDataSetChanged();
    	            }
    	        }, User.getInstance().location, false);
    			weatherWS.execute();
    		} else {
    			Toast.makeText(this.getActivity().getApplicationContext(), "Network error", Toast.LENGTH_SHORT).show();
    		}
        }
    }
    
    public void onTabSelected(Tab tab, FragmentTransaction ft) {
        // TODO Auto-generated method stub
        mFragment = new WeekTab();
        // Attach fragment1.xml layout
        ft.add(android.R.id.content, mFragment);
        ft.attach(mFragment);
    }
 
    public void onTabUnselected(Tab tab, FragmentTransaction ft) {
        // TODO Auto-generated method stub
        // Remove fragment1.xml layout
        ft.remove(mFragment);
    }
 
    public void onTabReselected(Tab tab, FragmentTransaction ft) {
        // TODO Auto-generated method stub
 
    }
 
}