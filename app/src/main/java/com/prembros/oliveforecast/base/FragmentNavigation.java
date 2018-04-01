package com.prembros.oliveforecast.base;

import android.support.v4.app.Fragment;

/**
 * 
 * Created by Prem$ on 3/12/2018.
 */

public interface FragmentNavigation {
    void pushFragment(Fragment fragment);
    void popFragment();
}