package com.android.appdeskweatherapp.view;


import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.android.appdeskweatherapp.R;
import com.android.appdeskweatherapp.appUtil.AppConstants;
import com.android.appdeskweatherapp.callback.NetworkCallResponse;
import com.android.appdeskweatherapp.repo.WeatherApi;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

/**
 * A simple {@link Fragment} subclass.
 */
public class CityFragment extends Fragment implements NetworkCallResponse {

    private Unbinder unbinder;
    private Activity activity;
    private LocationManager locationManager;
    private Location loc;
    @BindView(R.id.et_city)
    EditText et_city;
    @BindView(R.id.btnSubmit)
    Button Submit;
    @BindView(R.id.pb_progress)
    ProgressBar progressBar;

    public CityFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_city, container, false);
        unbinder = ButterKnife.bind(this, view);
        return view;
    }

    @OnClick(R.id.btnSubmit) void submitCityName() {

            if (et_city.getText().toString() != null) {
               getWeatherdetails(et_city.getText().toString());
            } else {
                Toast.makeText(activity, getString(R.string.emptyCity), Toast.LENGTH_SHORT).show();

            }

    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }

    private void getWeatherdetails( String city) {
        progressBar.setVisibility(View.VISIBLE);
        WeatherApi weatherApi = new WeatherApi(activity);
        weatherApi.getWeather(city,this);
    }




    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        this.activity = (Activity) context;
    }

    @Override
    public void callResponse(Boolean response, String temp, String wind) {
        progressBar.setVisibility(View.GONE);

    }

}
