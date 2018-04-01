package com.prembros.oliveforecast.ui.forecast;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.view.animation.LayoutAnimationController;
import android.widget.Toast;

import com.prembros.oliveforecast.R;
import com.prembros.oliveforecast.base.BaseFragment;
import com.prembros.oliveforecast.base.DynamicProgress;
import com.prembros.oliveforecast.data.model.WeatherForecast;

import butterknife.BindView;
import butterknife.ButterKnife;

import static android.view.View.GONE;
import static android.view.View.VISIBLE;
import static com.prembros.oliveforecast.ui.forecast.TodayForecastFragment.CITY_NAME;
import static com.prembros.oliveforecast.utility.Annotations.DayType.MULTIPLE_DAYS;
import static com.prembros.oliveforecast.utility.SharedPrefs.getForecastLimit;

public class MultipleDayForecastFragment extends BaseFragment {

    @BindView(R.id.list) RecyclerView recyclerView;
    @BindView(R.id.progress_bar) DynamicProgress progressBar;
    @BindView(R.id.swipe_refresh_layout) SwipeRefreshLayout swipeRefreshLayout;

    private MultipleDayForecastAdapter adapter;
    private String cityName;

    public MultipleDayForecastFragment() {}

    @NonNull public static MultipleDayForecastFragment newInstance(String cityName) {
        MultipleDayForecastFragment fragment = new MultipleDayForecastFragment();
        Bundle args = new Bundle();
        args.putString(CITY_NAME, cityName);
        fragment.setArguments(args);
        return fragment;
    }

    @Override public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle args = getArguments();
        if (args != null) cityName = args.getString(CITY_NAME);
    }

    @Override public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_multiple_day_forecast, container, false);
        unbinder = ButterKnife.bind(this, rootView);

        adapter = new MultipleDayForecastAdapter(this);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutAnimation(new LayoutAnimationController(
                AnimationUtils.loadAnimation(context, android.R.anim.fade_in), 0.2f
        ));

        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                getMultipleDayForeCast();
            }
        });
        return rootView;
    }

    @Override public void onResume() {
        super.onResume();
        progressBar.setVisibility(VISIBLE);
        getMultipleDayForeCast();
    }

    private void getMultipleDayForeCast() {
        if (cityName != null) getViewModel().getForecast(cityName, getForecastLimit(context), MULTIPLE_DAYS);
        else Toast.makeText(context, R.string.could_not_find_city, Toast.LENGTH_SHORT).show();
    }

    @Override public void handleResponse(WeatherForecast weatherForecast) {
        if (weatherForecast.getDayType() == MULTIPLE_DAYS) {
            adapter.updateList(weatherForecast.getForecast().getForecastday());
            dismissLoaders();
        }
    }

    @Override public void handleError(Throwable error) {
        Toast.makeText(context, R.string.something_went_wrong, Toast.LENGTH_SHORT).show();
        dismissLoaders();
    }

    private void dismissLoaders() {
        progressBar.setVisibility(GONE);
        if (swipeRefreshLayout.isRefreshing()) swipeRefreshLayout.setRefreshing(false);
    }

    @Override public void onDestroy() {
        super.onDestroy();
        adapter = null;
    }
}