package com.example.appsfinalproject.fragments.owner;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.appsfinalproject.R;

public class LocalView extends RecyclerView.ViewHolder implements View.OnClickListener {
    private View root;
    private TextView localNameTV;
    private ImageView localImage;
    private String localId;
    private OnLocalClickAction onClickAction;

    public LocalView(@NonNull View itemView, OnLocalClickAction onClickAction) {
        super(itemView);
        this.root = itemView;
        this.onClickAction = onClickAction;

        localNameTV = root.findViewById(R.id.row_local_name_TV);
        localNameTV.setOnClickListener(this);
        localImage = root.findViewById(R.id.row_local_img_view);
        localImage.setOnClickListener(this);
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

    @Override
    public void onClick(View v) {
        onClickAction.goToLocalInventory(localId);
    }

    public String getLocalId() {
        return localId;
    }

    public void setLocalId(String localId) {
        this.localId = localId;
    }

    public static interface OnLocalClickAction {
        void goToLocalInventory(String localId);
    }
}
