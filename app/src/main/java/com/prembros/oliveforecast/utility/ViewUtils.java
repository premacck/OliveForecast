package com.prembros.oliveforecast.utility;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.View;
import android.view.inputmethod.InputMethodManager;

import com.prembros.oliveforecast.data.model.WeatherForecast;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import static com.prembros.oliveforecast.utility.Annotations.DayType.TODAY;
import static com.prembros.oliveforecast.utility.Annotations.DayType.TOMORROW;
import static com.prembros.oliveforecast.utility.Annotations.Unit.METRIC;
import static com.prembros.oliveforecast.utility.Constants.CELSIUS;
import static com.prembros.oliveforecast.utility.Constants.DEGREE;
import static com.prembros.oliveforecast.utility.Constants.FAHRENHEIT;
import static com.prembros.oliveforecast.utility.Constants.KPH;
import static com.prembros.oliveforecast.utility.Constants.MPH;
import static com.prembros.oliveforecast.utility.SharedPrefs.getUnitType;

/**
 *
 * Created by Prem$ on 3/31/2018.
 */

public class ViewUtils {

    public static void hideKeyboard(Activity activity, View view) {
        try {
            InputMethodManager imm = (InputMethodManager) activity.getSystemService(Context.INPUT_METHOD_SERVICE);
            if (imm != null) {
                //            imm.toggleSoftInput(InputMethodManager.RESULT_UNCHANGED_SHOWN, InputMethodManager.RESULT_HIDDEN);
                imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Nullable public static String getToday(WeatherForecast weatherForecast) {
        try {
            return getDateHeader(weatherForecast.getCurrent().getLastUpdateEpoch());
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @Nullable public static String getTomorrow(WeatherForecast weatherForecast) {
        try {
            return getDateHeader(weatherForecast.getForecast().getForecastday().get(1).getDateEpoch());
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public static String getDateHeader(long epoch) {
        return new SimpleDateFormat("EEEE dd, hh:mm a", Locale.getDefault())
                .format(new Date(epoch * 1000L));
    }

    @NonNull public static String getSuitableTemp(Context context, double degreeInC, double degreeInF) {
        return getUnitType(context) == METRIC ?
                String.valueOf(degreeInC) + DEGREE + CELSIUS :
                String.valueOf(degreeInF) + DEGREE + FAHRENHEIT;
    }

    @NonNull public static String getSuitableMaxTemp(Context context, double maxTempC, double maxTempF) {
        return String.valueOf(getUnitType(context) == METRIC ?
                String.valueOf(maxTempC) + DEGREE + CELSIUS :
                String.valueOf(maxTempF) + DEGREE + FAHRENHEIT);
    }

    @NonNull public static String getSuitableMinTemp(Context context, double minTempC, double minTempF) {
        return String.valueOf(getUnitType(context) == METRIC ?
                String.valueOf(minTempC) + DEGREE + CELSIUS :
                String.valueOf(minTempF) + DEGREE + FAHRENHEIT);
    }

    @NonNull public static String getSuitableWindSpeed(Context context, double kphSpeed, double mphSpeed) {
        return getUnitType(context) == METRIC ? String.valueOf(kphSpeed) + KPH : String.valueOf(mphSpeed) + MPH;
    }

    @Nullable @SuppressLint("SwitchIntDef") public static String getHumidity(WeatherForecast weatherForecast) {
        try {
            switch (weatherForecast.getDayType()) {
                case TODAY:
                    return String.valueOf(weatherForecast.getCurrent().getHumidity());
                case TOMORROW:
                    return String.valueOf(weatherForecast.getForecast().getForecastday().get(1).getDay().getAvgHumidity());
                default:
                    return null;
            }
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @Nullable @SuppressLint("SwitchIntDef") public static String getCondition(WeatherForecast weatherForecast) {
        try {
            switch (weatherForecast.getDayType()) {
                case TODAY:
                    return String.valueOf(weatherForecast.getCurrent().getCondition().getText());
                case TOMORROW:
                    return String.valueOf(weatherForecast.getForecast().getForecastday().get(1).getDay().getCondition().getText());
                default:
                    return null;
            }
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public static int getPixels(@NonNull Context context, int dp) {
        return (int) (dp * context.getResources().getDisplayMetrics().density + 0.5f);
    }
}