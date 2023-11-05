package com.example.ltdd.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.ltdd.R;
import com.example.ltdd.fragment.SettingsFragment;

public class AccountManagerActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account_manager);
        findViewById(R.id.imageArrowback).setOnClickListener(view ->{
            Intent intent = new Intent(this, AdminActivity.class);
            startActivity(intent);
        });
    }
}