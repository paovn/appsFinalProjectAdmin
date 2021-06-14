package com.example.appsfinalproject.activities;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.example.appsfinalproject.R;
import com.example.appsfinalproject.activities.commons.ChooseDirectionMapsActivity;
import com.example.appsfinalproject.model.AdministradorGeneral;
import com.example.appsfinalproject.model.AdministradorLocal;
import com.example.appsfinalproject.model.ContabilidadLocal;
import com.example.appsfinalproject.model.Inventario;
import com.example.appsfinalproject.model.Local;
import com.example.appsfinalproject.model.Tipo_usuario;
import com.example.appsfinalproject.util.NotificationUtil;
import com.example.appsfinalproject.util.UtilDomi;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.UUID;

public class AddLocalActivity extends AppCompatActivity implements View.OnClickListener {

    private static final int GALLERY_CALLBACK = 11;
    private static final int CHOOSE_ADDRESS_CALLBACK = 12;

    private ImageButton localImageBtn;
    private EditText localNameET;
    private EditText adminNameET;
    private ImageButton addressButton;
    private EditText phoneET;
    private EditText passwordET;
    private Button addLocalBtn;
    private Button cancelBtn;
    private FirebaseAuth auth;
    private FirebaseFirestore db;
    private FirebaseStorage storage;

    private TextView addressTV;

