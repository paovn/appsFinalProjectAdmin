package com.example.appsfinalproject.fragments.admin;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;

import com.example.appsfinalproject.R;
import com.example.appsfinalproject.model.AdministradorLocal;
import com.example.appsfinalproject.model.ContabilidadLocal;
import com.example.appsfinalproject.model.Local;
import com.example.appsfinalproject.model.RegistroContable;
import com.example.appsfinalproject.model.Tipo_registro;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;


public class AccountingListFragment extends Fragment implements View.OnClickListener {

    private RecyclerView accountingListRV;
    private RegisterAdapter registerAdapter;

    private EditText cantidadPeriodoET;
    private Button generarBalanceListBT;
    private TextView balanceTV;
    private CheckBox ingresosCB;
    private CheckBox egresosCB;

    private FirebaseAuth auth;
    private FirebaseFirestore db;

    private ContabilidadLocal contabilidad;

    public AccountingListFragment() {
        // Required empty public constructor
    }

    public static AccountingListFragment newInstance() {
        AccountingListFragment fragment = new AccountingListFragment();
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
        View root = inflater.inflate(R.layout.fragment_accounting_list, container, false);
        db= FirebaseFirestore.getInstance();
        auth = FirebaseAuth.getInstance();
        accountingListRV = root.findViewById(R.id.accountingListRV);
        registerAdapter = new RegisterAdapter();

        cantidadPeriodoET = root.findViewById(R.id.cantidadPeriodoList_ET);
        ingresosCB = root.findViewById(R.id.ingresosList_CB);
        egresosCB = root.findViewById(R.id.egresosList_CB);
        balanceTV = root.findViewById(R.id.balanceTV);
        generarBalanceListBT = root.findViewById(R.id.generateBalanceList_BTN);
        generarBalanceListBT.setOnClickListener(this);

        LinearLayoutManager manager = new LinearLayoutManager(getContext(), RecyclerView.VERTICAL, false);
        accountingListRV.setLayoutManager(manager);
        accountingListRV.setAdapter(registerAdapter);

        getAcountingFromDB();
        return root;
    }

    private void getAcountingFromDB() {
        String idUser = auth.getCurrentUser().getUid();
        db.collection("users").whereEqualTo("id",idUser).get().addOnSuccessListener(
          command -> {
              String idLocal = command.getDocuments().get(0).toObject(AdministradorLocal.class).getIdLocal();
              db.collection("local").whereEqualTo("id", idLocal).get().addOnSuccessListener(
                      command1 -> {
                          Local local = command1.getDocuments().get(0).toObject(Local.class);
                          contabilidad = local.getContabilidad();
                          //registerAdapter.setRegisters(local.getContabilidad().getRegistros());
                          updateList(30);
                      }

              );
          }
        );
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.generateBalanceList_BTN:
                String periodo_raw = cantidadPeriodoET.getText().toString();
                int periodo = Integer.parseInt(periodo_raw);
                updateList(periodo);
                break;
        }
    }

    private void updateList(int periods) {

        ArrayList<RegistroContable> dataSets = new ArrayList<>();

        if (ingresosCB.isChecked()) {
            ArrayList data = contabilidad.getRangeForList(periods, ContabilidadLocal.INCOME_TYPE, Calendar.DAY_OF_YEAR);
            dataSets.addAll(data);
        }

        if (egresosCB.isChecked()) {
            ArrayList data = contabilidad.getRangeForList(periods, ContabilidadLocal.SPENDS_TYPE, Calendar.DAY_OF_YEAR);
            dataSets.addAll(data);
        }

        if (!(ingresosCB.isChecked() || egresosCB.isChecked())) {
            ArrayList data1 = contabilidad.getRangeForList(periods, ContabilidadLocal.INCOME_TYPE, Calendar.DAY_OF_YEAR);
            ArrayList data2 = contabilidad.getRangeForList(periods, ContabilidadLocal.SPENDS_TYPE, Calendar.DAY_OF_YEAR);
            dataSets.addAll(data1);
            dataSets.addAll(data2);
        }

        Collections.sort(dataSets);
        double balance = 0;
        for (RegistroContable reg :
                dataSets) {
            double cost = reg.getCosto();
            if (reg.getTipo() == Tipo_registro.EGRESO) cost *= -1;
            balance += cost;
        }
        balanceTV.setText(String.valueOf(balance));
        Collections.reverse(dataSets);
        registerAdapter.setRegisters(dataSets);
    }
}