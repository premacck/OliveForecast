package com.prembros.oliveforecast.base;

import android.arch.lifecycle.Observer;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;

import com.prembros.oliveforecast.data.model.WeatherForecast;
import com.prembros.oliveforecast.data.viewmodel.ForecastViewModel;

import butterknife.Unbinder;

import static com.prembros.oliveforecast.base.BaseActivity.viewModel;

/**
 *
 * Created by Prem$ on 3/30/2018.
 */

public abstract class BaseFragment extends Fragment {

    protected FragmentNavigation navigation;
    protected Unbinder unbinder;
    protected Context context;

    @Override public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        context = getContext();
    }

    @Override public void onResume() {
        super.onResume();
        observeWeatherForecastLiveData();
    }

    @Override public void onPause() {
        super.onPause();
        stopObservingForecastLiveData();
    }

    @Override public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof FragmentNavigation) {
            navigation = (FragmentNavigation) context;
        }
    }

    @Override public void onDestroyView() {
        super.onDestroyView();
        if (unbinder != null) unbinder.unbind();
    }

    @Override public void onDestroy() {
        super.onDestroy();
        navigation = null;
    }

    @Override public void onDetach() {
        super.onDetach();
        context = null;
    }

    public FragmentNavigation getNavigation() {
        return navigation;
    }

    @NonNull public BaseActivity getParentActivity() {
        try {
            if (getActivity() instanceof BaseActivity) return (BaseActivity) getActivity();
            else throw new IllegalStateException("Fragment must be attached to an activity extending BaseActivity");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return (BaseActivity) getActivity();
    }

    protected ForecastViewModel getViewModel() {
        return viewModel;
    }

    private void observeWeatherForecastLiveData() {
        viewModel.getWeatherForecastLiveData().observe(this, new Observer<WeatherForecast>() {
            @Override
            public void onChanged(@Nullable WeatherForecast weatherForecast) {
                if (weatherForecast != null) {
                    if (weatherForecast.getError() != null) handleError(weatherForecast.getError());
                    else handleResponse(weatherForecast);
                }
            }
        });
    }

    private void stopObservingForecastLiveData() {
        viewModel.getWeatherForecastLiveData().removeObservers(this);
    }

    public abstract void handleResponse(WeatherForecast weatherForecast);

    public abstract void handleError(Throwable error);
}