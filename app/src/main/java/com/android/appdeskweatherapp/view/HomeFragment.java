package com.android.appdeskweatherapp.view;


import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.android.appdeskweatherapp.R;
import com.android.appdeskweatherapp.callback.NetworkCallResponse;
import com.android.appdeskweatherapp.repo.WeatherApi;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * A simple {@link Fragment} subclass.
 */
public class HomeFragment extends Fragment implements NetworkCallResponse {
    private Unbinder unbinder;
    private Activity activity;
    private LocationManager locationManager;
    private  Location loc;
    @BindView(R.id.tv_temp)
    TextView tv_temp;
    @BindView(R.id.tv_city)
    TextView tv_city;

    public HomeFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_home2, container, false);
        unbinder = ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initView();
    }

    private void initView() {

        locationManager = (LocationManager) getActivity().getSystemService(Context.LOCATION_SERVICE);
        if (getActivity().checkSelfPermission(Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && getActivity().checkSelfPermission(Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            return;

        }
        loc = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
        String city= getCityName();
        tv_city.setText(city);
        getWeatherdetails();


    }

    private String getCityName() {
        Geocoder geocoder = new Geocoder(activity, Locale.getDefault());
        List<Address> addresses = null;
        try {
            addresses = geocoder.getFromLocation(loc.getLatitude(), loc.getLongitude(), 1);
        } catch (IOException e) {
            e.printStackTrace();
        }
        String cityName = addresses.get(0).getAddressLine(0);
        String stateName = addresses.get(0).getAddressLine(1);
        String countryName = addresses.get(0).getAddressLine(2);
        return cityName;
    }

    private void getWeatherdetails() {
        WeatherApi weatherApi = new WeatherApi(activity);
        weatherApi.getWeatherbyLatLong(String.valueOf(loc.getLatitude()),String.valueOf(loc.getLongitude()),this);
    }




    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        this.activity = (Activity) context;
    }

    @Override
    public void callResponse(Boolean response, String temp, String wind) {
        if(response){
            tv_temp.setText((temp + "  \u2103")+"\n Wind : "+wind);
        }
    }
}
