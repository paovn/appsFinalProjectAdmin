package com.example.appsfinalproject.model;

import android.util.Log;

import com.github.mikephil.charting.data.Entry;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;

public class ContabilidadLocal {
    private ArrayList<RegistroContable> registros;
    private String id;
    public final int MILI_SEC_DAY = 86400000;

    public ContabilidadLocal() {
    }

    public ContabilidadLocal(String id) {
        this.registros = new ArrayList<>();
        this.id = id;
    }

    public ArrayList<RegistroContable> getRegistros() {
        return registros;
    }

    public void setRegistros(ArrayList<RegistroContable> registros) {
        this.registros = registros;
    }

    public void addRegistro(String nombre, Date fecha, double costo, Tipo_registro tipo, String id) {
        RegistroContable reg = new RegistroContable(nombre,fecha,costo,tipo,id);
        registros.add(reg);
    }

    public String getId() {
        return id;
    }

    public ArrayList<Entry> getRange(int days) {
        ArrayList range_list = new ArrayList();
        HashMap<String,Float> contabilidad_dia = new HashMap<>();
        int pos = registros.size()-1;

        if(pos == -1) return range_list;

        RegistroContable actual = registros.get(pos);

        Calendar cal = Calendar.getInstance();
        cal.setTime(actual.getFecha());

        SimpleDateFormat sobj =  new SimpleDateFormat("dd-MM-yyyy");

        String act_Date = sobj.format(actual.getFecha());
        String new_Date = sobj.format(actual.getFecha());

        for (int i = 0; i < days && pos>=0; i++) {
            while(act_Date.compareTo(new_Date) == 0){
                Log.e(">>>>", "Actual Date in while: " + act_Date);

                float expend = (float)actual.getCosto();
                //if(actual.getTipo() != Tipo_registro.EGRESO){
               if(actual.getTipo() == Tipo_registro.EGRESO) expend*=-1;
                    if(contabilidad_dia.containsKey(act_Date)){
                        contabilidad_dia.put(act_Date,contabilidad_dia.get(act_Date)+expend);
                    }else{
                        contabilidad_dia.put(act_Date,expend);
                    }
                //}
                pos--;
                actual = registros.get(pos);
                new_Date = sobj.format(actual.getFecha());
            }
            cal.add(Calendar.DAY_OF_YEAR,-1);
            act_Date = sobj.format(cal.getTime());
            Log.e(">>>>", "Actual Date in Calendar : " + act_Date);
        }
        int day = 0;
        for (String i : contabilidad_dia.keySet()) {
            range_list.add(new Entry(day++,contabilidad_dia.get(i)));
        }
        day = 0;
        return range_list;
    }
}
