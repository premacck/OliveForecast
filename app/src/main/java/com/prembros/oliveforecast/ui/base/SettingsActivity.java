package com.prembros.oliveforecast.ui.base;

import android.os.Bundle;
import android.widget.Spinner;

import com.prembros.oliveforecast.R;
import com.prembros.oliveforecast.base.BaseActivity;
import com.prembros.oliveforecast.ui.customviews.CustomCheckedTextView;
import com.prembros.oliveforecast.utility.Annotations.Unit;

import org.jetbrains.annotations.Contract;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.OnItemSelected;
import butterknife.Unbinder;

import static com.prembros.oliveforecast.utility.Annotations.Unit.IMPERIAL;
import static com.prembros.oliveforecast.utility.Annotations.Unit.METRIC;
import static com.prembros.oliveforecast.utility.SharedPrefs.getForecastLimit;
import static com.prembros.oliveforecast.utility.SharedPrefs.getUnitType;
import static com.prembros.oliveforecast.utility.SharedPrefs.setForecastLimit;
import static com.prembros.oliveforecast.utility.SharedPrefs.setUnitType;

public class SettingsActivity extends BaseActivity {

    @BindView(R.id.metric) CustomCheckedTextView metric;
    @BindView(R.id.imperial) CustomCheckedTextView imperial;
    @BindView(R.id.forecast_limit_selector) Spinner forecastLimitSelector;

    private Unbinder unbinder;

    @Contract(value = " -> !null", pure = true) private SettingsActivity getThis() {
        return this;
    }

    @Override protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        unbinder = ButterKnife.bind(this);

        toggleTempUnit(getUnitType(getThis()), false);
        forecastLimitSelector.setSelection(getForecastLimit(getThis()) - 1);
    }

    @OnItemSelected(R.id.forecast_limit_selector) public void limitChanged(int position) {
        setForecastLimit(getThis(), position + 1);
    }

    @OnClick(R.id.metric) public void metricToggled() {
        toggleTempUnit(!metric.isChecked() ? METRIC : IMPERIAL, true);
    }

    @OnClick(R.id.imperial) public void imperialToggled() {
        toggleTempUnit(!imperial.isChecked() ? IMPERIAL : METRIC, true);
    }

    @OnClick(R.id.up_btn) public void goBack() {
        onBackPressed();
    }

    private void toggleTempUnit(@Unit int unitType, boolean isAction) {
        if (isAction) {
            metric.setChecked(!metric.isChecked());
            imperial.setChecked(!imperial.isChecked());
        }
        switch (unitType) {
            case Unit.METRIC:
                metric.setBackgroundResource(R.drawable.bg_btn_accent_curved_left);
                imperial.setBackgroundResource(R.drawable.bg_btn_white_curved_right);
                break;
            case Unit.IMPERIAL:
                metric.setBackgroundResource(R.drawable.bg_btn_white_curved_left);
                imperial.setBackgroundResource(R.drawable.bg_btn_accent_curved_right);
                break;
        }
        setUnitType(getThis(), unitType);
    }

    @Override protected void onDestroy() {
        super.onDestroy();
        if (unbinder != null) unbinder.unbind();
    }
}