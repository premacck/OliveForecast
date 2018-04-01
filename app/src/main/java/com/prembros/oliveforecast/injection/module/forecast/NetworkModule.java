package com.prembros.oliveforecast.injection.module.forecast;

import android.content.Context;

import com.prembros.oliveforecast.injection.scope.ForecastScope;

import java.io.File;
import java.util.concurrent.TimeUnit;

import dagger.Module;
import dagger.Provides;
import okhttp3.Cache;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;

import static com.prembros.oliveforecast.utility.Constants.APIXU_BASE_URL;

/**
 *
 * Created by Prem$ on 3/30/2018.
 */

@Module
public class NetworkModule {

    /**
     * 10MB cache
     */
    @Provides @ForecastScope
    Cache getCache(File directory) {
        return new Cache(directory, 10 * 1024 * 1024);
    }

    @Provides @ForecastScope
    File getCacheFile(Context context) {
        return new File(context.getCacheDir(), "ResponseCache");
    }

//    @Provides @ForecastScope
//    HttpLoggingInterceptor getLoggingInterceptor() {
//        return new HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY);
//    }

    @Provides @ForecastScope
    OkHttpClient getOkHttpClientWithAuthToken(Cache cache) {
        return new OkHttpClient.Builder()
//                .addInterceptor(loggingInterceptor)
                .readTimeout(30, TimeUnit.SECONDS)
                .connectTimeout(20, TimeUnit.SECONDS)
                .cache(cache)
                .build();
    }

    @Provides @ForecastScope
    public Retrofit getRetrofit(OkHttpClient okHttpClient) {
        return new Retrofit.Builder()
                .baseUrl(APIXU_BASE_URL)
                .addConverterFactory(ScalarsConverterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .client(okHttpClient)
                .build();
    }
}