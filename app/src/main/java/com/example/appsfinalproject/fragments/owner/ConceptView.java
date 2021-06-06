package com.example.appsfinalproject.fragments.owner;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.appsfinalproject.R;

public class ConceptView extends RecyclerView.ViewHolder {
    private View root;
    private TextView concept;
    private TextView monetaryValue;
    private TextView quantity;

    public ConceptView(@NonNull View itemView) {
        super(itemView);
        root = itemView;
        concept = root.findViewById(R.id.row_concept_TV);
        monetaryValue = root.findViewById(R.id.row_monetary_value_concept_TV);
        quantity = root.findViewById(R.id.row_concept_amount_TV);
    }

    public View getRoot() {
        return root;
    }

    public void setRoot(View root) {
        this.root = root;
    }

    public TextView getConcept() {
        return concept;
    }

    public void setConcept(TextView concept) {
        this.concept = concept;
    }

    public TextView getMonetaryValue() {
        return monetaryValue;
    }

    public void setMonetaryValue(TextView monetaryValue) {
        this.monetaryValue = monetaryValue;
    }

    public TextView getQuantity() {
        return quantity;
    }

    public void setQuantity(TextView quantity) {
        this.quantity = quantity;
    }
}
