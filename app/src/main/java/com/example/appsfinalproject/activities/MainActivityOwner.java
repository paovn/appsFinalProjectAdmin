package com.example.appsfinalproject.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;

import com.example.appsfinalproject.R;
import com.example.appsfinalproject.fragments.owner.ShopFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MainActivityOwner extends AppCompatActivity {

    private BottomNavigationView navigator;

    private ShopFragment shopFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_owner);

        shopFragment = ShopFragment.newInstance();
        configureNavigator();
        navigator.setSelectedItemId(R.id.owner_shops_item); // hace click en el fragment y lo muestra
    }

    private void configureNavigator() {
        navigator = findViewById(R.id.main_nav_bar_owner);

        navigator.setOnNavigationItemSelectedListener(
                (menuItem)->{
                    switch(menuItem.getItemId()){
                        case R.id.owner_map_item:
                            // TODO
                            break;
                        case R.id.owner_shops_item:
                            showFragment(shopFragment);
                            break;
                        case R.id.owner_stats_general_item:
                            // TODO
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
}