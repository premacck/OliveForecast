package com.prembros.oliveforecast.data.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.ArrayList;

public class Forecast implements Serializable{

    private static final long serialVersionUID = 1L;

    @SerializedName("forecastday") @Expose private ArrayList<Forecastday> forecastday = new ArrayList<>();

    public ArrayList<Forecastday> getForecastday() {
        return forecastday;
    }

    public void setForecastday(ArrayList<Forecastday> mForecastday) {
        this.forecastday = mForecastday;
    }
}