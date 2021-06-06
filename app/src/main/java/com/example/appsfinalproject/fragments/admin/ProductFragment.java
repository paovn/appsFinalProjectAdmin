package com.example.appsfinalproject.fragments.admin;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SearchView;

import com.example.appsfinalproject.R;

import java.util.ArrayList;


public class ProductFragment extends Fragment {

    private SearchView searchView;
    private RecyclerView productList;
    private ProductAdapter adapter;

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

        productList = v.findViewById(R.id.productsRV);
        LinearLayoutManager manager = new LinearLayoutManager(getContext(), RecyclerView.VERTICAL, false);
        productList.setLayoutManager(manager);
        adapter = new ProductAdapter(new ArrayList<>()); // TODO poner los productos reales de la DB
        productList.setAdapter(adapter);
        return v;
    }
}