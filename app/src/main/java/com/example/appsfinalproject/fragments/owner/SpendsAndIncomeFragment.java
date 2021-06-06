package com.example.appsfinalproject.fragments.owner;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.appsfinalproject.R;

import java.util.ArrayList;


public class SpendsAndIncomeFragment extends Fragment {

    private RecyclerView conceptList;
    private ConceptAdapter adapter;

    public SpendsAndIncomeFragment() {
        // Required empty public constructor
    }

    public static SpendsAndIncomeFragment newInstance() {
        SpendsAndIncomeFragment fragment = new SpendsAndIncomeFragment();
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
        View v = inflater.inflate(R.layout.fragment_spends_income, container, false);

        conceptList = v.findViewById(R.id.spends_income_RV);
        LinearLayoutManager manager = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
        conceptList.setLayoutManager(manager);
        adapter = new ConceptAdapter(new ArrayList<>()); // TODO poner la lista de ingresos y gastos
        conceptList.setAdapter(adapter);

        return v;
    }
}