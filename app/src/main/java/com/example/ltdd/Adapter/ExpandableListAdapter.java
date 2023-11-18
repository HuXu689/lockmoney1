package com.example.ltdd.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.ltdd.Model.DayData;
import com.example.ltdd.Model.Expense;
import com.example.ltdd.R;

import java.text.DecimalFormat;
import java.util.List;

public class ExpandableListAdapter extends BaseExpandableListAdapter {
    private Context context;
    private List<DayData> dayDataList;

    public ExpandableListAdapter(Context context, List<DayData> dayDataList) {
        this.context = context;
        this.dayDataList = dayDataList;
    }
    @Override
    public int getGroupCount() {
        return dayDataList.size();
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        return dayDataList.get(groupPosition).getExpenseList().size();
    }

    @Override
    public Object getGroup(int groupPosition) {
        return dayDataList.get(groupPosition);
    }

    @Override
    public Object getChild(int groupPosition, int childPosition) {
        return dayDataList.get(groupPosition).getExpenseList().get(childPosition);
    }

    @Override
    public long getGroupId(int groupPosition) {
        return groupPosition;
    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return childPosition;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }

    @Override
    public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {
        DayData dayData = (DayData) getGroup(groupPosition);
        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.listchitieu, null);
        }
        TextView tvNgay = convertView.findViewById(R.id.tvNgay);
        TextView tvTong = convertView.findViewById(R.id.tvTong);

        tvNgay.setText(dayData.getDayName());

        DecimalFormat decimalFormat = new DecimalFormat("0");
        String formattedValue = decimalFormat.format(dayData.getTotalAmount());
        tvTong.setText(formattedValue);

        return convertView;
    }

    @Override
    public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {
        Expense expenseItem = (Expense) getChild(groupPosition, childPosition);
        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.detailchiteu, null);
        }
        ImageView ivAnhDanhMuc = convertView.findViewById(R.id.ivAnhDanhMuc);
        TextView tvTenDanhMuc = convertView.findViewById(R.id.tvTenDanhMuc);
        TextView tvNoiDung = convertView.findViewById(R.id.tvNoiDung);
        TextView tvTien = convertView.findViewById(R.id.tvTien);

        ivAnhDanhMuc.setImageResource(expenseItem.getCategory().getAnhDanhMuc());
        tvTenDanhMuc.setText(expenseItem.getCategory().getTenDanhMuc());
        tvNoiDung.setText(expenseItem.getNoidung());

        DecimalFormat decimalFormat = new DecimalFormat("0");
        String formattedValue = decimalFormat.format(expenseItem.getTien());
        tvTien.setText(formattedValue);

        return convertView;
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return true;
    }

}