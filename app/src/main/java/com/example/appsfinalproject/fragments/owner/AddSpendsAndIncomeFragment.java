package com.example.appsfinalproject.fragments.owner;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.example.appsfinalproject.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;

public class AddSpendsAndIncomeFragment extends Fragment implements View.OnClickListener {

    private EditText registerNameET;
    private EditText amountET;
    private  Button addCostBTN;
    private  Button addIncomeBTN;


    private FirebaseFirestore db;
    private FirebaseAuth auth;

    public AddSpendsAndIncomeFragment() {
        // Required empty public constructor
    }

    public static AddSpendsAndIncomeFragment newInstance() {
        AddSpendsAndIncomeFragment fragment = new AddSpendsAndIncomeFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_add_spends_and_income, container, false);
        registerNameET = v.findViewById(R.id.add_register_name_ET);
        amountET = v.findViewById(R.id.add_amount_ET);
        addCostBTN = v.findViewById(R.id.add_cost_BTN);
        addIncomeBTN = v.findViewById(R.id.add_inconme_BTN);

        addCostBTN.setOnClickListener(this);
        addIncomeBTN.setOnClickListener(this);

        db= FirebaseFirestore.getInstance();
        auth= FirebaseAuth.getInstance();


        return v;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.add_cost_BTN:
                break;
            case R.id.add_inconme_BTN:
                break;
        }
    }
}