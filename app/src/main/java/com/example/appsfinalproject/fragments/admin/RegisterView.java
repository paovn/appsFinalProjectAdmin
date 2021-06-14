package com.example.appsfinalproject.fragments.admin;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.appsfinalproject.R;

public class RegisterView extends RecyclerView.ViewHolder {
    private View root;
    private TextView nameRegister;
    private TextView dateRegister;
    private TextView valueRegister;
    private TextView typeRegister;


    public RegisterView(@NonNull View itemView) {
        super(itemView);
        this.root =itemView;
        nameRegister = root.findViewById(R.id.registerNameRow);
        dateRegister = root.findViewById(R.id.dateRegisterRow);
        valueRegister = root.findViewById(R.id.valueRegisterRow);
        typeRegister = root.findViewById(R.id.typeRegisterRow);

    }

    public TextView getNameRegister() {
        return this.nameRegister;
    }

    public TextView getDateRegister() {
        return this.dateRegister;
    }

    public TextView getValueRegister() {
        return this.valueRegister;
    }

    public TextView getTypeRegister() {
        return typeRegister;
    }
}
