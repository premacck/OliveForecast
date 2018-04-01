package com.prembros.oliveforecast.ui.forecast;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.prembros.oliveforecast.R;
import com.prembros.oliveforecast.base.BaseFragment;
import com.prembros.oliveforecast.data.model.WeatherForecast;
import com.prembros.oliveforecast.ui.customviews.CustomTextView;
import com.prembros.oliveforecast.ui.customviews.ShimmerLinearLayout;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.bumptech.glide.load.engine.DiskCacheStrategy.RESOURCE;
import static com.prembros.oliveforecast.ui.forecast.TodayForecastFragment.CITY_NAME;
import static com.prembros.oliveforecast.utility.Annotations.DayType.TOMORROW;
import static com.prembros.oliveforecast.utility.Constants.HTTP;
import static com.prembros.oliveforecast.utility.ViewUtils.getCondition;
import static com.prembros.oliveforecast.utility.ViewUtils.getHumidity;
import static com.prembros.oliveforecast.utility.ViewUtils.getSuitableTemp;
import static com.prembros.oliveforecast.utility.ViewUtils.getSuitableWindSpeed;
import static com.prembros.oliveforecast.utility.ViewUtils.getTomorrow;

public class TomorrowForecastFragment extends BaseFragment {

    @BindView(R.id.root_layout) ShimmerLinearLayout shimmerLinearLayout;
    @BindView(R.id.weather_city_name) CustomTextView weatherCityName;
    @BindView(R.id.weather_date) CustomTextView weatherDate;
    @BindView(R.id.weather_temperature) CustomTextView weatherTemperature;
    @BindView(R.id.weather_icon) ImageView weatherIcon;
    @BindView(R.id.weather_wind_speed) CustomTextView weatherWindSpeed;
    @BindView(R.id.weather_wind_degree) CustomTextView weatherWindDegree;
    @BindView(R.id.weather_humidity) CustomTextView weatherHumidity;
    @BindView(R.id.weather_condition) CustomTextView weatherCondition;
    @BindView(R.id.swipe_refresh_layout) SwipeRefreshLayout swipeRefreshLayout;

    private String cityName;

    public TomorrowForecastFragment() {}

    @NonNull public static TomorrowForecastFragment newInstance(String cityName) {
        TomorrowForecastFragment fragment = new TomorrowForecastFragment();
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
        View rootView = inflater.inflate(R.layout.fragment_one_day_forecast, container, false);
        unbinder = ButterKnife.bind(this, rootView);
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                getTomorrowForecast();
            }
        });
        return rootView;
    }

    @Override public void onResume() {
        super.onResume();
        getTomorrowForecast();
    }

    private void getTomorrowForecast() {
        if (cityName != null) {
            shimmerize();
            getViewModel().getForecast(cityName, 2, TOMORROW);
        } else Toast.makeText(context, R.string.could_not_find_city, Toast.LENGTH_SHORT).show();
    }

    @Override public void handleResponse(WeatherForecast weatherForecast) {
        if (weatherForecast.getDayType() == TOMORROW) {
            try {
                try {
                    Glide.with(this)
                            .load(HTTP + weatherForecast.getForecast().getForecastday().get(1).getDay().getCondition().getIcon())
                            .apply(RequestOptions.diskCacheStrategyOf(RESOURCE).placeholder(R.drawable.image_view_shimmer))
                            .into(weatherIcon);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                weatherCityName.setText(cityName);

                weatherDate.setText(getTomorrow(weatherForecast));

                weatherTemperature.setText(getSuitableTemp(getContext(),
                        weatherForecast.getForecast().getForecastday().get(1).getDay().getAvgTempC(),
                        weatherForecast.getForecast().getForecastday().get(1).getDay().getAvgTempF()));

                weatherWindSpeed.setText(getSuitableWindSpeed(getContext(),
                        weatherForecast.getForecast().getForecastday().get(1).getDay().getMaxWindKph(),
                        weatherForecast.getForecast().getForecastday().get(1).getDay().getMaxWindMph()));

                weatherWindDegree.setText(null);

                weatherHumidity.setText(getString(R.string.humidity) + getHumidity(weatherForecast));

                weatherCondition.setText(getString(R.string.condition) + getCondition(weatherForecast));
            } catch (Exception e) {
                e.printStackTrace();
            }
            dismissLoaders();
        }
    }

    @Override public void handleError(Throwable error) {
        dismissLoaders();
        Toast.makeText(context, R.string.something_went_wrong, Toast.LENGTH_SHORT).show();
    }

    private void dismissLoaders() {
        deShimmerize();
        if (swipeRefreshLayout.isRefreshing()) swipeRefreshLayout.setRefreshing(false);
    }

    private void shimmerize() {
        shimmerLinearLayout.startShimmerAnimation();
        weatherCityName.setText("");
        weatherDate.setText("");
        weatherTemperature.setText("");
        weatherWindSpeed.setText("");
        weatherWindDegree.setText("");
        weatherHumidity.setText("");
        weatherCondition.setText("");
    }

    private void deShimmerize() {
        shimmerLinearLayout.stopShimmerAnimation();
    }
}