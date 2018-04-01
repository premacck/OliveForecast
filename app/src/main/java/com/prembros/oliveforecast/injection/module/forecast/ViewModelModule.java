package com.prembros.oliveforecast.injection.module.forecast;

import com.prembros.oliveforecast.base.OliveboardApplication;
import com.prembros.oliveforecast.data.viewmodel.ForecastViewModel;
import com.prembros.oliveforecast.injection.scope.ForecastScope;

import dagger.Module;
import dagger.Provides;

/**
 *
 * Created by Prem$ on 3/30/2018.
 */

@Module
public class ViewModelModule {

    @Provides @ForecastScope
    ForecastViewModel provideForecastViewModel(OliveboardApplication application) {
        return new ForecastViewModel(application);
    }
}