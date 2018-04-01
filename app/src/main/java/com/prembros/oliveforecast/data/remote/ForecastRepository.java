package com.prembros.oliveforecast.data.remote;

import android.arch.lifecycle.LiveData;

import com.prembros.oliveforecast.data.model.WeatherForecast;
import com.prembros.oliveforecast.utility.Annotations.DayType;

/**
 *
 * Created by Prem$ on 3/30/2018.
 */

public interface ForecastRepository {
    LiveData<WeatherForecast> getForecast(String location, int days, @DayType int dayType);
}