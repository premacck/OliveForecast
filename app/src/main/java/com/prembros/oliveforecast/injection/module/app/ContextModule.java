package com.prembros.oliveforecast.injection.module.app;

import android.content.Context;

import com.prembros.oliveforecast.injection.component.ForecastComponent;
import com.prembros.oliveforecast.injection.scope.AppScope;

import dagger.Module;
import dagger.Provides;

/**
 *
 * Created by Prem$ on 3/7/2018.
 */

@AppScope
@Module(subcomponents = ForecastComponent.class)
public class ContextModule {

    private final Context context;

    public ContextModule(Context context) {
        this.context = context;
    }

    @Provides @AppScope public Context getContext() {
        return context;
    }
}