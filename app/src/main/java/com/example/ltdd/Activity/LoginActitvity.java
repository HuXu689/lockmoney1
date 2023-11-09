package com.example.ltdd.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.ltdd.DAO.DAO;
import com.example.ltdd.R;

public class LoginActitvity extends AppCompatActivity {


    //<editor-fold defaultState="Collapse" desc="Khai báo">
    private TextView txtv_confirm_in_login;
    private EditText txt_login_username;
    private EditText txt_login_password;
    private String text_login_username, text_login_password;
    private DAO dao;
    //</editor-fold>

    //<editor-fold defaultState="Collapse" desc="Internal Function">
    private void LoadData(){
        text_login_username = txt_login_username.getText().toString();
        text_login_password = txt_login_password.getText().toString();
    }
    private String CheckData(){
        return "";
    }
    //</editor-fold>
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        //<editor-fold defaultState="collapse" desc="Liên kết với components">
        txtv_confirm_in_login = (TextView) findViewById(R.id.txtv_confirm_in_login);
        txt_login_username = (EditText) findViewById(R.id.txt_login_username);
        txt_login_password = (EditText) findViewById(R.id.txt_login_password);
        dao = new  DAO();
        //</editor-fold>

        //<editor-fold defaultState="Collapse" desc="Xử lý sự kiện">
        txtv_confirm_in_login.setOnClickListener(view ->{
            LoadData();
            String chk = CheckData();
            if (chk.isEmpty()){
                dao.loginUser(text_login_username, text_login_password, new DAO.returnResult() {
                    @Override
                    public void OnReturnResult(int result) {
                        if(result >= 0){
                            Toast.makeText(LoginActitvity.this, "Đăng nhập thành công!", Toast.LENGTH_SHORT).show();
                            if(result == 1){
                                startActivity(new Intent(LoginActitvity.this, AdminActivity.class));
                            }
                            else{
                                startActivity(new Intent(LoginActitvity.this, MainActivity.class));
                            }
                        }
                        else{
                            Toast.makeText(LoginActitvity.this, "Đăng nhập thất bại!", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }
            else{
                Toast.makeText(this, chk, Toast.LENGTH_SHORT).show();
            }
        });
        findViewById(R.id.imageArrowback).setOnClickListener(view ->{
            startActivity(new Intent(this, MainActivity.class));
        });
        //</editor-fold>
    }
}