package com.example.appsfinalproject.fragments.owner;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.example.appsfinalproject.R;
import com.example.appsfinalproject.model.Producto;

import java.util.List;

public class InventoryProductAdapter extends RecyclerView.Adapter<InventoryProductView> {
    private List<Producto> items;

    public InventoryProductAdapter(List<Producto> items) {
        this.items = items;
    }

    public void addProduct(Producto producto) {
        items.add(producto);
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public InventoryProductView onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View root = inflater.inflate(R.layout.row_inventory_product, null);
        ConstraintLayout rowroot = (ConstraintLayout) root;
        InventoryProductView inventoryProductView = new InventoryProductView(rowroot);
        return inventoryProductView;
    }

    @Override
    public void onBindViewHolder(@NonNull InventoryProductView holder, int position) {
        Producto producto = items.get(position);

        holder.getProductTV().setText(producto.getNombre());
        holder.getMonetaryValue().setText(producto.getPrecio()+"");
        holder.getDateTV().setText(producto.getQuantity());
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    public void setItems(List<Producto> items) {
        this.items = items;
        notifyDataSetChanged();
    }
}
