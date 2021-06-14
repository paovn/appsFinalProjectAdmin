package com.example.appsfinalproject.fragments.admin;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.appsfinalproject.R;
import com.example.appsfinalproject.model.AlertaProducto;
import com.example.appsfinalproject.model.Producto;
import com.google.firebase.storage.FirebaseStorage;

import java.util.ArrayList;
import java.util.List;

public class ProductAdapter extends RecyclerView.Adapter<ProductView> implements EditProductDialogFragment.EditProdDFInterface, OrderProductDialogFragment.OrderProductInterface {
    private List<Producto> products;
    private View root;
    private String path;
    private FirebaseStorage storage;
    private FragmentManager fragmentManager;
    private String localId;
    public ProductAdapter(FragmentManager fragmentManager) {
        this.products = new ArrayList<>();
        this.fragmentManager = fragmentManager;
    }

    public void addProduct(Producto product) {
        products.add(product);
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ProductView onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
         root = inflater.inflate(R.layout.row_product, null);
        ConstraintLayout rowroot = (ConstraintLayout) root;
        ProductView productView = new ProductView(rowroot);
        productView.getEditProductDialogFragment().setEditProdDFInterface(this);
        productView.getOrderProductDialogFragment().setOrderProductInterface(this);
        storage = FirebaseStorage.getInstance();
        return productView;
    }

    @Override
    public void onBindViewHolder(@NonNull ProductView holder, int position) {
        Producto product = products.get(position);
        holder.setFragmentManager(fragmentManager);
        holder.setProductId(product.getId());
        holder.setLocalId(localId);
        holder.setProduct(product);
        downloadPhoto(product.getPhotId(), holder);
        if(product.getAlertaProducto().equals(AlertaProducto.VERDE)){
            holder.getLinearLyRowProd().setBackgroundColor(ContextCompat.getColor(root.getContext(),R.color.light_green));
        }else if(product.getAlertaProducto().equals(AlertaProducto.AMARILLO)){

            holder.getLinearLyRowProd().setBackgroundColor(ContextCompat.getColor(root.getContext(),R.color.yellow));
        }else{
            holder.getLinearLyRowProd().setBackgroundColor(ContextCompat.getColor(root.getContext(),R.color.red));
        }
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

    public void setLocalId(String localId) {
        this.localId = localId;
    }
    @Override
    public void onDeleteProduct(Producto product){
        Log.e("onClose", "onCloseeee");
        products.remove(product);
        notifyDataSetChanged();
    };

    @Override
    public void onUpdateProducto(Producto product) {
        for(int i=0;i<products.size();i++){
            if(products.get(i).getId().equals(product.getId())){
                products.set(i, product);
                break;
            }
        }
        notifyDataSetChanged();
    }

    @Override
    public void onCloseOrderDF(Producto product) {
        for(int i=0;i<products.size();i++){
            if(products.get(i).getId().equals(product.getId())){
                products.set(i, product);
                break;
            }
        }
        notifyDataSetChanged();
    }
}
