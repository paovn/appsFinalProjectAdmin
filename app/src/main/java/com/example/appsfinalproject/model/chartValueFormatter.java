package com.example.appsfinalproject.model;

import com.github.mikephil.charting.components.AxisBase;
import com.github.mikephil.charting.formatter.ValueFormatter;

import java.util.ArrayList;

public class chartValueFormatter extends ValueFormatter {

    ArrayList<String> xValues;

    public chartValueFormatter(ArrayList<String> xValues) {
        this.xValues = xValues;
    }

    public void setxValues(ArrayList<String> xValues) {
        this.xValues = xValues;
    }

    @Override
    public String getAxisLabel(float value, AxisBase axis) {
        if((int)value == xValues.size()) return "";
        return xValues.get((int)(value));
    }

    @Override
    public String getFormattedValue(float value) {
        return xValues.get((int)(value));
    }

}
