package com.example.appsfinalproject.fragments.admin;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.example.appsfinalproject.R;
import com.example.appsfinalproject.model.Producto;

import java.util.List;

public class ProductAdapter extends RecyclerView.Adapter<ProductView> {
    private List<Producto> products;

    public ProductAdapter(List<Producto> products) {
        this.products = products;
    }

    public void addProduct(Producto product) {
        products.add(product);
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ProductView onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View root = inflater.inflate(R.layout.row_product, null);
        ConstraintLayout rowroot = (ConstraintLayout) root;
        ProductView productView = new ProductView(rowroot);
        return productView;
    }

    @Override
    public void onBindViewHolder(@NonNull ProductView holder, int position) {
        Producto product = products.get(position);
        Bitmap bitmap = BitmapFactory.decodeFile("/path/to/image/when/available.png"); // TODO
        holder.getProductImage().setImageBitmap(bitmap);
        holder.getProductNameTV().setText(product.getNombre());
    }

    @Override
    public int getItemCount() {
        return products.size();
    }
}
