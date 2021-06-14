package com.example.appsfinalproject.fragments.admin;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.appsfinalproject.R;
import com.example.appsfinalproject.model.RegistroContable;

import java.util.ArrayList;

public class RegisterAdapter extends RecyclerView.Adapter<RegisterView> {
    private ArrayList<RegistroContable> registers;

    public RegisterAdapter() {
        registers = new ArrayList<>();
    }
    @NonNull
    @Override
    public RegisterView onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View root = inflater.inflate(R.layout.row_register, null);
        RegisterView registerView = new RegisterView(root);
        return registerView;
    }

    @Override
    public void onBindViewHolder(@NonNull RegisterView holder, int position) {
        RegistroContable registroContable = registers.get(position);
        holder.getNameRegister().setText(registroContable.getNombre());
        holder.getDateRegister().setText(registroContable.getFecha().toString());
        holder.getValueRegister().setText(""+registroContable.getCosto());
        holder.getTypeRegister().setText(registroContable.getTipo().toString());
    }

    public void setRegisters(ArrayList<RegistroContable> registers) {
        this.registers = registers;
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return registers.size();
    }
}
