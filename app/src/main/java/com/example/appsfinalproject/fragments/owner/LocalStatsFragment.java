package com.example.appsfinalproject.fragments.owner;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.example.appsfinalproject.R;
import com.example.appsfinalproject.model.AdministradorLocal;
import com.example.appsfinalproject.model.ContabilidadLocal;
import com.example.appsfinalproject.model.Local;
import com.example.appsfinalproject.model.RegistroContable;
import com.example.appsfinalproject.model.Tipo_registro;
import com.example.appsfinalproject.model.Usuario;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.Date;
import java.util.Random;
import java.util.UUID;


public class LocalStatsFragment extends Fragment implements View.OnClickListener {

    private TextView periodTV;
    private Button back;
    private Button next;
    private LineChart chart;
    private LineData dataChart;
    private FirebaseFirestore db;
    private FirebaseAuth auth;

    public final int MILI_SEC_DAY = 86400000;

    public LocalStatsFragment() {
        // Required empty public constructor
    }

    public static LocalStatsFragment newInstance() {
        LocalStatsFragment fragment = new LocalStatsFragment();
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
        View v = inflater.inflate(R.layout.fragment_local_stats, container, false);
        periodTV = v.findViewById(R.id.period);
        back = v.findViewById(R.id.back_period_button);
        back.setOnClickListener(this);
        next = v.findViewById(R.id.advance_period_button);
        next.setOnClickListener(this);

        db = FirebaseFirestore.getInstance();
        auth = FirebaseAuth.getInstance();

        updateLocalAccounting();

        chart = v.findViewById(R.id.linechart);
        loadData(30);

        return v;
    }

    private void loadData(int days) {
        String id = auth.getCurrentUser().getUid();
        db.collection("users").whereEqualTo("id", auth.getCurrentUser().getUid()).get().addOnSuccessListener(
                command -> {
                    AdministradorLocal user = command.getDocuments().get(0).toObject(AdministradorLocal.class);
                    String idLocal = user.getIdLocal();

                    FirebaseFirestore.getInstance().collection("local").whereEqualTo("id", idLocal).get().addOnSuccessListener(
                            command1 -> {
                                Log.e(">>>", "Antes del out of bounds el id del local es: " + idLocal);
                                Local local = command1.getDocuments().get(0).toObject(Local.class);
                                ContabilidadLocal contabilidad = local.getContabilidad();
                                ArrayList data = contabilidad.getRange(days);
                                updateChart(data);
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

    private void updateChart(ArrayList data) {
        LineDataSet lineDataSet = new LineDataSet(data,"Rendimientos");
        dataChart = new LineData(lineDataSet);
        chart.setData(dataChart);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.back_period_button:
                break;
            case R.id.advance_period_button:
                break;
        }
    }

    //Creación de información dummy

    public void updateLocalAccounting(){
        db.collection("users").whereEqualTo("id", auth.getCurrentUser().getUid()).get().addOnSuccessListener(
                command -> {
                    AdministradorLocal user = command.getDocuments().get(0).toObject(AdministradorLocal.class);
                    String idLocal = user.getIdLocal();

                    FirebaseFirestore.getInstance().collection("local").whereEqualTo("id", idLocal).get().addOnSuccessListener(
                            command1 -> {
                                Log.e(">>>", "Antes del out of bounds el id del local es: " + idLocal);
                                Local local = command1.getDocuments().get(0).toObject(Local.class);
                                if(local.getContabilidad() == null){
                                    ContabilidadLocal contabilidad1 = new ContabilidadLocal(UUID.randomUUID().toString());
                                    createContabilidadData(contabilidad1);
                                    local.setContabilidad(contabilidad1);
                                    updateLocal(local);
                                }
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

    private void createContabilidadData(ContabilidadLocal contabilidad1) {
        Random rand = new Random();
        int days = 360;
        Date currentDate = new Date();
        long todayMiliSeconds = currentDate.getTime();
        ArrayList<RegistroContable> registros = new ArrayList<>();
        for (int i = 0; i < days; i++) {
            registros.add(new RegistroContable("Registro "+String.valueOf(i*2),new Date(todayMiliSeconds+MILI_SEC_DAY*i),rand.nextDouble()*1000000, Tipo_registro.EGRESO,UUID.randomUUID().toString()));
            registros.add(new RegistroContable("Registro "+String.valueOf(i*2+1),new Date(todayMiliSeconds+MILI_SEC_DAY*i),rand.nextDouble()*1000000, Tipo_registro.INGRESO,UUID.randomUUID().toString()));
        }
        contabilidad1.setRegistros(registros);
    }

    public static void updateLocal(Local local) {
        FirebaseFirestore.getInstance().collection("local").document(local.getId()).set(local).addOnSuccessListener(
                command -> {
                    Log.e(">>>>", "local id: " + local.getId());
                }).addOnFailureListener(
                command -> {
                    Log.e(">>>", "Falló en la tercera");
                }
        );
    }
}