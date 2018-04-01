package com.prembros.oliveforecast.data.remote;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.support.annotation.NonNull;

import com.prembros.oliveforecast.data.model.WeatherForecast;
import com.prembros.oliveforecast.utility.Annotations.DayType;

import javax.inject.Inject;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 *
 * Created by Prem$ on 3/30/2018.
 */

public class ForecastRepositoryImpl implements ForecastRepository {

    private ForecastService forecastService;

    @Inject public ForecastRepositoryImpl(ForecastService forecastService) {
        this.forecastService = forecastService;
    }

    @Override public LiveData<WeatherForecast> getForecast(String location, int days, @DayType final int dayType) {
        final MutableLiveData<WeatherForecast> liveData = new MutableLiveData<>();
        forecastService.getForecast(location, days).enqueue(new Callback<WeatherForecast>() {

            @Override public void onResponse(Call<WeatherForecast> call, Response<WeatherForecast> response) {
                liveData.setValue(response.isSuccessful() ? response.body().setDayType(dayType) : getErrorObject(dayType));
            }

            @NonNull private WeatherForecast getErrorObject(@DayType int dayType) {
                return new WeatherForecast(new Throwable("Something went wrong, please try again")).setDayType(dayType);
            }

            @Override public void onFailure(Call<WeatherForecast> call, Throwable t) {
                t.printStackTrace();
                liveData.setValue(getErrorObject(dayType));
            }
        });
        return liveData;
    }
}