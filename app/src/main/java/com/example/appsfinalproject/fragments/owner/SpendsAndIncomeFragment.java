package com.example.appsfinalproject.fragments.owner;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.appsfinalproject.R;
import com.example.appsfinalproject.model.Local;
import com.example.appsfinalproject.model.Usuario;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;


public class SpendsAndIncomeFragment extends Fragment {

    private RecyclerView registroContableList;
    private RegistroContableAdapter adapter;

    private FirebaseAuth auth;
    private FirebaseFirestore db;

    public SpendsAndIncomeFragment() {
        // Required empty public constructor
    }

    public static SpendsAndIncomeFragment newInstance() {
        SpendsAndIncomeFragment fragment = new SpendsAndIncomeFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_spends_income, container, false);

        registroContableList = v.findViewById(R.id.spends_income_RV);
        LinearLayoutManager manager = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
        registroContableList.setLayoutManager(manager);
        adapter = new RegistroContableAdapter(new ArrayList<>()); // TODO poner la lista de ingresos y gastos
        registroContableList.setAdapter(adapter);
        db = FirebaseFirestore.getInstance();
        auth = FirebaseAuth.getInstance();
        // FIXME puede que esto no funcione, probadlo

        /*
        db.collection("users").document(auth.getCurrentUser().getUid()).get()
                .addOnSuccessListener(
                        command -> {
                            Usuario u = command.toObject(Usuario.class);
                            /* FIXME no se como traer los registros contables de la base de datos
                            db.collection("local").document(u.getIdLocal()).get()
                                    .addOnSuccessListener(
                                            command1 -> {

                                                local = command1.toObject(Local.class);
                                                adapter.setItems(local.getInventario().getProductos_inventario());
                                            }
                                    );
                        }
                );*/

        return v;
    }
}