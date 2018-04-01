package com.prembros.oliveforecast.ui.base;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.TabLayout;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.common.api.CommonStatusCodes;
import com.google.android.gms.common.api.ResolvableApiException;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.LocationSettingsRequest;
import com.google.android.gms.location.LocationSettingsResponse;
import com.google.android.gms.location.LocationSettingsStatusCodes;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.prembros.oliveforecast.R;
import com.prembros.oliveforecast.base.BaseActivity;
import com.prembros.oliveforecast.base.FragmentNavigation;
import com.prembros.oliveforecast.ui.forecast.MultipleDayForecastFragment;
import com.prembros.oliveforecast.ui.forecast.TodayForecastFragment;
import com.prembros.oliveforecast.ui.forecast.TomorrowForecastFragment;
import com.seatgeek.placesautocomplete.OnPlaceSelectedListener;
import com.seatgeek.placesautocomplete.PlacesAutocompleteTextView;
import com.seatgeek.placesautocomplete.model.Place;

import org.jetbrains.annotations.Contract;

import java.util.List;
import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnEditorAction;

import static android.Manifest.permission.ACCESS_COARSE_LOCATION;
import static android.Manifest.permission.ACCESS_FINE_LOCATION;
import static android.Manifest.permission.INTERNET;
import static com.prembros.oliveforecast.utility.SharedPrefs.getForecastLimit;
import static com.prembros.oliveforecast.utility.ViewUtils.hideKeyboard;

public class MainActivity extends BaseActivity implements FragmentNavigation, OnPlaceSelectedListener {

    private static final String REQUESTING_LOCATION_UPDATES_KEY = "locationUpdates";
    private static final String KEY_LOCATION = "location";
    private static final int REQUEST_LOCATION_PERMISSIONS = 211;
    private static final int REQUEST_CODE_CHECK_SETTINGS = 312;

    @BindView(R.id.tabs) TabLayout tabLayout;
    @BindView(R.id.toolbar) Toolbar toolbar;
    @BindView(R.id.container) ViewPager viewPager;
    @BindView(R.id.search_bar) PlacesAutocompleteTextView searchBar;

    private boolean isRequestingLocationUpdates;
    public Location currentLocation;
    private LocationRequest locationRequest;
    private LocationCallback locationCallback;
    private FusedLocationProviderClient fusedLocationProviderClient;
    private FragmentManager fragmentManager;
    private SectionsPagerAdapter sectionsPagerAdapter;

    @Contract(value = " -> !null", pure = true) private MainActivity getThis() {
        return this;
    }

    @Override public void onSaveInstanceState(@NonNull Bundle outState) {
        outState.putBoolean(REQUESTING_LOCATION_UPDATES_KEY, isRequestingLocationUpdates);
        super.onSaveInstanceState(outState);
    }

    private void updateValuesFromBundle(Bundle savedInstanceState) {
        // Update the value of mRequestingLocationUpdates from the Bundle.
        if (savedInstanceState != null) {
            if (savedInstanceState.keySet().contains(REQUESTING_LOCATION_UPDATES_KEY)) {
                isRequestingLocationUpdates = savedInstanceState.getBoolean(REQUESTING_LOCATION_UPDATES_KEY);
            }
            if (savedInstanceState.keySet().contains(KEY_LOCATION)) {
                currentLocation = savedInstanceState.getParcelable(KEY_LOCATION);
            }
        }
    }

    @Override protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        unbinder = ButterKnife.bind(this);
        setSupportActionBar(toolbar);
        fragmentManager = getSupportFragmentManager();

        isRequestingLocationUpdates = false;
        updateValuesFromBundle(savedInstanceState);
        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(getThis());
        if (arePermissionsAllowed(getThis())) getLastLocation();
        else requestPermissions();

