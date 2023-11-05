package com.example.ltdd.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.ltdd.R;
import com.example.ltdd.fragment.SettingsFragment;

public class SignupActivity extends AppCompatActivity {

    TextView txvxacnhan;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        txvxacnhan = (TextView) findViewById(R.id.txvxacnhan);
        txvxacnhan.setOnClickListener(view ->{
            Intent intent = new Intent(this, MainActivity.class);
            startActivity(intent);
        });
        findViewById(R.id.imageArrowback).setOnClickListener(view ->{
            Intent intent = new Intent(this, MainActivity.class);
            startActivity(intent);
        });
    }
}