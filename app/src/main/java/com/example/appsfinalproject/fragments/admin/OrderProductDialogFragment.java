package com.example.appsfinalproject.fragments.admin;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import com.bumptech.glide.Glide;
import com.example.appsfinalproject.R;
import com.example.appsfinalproject.model.Local;
import com.example.appsfinalproject.model.Producto;
import com.example.appsfinalproject.model.RegistroContable;
import com.example.appsfinalproject.model.Registro_producto;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;

import java.util.Date;
import java.util.UUID;

public class OrderProductDialogFragment extends DialogFragment implements View.OnClickListener {

    private ImageView imageDFOrder;
    private EditText priceET;
    private EditText quantityET;
    private CheckBox egressCheckBox;
    private Button registerOrderBtn;
    private Button cancelBtnOrder;
    private TextView nameProOrderTV;
    private Producto product;
    private String  localId;

    private FirebaseStorage storage;
    private FirebaseFirestore db;

    private OrderProductInterface orderProductInterface;


    public OrderProductDialogFragment(Producto product, String localId) {
        this.product = product;
        this.localId = localId;
    }

    @Override
    public void onStart() {
        super.onStart();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
       View root = inflater.inflate(R.layout.order_product_df, null);
       storage = FirebaseStorage.getInstance();
       db = FirebaseFirestore.getInstance();
        imageDFOrder = root.findViewById(R.id.imageDFOrder);
        priceET = root.findViewById(R.id.priceETOrder);
        nameProOrderTV = root.findViewById(R.id.nameProOrderTV);
        quantityET = root.findViewById(R.id.quantityETOrder);
        egressCheckBox = root.findViewById(R.id.egressCheckBox);
        cancelBtnOrder = root.findViewById(R.id.cancelBtnOrder);
        cancelBtnOrder.setOnClickListener(this);
        registerOrderBtn = root.findViewById(R.id.registerOrderBtn);
        registerOrderBtn.setOnClickListener(this);

        getPhoto();
        return root;
    }
    private void getPhoto() {
        nameProOrderTV.setText(product.getNombre());
        storage.getReference().child("products").child(product.getPhotId()).getDownloadUrl().addOnCompleteListener(
                urlTask -> {
                    String url = urlTask.getResult().toString();
                    Glide.with(imageDFOrder).load(url).into(imageDFOrder);

                }
        );
    }
    public void updateProduct(){
        db.collection("local").whereEqualTo("id", localId).get().addOnSuccessListener(
                command -> {
                    Local local = command.getDocuments().get(0).toObject(Local.class);
                    Log.e("idLocal: " ,local.getId());
                    Log.e("idProducto: " , product.getId());
                    for(Producto p: local.getInventario().getProductos_inventario()){
                        if(p.getId().equals(product.getId())){
                            String idRegistro = UUID.randomUUID().toString();

                            float quant = Float.parseFloat(quantityET.getText().toString());
                            float price = Float.parseFloat(priceET.getText().toString());
                            if(egressCheckBox.isChecked()==true) {
                                p.setQuantitiy(p.getQuantitiy() - quant);
                            }else{
                                p.setQuantitiy(p.getQuantitiy() + quant);
                            }
                            Registro_producto registro_producto = new Registro_producto(idRegistro,new Date(), quant,price);
                            p.getRegistros().add(registro_producto);
                            product = p;
                            RegistroContable registroContable = new RegistroContable();
                            saveLocal(local);
                            break;
                        }
                    }

                }
        );
    }
    public void saveLocal(Local local){ //flag=0 delete,   flag=1 update
        db.collection("local")
                .document(local.getId()).set(local)
                .addOnSuccessListener(
                        dbtask -> {
                            Log.e(">>>", "Termina el proceso de aniadir local. Congrats, llegaste aqui sin errores");
                            orderProductInterface.onCloseOrderDF(product);
                            dismiss();
                        }

                ).addOnFailureListener(
                task->{
                    Log.e(">>", "no añadió el local");
                }
        );
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.registerOrderBtn:
                updateProduct();
                break;
            case R.id.cancelBtnOrder:
                dismiss();
                break;
        }
    }

    public void setOrderProductInterface(OrderProductInterface orderProductInterface) {
        this.orderProductInterface = orderProductInterface;
    }

    public void setProduct(Producto product) {
        this.product = product;
    }

    public void setLocalId(String localId) {
        this.localId = localId;
    }

    public interface OrderProductInterface{
        public void onCloseOrderDF(Producto product);
    }
}
