package com.android.appdeskweatherapp.view;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.LocationManager;
import android.os.Bundle;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.appdeskweatherapp.R;
import com.android.appdeskweatherapp.appUtil.AppConstants;
import com.android.appdeskweatherapp.appUtil.AppSharedPref;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends AppCompatActivity implements
        ActivityCompat.OnRequestPermissionsResultCallback {
    private static final int LOCATION_PERMISSION_REQUEST_CODE = 1;
    private AppSharedPref appSharedPref;
    private boolean permissionGranted;
    @Nullable
    @BindView(R.id.etUsername)
    EditText etUsername;
    @BindView(R.id.btnSubmit)
    Button btnSubmit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide(); // hide the title bar
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN); //enable full screen
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        appSharedPref = new AppSharedPref(this);
        checkIfLoggdIn();

    }

    private void checkIfLoggdIn() {
        if(appSharedPref.getValue(AppConstants.USERNAME)!=null){
            startActivity(new Intent(this, HomeActivity.class));
            finish();
        }else{
            checkLocationPermission();
        }
    }

    private void checkLocationPermission() {

        if (ContextCompat.checkSelfPermission(MainActivity.this,
                Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED){
            if (ActivityCompat.shouldShowRequestPermissionRationale(MainActivity.this,
                    Manifest.permission.ACCESS_FINE_LOCATION)){
                ActivityCompat.requestPermissions(MainActivity.this,
                        new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, LOCATION_PERMISSION_REQUEST_CODE);
            }else{
                ActivityCompat.requestPermissions(MainActivity.this,
                        new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, LOCATION_PERMISSION_REQUEST_CODE);
            }
        }
    }


    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions,
                                           int[] grantResults){
        switch (requestCode){
            case 1: {
                if (grantResults.length>0 && grantResults[0] == PackageManager.PERMISSION_GRANTED){
                    if (ContextCompat.checkSelfPermission(MainActivity.this,
                            Manifest.permission.ACCESS_FINE_LOCATION)==PackageManager.PERMISSION_GRANTED){
                        permissionGranted= true;
                    }
                }else{
                    permissionGranted = false;
                      }
                return;
            }
        }
    }

    @OnClick(R.id.btnSubmit) void submit() {
        if(permissionGranted) {
            if (etUsername.getText().toString() != null) {
                appSharedPref.putValue(AppConstants.USERNAME, etUsername.getText().toString());
                startActivity(new Intent(this, HomeActivity.class));
            } else {
                Toast.makeText(this, getString(R.string.emptyfield), Toast.LENGTH_SHORT).show();

            }
        }
            else {
                checkLocationPermission();
            }

    }
}
