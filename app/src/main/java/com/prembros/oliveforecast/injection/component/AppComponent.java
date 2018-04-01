package com.prembros.oliveforecast.injection.component;

import android.content.Context;

import com.prembros.oliveforecast.base.OliveboardApplication;
import com.prembros.oliveforecast.injection.module.app.ApplicationModule;
import com.prembros.oliveforecast.injection.module.app.ContextModule;
import com.prembros.oliveforecast.injection.scope.AppScope;

import dagger.Component;

/**
 *
 * Created by Prem$ on 3/30/2018.
 */

@AppScope
@Component(modules = {ApplicationModule.class, ContextModule.class})
public interface AppComponent {

//    Exposing getters for dependent components
    Context context();
    OliveboardApplication application();

//    Exposing instances of sub components
    ForecastComponent.Builder forecastComponentBuilder();
}