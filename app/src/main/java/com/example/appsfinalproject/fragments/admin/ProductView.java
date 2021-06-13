package com.example.appsfinalproject.fragments.admin;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.example.appsfinalproject.R;
import com.example.appsfinalproject.model.Producto;

public class ProductView extends RecyclerView.ViewHolder {
    private View root;
    private TextView productNameTV;
    private TextView quantityTV;
    private TextView lowRangeTV;
    private ImageView productImage;
    private ConstraintLayout rowProductCL;

    public ProductView(@NonNull View itemView) {
        super(itemView);
        this.root = itemView;

        productNameTV = root.findViewById(R.id.product_name_TV);
        quantityTV = root.findViewById(R.id.quantityTV);
        lowRangeTV = root.findViewById(R.id.low_range_TV);
        productImage = root.findViewById(R.id.row_product_img_view);
        rowProductCL = root.findViewById(R.id.rowProductCL);
        rowProductCL.setBackgroundColor(ContextCompat.getColor(root.getContext(), R.color.red));
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

    public void setQuantityTV(TextView quantityTV) {
        this.quantityTV = quantityTV;
    }

    public void setLowRangeTV(TextView lowRangeTV) {
        this.lowRangeTV = lowRangeTV;
    }

    public TextView getQuantityTV() {
        return quantityTV;
    }

    public TextView getLowRangeTV() {
        return lowRangeTV;
    }
}
