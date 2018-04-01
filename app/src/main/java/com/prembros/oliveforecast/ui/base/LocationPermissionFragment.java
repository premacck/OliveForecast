package com.prembros.oliveforecast.ui.base;

import android.content.Intent;
import android.os.Bundle;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.prembros.oliveforecast.R;

import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;


public class LocationPermissionFragment extends Fragment {

    private Unbinder unbinder;

    public LocationPermissionFragment() {}

    @NonNull public static LocationPermissionFragment newInstance() {
        return new LocationPermissionFragment();
    }

    @Override public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_location_permission, container, false);
        unbinder = ButterKnife.bind(this, rootView);
        return rootView;
    }

    @OnClick(R.id.turn_on_location_btn) public void turnOnLocationManually() {
        startActivity(new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS));
    }

    @Override public void onDestroyView() {
        super.onDestroyView();
        if (unbinder != null)
            unbinder.unbind();
    }
}