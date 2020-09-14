package com.android.appdeskweatherapp.repo;

import android.content.Context;
import android.widget.Toast;

import com.android.appdeskweatherapp.appUtil.AppConstants;
import com.android.appdeskweatherapp.callback.NetworkCallResponse;
import com.android.appdeskweatherapp.retrofit.AppApi;
import com.android.appdeskweatherapp.retrofit.RetrofitInstance;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class WeatherApi {

    private Context mContext;
    private NetworkCallResponse networkCallResponse;

    public WeatherApi(Context mContext) {
        this.mContext = mContext;
    }

    public void getWeather(String city, NetworkCallResponse networkCallResponse){
        this.networkCallResponse= networkCallResponse;
     AppApi webServiceClient = RetrofitInstance.buildClient(AppApi.class, AppConstants.BASE_URL);

     Call<ResponseBody> call = webServiceClient.getCurrentCityWeather(city,AppConstants.WEATHER_KEY);

     call.enqueue(new Callback<ResponseBody>() {
         @Override
         public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
             if (response.isSuccessful()) {
                 String resBody = null;
                 try {
                     resBody = response.body().string();
                     parseResult1(resBody);
                 } catch (IOException e) {
                     e.printStackTrace();
                 }

             } else {

                 String resBody = null;
                 try {
                     resBody = response.errorBody().string();
                     JSONObject jsonObject = new JSONObject(resBody);
                     if(jsonObject!=null){
                        if( jsonObject.has("message")){
                             Toast.makeText(mContext, jsonObject.getString("message"),Toast.LENGTH_LONG).show();
                         }
                     }
                 } catch (IOException e) {
                     e.printStackTrace();
                 } catch (JSONException e) {
                     e.printStackTrace();
                 }
                 networkCallResponse.callResponse(false,null,null);
             }
         }

         @Override
         public void onFailure(Call<ResponseBody> call, Throwable throwable) {

         }
     });

 }

    private void parseResult1(String resBody) {

        try {
            JSONObject jsonObject = new JSONObject(resBody);
            if(jsonObject.has("main")&&jsonObject.has("wind")){
                String temp= null, wind=null;
                JSONObject jsonObject1 = jsonObject.getJSONObject("main");
                JSONObject jsonObject2 = jsonObject.getJSONObject("wind");
                if(jsonObject1!=null) {
                    if (jsonObject1.has("temp")) {
                        temp = jsonObject1.getString("temp");
                    }
                }
                if(jsonObject2!=null){
                    if(jsonObject2.has("speed")){
                        wind= jsonObject2.getString("speed");
                    }
                }
                    networkCallResponse.callResponse(true,temp,wind);

            }
            networkCallResponse.callResponse(false,null,null);
        } catch (JSONException e) {
            e.printStackTrace();
            networkCallResponse.callResponse(false,null,null);
        }

    }

    public void getWeatherbyLatLong(String lat, String longi, NetworkCallResponse networkCallResponse){
        this.networkCallResponse = networkCallResponse;
        AppApi webServiceClient = RetrofitInstance.buildClient(AppApi.class, AppConstants.BASE_URL);

        Call<ResponseBody> call = webServiceClient.onceCallAPI(lat,longi,"metric",AppConstants.WEATHER_KEY);

        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if (response.isSuccessful()) {
                    String resBody = null;
                    try {
                        resBody = response.body().string();
                        parseResult(resBody);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                } else {
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable throwable) {
            }
        });

    }

    private void parseResult(String resBody) {
        try {
            JSONObject jsonObject = new JSONObject(resBody);
            if(jsonObject.has("current")){
                String temp= null, wind=null;
                JSONObject jsonObject1 = jsonObject.getJSONObject("current");
                if(jsonObject1!=null){
                    if(jsonObject1.has("temp")){
                      temp = jsonObject1.getString("temp");
                    }
                    if(jsonObject1.has("wind_speed")){
                     wind = jsonObject1.getString("wind_speed");
                    }
                    networkCallResponse.callResponse(true,temp,wind);
                }
            }
            networkCallResponse.callResponse(false,null,null);
        } catch (JSONException e) {
            e.printStackTrace();
            networkCallResponse.callResponse(false,null,null);
        }
    }
}
