package com.prembros.oliveforecast.injection.component;

import com.prembros.oliveforecast.data.viewmodel.ForecastViewModel;
import com.prembros.oliveforecast.injection.module.forecast.RepositoryModule;
import com.prembros.oliveforecast.injection.module.forecast.ViewModelModule;
import com.prembros.oliveforecast.injection.scope.ForecastScope;

import dagger.Subcomponent;

/**
 *
 * Created by Prem$ on 3/30/2018.
 */

@ForecastScope
@Subcomponent(modules = {RepositoryModule.class, ViewModelModule.class})
public interface ForecastComponent {

    ForecastViewModel forecastViewModel();

    void inject(ForecastViewModel forecastViewModel);

    @Subcomponent.Builder interface Builder {
        ForecastComponent build();
    }
}