        searchBar.setOnPlaceSelectedListener(this);
    }

    @Override protected void onResume() {
        super.onResume();
        calibrateThirdTab();
    }

    @Override public void onPlaceSelected(@NonNull Place place) {
        hideKeyboard(getThis(), searchBar);
        sectionsPagerAdapter.updateCity(place.terms.get(0).value);
    }

    @OnEditorAction(R.id.search_bar) public boolean locationNotSelected() {
        hideKeyboard(getThis(), searchBar);
        return true;
    }

    private void prepareTabs(String cityName) {
        if (sectionsPagerAdapter == null) {
            sectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager(), cityName);
            viewPager.setAdapter(sectionsPagerAdapter);

            viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));
            tabLayout.addOnTabSelectedListener(new TabLayout.ViewPagerOnTabSelectedListener(viewPager));

            calibrateThirdTab();
        }
    }

    private void calibrateThirdTab() {
        try {
            TabLayout.Tab tab = tabLayout.getTabAt(2);
            int forecastLimit = getForecastLimit(getThis());
            if (tab != null) tab.setText(forecastLimit + forecastLimit > 1 ? " Days" : " Day");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void updateCurrentCity() {
        Geocoder geocoder = new Geocoder(this, Locale.getDefault());
        try {
            List<Address> addresses = geocoder.getFromLocation(
                    currentLocation.getLatitude(), currentLocation.getLongitude(), 1);
            prepareTabs(addresses.get(0).getLocality());
        } catch (Exception e) {
            e.printStackTrace();
            prepareTabs(null);
        }
    }

    private void getLastLocation() {
        if (arePermissionsAllowed(getThis())) {
            if (ActivityCompat.checkSelfPermission(getThis(),
                    Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
                    && ActivityCompat.checkSelfPermission(
                    this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                return;
            }
            fusedLocationProviderClient.getLastLocation()
                    .addOnSuccessListener(getThis(), new OnSuccessListener<Location>() {
                        @Override
                        public void onSuccess(Location location) {
                            if (location != null) {
                                currentLocation = location;
                                updateCurrentCity();
                            }
                        }
                    })
                    .addOnFailureListener(getThis(), new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            createLocationCallback();
                        }
                    });
        } else requestPermissions();
    }

    private void createLocationCallback() {
        if (currentLocation == null) {
            locationCallback = new LocationCallback() {
                @Override
                public void onLocationResult(LocationResult locationResult) {
                    super.onLocationResult(locationResult);
                    currentLocation = locationResult.getLastLocation();
                    updateCurrentCity();
                    stopLocationUpdates();
                }
            };
        } else stopLocationUpdates();

        createLocationRequest();
    }

    private void createLocationRequest() {
        locationRequest = LocationRequest.create();
        locationRequest.setInterval(10000);
        locationRequest.setFastestInterval(1000);
        locationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);

        LocationSettingsRequest.Builder builder = new LocationSettingsRequest.Builder()
                .addLocationRequest(locationRequest);

        LocationServices.getSettingsClient(getThis()).checkLocationSettings(builder.build())
                .addOnSuccessListener(getThis(), new OnSuccessListener<LocationSettingsResponse>() {
                    @Override
                    public void onSuccess(LocationSettingsResponse locationSettingsResponse) {
                        startLocationUpdates();
                    }
                })
                .addOnFailureListener(getThis(), new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        int statusCode = ((ApiException) e).getStatusCode();
                        switch (statusCode) {
                            case CommonStatusCodes.RESOLUTION_REQUIRED:
                                try {
                                    ResolvableApiException resolvable = (ResolvableApiException) e;
                                    resolvable.startResolutionForResult(getThis(), REQUEST_CODE_CHECK_SETTINGS);
                                } catch (Exception sendEx) {
                                    sendEx.printStackTrace();
                                }
                                break;
                            case LocationSettingsStatusCodes.SETTINGS_CHANGE_UNAVAILABLE:
//                                Location settings are not satisfied.
//                                However, we have no way to fix the settings so we won't show the dialog.
                                break;
                        }
                    }
                });
        if (isRequestingLocationUpdates) startLocationUpdates();
    }

    private void startLocationUpdates() {
        try {
            if (ActivityCompat.checkSelfPermission(getThis(), Manifest.permission.ACCESS_FINE_LOCATION)
                    == PackageManager.PERMISSION_GRANTED
                    && ActivityCompat.checkSelfPermission(getThis(), Manifest.permission.ACCESS_COARSE_LOCATION)
                    == PackageManager.PERMISSION_GRANTED) {
                fusedLocationProviderClient.requestLocationUpdates(locationRequest, locationCallback, null);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void stopLocationUpdates() {
        try {
            fusedLocationProviderClient.removeLocationUpdates(locationCallback)
                    .addOnCompleteListener(getThis(), new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            isRequestingLocationUpdates = false;
                        }
                    });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_settings:
                startActivity(new Intent(getThis(), SettingsActivity.class));
                return true;
            default:
                return false;
        }
    }

    @Override public void pushFragment(Fragment fragment) {
        fragmentManager
                .beginTransaction()
                .replace(R.id.secondary_fragment_container, fragment, fragment.getClass().getSimpleName())
                .commit();
    }

    @Override public void popFragment() {
        fragmentManager.popBackStack();
    }

    @Override public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case REQUEST_CODE_CHECK_SETTINGS:
                switch (resultCode) {
                    case RESULT_OK:
                        // All required changes were successfully made
                        isRequestingLocationUpdates = true;
                        break;
                    case Activity.RESULT_CANCELED:
                        // The user was asked to change settings, but chose not to
                        Toast.makeText(getThis(), R.string.location_services_required, Toast.LENGTH_LONG).show();
                        isRequestingLocationUpdates = false;
                        break;
                    default:
                        break;
                }
                break;
            default:
                break;
        }
    }

    /**
     * PERMISSIONS AND STUFF
     */
    private boolean arePermissionsAllowed(Context applicationContext) {
        int fineLocationResult = ContextCompat.checkSelfPermission(applicationContext, ACCESS_FINE_LOCATION);
        int coarseLocationResult = ContextCompat.checkSelfPermission(applicationContext, ACCESS_COARSE_LOCATION);
        int internetResult = ContextCompat.checkSelfPermission(applicationContext, INTERNET);

        return fineLocationResult == PackageManager.PERMISSION_GRANTED &&
                coarseLocationResult == PackageManager.PERMISSION_GRANTED &&
                internetResult == PackageManager.PERMISSION_GRANTED;
    }

    private void requestPermissions() {
        ActivityCompat.requestPermissions(
                getThis(), new String[]{ACCESS_FINE_LOCATION, ACCESS_COARSE_LOCATION, INTERNET}, REQUEST_LOCATION_PERMISSIONS);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case REQUEST_LOCATION_PERMISSIONS:
                if (grantResults.length > 0) {
                    boolean fineLocationAccepted = grantResults[0] == PackageManager.PERMISSION_GRANTED;
                    boolean coarseLocationAccepted = grantResults[1] == PackageManager.PERMISSION_GRANTED;
                    boolean internetAccepted = grantResults[2] == PackageManager.PERMISSION_GRANTED;

                    if (fineLocationAccepted && coarseLocationAccepted && internetAccepted) {
//                        permissions are granted
                        getLastLocation();
                    } else {
//                        permissions are denied, show empty fragment
                        showEmptyFragment();
                    }
                }
//                else {
////                        permissions are denied, show empty fragment
//                    showEmptyFragment();
//                }
                break;
            default:
                break;
        }
    }

    private void showEmptyFragment() {
        pushFragment(LocationPermissionFragment.newInstance());
    }

    public class SectionsPagerAdapter extends FragmentStatePagerAdapter {

        private String cityName;

        SectionsPagerAdapter(FragmentManager fm, String cityName) {
            super(fm);
            this.cityName = cityName;
        }

        @Override public Fragment getItem(int position) {
            switch (position) {
                case 0:
                    return TodayForecastFragment.newInstance(cityName);
                case 1:
                    return TomorrowForecastFragment.newInstance(cityName);
                case 2:
                    return MultipleDayForecastFragment.newInstance(cityName);
                default:
                    return null;
            }
        }

        @Override public int getItemPosition(@NonNull Object object) {
            return POSITION_NONE;
        }

        @Override public int getCount() {
            return 3;
        }

        void updateCity(String cityName) {
            this.cityName = cityName;
            notifyDataSetChanged();
        }
    }
}