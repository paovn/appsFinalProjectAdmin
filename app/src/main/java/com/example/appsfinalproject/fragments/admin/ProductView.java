package com.example.appsfinalproject.fragments.admin;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.appsfinalproject.R;
import com.example.appsfinalproject.model.Producto;

public class ProductView extends RecyclerView.ViewHolder {
    private View root;
    private TextView productNameTV;
    private ImageView productImage;

    public ProductView(@NonNull View itemView) {
        super(itemView);
        this.root = itemView;

        productNameTV = root.findViewById(R.id.row_product_name_TV);
        productImage = root.findViewById(R.id.row_product_img_view);
    }

    public View getRoot() {
        return root;
    }

    public void setRoot(View root) {
        this.root = root;
    }

    public TextView getProductNameTV() {
        return productNameTV;
    }

    public void setProductNameTV(TextView productNameTV) {
        this.productNameTV = productNameTV;
    }

    public ImageView getProductImage() {
        return productImage;
    }

    public void setProductImage(ImageView productImage) {
        this.productImage = productImage;
    }
}
