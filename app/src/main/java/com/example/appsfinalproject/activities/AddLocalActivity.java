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
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import com.example.appsfinalproject.R;
import com.example.appsfinalproject.model.Inventario;
import com.example.appsfinalproject.model.Local;
import com.example.appsfinalproject.model.UtilDomi;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.UUID;

public class AddLocalActivity extends AppCompatActivity implements View.OnClickListener {

    private static final int GALLERY_CALLBACK = 11;
    private ImageButton localImageBtn;
    private EditText localNameET;
    private EditText adminNameET;
    private EditText addressET;
    private EditText phoneET;
    private Button addLocalBtn;
    private Button cancelBtn;
    private FirebaseAuth auth;
    private FirebaseFirestore db;
    private FirebaseStorage storage;
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
        addressET = findViewById(R.id.addressET);
        phoneET=findViewById(R.id.phoneET);
        addLocalBtn = findViewById(R.id.addLocalBtn);
        cancelBtn = findViewById(R.id.cancelBtn);
        addLocalBtn.setOnClickListener(this);
        cancelBtn.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.addLocalBtn:
                saveLocal();
                break;
            case R.id.cancelBtn:
                Intent i = new Intent(this, MainActivityOwner.class).putExtra("from", "AddLocalActivity");
                startActivity(i);
                break;
            case R.id.localImageBtn:
                Intent i2 = new Intent(Intent.ACTION_GET_CONTENT);
                i2.setType("image/*");
                startActivityForResult(i2, GALLERY_CALLBACK);
                Bitmap bitmap = BitmapFactory.decodeFile(path);
                localImageBtn.setImageBitmap(bitmap);
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
        storage.getReference().child("local").child(photoID).putStream(fis).addOnFailureListener(
                command2-> {
                    Log.e(">>>", "Falló al subir la imagen");
                }
        );


    }
    public void saveLocal(){
        String localName = localNameET.getText().toString();
        String adminName = adminNameET.getText().toString();
        String address = addressET.getText().toString();
        String phone = phoneET.getText().toString();

        Inventario inventario = new Inventario();
        String id = UUID.randomUUID().toString();
        String photoID = UUID.randomUUID().toString();
        Local local = new Local(localName,adminName,phone,inventario,id,photoID);
        db.collection("local")
                .document(local.getId()).set(local)
                .addOnSuccessListener(
                        dbtask -> {
                            uploadPhoto(photoID);
                            Toast.makeText(this, "Se ha añadido el local correctamente", Toast.LENGTH_LONG).show();
                            getIntent().putExtra("logro", "completo");
                            setResult(RESULT_OK);
                            finish();
                        }
                ).addOnFailureListener(
                        task->{
                            Log.e(">>", "no añadió el local");
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

        }
    }
}