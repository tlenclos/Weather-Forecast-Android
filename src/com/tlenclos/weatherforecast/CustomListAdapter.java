package com.tlenclos.weatherforecast;

import java.util.ArrayList;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class CustomListAdapter extends BaseAdapter {
	 
    private ArrayList<Weather> listData;
 
    private LayoutInflater layoutInflater;
 
    public CustomListAdapter(Context context, ArrayList<Weather> listData) {
        this.listData = listData;
        layoutInflater = LayoutInflater.from(context);
    }
 
    @Override
    public int getCount() {
        return listData.size();
    }
 
    @Override
    public Object getItem(int position) {
        return listData.get(position);
    }
 
    @Override
    public long getItemId(int position) {
        return position;
    }
 
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if (convertView == null) {
            convertView = layoutInflater.inflate(R.layout.list_row_day, null);
            holder = new ViewHolder();
            holder.dayView = (TextView) convertView.findViewById(R.id.day);
            holder.temperatureView = (TextView) convertView.findViewById(R.id.temperature);
            holder.pressureView = (TextView) convertView.findViewById(R.id.pressure);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
 
        holder.dayView.setText(listData.get(position).day.toString());
        holder.temperatureView.setText(""+listData.get(position).temperature);
        holder.pressureView.setText(""+listData.get(position).pressure);
 
        return convertView;
    }
 
    static class ViewHolder {
        TextView dayView;
        TextView temperatureView;
        TextView pressureView;
    }
 
}