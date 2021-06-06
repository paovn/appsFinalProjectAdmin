package com.example.appsfinalproject.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.Manifest;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.example.appsfinalproject.R;
import com.example.appsfinalproject.fragments.admin.AddProductFragment;
import com.example.appsfinalproject.fragments.admin.ProductFragment;
import com.example.appsfinalproject.fragments.admin.ViewProductFragment;
import com.example.appsfinalproject.model.AdministradorGeneral;
import com.example.appsfinalproject.model.Tipo_usuario;
import com.example.appsfinalproject.model.Usuario;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.UUID;

public class MainActivityAdmin extends AppCompatActivity {

    private Usuario usuario;

    private BottomNavigationView navigator;
    private ProductFragment productFragment;
    private AddProductFragment addProductFragment;
    private ViewProductFragment viewProductFragment;
    private FirebaseFirestore db;
    private FirebaseAuth auth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_admin);

        productFragment = ProductFragment.newInstance();
        addProductFragment = AddProductFragment.newInstance();
        viewProductFragment = ViewProductFragment.newInstance();
        db = FirebaseFirestore.getInstance();
        auth = FirebaseAuth.getInstance();
        requestPermissions();
        configureNavigator();
        //createUser();
        showFragment(productFragment);
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

    private void showFragment(Fragment fragment) {
        // todas las actividades vienen con el fragmentManager solo lo debemos llamar
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.replace(R.id.fragmentContainerAdmin, fragment);
        transaction.commit();
    }


    public void createUser(){
        auth.createUserWithEmailAndPassword(
                "admin@admin.com",
                "admon169"
        ).addOnSuccessListener(
                command -> {
                    // Aquí ya estamos loggeados
                    String id = auth.getCurrentUser().getUid();
                    Usuario user = new AdministradorGeneral(
                            "admin@admin.com",
                            "admon169",
                            id,
                            Tipo_usuario.ADMINISTRADOR_G
                    );
                    saveUser(user);
                }
        ).addOnFailureListener(
                command -> {
                    Toast.makeText(this, command.getMessage(), Toast.LENGTH_LONG).show();
                }
        );
    }

    private void saveUser(Usuario user){

        db.collection("users")
                .document(user.getId()).set(user)
                .addOnSuccessListener(
                        dbtask -> {
                        }
                ).addOnFailureListener(task->{
                    Log.e(">>", "errooooooooooooor");
        });
    }
}