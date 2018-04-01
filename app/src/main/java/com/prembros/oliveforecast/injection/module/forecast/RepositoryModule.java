package com.prembros.oliveforecast.injection.module.forecast;

import com.prembros.oliveforecast.data.BrokerLiveData;
import com.prembros.oliveforecast.data.model.WeatherForecast;
import com.prembros.oliveforecast.data.remote.ForecastRepository;
import com.prembros.oliveforecast.data.remote.ForecastRepositoryImpl;
import com.prembros.oliveforecast.data.remote.ForecastService;
import com.prembros.oliveforecast.injection.scope.ForecastScope;

import dagger.Module;
import dagger.Provides;
import retrofit2.Retrofit;

/**
 *
 * Created by Prem$ on 3/30/2018.
 */

@Module(includes = NetworkModule.class)
public class RepositoryModule {

    @Provides @ForecastScope
    BrokerLiveData<WeatherForecast> provideWeatherForecastLiveData() {
        return new BrokerLiveData<>();
    }

    @Provides @ForecastScope
    ForecastService provideForecastService(Retrofit retrofit) {
        return retrofit.create(ForecastService.class);
    }

    @Provides @ForecastScope
    ForecastRepository provideForecastRepository(ForecastService forecastService) {
        return new ForecastRepositoryImpl(forecastService);
    }
}