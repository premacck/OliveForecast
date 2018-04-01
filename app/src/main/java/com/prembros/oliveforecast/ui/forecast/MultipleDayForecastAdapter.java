package com.prembros.oliveforecast.ui.forecast;

import android.graphics.drawable.Drawable;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.request.target.Target;
import com.prembros.oliveforecast.R;
import com.prembros.oliveforecast.base.BaseFragment;
import com.prembros.oliveforecast.base.BaseRecyclerView;
import com.prembros.oliveforecast.data.model.Forecastday;
import com.prembros.oliveforecast.ui.customviews.CustomTextView;
import com.prembros.oliveforecast.ui.customviews.ShimmerLinearLayout;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.bumptech.glide.load.engine.DiskCacheStrategy.RESOURCE;
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

        @BindView(R.id.root_layout) ShimmerLinearLayout shimmerLinearLayout;
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
            shimmerize();
            try {
                Glide.with(fragment)
                        .load(HTTP + forecastday.getDay().getCondition().getIcon())
                        .apply(RequestOptions.diskCacheStrategyOf(RESOURCE).placeholder(R.drawable.image_view_shimmer))
                        .listener(new RequestListener<Drawable>() {
                            @Override public boolean onLoadFailed(@Nullable GlideException e, Object model,
                                                                  Target<Drawable> target, boolean isFirstResource) {
                                deShimmerize();
                                return false;
                            }

                            @Override public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target,
                                                                     DataSource dataSource, boolean isFirstResource) {
                                deShimmerize();
                                return false;
                            }
                        })
                        .into(icon);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        private void setTexts() {
            date.setText(getDateHeader(forecastday.getDateEpoch()));
            condition.setText(forecastday.getDay().getCondition().getText());

            max.setText(getSuitableMaxTemp(fragment.getContext(),
                    forecastday.getDay().getMaxTempC(), forecastday.getDay().getMaxTempF()));
            min.setText(getSuitableMinTemp(fragment.getContext(),
                    forecastday.getDay().getMinTempC(), forecastday.getDay().getMinTempF()));
        }

        private void shimmerize() {
            shimmerLinearLayout.startShimmerAnimation();
            date.setText("");
            condition.setText("");
            max.setText("");
            min.setText("");
        }

        private void deShimmerize() {
            shimmerLinearLayout.stopShimmerAnimation();
            setTexts();
        }
    }
}