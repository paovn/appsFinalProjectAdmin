package com.example.appsfinalproject.fragments.admin;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;

import com.example.appsfinalproject.R;
import com.example.appsfinalproject.activities.MainActivityAdmin;
import com.example.appsfinalproject.model.AdministradorLocal;
import com.example.appsfinalproject.model.Local;
import com.example.appsfinalproject.util.UtilDomi;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.UUID;


public class AddProductFragment extends Fragment implements View.OnClickListener {
    private MainActivityAdmin mainActivityAdmin;

    private ImageButton imageButton;
    private EditText titleET;
    private EditText presentationET;
    private EditText unitET;
    private EditText mediumRangeET;
    private EditText lowRangeET;
    private Button addButton;
    private FirebaseFirestore db;

    private FirebaseAuth auth;
    private FirebaseStorage storage;
    private String path;

    public AddProductFragment() {
        // Required empty public constructor
    }

    public static AddProductFragment newInstance(MainActivityAdmin mainActivityAdmin) {
        AddProductFragment fragment = new AddProductFragment();
        fragment.mainActivityAdmin = mainActivityAdmin;
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
        storage = FirebaseStorage.getInstance();
        imageButton.setOnClickListener(this);

        titleET = v.findViewById(R.id.add_product_title_ET);
        presentationET = v.findViewById(R.id.add_presentation_ET);
        unitET = v.findViewById(R.id.add_amount_ET);
        mediumRangeET = v.findViewById(R.id.add_medium_range_ET);
        lowRangeET = v.findViewById(R.id.add_low_range_ET);

        addButton = v.findViewById(R.id.add_product_BTN);
        addButton.setOnClickListener(this);

        return v;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.add_product_BTN:
                addProducto();
                break;
            case R.id.add_product_image_button:
                Intent i2 = new Intent(Intent.ACTION_GET_CONTENT);
                i2.setType("image/*");
                startActivityForResult(i2, 11);
                break;
        }

    }
    private void addProducto(){
        String id = auth.getCurrentUser().getUid();
        db.collection("users").whereEqualTo("id", id).get().addOnSuccessListener(
                command -> {
                    AdministradorLocal user = command.getDocuments().get(0).toObject(AdministradorLocal.class);
                    String idLocal = user.getIdLocal();

                    db.collection("local").whereEqualTo("id", idLocal).get().addOnSuccessListener(
                            command1 -> {
                                Local local = command1.getDocuments().get(0).toObject(Local.class);
                                String productName =titleET.getText().toString();
                                String idProducto = UUID.randomUUID().toString();
                                String presentation = presentationET.getText().toString();
                                float lowRange = Float.parseFloat(lowRangeET.getText().toString());
                                float middleRange = Float.parseFloat(mediumRangeET.getText().toString());
                                local.getInventario().addProducto(productName, presentation,idProducto, lowRange,middleRange,idProducto);
                                uploadPhoto(idProducto);
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

    }
    private void updateLocal(Local local){
        db.collection("local").document(local.getId()).set(local).addOnSuccessListener(
                command -> {
                    Log.e(">>>>" ,"local id: "+ local.getId());
                    mainActivityAdmin.getNavigator().setSelectedItemId(R.id.principalItem); // hace click en el fragment principal para mandarnos ahi
        }).addOnFailureListener(
                command ->{
                    Log.e(">>>", "Falló en la tercera");
                }
        );
    }

    private void uploadPhoto(String photoID) {
        FileInputStream fis = null;
        try {
            fis = new FileInputStream(new File(path));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        storage.getReference().child("products").child(photoID).putStream(fis).addOnSuccessListener(
                command -> {
                    Log.e(">>>", "Subida la foto");
                }
        ).addOnFailureListener(
                command2-> {
                    Log.e(">>>", "Falló al subir la imagen");
                }
        );
    }
    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == 11 && resultCode == Activity.RESULT_OK){
            Uri uri = data.getData();
            path = UtilDomi.getPath(getContext(), uri);
            Bitmap bitmap = BitmapFactory.decodeFile(path);
            imageButton.setImageBitmap(bitmap);
        }
    }

}
