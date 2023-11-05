package com.example.ltdd.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.ltdd.R;

public class AdminActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin);

        findViewById(R.id.imageArrowback).setOnClickListener(view ->{
            Intent intent = new Intent(this, MainActivity.class);
            startActivity(intent);
        });

        findViewById(R.id.txvQLDM).setOnClickListener(view ->{
            Intent intent = new Intent(this, CategoryManagerActivity.class);
            startActivity(intent);
        });

        findViewById(R.id.txvQLTK).setOnClickListener(view ->{
            Intent intent = new Intent(this, AccountManagerActivity.class);
            startActivity(intent);
        });
        findViewById(R.id.txvTKCT).setOnClickListener(view ->{
            Intent intent = new Intent(this, ExpenseReportAdminActivity.class);
            startActivity(intent);
        });
    }
}
