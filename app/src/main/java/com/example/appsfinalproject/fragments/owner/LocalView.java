package com.example.appsfinalproject.fragments.owner;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.appsfinalproject.R;

public class LocalView extends RecyclerView.ViewHolder {
    private View root;
    private TextView localNameTV;
    private ImageView localImage;
    private String localId;

    public LocalView(@NonNull View itemView) {
        super(itemView);
        this.root = itemView;

        localNameTV = root.findViewById(R.id.row_local_name_TV);
        localImage = root.findViewById(R.id.row_local_img_view);
    }

    public View getRoot() {
        return root;
    }

    public void setRoot(View root) {
        this.root = root;
    }

    public TextView getLocalNameTV() {
        return localNameTV;
    }

    public void setLocalNameTV(TextView localNameTV) {
        this.localNameTV = localNameTV;
    }

    public ImageView getLocalImage() {
        return localImage;
    }

    public void setLocalImage(ImageView localImage) {
        this.localImage = localImage;
    }

    public String getLocalId() {
        return localId;
    }

    public void setLocalId(String localId) {
        this.localId = localId;
    }
}
