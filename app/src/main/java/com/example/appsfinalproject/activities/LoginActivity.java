package com.example.appsfinalproject.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.appsfinalproject.R;
import com.example.appsfinalproject.model.AdministradorLocal;
import com.example.appsfinalproject.model.Tipo_usuario;
import com.example.appsfinalproject.model.Usuario;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.UUID;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener {

    private EditText emailET;
    private EditText passwordET;
    private Button loginBtn;
    private FirebaseAuth auth;
    private FirebaseFirestore db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        auth = FirebaseAuth.getInstance();
        db = FirebaseFirestore.getInstance();
        emailET = findViewById(R.id.emailET);
        passwordET = findViewById(R.id.passwordET);
        loginBtn = findViewById(R.id.loginBTN);
        loginBtn.setOnClickListener(this);
      //  creatUserLocal();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.loginBTN:
                login();
                break;
        }
    }

    private void login(){
        auth.signInWithEmailAndPassword(
                emailET.getText().toString(),
                passwordET.getText().toString()
        ).addOnSuccessListener(
                command -> {
                    FirebaseUser fireUser = auth.getCurrentUser();
                    String id = fireUser.getUid();
                    sendToActivity(id);
                }
        ).addOnFailureListener(
                command -> {
                    Toast.makeText(this, "Invalid credentials, inserting dummy admin", Toast.LENGTH_LONG).show();
                    MainActivityAdmin.createAdmin();
                    Log.e(">>", "no funcionó el login: " + command.getMessage());
                }
        );
    }

    public void sendToActivity(String id){
        Log.e(">>>", "The id is " + id);
        db.collection("users").whereEqualTo("id", id).get().addOnSuccessListener(
                command -> {
                    Usuario user = command.getDocuments().get(0).toObject(Usuario.class);
                    Intent i;
                    if(user.getTipo().equals(Tipo_usuario.ADMINISTRADOR_G)){
                        i  = new Intent(this, MainActivityOwner.class);
                    }else {
                        i = new Intent(this, MainActivityAdmin.class);
                    }
                    startActivity(i);
                }
        );
    }

    public void creatUserLocal(){
        auth.createUserWithEmailAndPassword("local1@gmail.com","local169").addOnSuccessListener(
                command -> {
                    String id = auth.getCurrentUser().getUid();
                    AdministradorLocal userLocal = new AdministradorLocal(
                            "23c2f305-985f-49db-907f-c31ac11f0a8b",
                            "local1@local.com",
                            id,
                            Tipo_usuario.ADMINISTRADOR_L
                    );
                    db.collection("users").document(id).set(userLocal).addOnSuccessListener(
                            dbtask -> {
                                Log.e(">>>", "Admin registrado en la base de datos");
                            }
                    ).addOnFailureListener(
                            task -> {
                                Log.e(">>>", "Error al registrar al Admin en la base de datos: " + task.getMessage());
                            });
                });

    }
}