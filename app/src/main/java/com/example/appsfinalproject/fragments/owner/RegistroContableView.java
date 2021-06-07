package com.example.appsfinalproject.fragments.owner;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.appsfinalproject.R;

public class RegistroContableView extends RecyclerView.ViewHolder {
    private View root;
    private TextView amount;
    private TextView monetaryValue;
    private TextView name;

    public RegistroContableView(@NonNull View itemView) {
        super(itemView);
        root = itemView;
        amount = root.findViewById(R.id.row_spend_income_amount_TV);
        monetaryValue = root.findViewById(R.id.row_monetary_value_product_TV);
        name = root.findViewById(R.id.row_spend_income_TV);
    }

    public TextView getAmount() {
        return amount;
    }

    public void setAmount(TextView amount) {
        this.amount = amount;
    }

    public TextView getMonetaryValue() {
        return monetaryValue;
    }

    public void setMonetaryValue(TextView monetaryValue) {
        this.monetaryValue = monetaryValue;
    }

    public TextView getName() {
        return name;
    }

    public void setName(TextView name) {
        this.name = name;
    }
}
