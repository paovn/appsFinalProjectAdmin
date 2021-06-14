package com.example.appsfinalproject.model;

import android.util.Log;

import com.github.mikephil.charting.data.Entry;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;

public class ContabilidadLocal {
    private ArrayList<RegistroContable> registros;
    private String id;
    public static final long MILI_SEC_DAY = 86400000;
    public static final long MILI_SEC_WEEK = 604800000;
    public static final long MILI_SEC_MONTH = 2592000000l;


    public static final int DAY_PERIOD = 0;
    public static final int WEEK_PERIOD = 1;
    public static final int MONTH_PERIOD = 2;

    public static final int INCOME_TYPE = 0;
    public static final int SPENDS_TYPE = 1;
    public static final int UTILITY_TYPE = 2;

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

    public ArrayList getRange(int days, int dataType, int calendarType, String dateFormat) {
        ArrayList range_list = new ArrayList();
        HashMap<String,Float> contabilidad_periodo = new HashMap<>();
        Collections.sort(registros);
        int pos = registros.size()-1;

        if(pos == -1) return range_list;

        RegistroContable actual = registros.get(pos);

        Calendar act_Date = Calendar.getInstance();
        act_Date.setTime(actual.getFecha());

        Calendar new_Date = Calendar.getInstance();
        new_Date.setTime(actual.getFecha());


        SimpleDateFormat sobj =  new SimpleDateFormat(dateFormat);


        for (int i = 0; i < days && pos>=0; i++) {
            while(act_Date.get(calendarType) == new_Date.get(calendarType) && pos>=0){
                Log.e(">>>>", "Actual Date in while: " +sobj.format(act_Date.getTime()));

                float expend = (float)actual.getCosto();

                //Esto se puede actualizar con switch case y metodos para el uso de los checkbox
                switch (dataType){
                    case UTILITY_TYPE:
                        putUtility(actual,expend,sobj.format(act_Date.getTime()),contabilidad_periodo);
                        break;
                    case SPENDS_TYPE:
                        putExpends(actual,expend,sobj.format(act_Date.getTime()),contabilidad_periodo);
                        break;
                    case INCOME_TYPE:
                        putIncome(actual,expend,sobj.format(act_Date.getTime()),contabilidad_periodo);
                        break;
                }

                pos--;
                if(pos<0)continue;
                actual = registros.get(pos);
                new_Date.setTime(actual.getFecha());
                //new_Date = actual.getFecha();
            }

            act_Date.add(calendarType,-1);

            if(act_Date.get(calendarType) != new_Date.get(calendarType)){
                contabilidad_periodo.put(sobj.format(act_Date.getTime()),0f);
            }

            Log.e(">>>>", "Actual Date in Calendar : " + sobj.format(act_Date.getTime()));
        }
        int week = 0;
        for (String i : contabilidad_periodo.keySet()) {
            range_list.add(new Entry(week++,contabilidad_periodo.get(i)));
        }

        return range_list;
    }


    private void putExpends(RegistroContable actual, float expend, String act_Date, HashMap<String, Float> contabilidad_dia){
        if(actual.getTipo() == Tipo_registro.INGRESO) return;
        if(contabilidad_dia.containsKey(act_Date)){
            contabilidad_dia.put(act_Date,contabilidad_dia.get(act_Date)+expend);
        }else{
            contabilidad_dia.put(act_Date,expend);
        }
    }

    private void putIncome(RegistroContable actual, float expend, String act_Date, HashMap<String, Float> contabilidad_dia){
        if(actual.getTipo() == Tipo_registro.EGRESO) return;
        if(contabilidad_dia.containsKey(act_Date)){
            contabilidad_dia.put(act_Date,contabilidad_dia.get(act_Date)+expend);
        }else{
            contabilidad_dia.put(act_Date,expend);
        }
    }

    private void putUtility(RegistroContable actual, float expend, String act_Date, HashMap<String, Float> contabilidad_dia){
        if(actual.getTipo() == Tipo_registro.EGRESO) expend*=-1;
        if(contabilidad_dia.containsKey(act_Date)){
            contabilidad_dia.put(act_Date,contabilidad_dia.get(act_Date)+expend);
        }else{
            contabilidad_dia.put(act_Date,expend);
        }
    }


    public ArrayList<String> getRangeXaxis(int periods, int calendarType, String formatType) {
        ArrayList<String> out = new ArrayList<>();

        int pos = registros.size()-1;

        if(pos == -1) return out;

        RegistroContable actual = registros.get(pos);

        Calendar act_Date = Calendar.getInstance();
        act_Date.setTime(actual.getFecha());


        SimpleDateFormat sobj =  new SimpleDateFormat(formatType,Locale.ENGLISH);


        for (int i = 0; i < periods && pos>=0; i++) {
            act_Date.add(calendarType,-1);
            out.add(sobj.format(act_Date.getTime()));
            Log.e(">>>>", "Date for format Calendar : " + act_Date);
        }


        return out;
    }

    public void sortRegisters() {
        Collections.sort(registros);
    }

    public ArrayList getRangeForList(int periods, int dataType, int calendarType) {
        ArrayList<RegistroContable> range_list = new ArrayList();
        Collections.sort(registros);
        int pos = registros.size()-1;
        if(pos == -1) return range_list;

        RegistroContable actual = registros.get(pos);

        Calendar act_Date = Calendar.getInstance();
        act_Date.setTime(actual.getFecha());

        Calendar new_Date = Calendar.getInstance();
        new_Date.setTime(actual.getFecha());


        SimpleDateFormat sobj =  new SimpleDateFormat("yyyy MM dd");


        for (int i = 0; i < periods && pos>=0; i++) {
            while(act_Date.get(calendarType) == new_Date.get(calendarType) && pos>0){
                Log.e(">>>>", "Actual Date in while: " +sobj.format(act_Date.getTime()));

                float expend = (float)actual.getCosto();

                //Esto se puede actualizar con switch case y metodos para el uso de los checkbox
                if(dataType == ContabilidadLocal.INCOME_TYPE &&  actual.getTipo() == Tipo_registro.INGRESO){
                    range_list.add(actual);
                }

                if(dataType == ContabilidadLocal.SPENDS_TYPE &&  actual.getTipo() == Tipo_registro.EGRESO){
                    range_list.add(actual);
                }


                pos--;
                actual = registros.get(pos);
                new_Date.setTime(actual.getFecha());
                String str_new_date = sobj.format(new_Date.getTime());
                //new_Date = actual.getFecha();
            }

            act_Date.add(calendarType,-1);
            String str_act_date = sobj.format(act_Date.getTime());
        }
        return range_list;
    }
}
