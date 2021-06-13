package com.example.appsfinalproject.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.Manifest;
import android.os.Bundle;
import android.util.Log;

import com.example.appsfinalproject.R;
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

public class MainActivityAdmin extends AppCompatActivity {

    private static AdministradorGeneral usuarioMayor;

    private BottomNavigationView navigator;
    private ProductFragment productFragment;
    private AddProductFragment addProductFragment;
    private ViewProductFragment viewProductFragment;
    private SpendsAndIncomeFragment spendsAndIncomeFragment;
    private AddSpendsAndIncomeFragment addSpendsAndIncomeFragment;
    private LocalStatsFragment localStatsFragment;
    private FirebaseFirestore db;
    private FirebaseAuth auth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_admin);
        getSupportActionBar().hide();
        productFragment = ProductFragment.newInstance();
        addProductFragment = AddProductFragment.newInstance(this);
        viewProductFragment = ViewProductFragment.newInstance();
        spendsAndIncomeFragment = SpendsAndIncomeFragment.newInstance();
        addSpendsAndIncomeFragment = AddSpendsAndIncomeFragment.newInstance();
        localStatsFragment = LocalStatsFragment.newInstance();

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
                        case R.id.contabilidadItem2:
                            showFragment(addSpendsAndIncomeFragment);
                            break;
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

/*
    public static void saveUserLocal() {
        String id = UUID.randomUUID().toString();
        AdministradorLocal userLocal = new AdministradorLocal(
                "245f0a73-db9b-472a-a5a9-450571553f72",
                "local1@local.com",
                "xlocal1",
                id,
                Tipo_usuario.ADMINISTRADOR_L
        );

        saveUser(userLocal, false);
        Log.e(">>>", "aniade el admon de local1");
    }

    public static void saveLocal(Local local, Usuario localUser){
        FirebaseFirestore.getInstance().collection("local")
                .document(local.getId()).set(local)
                .addOnSuccessListener(
                        dbtask -> {
                            updateLocalAccounting(localUser);
                        }
                ).addOnFailureListener(task->{
            Log.e(">>", "errooooooooooooor");
        });
    }

    public static void updateLocalAccounting(Usuario localUser){
        FirebaseFirestore.getInstance().collection("users").whereEqualTo("id", localUser.getId()).get().addOnSuccessListener(
                command -> {
                    AdministradorLocal user = command.getDocuments().get(0).toObject(AdministradorLocal.class);
                    String idLocal = user.getIdLocal();

                    FirebaseFirestore.getInstance().collection("local").whereEqualTo("id", idLocal).get().addOnSuccessListener(
                            command1 -> {
                                ContabilidadLocal contabilidad1 = new ContabilidadLocal(UUID.randomUUID().toString());
                                Log.e(">>>", "Antes del out of bounds el id del local es: " + idLocal);
                                Local local = command1.getDocuments().get(0).toObject(Local.class);
                                local.setContabilidad(contabilidad1);
                                updateLocal(local);
                                updateAccounting(contabilidad1);
                            }
                    ).addOnFailureListener(
                            command2 ->{
                                Log.e(">>>", "Falló obteniendo el local");
                            }
                    );
                }
        ).addOnFailureListener(
                command222 ->{
                    Log.e(">>>", "Falló obteniendo el usuario");
                }
        );

    }

    public static void updateAccounting(ContabilidadLocal contabilidad1) {
        FirebaseFirestore.getInstance().collection("accounting")
                .document(contabilidad1.getId()).set(contabilidad1)
                .addOnSuccessListener(
                        dbtask -> {
                            Log.e(">>>", "todo bien agregando la contabilidad");
                        }
                ).addOnFailureListener(task->{
            Log.e(">>>", "errooooooooooooor agregando contabilidad");
        });
    }

    public static void updateLocal(Local local) {
        FirebaseFirestore.getInstance().collection("local").document(local.getId()).set(local).addOnSuccessListener(
                command -> {
                    Log.e(">>>>", "local id: " + local.getId());
                }).addOnFailureListener(
                command -> {
                    Log.e(">>>", "Falló en la tercera");
                }
        );
    }
    */

}