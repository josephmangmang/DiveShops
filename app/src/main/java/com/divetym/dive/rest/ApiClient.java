package com.divetym.dive.rest;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by kali_root on 3/28/2017.
 */

public class ApiClient {
    private static final String BASE_ENDPOINT_URL_LOCAL = "http://192.168.254.6/v1/";
    private static final String BASE_ENPOINT_URL_ONLINE = "http://128.199.101.93/v1/";
    public static boolean sServerLocal = true;
    public static boolean sUpdateRetrofit = false;

    private static Retrofit sRetrofit = null;

    public static Retrofit getClient() {
        if (sRetrofit == null || sUpdateRetrofit) {
            sRetrofit = new Retrofit.Builder()
                    .baseUrl(sServerLocal ? BASE_ENDPOINT_URL_LOCAL : BASE_ENPOINT_URL_ONLINE)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
            sUpdateRetrofit = false;
        }
        return sRetrofit;
    }

    public static ApiInterface getApiInterface() {
        return getClient().create(ApiInterface.class);
    }
}
