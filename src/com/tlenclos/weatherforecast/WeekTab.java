package com.tlenclos.weatherforecast;

import java.util.ArrayList;

import android.app.ActionBar.Tab;
import android.app.ActionBar.TabListener;
import android.app.Fragment;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;
 
public class WeekTab extends Fragment implements TabListener {
 
    private Fragment mFragment;
    
    // Initialize the array
    String[] monthsArray = { "JAN", "FEB", "MAR", "APR", "MAY", "JUNE", "JULY",
 "AUG", "SEPT", "OCT", "NOV", "DEC" };
 
    // Declare the UI components
    private ListView daysListView;
    private ArrayAdapter arrayAdapter;
    
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Get the view from fragment1.xml
        getActivity().setContentView(R.layout.week_tab);

        ArrayList<Weather> weathers = getListData();
        daysListView = (ListView) this.getActivity().findViewById(R.id.days_list);
        daysListView.setAdapter(new CustomListAdapter(this.getActivity(), weathers));
    }
 
    private ArrayList<Weather> getListData() {
        ArrayList<Weather> results = new ArrayList<Weather>();
        Weather newsData = new Weather();
        newsData.temperature = 10;
        newsData.pressure = 12;
        results.add(newsData);
 
        newsData = new Weather();
        newsData.temperature = 10;
        newsData.pressure = 12;
        results.add(newsData);
        
        return results;
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