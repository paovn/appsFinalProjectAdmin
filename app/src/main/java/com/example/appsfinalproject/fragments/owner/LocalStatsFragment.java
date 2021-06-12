package com.example.appsfinalproject.fragments.owner;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.example.appsfinalproject.R;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;

import java.util.ArrayList;
import java.util.Random;


public class LocalStatsFragment extends Fragment implements View.OnClickListener {

    private TextView periodTV;
    private Button back;
    private Button next;
    private LineChart chart;
    private LineData dataChart;

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
        chart = v.findViewById(R.id.linechart);
        loadData(30);
        chart.setData(dataChart);

        return v;
    }

    private void loadData(int days) {
        ArrayList data = new ArrayList();
        Random rand = new Random();
        for (int i = 0; i < days; i++) {
            data.add(new Entry(rand.nextInt(100000),i));
        }

        LineDataSet lineDataSet = new LineDataSet(data,"Rendimientos");
        dataChart = new LineData(lineDataSet);
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
}