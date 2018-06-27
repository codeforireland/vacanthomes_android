package com.codeforireland.vacanthome.network;

import android.util.Log;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ServiceFactory {

    private static final String TAG = ServiceFactory.class.getSimpleName();

    private static OkHttpClient.Builder okHttpClient = new OkHttpClient.Builder();

    private static Retrofit.Builder builder =
            new Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create());

    public static <S> S createService(Class<S> seviceClass, String baseUrl) {

        Retrofit retrofit = builder
                .baseUrl(baseUrl)
                .client(okHttpClient.build()).build();
        Log.d(TAG, "base url: "+retrofit.baseUrl());
        return retrofit.create(seviceClass);
    }


}
