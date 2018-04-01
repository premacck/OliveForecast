package com.prembros.oliveforecast.base;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.prembros.oliveforecast.data.viewmodel.ForecastViewModel;

import butterknife.Unbinder;

/**
 *
 * Created by Prem$ on 3/30/2018.
 */

public abstract class BaseActivity extends AppCompatActivity {

    protected Unbinder unbinder;
    public static ForecastViewModel viewModel;

    @Override protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (viewModel == null) viewModel = getAnInstanceOfViewModel();
    }

    private ForecastViewModel getAnInstanceOfViewModel() {
        return OliveboardApplication.get(this).getAppComponent().forecastComponentBuilder().build().forecastViewModel();
    }

    @Override protected void onDestroy() {
        super.onDestroy();
        if (unbinder != null) unbinder.unbind();
//        viewModel = null;
    }
}