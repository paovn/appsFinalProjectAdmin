package com.example.appsfinalproject.fragments.owner;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.appsfinalproject.R;
import com.example.appsfinalproject.activities.AddLocalActivity;
import com.example.appsfinalproject.activities.MainActivityOwner;


public class ShopFragment extends Fragment implements View.OnClickListener {

    private RecyclerView shopList;
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
        // TODO set adapter (concept)
        LinearLayoutManager manager = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
        shopList.setLayoutManager(manager);

        addShopButton = v.findViewById(R.id.add_shop_button);
        addShopButton.setOnClickListener(this);

        return v;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.add_shop_button:
                Intent i = new Intent(getContext(), AddLocalActivity.class);
                startActivity(i);
                break;
        }
    }
}