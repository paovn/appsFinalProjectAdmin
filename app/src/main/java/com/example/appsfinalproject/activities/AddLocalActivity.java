package com.example.appsfinalproject.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.appsfinalproject.R;
import com.example.appsfinalproject.model.Inventario;
import com.example.appsfinalproject.model.Local;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.UUID;

public class AddLocalActivity extends AppCompatActivity implements View.OnClickListener {

    private EditText localNameET;
    private EditText adminNameET;
    private EditText addressET;
    private EditText phoneET;
    private Button addLocalBtn;
    private Button cancelBtn;
    private FirebaseAuth auth;
    private FirebaseFirestore db;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_local);
        auth= FirebaseAuth.getInstance();
        db=FirebaseFirestore.getInstance();
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

        }
    }
    public void saveLocal(){
        String localName = localNameET.getText().toString();
        String adminName = adminNameET.getText().toString();
        String address = addressET.getText().toString();
        String phone = phoneET.getText().toString();

        Inventario inventario = new Inventario();
        String id = UUID.randomUUID().toString();
        Local local = new Local(localName,adminName,phone,inventario,id);
        db.collection("local")
                .document(local.getId()).set(local)
                .addOnSuccessListener(
                        dbtask -> {
                            Toast.makeText(this, "Se ha añadido el local correctamente", Toast.LENGTH_LONG).show();
                            Intent i = new Intent(this, MainActivityOwner.class);
                            startActivity(i);
                        }
                ).addOnFailureListener(task->{
            Log.e(">>", "no añadió el local");
        });
    }
}