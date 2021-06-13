package com.example.appsfinalproject.fragments.admin;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SearchView;

import com.example.appsfinalproject.R;
import com.example.appsfinalproject.model.AdministradorLocal;
import com.example.appsfinalproject.model.Local;
import com.example.appsfinalproject.model.Producto;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.List;


public class ProductFragment extends Fragment {

    private SearchView searchView;
    private RecyclerView productList;
    private ProductAdapter adapter;
    private Local local;
    private FirebaseAuth auth;
    private FirebaseFirestore db;

    public ProductFragment() {
        // Required empty public constructor
    }

    public static ProductFragment newInstance() {
        ProductFragment fragment = new ProductFragment();
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
        View v = inflater.inflate(R.layout.fragment_product, container, false);
        searchView = v.findViewById(R.id.product_search_view);
        auth = FirebaseAuth.getInstance();
        db = FirebaseFirestore.getInstance();
        productList = v.findViewById(R.id.productsRV);
        LinearLayoutManager manager = new LinearLayoutManager(getContext(), RecyclerView.VERTICAL, false);
        productList.setLayoutManager(manager);
        adapter = new ProductAdapter(new ArrayList<>()); // TODO poner los productos reales de la DB
        productList.setAdapter(adapter);

        getProductsFromDB();
        return v;
    }

    public void getProductsFromDB() {
        String idUser = auth.getCurrentUser().getUid();

        db.collection("users").whereEqualTo("id", idUser).get().addOnSuccessListener(
                command -> {
                    String idLocal = command.getDocuments().get(0).toObject(AdministradorLocal.class).getIdLocal();
                    db.collection("local").whereEqualTo("id", idLocal).get().addOnSuccessListener(
                            command1 -> {

                                    Local local = command1.getDocuments().get(0).toObject(Local.class);
                                    adapter.setProducts(local.getInventario().getProductos_inventario());

                            }

                    );

                }
        );


    }

    }