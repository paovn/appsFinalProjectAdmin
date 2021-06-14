package com.example.appsfinalproject.fragments.owner;

import android.content.Intent;
import android.os.Bundle;

import androidx.core.content.ContextCompat;
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
import com.example.appsfinalproject.activities.MainActivityAdmin;
import com.example.appsfinalproject.activities.MainActivityOwner;
import com.example.appsfinalproject.model.AdministradorGeneral;
import com.example.appsfinalproject.model.AdministradorLocal;
import com.example.appsfinalproject.model.ContabilidadLocal;
import com.example.appsfinalproject.model.Local;
import com.example.appsfinalproject.model.RegistroContable;
import com.example.appsfinalproject.model.Tipo_registro;
import com.example.appsfinalproject.model.Tipo_usuario;
import com.example.appsfinalproject.model.Usuario;
import com.example.appsfinalproject.model.chartValueFormatter;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.AxisBase;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.formatter.IAxisValueFormatter;
import com.github.mikephil.charting.formatter.ValueFormatter;
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Random;
import java.util.UUID;

import javax.crypto.Cipher;


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
    ContabilidadLocal temp_contabilidad;

    private int rangeDateType;
    private String messageRangeType[];

    private ArrayList<Integer> chartLines;



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
        updateAccounting();
        chart = v.findViewById(R.id.linechart);
        messageRangeType = new String[]{"Días", "Semanas", "Meses"};
        rangeDateType = ContabilidadLocal.DAY_PERIOD;
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
                updateChart(periodo,rangeDateType);
                break;
        }

    }

    private void loadData() {
        String id = auth.getCurrentUser().getUid();
        db.collection("users").whereEqualTo("id", auth.getCurrentUser().getUid()).get().addOnSuccessListener(
                command -> {
                    Usuario userStart = command.getDocuments().get(0).toObject(Usuario.class);

                    if(userStart.getTipo().equals(Tipo_usuario.ADMINISTRADOR_G)){
                        AdministradorGeneral admin =  command.getDocuments().get(0).toObject(AdministradorGeneral.class);
                        ArrayList<String> locales = admin.getIdLocales();
                        contabilidad = new ContabilidadLocal(UUID.randomUUID().toString());

                        FirebaseFirestore.getInstance().collection("local").get().addOnSuccessListener(
                                command1 -> {
                                    Log.e(">>>", "Recorriendo los locales bro");
                                    for(int i = 0; i<command1.getDocuments().size(); i++){
                                        Local local = command1.getDocuments().get(i).toObject(Local.class);
                                        ContabilidadLocal temp_contabilidad = local.getContabilidad();
                                        for (int j = 0; j < temp_contabilidad.getRegistros().size(); j++) {
                                            contabilidad.getRegistros().add(temp_contabilidad.getRegistros().get(j));
                                        }
                                    }
                                    updateChart(30, ContabilidadLocal.DAY_PERIOD);

                                }
                        ).addOnFailureListener(
                                command2 ->{
                                    Log.e(">>>", "Falló obteniendo el local");
                                }
                        );


                        contabilidad.sortRegisters();
                    }else {
                        AdministradorLocal user = command.getDocuments().get(0).toObject(AdministradorLocal.class);
                        String idLocal = user.getIdLocal();
                        FirebaseFirestore.getInstance().collection("local").whereEqualTo("id", idLocal).get().addOnSuccessListener(
                                command1 -> {
                                    Log.e(">>>", "Antes del out of bounds el id del local es: " + idLocal);
                                    Local local = command1.getDocuments().get(0).toObject(Local.class);
                                    contabilidad = local.getContabilidad();
                                    updateChart(30, ContabilidadLocal.DAY_PERIOD);
                                }
                        ).addOnFailureListener(
                                command2 ->{
                                    Log.e(">>>", "Falló obteniendo el local");
                                }
                        );
                    }

                }
        ).addOnFailureListener(
                command222 ->{
                    Log.e(">>>", "Falló obteniendo el usuario");
                }
        );
    }




    public ArrayList selectData(int periods,int periodType, int dataType){
        ArrayList data = new ArrayList();
        //ArrayList xAxis = new ArrayList();

        switch (periodType){
            case ContabilidadLocal.DAY_PERIOD:
                data = contabilidad.getRange(periods,dataType, Calendar.DAY_OF_YEAR,"yyyy MMM dd");
                break;
            case ContabilidadLocal.WEEK_PERIOD:
                data = contabilidad.getRange(periods,dataType, Calendar.WEEK_OF_YEAR,"yyyy MMM dd");
                break;
            case ContabilidadLocal.MONTH_PERIOD:
                data = contabilidad.getRange(periods,dataType, Calendar.MONTH,"yyyy MMM dd");
                //data = contabilidad.getRangeMonths(periods,dataType);
                break;
        }
        return data;
    }

    private void updateChart(int periods,int periodType) {
        this.getActivity().runOnUiThread(
                () -> {
                    ArrayList<ILineDataSet> dataSets = new ArrayList<>();
                    ArrayList<String> xLabels = new ArrayList<>();

                    xLabels = contabilidad.getRangeXaxis(periods,periodType,"dd-MM-yy");

                    if(ingresosCB.isChecked()){
                        ArrayList data = selectData(periods,periodType,ContabilidadLocal.INCOME_TYPE);
                        dataSets.add(createLineData(data, android.R.color.holo_green_dark,android.R.color.holo_green_light ));
                    }

                    if(egresosCB.isChecked()){
                        ArrayList data = selectData(periods,periodType,ContabilidadLocal.SPENDS_TYPE);
                        dataSets.add(createLineData(data, android.R.color.holo_red_dark,android.R.color.holo_red_light ));
                    }

                    if(utilidadCB.isChecked()){
                        ArrayList data = selectData(periods,periodType,ContabilidadLocal.UTILITY_TYPE);
                        dataSets.add(createLineData(data, android.R.color.holo_blue_dark,android.R.color.holo_blue_light ));
                    }

                    if(!(ingresosCB.isChecked() || egresosCB.isChecked() || utilidadCB.isChecked())){
                        ArrayList data = selectData(periods,periodType,ContabilidadLocal.INCOME_TYPE);
                        dataSets.add(createLineData(data, android.R.color.holo_green_dark,android.R.color.holo_green_light ));
                    }

                    LineData dataChart = new LineData(dataSets);
                    chart.setData(dataChart);
                    changeCharacteristicsChart(chart,xLabels);
                }
        );

    }

    private void changeCharacteristicsChart(LineChart chart, ArrayList<String> xValues) {
        chart.getAxisRight().setEnabled(false);

        XAxis xAxis = chart.getXAxis();

        xAxis.setGranularity(1f);


        //xAxis.setValueFormatter(new chartValueFormatter(xValues));


        chart.invalidate();
    }

    private LineDataSet createLineData(ArrayList<Entry> data, int lineColor, int fillColor){
        lineColor = ContextCompat.getColor(this.getContext(), lineColor);
        fillColor = ContextCompat.getColor(this.getContext(), fillColor);
        LineDataSet dataSet = new LineDataSet(data,"Rendimientos");
        dataSet.setDrawCircles(true);
        dataSet.setCircleColor(lineColor);
        dataSet.setValueTextSize(0f);
        dataSet.setLineWidth(3f); //lineWidth = 3f;
        //dataSet.setMode(LineDataSet.Mode.HORIZONTAL_BEZIER);
        dataSet.setColor(lineColor);
        dataSet.setDrawFilled(true);
        dataSet.setLabel("");
        dataSet.setFillColor(fillColor);
        return dataSet;
    }

    //Creación de información dummy

    public void updateAccounting(){
        db.collection("users").whereEqualTo("id", auth.getCurrentUser().getUid()).get().addOnSuccessListener(
                command -> {

                    Usuario userStart = command.getDocuments().get(0).toObject(Usuario.class);

                    if(userStart.getTipo().equals(Tipo_usuario.ADMINISTRADOR_G)){
                        AdministradorGeneral admin =  command.getDocuments().get(0).toObject(AdministradorGeneral.class);
                        ArrayList<String> locales = admin.getIdLocales();
                        for (int i = 0; i < locales.size(); i++) {
                            updateLocalAccounting(locales.get(i));
                        }
                    }else{
                        updateLocalAccounting(auth.getCurrentUser().getUid());
                    }
                }
        ).addOnFailureListener(
                command222 ->{
                    Log.e(">>>", "Falló obteniendo el usuario");
                }
        );
    }

    public void updateLocalAccounting(String id){
        db.collection("users").whereEqualTo("id", id).get().addOnSuccessListener(
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