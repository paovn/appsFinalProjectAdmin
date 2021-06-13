package com.example.appsfinalproject.fragments.admin;

import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.appsfinalproject.R;
import com.example.appsfinalproject.model.Producto;

public class ProductView extends RecyclerView.ViewHolder implements View.OnClickListener {
    private View root;
    private TextView productNameTV;
    private TextView quantityTV;
    private TextView lowRangeTV;
    private ImageView productImage;
    private ConstraintLayout rowProductCL;
    private Button editProductBtn;
    private Button registerProductBtn;

    private EditProductDialogFragment editProductDialogFragment;


    private FragmentManager fragmentManager;
    private Producto product;
    private String productId;
    private String localId;
    public ProductView(@NonNull View itemView) {
        super(itemView);
        this.root = itemView;
        productNameTV = root.findViewById(R.id.product_name_TV);
        quantityTV = root.findViewById(R.id.quantityTV);
        lowRangeTV = root.findViewById(R.id.low_range_TV);
        productImage = root.findViewById(R.id.row_product_img_view);
        rowProductCL = root.findViewById(R.id.rowProductCL);
       // rowProductCL.setBackgroundColor(ContextCompat.getColor(root.getContext(), R.color.red));
        editProductBtn = root.findViewById(R.id.editProductBtn);
        registerProductBtn = root.findViewById(R.id.registerProductBtn);
        editProductBtn.setOnClickListener(this);
        registerProductBtn.setOnClickListener(this);
        editProductDialogFragment = new EditProductDialogFragment("", "", null);
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

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.editProductBtn:
                editProductDialogFragment.setLocalId(localId);
                editProductDialogFragment.setProductId(productId);
                editProductDialogFragment.setProduct(product);

                editProductDialogFragment.show(fragmentManager, "editProduct");
                break;
            case R.id.registerProductBtn:
                break;
        }
    }

    public void setFragmentManager(FragmentManager fragmentManager) {
        this.fragmentManager = fragmentManager;
    }

    public void setProductId(String productId) {
        this.productId = productId;
    }

    public void setLocalId(String localId) {
        this.localId = localId;
    }

    public void setProduct(Producto product) {
        this.product = product;
    }

    public EditProductDialogFragment getEditProductDialogFragment() {
        return editProductDialogFragment;
    }
}
