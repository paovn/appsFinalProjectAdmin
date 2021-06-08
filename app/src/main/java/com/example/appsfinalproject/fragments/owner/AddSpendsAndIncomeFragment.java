package com.example.appsfinalproject.fragments.owner;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.example.appsfinalproject.R;
import com.example.appsfinalproject.model.AdministradorLocal;
import com.example.appsfinalproject.model.ContabilidadLocal;
import com.example.appsfinalproject.model.Local;
import com.example.appsfinalproject.model.Tipo_registro;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.Date;
import java.util.UUID;

public class AddSpendsAndIncomeFragment extends Fragment implements View.OnClickListener {

    private EditText registerNameET;
    private EditText amountET;
    private  Button addCostBTN;
    private  Button addIncomeBTN;


    private FirebaseFirestore db;
    private FirebaseAuth auth;

    public AddSpendsAndIncomeFragment() {
        // Required empty public constructor
    }

    public static AddSpendsAndIncomeFragment newInstance() {
        AddSpendsAndIncomeFragment fragment = new AddSpendsAndIncomeFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_add_spends_and_income, container, false);
        registerNameET = v.findViewById(R.id.add_register_name_ET);
        amountET = v.findViewById(R.id.add_amount_ET);
        addCostBTN = v.findViewById(R.id.add_cost_BTN);
        addIncomeBTN = v.findViewById(R.id.add_inconme_BTN);

        addCostBTN.setOnClickListener(this);
        addIncomeBTN.setOnClickListener(this);

        db= FirebaseFirestore.getInstance();
        auth= FirebaseAuth.getInstance();


        return v;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.add_cost_BTN:
                addRegister(0);
                break;
            case R.id.add_inconme_BTN:
                addRegister(1);
                break;
        }
    }

    public  void addRegister(int type){
        db.collection("users").whereEqualTo("id", "152091e6-71b5-4230-8743-9c4616297cf4").get().addOnSuccessListener(
                command -> {
                    AdministradorLocal user = command.getDocuments().get(0).toObject(AdministradorLocal.class);
                    String idLocal = user.getIdLocal();

                    db.collection("local").whereEqualTo("id", idLocal).get().addOnSuccessListener(
                            command1 -> {
                                Local local = command1.getDocuments().get(0).toObject(Local.class);
                                ContabilidadLocal contabilidad = local.getContabilidad();
                                if(contabilidad == null){
                                    contabilidad = new ContabilidadLocal(UUID.randomUUID().toString());
                                    local.setContabilidad(contabilidad);
                                }
                                if(type == 1){
                                    contabilidad.addRegistro(registerNameET.getText().toString(),new Date(), Double.valueOf(amountET.getText().toString()), Tipo_registro.INGRESO, UUID.randomUUID().toString());
                                }else{
                                    contabilidad.addRegistro(registerNameET.getText().toString(),new Date(), Double.valueOf(amountET.getText().toString()), Tipo_registro.EGRESO, UUID.randomUUID().toString());
                                }
                                amountET.setText("");
                                registerNameET.setText("");

                                updateLocal(local);
                                updateAccounting(contabilidad);

                            }
                    ).addOnFailureListener(
                            command2 ->{
                                Log.e(">>>", "Falló obteniendo el local");
                            }
                    );

                }
        ).addOnFailureListener(
                command222 ->{
                    Log.e(">>>", "Falló obteniendo el usuario");
                }
        );
    }

    private void updateAccounting(ContabilidadLocal contabilidad1) {
        db.collection("accounting")
                .document(contabilidad1.getId()).set(contabilidad1)
                .addOnSuccessListener(
                        command -> {
                            Log.e(">>>>" ,"local id: "+ contabilidad1.getId());
                        }
                ).addOnFailureListener(task->{
            Log.e(">>", "errooooooooooooor agregando contabilidad");
        });
    }

    private void updateLocal(Local local){
        db.collection("local").document(local.getId()).set(local).addOnSuccessListener(
                command -> {
                    Log.e(">>>>" ,"local id: "+ local.getId());
                }).addOnFailureListener(
                command ->{
                    Log.e(">>>", "Falló añadiendo el local");
                }
        );
    }

}