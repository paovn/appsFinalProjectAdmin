package com.example.appsfinalproject.fragments.owner;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.appsfinalproject.R;

public class InventoryProductView extends RecyclerView.ViewHolder {
    private View root;
    private TextView productTV;
    private TextView monetaryValue;
    private TextView dateTV;

    public InventoryProductView(@NonNull View itemView) {
        super(itemView);
        root = itemView;
        productTV = root.findViewById(R.id.row_inventory_product_TV);
        monetaryValue = root.findViewById(R.id.row_monetary_value_product_TV);
        dateTV = root.findViewById(R.id.row_product_date_TV);
    }

    public TextView getProductTV() {
        return productTV;
    }

    public void setProductTV(TextView productTV) {
        this.productTV = productTV;
    }

    public TextView getMonetaryValue() {
        return monetaryValue;
    }

    public void setMonetaryValue(TextView monetaryValue) {
        this.monetaryValue = monetaryValue;
    }

    public TextView getDateTV() {
        return dateTV;
    }

    public void setDateTV(TextView dateTV) {
        this.dateTV = dateTV;
    }
}
