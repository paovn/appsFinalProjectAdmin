package com.example.appsfinalproject.fragments.owner;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.appsfinalproject.R;
import com.example.appsfinalproject.activities.AddLocalActivity;
import com.example.appsfinalproject.activities.LocalActivityOwner;
import com.example.appsfinalproject.activities.MainActivityOwner;
import com.example.appsfinalproject.model.Local;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

import static android.app.Activity.RESULT_OK;


public class ShopFragment extends Fragment implements View.OnClickListener, LocalView.OnLocalClickAction {

    public static final int ADD_LOCAL_REQUEST_CODE = 12345;

    private RecyclerView shopList;
    private LocalAdapter localAdapter;
    private Button addShopButton;

    public ShopFragment() {
        // Required empty public constructor
    }

    public static ShopFragment newInstance() {
        ShopFragment fragment = new ShopFragment();
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
        View v = inflater.inflate(R.layout.fragment_shop, container, false);

        shopList = v.findViewById(R.id.shop_RV);
        LinearLayoutManager manager = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
        shopList.setLayoutManager(manager);
        localAdapter = new LocalAdapter(this);
        shopList.setAdapter(localAdapter);
        getLocalsFromDatabase();

        addShopButton = v.findViewById(R.id.add_shop_button);
        addShopButton.setOnClickListener(this);

        return v;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.add_shop_button:
                Intent i = new Intent(getContext(), AddLocalActivity.class);
                startActivityForResult(i, ADD_LOCAL_REQUEST_CODE);
                break;
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable @org.jetbrains.annotations.Nullable Intent data) {
        super.onActivityResult(requestCode,resultCode,data);
        Log.e(">>>", "requestCode = " + requestCode + ", resultCode = " + resultCode + ", resultOk = " + RESULT_OK);
        if(requestCode == ADD_LOCAL_REQUEST_CODE && resultCode == RESULT_OK) {
            Log.e(">>>", "Aniadiendo el local al RecyclerView");
            getLocalsFromDatabase(); // TODO ver si ponemos uno por uno
        }
    }

    public void getLocalsFromDatabase() {
        ArrayList<Local> locals = new ArrayList<>();
        FirebaseFirestore.getInstance().collection("local")
                .get().addOnSuccessListener(
                        command -> {
                            List<DocumentSnapshot> docs = command.getDocuments();
                            for(DocumentSnapshot doc : docs) {
                                locals.add(doc.toObject(Local.class));
                                Log.e(">>>", "Local: " + doc.toString());
                            }
                            Log.e(">>>", "Se trajo los locales de la base de datos");
                            localAdapter.setLocals(locals);
                        }
        ).addOnFailureListener(
                command -> {
                    Log.e(">>>", "Ocurrio un error al traer los locales de la base de datos");
                }
        );
    }

    @Override
    public void goToLocalInventory(String localId) {
        Intent i = new Intent(getContext(), LocalActivityOwner.class);
        i.putExtra("localId", localId);
        startActivity(i);
    }
}