package com.android.appdeskweatherapp.retrofit;

import com.android.appdeskweatherapp.appUtil.AppConstants;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitInstance {
    private static Retrofit retrofit;

    public static Retrofit getClient() {
        if (retrofit == null) {
            retrofit = new retrofit2.Retrofit.Builder()
                    .baseUrl(AppConstants.BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }
        return retrofit;

    }

    public static <object> object buildClient(Class<object> serviceClass, String baseUrl) {

        OkHttpClient httpClient = new OkHttpClient.Builder()
                .build();

        Retrofit.Builder builder =
                new Retrofit.Builder()
                        .baseUrl(AppConstants.BASE_URL)
                        .addConverterFactory(GsonConverterFactory.create());
        //.addCallAdapterFactory(RxJava2CallAdapterFactory.create());

        Retrofit retrofit = builder.client(httpClient.newBuilder().connectTimeout(10, TimeUnit.SECONDS)
                .readTimeout(120, TimeUnit.SECONDS).writeTimeout(120, TimeUnit.SECONDS).build()).build();
        return retrofit.create(serviceClass);
    }

}
