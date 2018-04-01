package com.prembros.oliveforecast.base;

import android.app.Activity;
import android.app.Application;

import com.prembros.oliveforecast.injection.component.AppComponent;
import com.prembros.oliveforecast.injection.component.DaggerAppComponent;
import com.prembros.oliveforecast.injection.module.app.ApplicationModule;
import com.prembros.oliveforecast.injection.module.app.ContextModule;

/**
 *
 * Created by Prem$ on 3/30/2018.
 */

public class OliveboardApplication extends Application {

    private AppComponent appComponent;

    @Override public void onCreate() {
        super.onCreate();
        appComponent = DaggerAppComponent
                .builder()
                .applicationModule(new ApplicationModule(this))
                .contextModule(new ContextModule(this))
                .build();
    }

    public static OliveboardApplication get(Activity activity) {
        return (OliveboardApplication) activity.getApplication();
    }

    public AppComponent getAppComponent() {
        return appComponent;
    }
}