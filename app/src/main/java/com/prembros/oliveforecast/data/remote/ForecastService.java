package com.prembros.oliveforecast.data.remote;

import com.prembros.oliveforecast.data.model.WeatherForecast;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

import static com.prembros.oliveforecast.utility.Constants.APIXU_API_KEY;

/**
 *
 * Created by Prem$ on 3/30/2018.
 */

public interface ForecastService {

    @GET("/v1/forecast.json?key=" + APIXU_API_KEY)
    Call<WeatherForecast> getForecast(@Query("q") String location, @Query("days") int days);
}