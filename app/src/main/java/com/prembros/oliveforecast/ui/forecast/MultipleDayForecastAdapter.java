package com.prembros.oliveforecast.ui.forecast;

import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.prembros.oliveforecast.R;
import com.prembros.oliveforecast.base.BaseFragment;
import com.prembros.oliveforecast.base.BaseRecyclerView;
import com.prembros.oliveforecast.data.model.Forecastday;
import com.prembros.oliveforecast.ui.customviews.CustomTextView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.prembros.oliveforecast.utility.Constants.HTTP;
import static com.prembros.oliveforecast.utility.ViewUtils.getDateHeader;
import static com.prembros.oliveforecast.utility.ViewUtils.getSuitableMaxTemp;
import static com.prembros.oliveforecast.utility.ViewUtils.getSuitableMinTemp;

/**
 *
 * Created by Prem$ on 3/30/2018.
 */

public class MultipleDayForecastAdapter extends BaseRecyclerView.Adapter {

    private List<Forecastday> forecastDayList;
    private BaseFragment fragment;

    MultipleDayForecastAdapter(BaseFragment fragment) {
        this.fragment = fragment;
        forecastDayList = new ArrayList<>();
    }

    @NonNull @Override public BaseRecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new MultipleDayForecastViewHolder(
                LayoutInflater.from(parent.getContext()).inflate(R.layout.item_forecast, parent, false)
        );
    }

    @Override public int getItemCount() {
        return forecastDayList.size();
    }

    @Override public void notifyDataChanged() {
        fragment.getParentActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                notifyDataSetChanged();
            }
        });
    }

    void updateList(List<Forecastday> forecastDays) {
        forecastDayList.clear();
        forecastDayList.addAll(forecastDays);
        notifyDataChanged();
    }

    class MultipleDayForecastViewHolder extends BaseRecyclerView.ViewHolder {

        @BindView(R.id.forecast_date) CustomTextView date;
        @BindView(R.id.forecast_condition) CustomTextView condition;
        @BindView(R.id.weather_icon) ImageView icon;
        @BindView(R.id.forecast_max) CustomTextView max;
        @BindView(R.id.forecast_min) CustomTextView min;
        Forecastday forecastday;

        MultipleDayForecastViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        @Override public void bind() {
            forecastday = forecastDayList.get(getAdapterPosition());
            try {
                Glide.with(fragment)
                        .load(HTTP + forecastday.getDay().getCondition().getIcon())
                        .into(icon);
            } catch (Exception e) {
                e.printStackTrace();
            }
            date.setText(getDateHeader(forecastday.getDateEpoch()));
            condition.setText(forecastday.getDay().getCondition().getText());

            max.setText(getSuitableMaxTemp(fragment.getContext(),
                    forecastday.getDay().getMaxTempC(), forecastday.getDay().getMaxTempF()));
            min.setText(getSuitableMinTemp(fragment.getContext(),
                    forecastday.getDay().getMinTempC(), forecastday.getDay().getMinTempF()));
        }
    }
}