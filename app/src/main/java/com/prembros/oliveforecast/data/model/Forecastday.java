package com.prembros.oliveforecast.data.model;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.ArrayList;

public class Forecastday implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	@SerializedName("date") private String date;
	@SerializedName("date_epoch") private int date_epoch;
	@SerializedName("day") private Day day;
	@SerializedName("astro") private Astro astro;
	@SerializedName("hour") private ArrayList<Hour> hour;
	
    public String getDate()
    {
    	return date;
    }
    public void setDate(String mDate)
    {
    	this.date = mDate;
    }
    
    public int getDateEpoch()
    {
    	return date_epoch;
    }
    public void setDateEpoch(int mDateEpoch)
    {
    	this.date_epoch = mDateEpoch;
    }
    
    public Day getDay()
    {
    	return day;
    }
    public void setDay(Day mDay)
    {
    	this.day = mDay;
    }
    
    public Astro getAstro()
    {
    	return astro;
    }
    public void setAstro(Astro mAstro)
    {
    	this.astro = mAstro;
    }
    
    public ArrayList<Hour> getHour()
    {
    	return hour;
    }
    public void setHour(ArrayList<Hour> mHour)
    {
    	this.hour = mHour;
    }
}