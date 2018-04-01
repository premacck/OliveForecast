package com.prembros.oliveforecast.data.viewmodel;

import android.arch.lifecycle.ViewModel;

import com.prembros.oliveforecast.base.OliveboardApplication;
import com.prembros.oliveforecast.data.BrokerLiveData;
import com.prembros.oliveforecast.data.model.WeatherForecast;
import com.prembros.oliveforecast.data.remote.ForecastRepository;
import com.prembros.oliveforecast.utility.Annotations.DayType;

import javax.inject.Inject;

/**
 *
 * Created by Prem$ on 3/30/2018.
 */

public class ForecastViewModel extends ViewModel {

    @Inject BrokerLiveData<WeatherForecast> weatherForecastLiveData;
    @Inject ForecastRepository forecastRepository;

    public ForecastViewModel(OliveboardApplication application) {
        application.getAppComponent().forecastComponentBuilder().build().inject(this);
    }

    public BrokerLiveData<WeatherForecast> getWeatherForecastLiveData() {
        return weatherForecastLiveData;
    }

    public void getForecast(String location, int days, @DayType int dayType) {
        weatherForecastLiveData.observeOn(forecastRepository.getForecast(location, days, dayType));
    }
}