package com.example.appsfinalproject.fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;

import com.example.appsfinalproject.R;


public class AddProductFragment extends Fragment implements View.OnClickListener {

    private ImageButton imageButton;
    private EditText titleET;
    private EditText presentationET;
    private EditText unitET;
    private EditText mediumRangeET;
    private EditText lowRangeET;
    private Button addButton;

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

        imageButton = v.findViewById(R.id.add_product_image_button);
        imageButton.setOnClickListener(this);

        titleET = v.findViewById(R.id.add_product_title_ET);
        presentationET = v.findViewById(R.id.add_presentation_ET);
        unitET = v.findViewById(R.id.add_unit_ET);
        mediumRangeET = v.findViewById(R.id.add_medium_range_ET);
        lowRangeET = v.findViewById(R.id.add_low_range_ET);

        addButton = v.findViewById(R.id.add_product_button);
        addButton.setOnClickListener(this);

        return v;
    }

    @Override
    public void onClick(View v) {

    }
}