    private String path;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_local);
        auth= FirebaseAuth.getInstance();
        db=FirebaseFirestore.getInstance();
        storage = FirebaseStorage.getInstance();
        localImageBtn =findViewById(R.id.localImageBtn);
        localImageBtn.setOnClickListener(this);
        localNameET = findViewById(R.id.localNameEt);
        adminNameET = findViewById(R.id.adminNameET);
        addressButton = findViewById(R.id.addressButton);
        addressButton.setOnClickListener(this);
        phoneET = findViewById(R.id.phoneET);
        passwordET = findViewById(R.id.passwordLocalET);
        addLocalBtn = findViewById(R.id.addLocalBtn);
        cancelBtn = findViewById(R.id.cancelBtn);
        addLocalBtn.setOnClickListener(this);
        cancelBtn.setOnClickListener(this);
        addressTV = findViewById(R.id.addressAddLocalTV);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.addLocalBtn:
                addAdministratorForNewLocalInFirebaseAuth();
                break;
            case R.id.cancelBtn:
                /*Intent i = new Intent(this, MainActivityOwner.class).putExtra("from", "AddLocalActivity");
                startActivity(i);
                No es necesario, solo se hace finish y funciona jabroso
                */
                finish();
                break;
            case R.id.localImageBtn:
                Intent i2 = new Intent(Intent.ACTION_GET_CONTENT);
                i2.setType("image/*");
                startActivityForResult(i2, GALLERY_CALLBACK);
                break;
            case R.id.addressButton:
                Intent i3 = new Intent(this, ChooseDirectionMapsActivity.class);
                startActivityForResult(i3, CHOOSE_ADDRESS_CALLBACK);
                break;
        }
    }

    private void uploadPhoto(String photoID) {
        FileInputStream fis = null;
        try {
            fis = new FileInputStream(new File(path));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        storage.getReference().child("local").child(photoID).putStream(fis).addOnSuccessListener(
                command -> {
                    Log.e(">>>", "Subida la foto");
                    Toast.makeText(this, "Se ha añadido el local correctamente", Toast.LENGTH_LONG).show();
                    setResult(RESULT_OK);
                    finish();
                }
        ).addOnFailureListener(
                command2-> {
                    Log.e(">>>", "Falló al subir la imagen");
                    Toast.makeText(this, "Se ha añadido el local correctamente", Toast.LENGTH_LONG).show();
                    setResult(RESULT_OK);
                    finish();
                }
        );
    }

    public void saveLocal(Local local){
        db.collection("local")
                .document(local.getId()).set(local)
                .addOnSuccessListener(
                        dbtask -> {
                            Log.e(">>>", "Termina el proceso de aniadir local. Congrats, llegaste aqui sin errores");
                        }
                ).addOnFailureListener(
                        task->{
                            Log.e(">>", "no añadió el local");
                        }
                );
    }

    private void addAdministratorForNewLocalInFirebaseAuth() {
        String localName = localNameET.getText().toString();
        String adminName = adminNameET.getText().toString();
        String phone = phoneET.getText().toString();

        Inventario inventario = new Inventario();
        String emailAdminLocal = localName.replace(" ", "_") + "@local.com";
        String idOwner = auth.getCurrentUser().getUid();
        auth.createUserWithEmailAndPassword(emailAdminLocal, passwordET.getText().toString())
        .addOnSuccessListener(
                command -> {
                    String id = command.getUser().getUid();
                    Log.e(">>>", "id de usuario creado = " + id + ", id del admin = " + idOwner);
                    Local local = new Local(localName,adminName, addressTV.getText().toString(), phone,inventario,id,id);
                    ContabilidadLocal contabilidadLocal = new ContabilidadLocal(UUID.randomUUID().toString());
                    local.setContabilidad(contabilidadLocal);
                    Log.e(">>>", "Se creo el admin del local " + local.getId() + " en FirebaseAuth");
                    addAdministratorForNewLocalInFirebaseFirestore(local, emailAdminLocal, auth.getCurrentUser().getUid(), idOwner);
                }
        ).addOnFailureListener(
                command -> {
                    Log.e(">>>", "No se pudo crear el admin del local en FirebaseAuth");
                }
        );
    }

    private void addAdministratorForNewLocalInFirebaseFirestore(Local local, String emailAdminLocal, String adminId, String idOwner) {
        AdministradorLocal adminLocal = new AdministradorLocal(local.getId(), emailAdminLocal, adminId, Tipo_usuario.ADMINISTRADOR_L);
        db.collection("users").document(adminId).set(adminLocal)
        .addOnSuccessListener(
                command -> {
                    Log.e(">>>", "Se ha creado el usuario admin del local " + local.getId() + " en FirebaseFirestore");
                    addLocalToOwner(local.getId(), idOwner);
                    saveLocal(local);
                    uploadPhoto(local.getPhotoId());
                    String msg = "El local \"" + local.getNombreLocal() + "\" ha sido creado.\n" +
                            "El nombre de usuario del administrador del local es " + emailAdminLocal + " y la clave es la que ha elegido en el momento de inscripcion del local.";
                    NotificationUtil.createNotification(this, "Nuevo local \uD83C\uDFEA", msg, new Intent(this, AddLocalActivity.class));
                }
        ).addOnFailureListener(
                command -> {
                    Log.e(">>>", "No se pudo crear el usuario admin del local " + local.getId() + " en FirebaseFirestore: " + command.getMessage());
                }
        );
    }

    private void addLocalToOwner(String idLocal, String ownerid) {
        db.collection("users").document(ownerid)
                .get().addOnSuccessListener(
                        command -> {
                            AdministradorGeneral admin = command.toObject(AdministradorGeneral.class);
                            Log.e(">>>", "admin id is " + admin.getId());
                            admin.getIdLocales().add(idLocal);
                            MainActivityAdmin.saveAdminInFireBaseDatabase(admin);
                            Log.e(">>>", "Se ha aniadido el local al admin general");
                        }
        ).addOnFailureListener(
                command -> {
                    Log.e(">>>", "No se pudo aniadir el local al admin general: " + command.getMessage());
                }
        );
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == GALLERY_CALLBACK && resultCode == Activity.RESULT_OK){
            Uri uri = data.getData();
            path = UtilDomi.getPath(this, uri);
            Bitmap bitmap = BitmapFactory.decodeFile(path);
            localImageBtn.setImageBitmap(bitmap);
            Log.e(">>>", "Se puso la imagen en el boton");
        } else if(requestCode == CHOOSE_ADDRESS_CALLBACK && resultCode == Activity.RESULT_OK) {
            String address = data.getExtras().getString("address");
            addressTV.setText(address);
            Log.e(">>>", "address is " + address);
        }
    }
}