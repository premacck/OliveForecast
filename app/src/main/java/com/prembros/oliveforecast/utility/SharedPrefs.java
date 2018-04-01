package com.prembros.oliveforecast.utility;

import android.content.Context;
import android.content.SharedPreferences;

import com.prembros.oliveforecast.utility.Annotations.Unit;

import static com.prembros.oliveforecast.utility.Annotations.Unit.METRIC;

/**
 *
 * Created by Prem$ on 3/31/2018.
 */

public class SharedPrefs {

    private static final String OLIVE_BOARD_PREFS = "olive_board_preferences";
    private static final String UNIT = "unit";
    private static final String FORECAST_LIMIT = "forecast_limit";

    private static SharedPreferences getSharedPreferences(Context context) {
        return context.getSharedPreferences(OLIVE_BOARD_PREFS, Context.MODE_PRIVATE);
    }

    public static void setUnitType(Context context, @Unit int unit) {
        getSharedPreferences(context).edit().putInt(UNIT, unit).apply();
    }

    @Unit public static int getUnitType(Context context) {
        return getSharedPreferences(context).getInt(UNIT, METRIC);
    }

    public static void setForecastLimit(Context context, int limit) {
        getSharedPreferences(context).edit().putInt(FORECAST_LIMIT, limit).apply();
    }

    public static int getForecastLimit(Context context) {
        return getSharedPreferences(context).getInt(FORECAST_LIMIT, 3);
    }
}