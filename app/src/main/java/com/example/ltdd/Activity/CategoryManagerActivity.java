package com.example.ltdd.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.GridView;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.ltdd.Adapter.CategoryItemAdapter;
import com.example.ltdd.Model.Category;
import com.example.ltdd.Adapter.CategoryAdapter;
import com.example.ltdd.Model.CategoryItem;
import com.example.ltdd.R;

import java.util.ArrayList;

public class CategoryManagerActivity extends AppCompatActivity {

    //<editor-fold defaultState="collapse" desc="Khởi tạo dữ liệu cho List Category in Write Fragment">
    int cate_images[] = {R.drawable.img_food, R.drawable.img_payment, R.drawable.img_cart, R.drawable.img_transit};
    String cate_names[] = {"Đồ ăn", "Thanh toán", "Cửa hàng", "Di chuyển"};

    //</editor-fold>
    //<editor-fold defaultState="collapse" desc="Khai báo cho List Category in Write Fragment">

    ArrayList<CategoryItem> categoryItems;
    CategoryItemAdapter categoryItemAdapter;
    ListView lvCateItem;

    //</editor-fold>
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_category_manager);

        //<editor-fold defaultState ="collapse" desc="Hiện thị list category">
        lvCateItem = findViewById(R.id.lv_category);
        categoryItems = new ArrayList<>();
        for (int i = 0; i < cate_images.length; i++) {
            categoryItems.add(new CategoryItem(cate_images[i],cate_names[i]));
        }
        categoryItemAdapter = new CategoryItemAdapter(this, R.layout.category_item_in_cate_manager_actitvity, categoryItems);
        lvCateItem.setAdapter(categoryItemAdapter);
        //</editor-fold>
        findViewById(R.id.imageArrowback).setOnClickListener(view ->{
            Intent intent = new Intent(this, AdminActivity.class);
            startActivity(intent);
        });

    }
}