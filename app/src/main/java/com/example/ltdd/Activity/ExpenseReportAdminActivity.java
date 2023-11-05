package com.example.ltdd.Activity;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.example.ltdd.R;
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter;

import java.util.ArrayList;

public class ExpenseReportAdminActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_expense_report_admin);

        BarChart barChart = findViewById(R.id.barChart);

        // Dữ liệu thống kê thu nhập cho 12 tháng
        ArrayList<BarEntry> entries = new ArrayList<>();
        entries.add(new BarEntry(0, 1000));  // Tháng 1
        entries.add(new BarEntry(1, 3000));  // Tháng 2
        entries.add(new BarEntry(2, 7000));  // Tháng 3
        entries.add(new BarEntry(3, 4500));  // Tháng 4
        entries.add(new BarEntry(4, 9000));  // Tháng 5
        entries.add(new BarEntry(5, 12500));  // Tháng 6
        entries.add(new BarEntry(6, 2000));  // Tháng 7
        entries.add(new BarEntry(7, 6000));  // Tháng 8
        entries.add(new BarEntry(8, 5000));  // Tháng 9
        entries.add(new BarEntry(9, 8500));  // Tháng 10
        entries.add(new BarEntry(10, 3000));  // Tháng 11
        entries.add(new BarEntry(11, 6500));  // Tháng 12

        BarDataSet barDataSet = new BarDataSet(entries, "Thu nhập theo tháng");
        BarData barData = new BarData(barDataSet);

        findViewById(R.id.imageArrowback).setOnClickListener(view ->{
            Intent intent = new Intent(this, AdminActivity.class);
            startActivity(intent);
        });

        // Cấu hình biểu đồ
        barChart.setData(barData);
        barChart.animateY(2000);  // Animation

        // Cấu hình trục X (tháng)
        String[] months = new String[]{"1", "2", "3", "4", "5", "6", "7", "8", "9", "10", "11", "12"};
        barChart.getXAxis().setValueFormatter(new IndexAxisValueFormatter(months));
        barChart.getXAxis().setLabelCount(12);
        barChart.getXAxis().setPosition(XAxis.XAxisPosition.BOTTOM);
    }
    private ArrayList<BarEntry> entries;

    // Constructor và các phần khác của lớp

    public ArrayList<BarEntry> getEntries() {
        return entries;
    }
}