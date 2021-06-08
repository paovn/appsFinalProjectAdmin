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
import com.example.appsfinalproject.fragments.owner.AddSpendsAndIncomeFragment;
import com.example.appsfinalproject.fragments.owner.SpendsAndIncomeFragment;
import com.example.appsfinalproject.model.AdministradorGeneral;
import com.example.appsfinalproject.model.AdministradorLocal;
import com.example.appsfinalproject.model.ContabilidadLocal;
import com.example.appsfinalproject.model.Inventario;
import com.example.appsfinalproject.model.Local;
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
    private SpendsAndIncomeFragment spendsAndIncomeFragment; //A lo mejor hay que quitar esto de aqui XD
    private AddSpendsAndIncomeFragment addSpendsAndIncomeFragment;
    private FirebaseFirestore db;
    private FirebaseAuth auth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_admin);

        productFragment = ProductFragment.newInstance();
        addProductFragment = AddProductFragment.newInstance();
        viewProductFragment = ViewProductFragment.newInstance();
        spendsAndIncomeFragment = SpendsAndIncomeFragment.newInstance();
        addSpendsAndIncomeFragment = addSpendsAndIncomeFragment.newInstance();
        db = FirebaseFirestore.getInstance();
        auth = FirebaseAuth.getInstance();
        requestPermissions();
        configureNavigator();
        //saveUser(createUser());
        //saveUserLocal();
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
                            showFragment(spendsAndIncomeFragment);
                            break;
                        case R.id.contabilidadItem2:
                            showFragment(addSpendsAndIncomeFragment);
                            break;
                        case R.id.estadisticasItem:
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


    public Usuario createUser(){
        /*auth.createUserWithEmailAndPassword(
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
        );*/
        AdministradorGeneral user2 = new AdministradorGeneral(
                "admin@admin.com",
                "admon169",
                "xgs59h6q9APWAeW36tGY8Ygh2tb2",
                Tipo_usuario.ADMINISTRADOR_G
        );

        Inventario inventario1 = new Inventario();
        //saveInventario(inventario1);
        //ContabilidadLocal contabilidad1 = new ContabilidadLocal();   //Tener en cuenta crearlo con contabilidad para proximos dummies

        String idLocal = UUID.randomUUID().toString();
        Local local1 = new Local("Local1", "Carlos", "3259996452",inventario1,idLocal );
        //local1.setContabilidad(contabilidad1);
        saveLocal(local1);
        user2.getIdLocales().add(local1.getId());

        return user2;


    }
    private void saveUserLocal() {
        String id = UUID.randomUUID().toString();
        AdministradorLocal userLocal = new AdministradorLocal(
                "245f0a73-db9b-472a-a5a9-450571553f72",
                "local1@local.com",
                "xlocal1",
                id,
                Tipo_usuario.ADMINISTRADOR_L
        );
        db.collection("users").document(id).set(userLocal).
                addOnSuccessListener(
                dbtask -> {
                }
            ).addOnFailureListener(task-> {
            Log.e(">>", "errooooooooooooor");
        });

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


    private void saveLocal(Local local){
        db.collection("local")
                .document(local.getId()).set(local)
                .addOnSuccessListener(
                        dbtask -> {
                        }
                ).addOnFailureListener(task->{
            Log.e(">>", "errooooooooooooor");
        });
    }

    private void updateLocalAccounting(){
        db.collection("users").whereEqualTo("id", "152091e6-71b5-4230-8743-9c4616297cf4").get().addOnSuccessListener(
                command -> {
                    AdministradorLocal user = command.getDocuments().get(0).toObject(AdministradorLocal.class);
                    String idLocal = user.getIdLocal();

                    db.collection("local").whereEqualTo("id", idLocal).get().addOnSuccessListener(
                            command1 -> {
                                ContabilidadLocal contabilidad1 = new ContabilidadLocal(UUID.randomUUID().toString());
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

    private void updateAccounting(ContabilidadLocal contabilidad1) {
        db.collection("accounting")
                .document(contabilidad1.getId()).set(contabilidad1)
                .addOnSuccessListener(
                        dbtask -> {
                        }
                ).addOnFailureListener(task->{
            Log.e(">>", "errooooooooooooor agregando contabilidad");
        });
    }

    private void updateLocal(Local local){
        db.collection("local").document(local.getId()).set(local).addOnSuccessListener(
                command -> {
                    Log.e(">>>>" ,"local id: "+ local.getId());
                }).addOnFailureListener(
                command ->{
                    Log.e(">>>", "Falló en la tercera");
                }
        );
    }


}