package com.prembros.oliveforecast.data.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.prembros.oliveforecast.utility.Annotations.DayType;

import java.io.Serializable;

public class WeatherForecast implements Serializable{

	private static final long serialVersionUID = 1L;
	
	@SerializedName("location") @Expose private Location location;
    @SerializedName("current") @Expose private Current current;
	@SerializedName("forecast") @Expose private Forecast forecast;
	private Throwable error;
	@DayType private int dayType;

    public WeatherForecast(Throwable error) {
        this.error = error;
    }

    public Throwable getError() {
        return error;
    }

    public Location getLocation() {
        return location;
    }

    public void setLocation(Location mLocation) {
        this.location = mLocation;
    }

    public Current getCurrent() {
        return current;
    }

    public void setCurrent(Current mCurrent) {
        this.current = mCurrent;
    }

    public Forecast getForecast() {
        return forecast;
    }

    public void setForecast(Forecast mForecast) {
    	 this.forecast = mForecast;
     }

    @DayType public int getDayType() {
        return dayType;
    }

    public WeatherForecast setDayType(@DayType int dayType) {
        this.dayType = dayType;
        return this;
    }
}