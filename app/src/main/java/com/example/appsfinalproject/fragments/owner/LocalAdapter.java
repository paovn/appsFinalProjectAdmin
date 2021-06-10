package com.example.appsfinalproject.fragments.owner;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.example.appsfinalproject.R;
import com.example.appsfinalproject.model.Local;

import java.util.ArrayList;
import java.util.List;

public class LocalAdapter extends RecyclerView.Adapter<LocalView> {
    private List<Local> locals;

    public LocalAdapter() {
        locals = new ArrayList<>();
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
        LocalView localView = new LocalView(rowroot);
        Log.e(">>>", "Creando viewholder para el Local");
        return localView;
    }

    @Override
    public void onBindViewHolder(@NonNull LocalView holder, int position) {
        Local local = locals.get(position);
        /*Bitmap bitmap = BitmapFactory.decodeFile("/path/to/image/when/available.png"); // TODO
        holder.getLocalImage().setImageBitmap(bitmap);*/
        holder.getLocalNameTV().setText(local.getNombreLocal());
        Log.e(">>>", "Se le da el viewholder al Local");
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
