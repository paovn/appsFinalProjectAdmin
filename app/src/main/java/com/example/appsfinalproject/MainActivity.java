package com.example.appsfinalproject;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.Manifest;
import android.os.Bundle;

import com.example.appsfinalproject.fragments.AddProductFragment;
import com.example.appsfinalproject.fragments.ProductFragment;
import com.example.appsfinalproject.fragments.ViewProductFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MainActivity extends AppCompatActivity {

    private BottomNavigationView navigator;
    private ProductFragment productFragment;
    private AddProductFragment addProductFragment;
    private ViewProductFragment viewProductFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        productFragment = ProductFragment.newInstance();
        addProductFragment = AddProductFragment.newInstance();
        viewProductFragment = ViewProductFragment.newInstance();

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
        navigator = findViewById(R.id.navBarAdmin);

        navigator.setOnNavigationItemSelectedListener(
                (menuItem)->{
                    switch(menuItem.getItemId()){
                        case R.id.principalItem:
                            showFragment(productFragment);
                            break;
                        case R.id.aniadirItem:
                            showFragment(addProductFragment);
                            break;
                        case R.id.contabilidadItem:
                            break;
                    }
                    return true; // le estoy diciendo que si estoy manejando la acción de la barra
                }
        );
    }

    private void showFragment(Fragment fragment){
        // todas las actividades vienen con el fragmentManager solo lo debemos llamar
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.replace(R.id.fragmentContainerAdmin, fragment);
        transaction.commit();
    }
}