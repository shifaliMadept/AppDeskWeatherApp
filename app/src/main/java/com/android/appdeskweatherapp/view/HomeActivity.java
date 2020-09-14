package com.android.appdeskweatherapp.view;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;
import android.view.MenuItem;
import android.view.WindowManager;

import com.android.appdeskweatherapp.R;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import butterknife.BindView;
import butterknife.ButterKnife;

public class HomeActivity extends AppCompatActivity {
    @BindView(R.id.navigation)
    BottomNavigationView bottomNavigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_home);
        ButterKnife.bind(this);
        loadFragment(new HomeFragment());
        setNavigationListener();
    }

    private void setNavigationListener() {
        bottomNavigationView.getMenu().getItem(0).setChecked(true);
        BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
                = new BottomNavigationView.OnNavigationItemSelectedListener() {

            @Override
            public boolean onNavigationItemSelected(MenuItem item) {

                switch (item.getItemId()) {
                    case R.id.home:
                        item.setChecked(true);
                        loadFragment(new HomeFragment());
                        break;
                    case R.id.select_city:
                        item.setChecked(true);
                        loadFragment(new CityFragment());
                        break;
                    case R.id.report:
                        item.setChecked(true);
                        loadFragment(new HomeFragment());
                        break;
                    case R.id.profile:
                        item.setChecked(true);
                        loadFragment(new HomeFragment());
                        break;
                }
                return false;
            }
        };
        bottomNavigationView.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);

    }

    private void loadFragment(Fragment homeFragment) {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.frame_container, homeFragment);
        transaction.addToBackStack(null);
        transaction.commit();
    }
}
