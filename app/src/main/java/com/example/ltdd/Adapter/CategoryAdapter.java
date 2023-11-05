package com.example.ltdd.Adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

import com.example.ltdd.Model.Category;

import java.util.ArrayList;

public class CategoryAdapter extends ArrayAdapter<Category> {
    public CategoryAdapter(Context context, ArrayList<Category> categories) {
        super(context, 0, categories);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        /*Category category = getItem(position);
        if(convertView == null){
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.listdanhmuc, parent, false);
        }

        ImageView ivAnhDanhMuc = convertView.findViewById(R.id.ivAnhDanhMuc);
        TextView tvTenDanhMuc = convertView.findViewById(R.id.tvTenDanhMuc);

        ivAnhDanhMuc.setImageResource(category.getAnhDanhMuc());
        tvTenDanhMuc.setText(category.getTenDanhMuc());*/

        return convertView;
    }
}
