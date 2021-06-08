package com.example.appsfinalproject.fragments.owner;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.example.appsfinalproject.R;
import com.example.appsfinalproject.model.RegistroContable;

import java.util.List;

public class RegistroContableAdapter extends RecyclerView.Adapter<RegistroContableView> {
    private List<RegistroContable> items;

    public RegistroContableAdapter(List<RegistroContable> items) {
        this.items = items;
    }

    public void addProduct(RegistroContable RegistroContable) {
        items.add(RegistroContable);
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public RegistroContableView onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View root = inflater.inflate(R.layout.row_spend_income, null);
        ConstraintLayout rowroot = (ConstraintLayout) root;
        RegistroContableView inventoryProductView = new RegistroContableView(rowroot);
        return inventoryProductView;
    }

    @Override
    public void onBindViewHolder(@NonNull RegistroContableView holder, int position) {
        RegistroContable RegistroContable = items.get(position);
        RegistroContable r = items.get(position);
        holder.getAmount().setText("ALGUNA CANTIDAD"); // FIXME no veo el atributo
        holder.getMonetaryValue().setText(r.getCosto()+"");
        holder.getName().setText(r.getNombre());
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    public void setItems(List<RegistroContable> items) {
        this.items = items;
        notifyDataSetChanged();
    }
}
