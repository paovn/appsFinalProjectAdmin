package com.example.appsfinalproject.fragments.admin;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.appsfinalproject.R;
import com.example.appsfinalproject.fragments.owner.LocalView;
import com.example.appsfinalproject.model.Producto;
import com.google.firebase.storage.FirebaseStorage;

import java.util.List;

public class ProductAdapter extends RecyclerView.Adapter<ProductView> {
    private List<Producto> products;
    private String path;
    private FirebaseStorage storage;

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
        storage = FirebaseStorage.getInstance();
        return productView;
    }

    @Override
    public void onBindViewHolder(@NonNull ProductView holder, int position) {
        Producto product = products.get(position);
        downloadPhoto(product.getPhotId(), holder);
        holder.getProductNameTV().setText(product.getNombre());
        holder.getQuantityTV().setText(""+product.getQuantitiy());
        holder.getLowRangeTV().setText(""+product.getLowRange());

    }

    @Override
    public int getItemCount() {
        return products.size();
    }

    public void setProducts(List<Producto> products) {
        this.products = products;
        notifyDataSetChanged();
    }

    public void downloadPhoto(String photoId, ProductView holder) {
        storage.getReference().child("products").child(photoId).getDownloadUrl().addOnCompleteListener(
                urlTask -> {
                    String url = urlTask.getResult().toString();
                    ImageView img = holder.getProductImage();
                    Glide.with(img).load(url).into(img);
                }
        );
    }
}
