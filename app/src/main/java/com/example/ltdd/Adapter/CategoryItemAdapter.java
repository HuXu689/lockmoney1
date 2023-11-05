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

import com.example.ltdd.R;
import com.example.ltdd.Model.CategoryItem;

import java.util.ArrayList;


public class CategoryItemAdapter extends ArrayAdapter<CategoryItem> {
    Activity context;
    int id_layout;
    ArrayList<CategoryItem> categoryItems;

    public CategoryItemAdapter(Activity context, int id_layout, ArrayList<CategoryItem> categoryItems) {
        super(context, id_layout, categoryItems);
        this.context = context;
        this.id_layout = id_layout;
        this.categoryItems = categoryItems;
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
        CategoryItem categoryItem = categoryItems.get(position);
        // Khai báo, tham chiếu id và hiện thị lên imageView
        ImageView cate_item_image = convertView.findViewById(R.id.writeFrag_cate_item_image);
        cate_item_image.setImageResource(categoryItem.getCate_image_id());
        // Tương tự
        TextView cate_item_name = convertView.findViewById(R.id.writeFrag_cate_item_name);
        cate_item_name.setText(categoryItem.getCate_name());
        return convertView;
    }

}
