package com.example.appsfinalproject.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

import com.example.appsfinalproject.R;
import com.example.appsfinalproject.fragments.owner.LocalStatsFragment;
import com.example.appsfinalproject.fragments.owner.LocalFragment;
import com.example.appsfinalproject.fragments.owner.ViewLocalsInMapsFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;

public class MainActivityOwner extends AppCompatActivity implements View.OnClickListener {

    private BottomNavigationView navigator;
    private ImageButton logoutBtnOwner;
    private LocalFragment localFragment;
    private ViewLocalsInMapsFragment viewLocalsInMapsFragment;
    private LocalStatsFragment localStatsFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_owner);
        getSupportActionBar().hide();
        getSupportActionBar().hide();
        localFragment = LocalFragment.newInstance();
        localStatsFragment = LocalStatsFragment.newInstance();
        viewLocalsInMapsFragment = ViewLocalsInMapsFragment.newInstance();
        logoutBtnOwner = findViewById(R.id.logoutBtnOwner);
        logoutBtnOwner.setOnClickListener(this);
        configureNavigator();
        navigator.setSelectedItemId(R.id.owner_shops_item); // hace click en el fragment y lo muestra
    }

    private void configureNavigator() {
        navigator = findViewById(R.id.main_nav_bar_owner);

        navigator.setOnNavigationItemSelectedListener(
                (menuItem)->{
                    switch(menuItem.getItemId()){
                        case R.id.owner_map_item:
                            showFragment(viewLocalsInMapsFragment);
                            break;
                        case R.id.owner_shops_item:
                            showFragment(localFragment);
                            break;
                        case R.id.owner_stats_general_item:
                            showFragment(localStatsFragment);
                            break;
                    }
                    return true; // le estoy diciendo que si estoy manejando la acción de la barra
                }
        );
    }

    public void showFragment(Fragment fragment) {
        // todas las actividades vienen con el fragmentManager solo lo debemos llamar
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.replace(R.id.mainFragmentContainerOwner, fragment);
        transaction.commit();
    }

    @Override
    public void onClick(View v) {
        switch(v.getId()){
            case R.id.logoutBtnOwner:
                FirebaseAuth.getInstance().signOut();
                Intent i = new Intent(this, LoginActivity.class);
                startActivity(i);
                break;
        }
    }
}