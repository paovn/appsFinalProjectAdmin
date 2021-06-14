package com.example.appsfinalproject.fragments.admin;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.appsfinalproject.R;
import com.example.appsfinalproject.model.AdministradorLocal;
import com.example.appsfinalproject.model.Local;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;


public class AccountingListFragment extends Fragment {

    private RecyclerView accountingListRV;
    private RegisterAdapter registerAdapter;
    private FirebaseAuth auth;
    private FirebaseFirestore db;
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
        LinearLayoutManager manager = new LinearLayoutManager(getContext(), RecyclerView.VERTICAL, false);
        accountingListRV.setLayoutManager(manager);
        accountingListRV.setAdapter(registerAdapter);

        getRegisterFromDB();
        return root;
    }

    private void getRegisterFromDB() {
        String idUser = auth.getCurrentUser().getUid();
        db.collection("users").whereEqualTo("id",idUser).get().addOnSuccessListener(
          command -> {
              String idLocal = command.getDocuments().get(0).toObject(AdministradorLocal.class).getIdLocal();
              db.collection("local").whereEqualTo("id", idLocal).get().addOnSuccessListener(
                      command1 -> {
                          Local local = command1.getDocuments().get(0).toObject(Local.class);
                          registerAdapter.setRegisters(local.getContabilidad().getRegistros());
                      }

              );
          }
        );
    }
}