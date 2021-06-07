package com.example.appsfinalproject.fragments.owner;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.appsfinalproject.R;
import com.example.appsfinalproject.model.Local;
import com.example.appsfinalproject.model.Usuario;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;


public class OwnerInventoryFragment extends Fragment {

    private RecyclerView productList;
    private InventoryProductAdapter adapter;

    private Local local;

    private FirebaseAuth auth;
    private FirebaseFirestore db;

    public OwnerInventoryFragment() {
        // Required empty public constructor
    }

    public static OwnerInventoryFragment newInstance() {
        OwnerInventoryFragment fragment = new OwnerInventoryFragment();
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
        View v = inflater.inflate(R.layout.fragment_owner_inventory, container, false);

        auth = FirebaseAuth.getInstance();
        db = FirebaseFirestore.getInstance();

        productList = v.findViewById(R.id.owner_inventory_RV);
        LinearLayoutManager manager = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
        productList.setLayoutManager(manager);
        adapter = new InventoryProductAdapter(new ArrayList<>());
        productList.setAdapter(adapter);

        // FIXME puede que esto no funcione, probadlo
        db.collection("users").document(auth.getCurrentUser().getUid()).get()
        .addOnSuccessListener(
                command -> {
                    Usuario u = command.toObject(Usuario.class);
                    db.collection("local").document(u.getIdLocal()).get()
                            .addOnSuccessListener(
                                    command1 -> {
                                        local = command1.toObject(Local.class);
                                        adapter.setItems(local.getInventario().getProductos_inventario());
                                    }
                            );
                }
        );

        return v;
    }
}