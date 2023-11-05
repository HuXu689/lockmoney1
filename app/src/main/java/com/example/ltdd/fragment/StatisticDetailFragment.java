package com.example.ltdd.fragment;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

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

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link StatisticDetailFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class StatisticDetailFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public StatisticDetailFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment StatisticDetailFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static StatisticDetailFragment newInstance(String param1, String param2) {
        StatisticDetailFragment fragment = new StatisticDetailFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
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
    public void StatisticFragment() {
        // Required empty public constructor
    }

    View view;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.activity_statistic_detail, container, false);
        ArrayList<Item> items = new ArrayList<>();
        int images[] = {R.drawable.img_imagefood, R.drawable.img_imagebill, R.drawable.img_imagecart, R.drawable.img_imagetransport};
        String item_names[] = {"Đồ ăn", "Thanh toán", "Cửa hàng", "Vận chuyển"};
        int count[] = {10, 10, 10, 10};
        int amount[] = {20000000, 12000000, 6000000, 600000};

        for (int i = 0; i < images.length; i++) {
            items.add(new Item(images[i], item_names[i], count[i], amount[i]));
        }

        ItemAdapter itemAdapter = new ItemAdapter(requireActivity(), R.layout.item, items);
        ListView lvItem = view.findViewById(R.id.lvItem);
        lvItem.setAdapter(itemAdapter);

        PieChart pieChart = view.findViewById(R.id.piechart);
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

        view.findViewById(R.id.imageArrowback).setOnClickListener(view ->{/*
            Intent intent = new Intent(this, MainActivity.class);
            startActivity(intent);*/
            requireActivity().getSupportFragmentManager().beginTransaction().replace(R.id.View, new StatisticFragment()).commit();
        });

        return view;
    }
}