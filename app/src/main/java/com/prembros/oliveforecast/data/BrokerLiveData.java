package com.prembros.oliveforecast.data;

import android.arch.lifecycle.LifecycleOwner;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MediatorLiveData;
import android.arch.lifecycle.Observer;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.prembros.oliveforecast.data.viewmodel.ForecastViewModel;

/**
 * Created by Prem$ on 3/10/2018.
 *
 * A custom {@link MediatorLiveData} class which is useful for setting up observers efficiently in
 * {@link ForecastViewModel}
 *
 * @param <T> The type of data to be held by this instance
 */

public class BrokerLiveData<T> extends MediatorLiveData<T> {

    /**
     * <p> Use this method to observe to the {@link LiveData} emitted by the API call.</p>
     * <p> This method adds the {@link LiveData} returned by the API call
     *     and upon completion, handles the changes and disposes off the source.</p>
     * @param liveData the API call returning the suitable {@link LiveData <T>}.
     */
    public void observeOn(final LiveData<T> liveData) {
        try {
            this.addSource(
                    liveData,
                    new Observer<T>() {
                        @Override
                        public void onChanged(@Nullable T t) {
                            BrokerLiveData.this.removeSource(liveData);
                            BrokerLiveData.this.setValue(t);
                        }
                    }
            );
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void removeObserverAndBeDoneWith(@NonNull Observer<T> observer) {
        super.removeObserver(observer);
        setValue(null);
    }

    public void removeObserverAndBeDoneWith(@NonNull LifecycleOwner owner) {
        super.removeObservers(owner);
        setValue(null);
    }
}