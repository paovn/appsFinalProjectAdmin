package com.example.appsfinalproject.fragments.admin;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.DialogFragment;

import com.bumptech.glide.Glide;
import com.example.appsfinalproject.R;
import com.example.appsfinalproject.model.Local;
import com.example.appsfinalproject.model.Producto;
import com.example.appsfinalproject.util.UtilDomi;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;

public class EditProductDialogFragment extends DialogFragment implements View.OnClickListener {

    private ImageButton productImageDF;
    private EditText productNameDF;
    private EditText presentationProductDF;
    private EditText middleRangeDF;
    private EditText lowRangeDF;
    private Button deleteBtnDF;
    private Button editBtnDF;
    private Button goBackBtnDF;

    private String path;
    private String  productId;
    private String  localId;
    private Producto product;

    private FirebaseFirestore db;
    private FirebaseStorage storage;
    private EditProdDFInterface editProdDFInterface;

    public void setEditProdDFInterface(EditProductDialogFragment.EditProdDFInterface editProdDFInterface) {
        this.editProdDFInterface = editProdDFInterface;
    }

    @Override
    public void onStart() {
        super.onStart();
        //Aseguramos el tamaño del dialog
        //getDialog().getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        //Ponemos el dialogo transparente para poder usar bordes redondos
      //  getDialog().getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
    }

    public EditProductDialogFragment(String productId, String localId, Producto product) {
        this.productId = productId;
        this.localId = localId;
        this.product = product;
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.edit_product_df, null);
        db=FirebaseFirestore.getInstance();
        storage=FirebaseStorage.getInstance();
        productImageDF = root.findViewById(R.id.productImageDF);
        productNameDF = root.findViewById(R.id.productNameDF);
        presentationProductDF = root.findViewById(R.id.presentationProductDF);
        middleRangeDF = root.findViewById(R.id.middleRangeDF);
        lowRangeDF = root.findViewById(R.id.lowRangeDF);
        deleteBtnDF = root.findViewById(R.id.deleteBtnDF);
        editBtnDF = root.findViewById(R.id.editBtnDF);
        goBackBtnDF = root.findViewById(R.id.goBackBtnDF);
        productImageDF.setOnClickListener(this);
        deleteBtnDF.setOnClickListener(this);
        editBtnDF.setOnClickListener(this);
        goBackBtnDF.setOnClickListener(this);
        fillFields();
        return root;
    }

    private void fillFields() {
        getPhoto(product.getPhotId());
        productNameDF.setText(product.getNombre());
        presentationProductDF.setText(product.getPresentation());
        middleRangeDF.setText(""+product.getMiddleRange());
        lowRangeDF.setText(""+product.getLowRange());
    }

    private void getPhoto(String photoId) {
        storage.getReference().child("products").child(photoId).getDownloadUrl().addOnCompleteListener(
                urlTask -> {
                    String url = urlTask.getResult().toString();
                    Glide.with(productImageDF).load(url).into(productImageDF);

                }
        );
    }
    private void deleteProduct(){

        db.collection("local").whereEqualTo("id", localId).get().addOnSuccessListener(
                command -> {
                    Local local = command.getDocuments().get(0).toObject(Local.class);
                    for(Producto p: local.getInventario().getProductos_inventario()){
                        if(p.getId().equals(productId)){
                            local.getInventario().getProductos_inventario().remove(p);
                            break;
                        }
                    }
                    storage.getReference().child("products").child(product.getPhotId()).delete().addOnSuccessListener(
                      command1 -> {
                          saveLocal(local,0);
                      }
                    );

                }
        );
    }


    public void saveLocal(Local local, int flag){ //flag=0 delete,   flag=1 update
        db.collection("local")
                .document(local.getId()).set(local)
                .addOnSuccessListener(
                        dbtask -> {
                            Log.e(">>>", "Termina el proceso de aniadir local. Congrats, llegaste aqui sin errores");
                            if(flag==0){
                                editProdDFInterface.onDeleteProduct(product);
                                dismiss();
                            }else{
                                editProdDFInterface.onUpdateProducto(product);
                                dismiss();
                            }
                        }

                ).addOnFailureListener(
                task->{
                    Log.e(">>", "no añadió el local");
                }
        );
    }
    public void updateProduct(){
        db.collection("local").whereEqualTo("id", localId).get().addOnSuccessListener(
                command -> {
                    Local local = command.getDocuments().get(0).toObject(Local.class);
                    for(Producto p: local.getInventario().getProductos_inventario()){
                        if(p.getId().equals(productId)){
                            p.setNombre(productNameDF.getText().toString());
                            p.setPresentation(presentationProductDF.getText().toString());
                            p.setMiddleRange(Float.parseFloat(middleRangeDF.getText().toString()));
                            p.setLowRange(Float.parseFloat(lowRangeDF.getText().toString()));
                            product = p;
                            break;
                        }
                    }
                    if(path!=null){
                        FileInputStream fis = null;
                        try {
                            fis = new FileInputStream(new File(path));
                        } catch (FileNotFoundException e) {
                            e.printStackTrace();
                        }
                        storage.getReference().child("products").child(productId).putStream(fis).addOnSuccessListener(
                                command1 -> {
                                    saveLocal(local,1);
                                }
                        );
                    }else{
                        saveLocal(local,1);
                    }
                }
        );
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.deleteBtnDF:
                deleteProduct();
                Toast.makeText(getContext(), "Se ha eliminado el producto correctamente", Toast.LENGTH_LONG).show();
                editProdDFInterface.onDeleteProduct(product);
                dismiss();
                break;
            case R.id.goBackBtnDF:
                dismiss();
                break;
            case R.id.editBtnDF:
                Toast.makeText(getContext(), "Se ha actualizado el producto correctamente", Toast.LENGTH_LONG).show();
                updateProduct();
                break;
            case R.id.productImageDF:
                Intent i2 = new Intent(Intent.ACTION_GET_CONTENT);
                i2.setType("image/*");
                startActivityForResult(i2, 11);
                break;
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == 11 && resultCode == Activity.RESULT_OK){
            Uri uri = data.getData();
            path = UtilDomi.getPath(getContext(), uri);
            Bitmap bitmap = BitmapFactory.decodeFile(path);
            productImageDF.setImageBitmap(bitmap);
        }
    }

    public interface EditProdDFInterface{

        public void onDeleteProduct(Producto product);
        public void onUpdateProducto(Producto product);

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
}
