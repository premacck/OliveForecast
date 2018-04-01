package com.prembros.oliveforecast.utility;

import android.support.annotation.IntDef;

import java.lang.annotation.Retention;

import static com.prembros.oliveforecast.utility.Annotations.DayType.MULTIPLE_DAYS;
import static com.prembros.oliveforecast.utility.Annotations.DayType.TODAY;
import static com.prembros.oliveforecast.utility.Annotations.DayType.TOMORROW;
import static com.prembros.oliveforecast.utility.Annotations.Unit.IMPERIAL;
import static com.prembros.oliveforecast.utility.Annotations.Unit.METRIC;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

/**
 *
 * Created by Prem$ on 3/30/2018.
 */

public class Annotations {

    @Retention(RUNTIME)
    @IntDef({TODAY, TOMORROW, MULTIPLE_DAYS})
    public @interface DayType {
        int TODAY = 0;
        int TOMORROW = 1;
        int MULTIPLE_DAYS = 2;
    }

    @Retention(RUNTIME)
    @IntDef({METRIC, IMPERIAL})
    public @interface Unit {
        int METRIC = 0;
        int IMPERIAL = 1;
    }
}