package com.example.appsfinalproject.fragments.owner;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.appsfinalproject.R;
import com.example.appsfinalproject.model.Local;
import com.example.appsfinalproject.util.UtilDomi;
import com.google.firebase.storage.FirebaseStorage;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class LocalAdapter extends RecyclerView.Adapter<LocalView> {
    private List<Local> locals;
    LocalView.OnLocalClickAction onLocalClickAction;
    private FirebaseStorage storage;

    public LocalAdapter(LocalView.OnLocalClickAction onLocalClickAction) {
        locals = new ArrayList<>();
        this.onLocalClickAction = onLocalClickAction;
        storage = FirebaseStorage.getInstance();
    }

    public void addLocal(Local local) {
        locals.add(local);
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public LocalView onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View root = inflater.inflate(R.layout.row_local, null);
        ConstraintLayout rowroot = (ConstraintLayout) root;
        LocalView localView = new LocalView(rowroot, onLocalClickAction);
        Log.e(">>>", "Creando viewholder para el Local");
        return localView;
    }

    @Override
    public void onBindViewHolder(@NonNull LocalView holder, int position) {
        Local local = locals.get(position);
        holder.setLocalId(local.getId());
        holder.getLocalNameTV().setText(local.getNombreLocal());
        downloadPhoto(local.getPhotoId(), holder);
        Log.e(">>>", "Se le da el viewholder al Local");
    }

    public void downloadPhoto(String photoId, LocalView holder) {
        storage.getReference().child("local").child(photoId).getDownloadUrl().addOnCompleteListener(
                urlTask -> {
                    String url = urlTask.getResult().toString();
                    ImageView img = holder.getLocalImage();
                    Glide.with(img).load(url).into(img);
                }
        );
    }

    @Override
    public int getItemCount() {
        return locals.size();
    }

    public void setLocals(List<Local> locals) {
        this.locals = locals;
        notifyDataSetChanged();
    }
}
