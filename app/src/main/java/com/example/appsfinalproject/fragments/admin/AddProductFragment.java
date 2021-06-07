package com.example.appsfinalproject.fragments.admin;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;

import com.example.appsfinalproject.R;
import com.example.appsfinalproject.model.AdministradorLocal;
import com.example.appsfinalproject.model.Local;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.UUID;


public class AddProductFragment extends Fragment implements View.OnClickListener {

    private ImageButton imageButton;
    private EditText titleET;
    private EditText presentationET;
    private EditText unitET;
    private EditText mediumRangeET;
    private EditText lowRangeET;
    private Button addButton;
    private FirebaseFirestore db;
    private FirebaseAuth auth;

    public AddProductFragment() {
        // Required empty public constructor
    }

    public static AddProductFragment newInstance() {
        AddProductFragment fragment = new AddProductFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_add_product, container, false);
        db=FirebaseFirestore.getInstance();
        auth=FirebaseAuth.getInstance();
        imageButton = v.findViewById(R.id.add_product_image_button);
        imageButton.setOnClickListener(this);

        titleET = v.findViewById(R.id.add_product_title_ET);
        presentationET = v.findViewById(R.id.add_presentation_ET);
        unitET = v.findViewById(R.id.add_amount_ET);
        mediumRangeET = v.findViewById(R.id.add_medium_range_ET);
        lowRangeET = v.findViewById(R.id.add_low_range_ET);

        addButton = v.findViewById(R.id.add_cost_BTN);
        addButton.setOnClickListener(this);

        return v;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.add_cost_BTN:

                // ver el usuario que está logueado y obtenerle el local. Como el local tiene el id del inventario,
                //traer el inventario de la base de datos y a ese añadirle el producto
                db.collection("users").whereEqualTo("id", "152091e6-71b5-4230-8743-9c4616297cf4").get().addOnSuccessListener(
                        command -> {
                            AdministradorLocal user = command.getDocuments().get(0).toObject(AdministradorLocal.class);
                            String idLocal = user.getIdLocal();

                            db.collection("local").whereEqualTo("id", idLocal).get().addOnSuccessListener(
                                    command1 -> {
                                        Local local = command1.getDocuments().get(0).toObject(Local.class);
                                        String productName =titleET.getText().toString();
                                        String idProducto = UUID.randomUUID().toString();
                                        local.getInventario().addProducto(productName, idProducto);
                                        updateLocal(local);
                                    }
                            ).addOnFailureListener(
                                    command2 ->{
                                        Log.e(">>>", "Falló en la segunda");
                                    }
                            );

                        }
                ).addOnFailureListener(
                        command222 ->{
                            Log.e(">>>", "Falló en la primera");
                        }
                );

                break;

        }

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

    private void addCountability(){

    }
}
