package com.example.ltdd.Adapter;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.ltdd.Model.Item;
import com.example.ltdd.R;

import java.util.ArrayList;

public class ItemAdapter extends ArrayAdapter<Item> {
    Activity context;
    int id_layout;
    ArrayList<Item> items;

    public ItemAdapter(Activity context, int id_layout, ArrayList<Item> items) {
        super(context, id_layout, items);
        this.context = context;
        this.id_layout = id_layout;
        this.items = items;
    }
    // Gọi getView sắp xếp dữ liệu

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        // Tạo Infractor chứa layout
        LayoutInflater layoutInflater = context.getLayoutInflater();
        // Gọi id Layout tạo thành View
        convertView = layoutInflater.inflate(id_layout, null);
        // Lấy 1 phần tử
        Item item = items.get(position);
        // Khai báo, tham chiếu id và hiện thị lên imageView
        ImageView item_image = convertView.findViewById(R.id.item_image);
        item_image.setImageResource(item.getItem_image());
        // Tương tự
        TextView txtName = convertView.findViewById(R.id.txtName);
        txtName.setText(item.getItem_name());
        TextView txtCount = convertView.findViewById(R.id.txtCount);
        txtCount.setText(item.getCount() + " giao dich");
        TextView txtAmount = convertView.findViewById(R.id.txtAmount);
        txtAmount.setText("-" + item.getAmount());
        return convertView;
    }
}
