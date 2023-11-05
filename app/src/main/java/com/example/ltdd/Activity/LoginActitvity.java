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

public class LoginActitvity extends AppCompatActivity {

    TextView txvxacnhan;
    EditText tbTaiKhoan;
    EditText tbMatKhau;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        txvxacnhan = (TextView) findViewById(R.id.txvxacnhan);
        tbTaiKhoan = (EditText) findViewById(R.id.tbTaiKhoan);
        tbMatKhau = (EditText) findViewById(R.id.tbMatKhau);

        txvxacnhan.setOnClickListener(view ->{
            Intent intent;
            if (tbTaiKhoan.getText().toString().equals("admin") && tbMatKhau.getText().toString().equals("admin"))
                intent = new Intent(this, AdminActivity.class);
            else
                intent = new Intent(this, MainActivity.class);
            startActivity(intent);
        });
        findViewById(R.id.imageArrowback).setOnClickListener(view ->{
            Intent intent = new Intent(this, MainActivity.class);
            startActivity(intent);
        });
    }
}