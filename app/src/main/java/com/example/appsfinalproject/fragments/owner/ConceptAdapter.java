package com.example.appsfinalproject.fragments.owner;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.example.appsfinalproject.R;

import java.util.List;

public class ConceptAdapter extends RecyclerView.Adapter<ConceptView> {
    private List<Object> items;

    public ConceptAdapter(List<Object> items) {
        this.items = items;
    }

    public void addConcept(Object concept) {
        items.add(concept);
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ConceptView onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View root = inflater.inflate(R.layout.row_concept, null);
        ConstraintLayout rowroot = (ConstraintLayout) root;
        ConceptView conceptView = new ConceptView(rowroot);
        return conceptView;
    }

    @Override
    public void onBindViewHolder(@NonNull ConceptView holder, int position) {
        Object concept = items.get(position);
        // TODO ver cual es el tipo apropiado en lugar de Object o como se puede manejar para no repetir el codigo
        //holder.getConcept().setText();
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    public void setItems(List<Object> items) {
        this.items = items;
        notifyDataSetChanged();
    }
}
