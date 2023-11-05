package com.example.ltdd.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.ltdd.Adapter.ItemAdapter;
import com.example.ltdd.Model.Item;
import com.example.ltdd.R;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.formatter.ValueFormatter;
import com.github.mikephil.charting.utils.ColorTemplate;

import java.util.ArrayList;

public class StatisticDetailActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_statistic_detail);

        // Xử lý code Activity
        ArrayList<Item> items = new ArrayList<>();
        int images[] = {R.drawable.img_imagefood, R.drawable.img_imagebill, R.drawable.img_imagecart, R.drawable.img_imagetransport};
        String item_names[] = {"Đồ ăn", "Thanh toán", "Cửa hàng", "Vận chuyển"};
        int count[] = {10, 10, 10, 10};
        int amount[] = {20000000, 12000000, 6000000, 600000};

        for (int i = 0; i < images.length; i++) {
            items.add(new Item(images[i], item_names[i], count[i], amount[i]));
        }

        ItemAdapter itemAdapter = new ItemAdapter(this, R.layout.item, items);
        ListView lvItem = findViewById(R.id.lvItem);
        lvItem.setAdapter(itemAdapter);

        PieChart pieChart = findViewById(R.id.piechart);
        ArrayList<PieEntry> entries = new ArrayList<>();
        int total = getTotal(items);
        for (Item item : items) {
            entries.add(new PieEntry(getPercent(item.getAmount(), total), item.getItem_name()));
        }
        PieDataSet pieDataSet = new PieDataSet(entries, "");
        pieDataSet.setColors(ColorTemplate.MATERIAL_COLORS);
        pieDataSet.setValueTextSize(16f);
        pieDataSet.setValueFormatter(new ValueFormatter() {
            @Override
            public String getFormattedValue(float value) {
                if (value > 4) // Nếu nhỏ hơn 4% thì không hiển thị ra
                    return String.format("%.0f%%", value);
                else
                    return "";
            }
        });

        PieData pieData = new PieData(pieDataSet);
        pieChart.setData(pieData);
        pieChart.setDrawEntryLabels(false);
        pieChart.getDescription().setEnabled(false);
        pieChart.animateY(1000);
        pieChart.invalidate();

        findViewById(R.id.imageArrowback).setOnClickListener(view ->{
            Intent intent = new Intent(this, MainActivity.class);
            startActivity(intent);
        });
    }

    public int getTotal(ArrayList<Item> items) {
        int total = 0;
        for (int i = 0; i < items.size(); i++) {
            total += items.get(i).getAmount();
        }
        return total;
    }

    public int getPercent(int amount, int total) {
        return (int) Math.round(amount * 100f / total);
    }
}
