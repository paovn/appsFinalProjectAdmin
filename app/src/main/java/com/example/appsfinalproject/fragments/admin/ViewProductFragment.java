package com.example.appsfinalproject.fragments.admin;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;

import com.example.appsfinalproject.R;


public class ViewProductFragment extends Fragment implements View.OnClickListener {

    private ImageButton imageButton;
    private EditText titleET;
    private EditText presentationET;
    private EditText unitET;
    private EditText mediumRangeET;
    private EditText lowRangeET;
    private Button returnButton;
    private Button deleteButton;
    private Button editButton;

    public ViewProductFragment() {
        // Required empty public constructor
    }

    public static ViewProductFragment newInstance() {
        ViewProductFragment fragment = new ViewProductFragment();
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
        View v = inflater.inflate(R.layout.fragment_view_product, container, false);

        imageButton = v.findViewById(R.id.productImageDF);
        imageButton.setOnClickListener(this);

        titleET = v.findViewById(R.id.productNameDF);
        presentationET = v.findViewById(R.id.presentationProductDF);
        unitET = v.findViewById(R.id.view_unit_ET);
        mediumRangeET = v.findViewById(R.id.middleRangeDF);
        lowRangeET = v.findViewById(R.id.lowRangeDF);

        returnButton = v.findViewById(R.id.goBackBtnDF);
        returnButton.setOnClickListener(this);
        editButton = v.findViewById(R.id.editBtnDF);
        editButton.setOnClickListener(this);
        deleteButton = v.findViewById(R.id.deleteBtnDF);
        deleteButton.setOnClickListener(this);
        return v;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.productImageDF:
                break;
            case R.id.deleteBtnDF:
                break;
            case R.id.editBtnDF:
                break;
            case R.id.goBackBtnDF:
                break;
        }
    }
}