package com.example.appsfinalproject.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.Manifest;
import android.os.Bundle;

import com.example.appsfinalproject.R;
import com.example.appsfinalproject.fragments.owner.LocalStatsFragment;
import com.example.appsfinalproject.fragments.owner.OwnerInventoryFragment;
import com.example.appsfinalproject.fragments.owner.SpendsAndIncomeFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class LocalActivityOwner extends AppCompatActivity {

    private BottomNavigationView navigator;

    private OwnerInventoryFragment ownerInventoryFragment;
    private SpendsAndIncomeFragment spendsAndIncomeFragment;
    private LocalStatsFragment localStatsFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_local_owner);

        ownerInventoryFragment = OwnerInventoryFragment.newInstance();
        spendsAndIncomeFragment = SpendsAndIncomeFragment.newInstance();
        localStatsFragment = LocalStatsFragment.newInstance();

        requestPermissions();
        configureNavigator();
    }

    private void requestPermissions() {
        ActivityCompat.requestPermissions(this, new String[]{
                Manifest.permission.CAMERA,
                Manifest.permission.WRITE_EXTERNAL_STORAGE,
                Manifest.permission.READ_EXTERNAL_STORAGE,
                Manifest.permission.ACCESS_COARSE_LOCATION,
                Manifest.permission.ACCESS_FINE_LOCATION
        }, 12345);
    }

    private void configureNavigator() {
        navigator = findViewById(R.id.navBarOwner);

        navigator.setOnNavigationItemSelectedListener(
                (menuItem)->{
                    switch(menuItem.getItemId()){
                        case R.id.owner_acountability_item:
                            showFragment(spendsAndIncomeFragment);
                            break;
                        case R.id.owner_inventory_item:
                            showFragment(ownerInventoryFragment);
                            break;
                        case R.id.owner_stats_local_item:
                            showFragment(localStatsFragment);
                            break;
                    }
                    return true; // le estoy diciendo que si estoy manejando la acción de la barra
                }
        );
    }

    private void showFragment(Fragment fragment) {
        // todas las actividades vienen con el fragmentManager solo lo debemos llamar
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.replace(R.id.fragmentContainerOwnerAccountingAndStatistics, fragment);
        transaction.commit();
    }
}