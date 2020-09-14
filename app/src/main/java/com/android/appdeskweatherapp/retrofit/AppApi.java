package com.android.appdeskweatherapp.retrofit;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface AppApi {

    @GET("weather")
    Call<ResponseBody> getCurrentCityWeather(@Query("q") String q,
                                     @Query("appid") String appid);

    @GET("onecall")
    Call<ResponseBody> onceCallAPI(@Query("lat") String lat ,@Query("lon") String lon, @Query("units") String units,
                            @Query("appid") String appid);
}
