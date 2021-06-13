package com.example.appsfinalproject.fragments.owner;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
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
    private Button generateChart;
    private EditText cantidadPeriodoET;
    private CheckBox ingresosCB;
    private CheckBox egresosCB;
    private CheckBox utilidadCB;
    private LineChart chart;
    private FirebaseFirestore db;
    private FirebaseAuth auth;
    private ContabilidadLocal contabilidad;
    private int rangeDateType;
    private String messageRangeType[];

    public static final int DAY_PERIOD = 0;
    public static final int WEEK_PERIOD = 1;
    public static final int MONTH_PERIOD = 2;

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
        periodTV = v.findViewById(R.id.period_TV);
        cantidadPeriodoET = v.findViewById(R.id.catidadPeriodo_ET);
        ingresosCB = v.findViewById(R.id.ingresos_CB);
        egresosCB = v.findViewById(R.id.egresos_CB);
        utilidadCB = v.findViewById(R.id.utilidad_CB);
        back = v.findViewById(R.id.back_period_button);
        back.setOnClickListener(this);
        next = v.findViewById(R.id.advance_period_button);
        next.setOnClickListener(this);
        generateChart = v.findViewById(R.id.generateChart_BTN);
        generateChart.setOnClickListener(this);
        db = FirebaseFirestore.getInstance();
        auth = FirebaseAuth.getInstance();
        updateLocalAccounting();
        chart = v.findViewById(R.id.linechart);
        messageRangeType = new String[]{"Días", "Semanas", "Meses"};
        rangeDateType = DAY_PERIOD;
        loadData();
        return v;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.back_period_button:
                rangeDateType = (rangeDateType-1)%3;
                if(rangeDateType == -1) {
                    rangeDateType = 2;
                }
                periodTV.setText(messageRangeType[rangeDateType]);
                break;
            case R.id.advance_period_button:
                rangeDateType = (rangeDateType+1)%3;
                periodTV.setText(messageRangeType[rangeDateType]);
                break;
            case R.id.generateChart_BTN:
                String periodo_raw = cantidadPeriodoET.getText().toString();
                int periodo = Integer.parseInt(periodo_raw);
                selectData(periodo,rangeDateType);
                break;
        }

    }

    private void loadData() {
        String id = auth.getCurrentUser().getUid();
        db.collection("users").whereEqualTo("id", auth.getCurrentUser().getUid()).get().addOnSuccessListener(
                command -> {
                    AdministradorLocal user = command.getDocuments().get(0).toObject(AdministradorLocal.class);
                    String idLocal = user.getIdLocal();

                    FirebaseFirestore.getInstance().collection("local").whereEqualTo("id", idLocal).get().addOnSuccessListener(
                            command1 -> {
                                Log.e(">>>", "Antes del out of bounds el id del local es: " + idLocal);
                                Local local = command1.getDocuments().get(0).toObject(Local.class);
                                contabilidad = local.getContabilidad();
                                selectData(30, DAY_PERIOD);
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

    public void selectData(int periods,int periodType){
        ArrayList data = new ArrayList();
        ArrayList xAxis = new ArrayList();

        switch (periodType){
            case DAY_PERIOD:
                data = contabilidad.getRangeDays(periods);
                break;
            case WEEK_PERIOD:
                break;
            case MONTH_PERIOD:
                break;
        }

        updateChart(data);
    }

    private void updateChart(ArrayList<Entry> data) {
        this.getActivity().runOnUiThread(
                () -> {
                    LineDataSet lineDataSet = new LineDataSet(data,"Rendimientos");
                    LineData dataChart = new LineData(lineDataSet);
                    chart.setData(dataChart);
                    chart.invalidate();
                }
        );

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
                                if(local.getContabilidad() == null || local.getContabilidad().getRegistros().size() == 0){
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
            double select =rand.nextDouble();
            if ( select > 0.5) {
                registros.add(new RegistroContable("Registro " + String.valueOf(i * 2), new Date(todayMiliSeconds + ContabilidadLocal.MILI_SEC_DAY * i), rand.nextDouble() * 1000000, Tipo_registro.EGRESO, UUID.randomUUID().toString()));
                registros.add(new RegistroContable("Registro " + String.valueOf(i * 2 + 1), new Date(todayMiliSeconds + ContabilidadLocal.MILI_SEC_DAY * i), rand.nextDouble() * 1000000, Tipo_registro.INGRESO, UUID.randomUUID().toString()));
            }
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