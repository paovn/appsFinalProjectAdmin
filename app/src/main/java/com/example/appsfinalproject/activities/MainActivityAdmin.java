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
import android.view.View;
import android.widget.ImageButton;

import com.example.appsfinalproject.R;
import com.example.appsfinalproject.fragments.admin.AccountingListFragment;
import com.example.appsfinalproject.fragments.admin.AddProductFragment;
import com.example.appsfinalproject.fragments.admin.ProductFragment;
import com.example.appsfinalproject.fragments.admin.ViewProductFragment;
import com.example.appsfinalproject.fragments.owner.AddSpendsAndIncomeFragment;
import com.example.appsfinalproject.fragments.owner.LocalStatsFragment;
import com.example.appsfinalproject.fragments.owner.SpendsAndIncomeFragment;
import com.example.appsfinalproject.model.AdministradorGeneral;
import com.example.appsfinalproject.model.Tipo_usuario;
import com.example.appsfinalproject.model.Usuario;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.UUID;

public class MainActivityAdmin extends AppCompatActivity implements View.OnClickListener {

    private static AdministradorGeneral usuarioMayor;

    private BottomNavigationView navigator;
    private ImageButton logoutBtnAdmin;
    private ProductFragment productFragment;
    private AddProductFragment addProductFragment;
    private ViewProductFragment viewProductFragment;
    private SpendsAndIncomeFragment spendsAndIncomeFragment;
    private AccountingListFragment accountingListFragment;
    private AddSpendsAndIncomeFragment addSpendsAndIncomeFragment;
    private LocalStatsFragment localStatsFragment;
    private FirebaseFirestore db;
    private FirebaseAuth auth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_admin);
        productFragment = ProductFragment.newInstance();
        addProductFragment = AddProductFragment.newInstance(this);
        viewProductFragment = ViewProductFragment.newInstance();
        spendsAndIncomeFragment = SpendsAndIncomeFragment.newInstance();
        accountingListFragment = AccountingListFragment.newInstance();
        addSpendsAndIncomeFragment = AddSpendsAndIncomeFragment.newInstance();
        localStatsFragment = LocalStatsFragment.newInstance();
        logoutBtnAdmin = findViewById(R.id.logoutBtnAdmin);
        logoutBtnAdmin.setOnClickListener(this);
        db = FirebaseFirestore.getInstance();
        auth = FirebaseAuth.getInstance();

        requestPermissions();
        configureNavigator();
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
                        case R.id.productsItem:
                            showFragment(productFragment);
                            break;
                        case R.id.aniadirItem:
                            showFragment(addProductFragment);
                            break;
                        case R.id.addRegisterItem:
                            showFragment(addSpendsAndIncomeFragment);
                            break;
                        case R.id.accountingItem:
                            showFragment(accountingListFragment);
                        case R.id.estadisticasItem:
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
        transaction.replace(R.id.fragmentContainerAdmin, fragment);
        transaction.commit();
    }

    public static Usuario createAdmin(){
        String id = UUID.randomUUID().toString();

        AdministradorGeneral user = new AdministradorGeneral(
                "admin@admin.com",
                id,
                Tipo_usuario.ADMINISTRADOR_G
        );
        FirebaseAuth.getInstance().createUserWithEmailAndPassword(user.getUsername(), "admon169")
                .addOnSuccessListener(
                        command -> {
                            Log.e(">>>", "Admin fue registrado en FireBaseAuth");
                            user.setId(FirebaseAuth.getInstance().getCurrentUser().getUid());
                            saveAdminInFireBaseDatabase(user);
                        }
                ).addOnFailureListener(
                        command -> {
                            Log.e(">>>", "No se pudo registrar el admin en FireBaseAuth: " + command.getMessage());
                        }
        );

        return user;
    }

    public static void saveAdminInFireBaseDatabase(Usuario user){
        FirebaseFirestore.getInstance().collection("users")
                .document(user.getId()).set(user)
                .addOnSuccessListener(
                        dbtask -> {
                            Log.e(">>>", "Admin registrado/actualizado en la base de datos");
                        }
                ).addOnFailureListener(
                        task -> {
                            Log.e(">>>", "Error al registrar/actualizar al Admin en la base de datos: " + task.getMessage());
                        });
    }

    public BottomNavigationView getNavigator() {
        return navigator;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.logoutBtnAdmin:
                FirebaseAuth.getInstance().signOut();
                Intent i = new Intent(this, LoginActivity.class);
                startActivity(i);
                break;
        }
    }



}