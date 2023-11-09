package com.example.ltdd.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.ltdd.Adapter.UserAdater;
import com.example.ltdd.DAO.DAO;
import com.example.ltdd.Model.UserModel;
import com.example.ltdd.R;

import java.util.ArrayList;


public class AccountManagerActivity extends AppCompatActivity {
    //<editor-fold defaultState="Collapse" desc="Khai báo">
    private EditText txt_search_username;
    private TextView txtv_search_user, txtv_delete_user;
    private ListView lv_user;
    private DAO dao;

    //</editor-fold>
    //<editor-fold defaultState="Collapse" desc="internal function">

    //</editor-fold>
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account_manager);
        findViewById(R.id.imageArrowback).setOnClickListener(view ->{
            startActivity(new Intent(this, AdminActivity.class));
        });
        //<editor-fold defaultState="Collapse" desc="liên kết">
        txt_search_username = findViewById(R.id.txt_search_username);
        txtv_search_user = findViewById(R.id.txtv_search_user);
        txtv_delete_user = findViewById(R.id.txtv_delete_user);
        lv_user = findViewById(R.id.lv_user);
        dao = new DAO();
        //</editor-fold>

        //<editor-fold defaultState="Collapse" desc="Hiện thị list user">
        txtv_search_user.setOnClickListener(view -> {
            dao.getListUsers(txt_search_username.getText().toString(), new DAO.OnUserListListener() {
                @Override
                public void onUserListReceived(ArrayList<UserModel> userList) {
                    UserAdater userAdater;
                    userAdater = new UserAdater(AccountManagerActivity.this, R.layout.user_item_in_admin, userList);
                    lv_user.setAdapter(userAdater);
                    //<editor-fold defaultState="Collapse" desc="Xóa user">
                    lv_user.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                        @Override
                        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                            String uid = userList.get(position).getUser_id();

                        }
                    });
                    //</editor-fold>
                }
                @Override
                public void onUserListError(String error) {
                    Toast.makeText(AccountManagerActivity.this, "Lỗi: " + error, Toast.LENGTH_SHORT).show();
                }
            });
        });
        //</editor-fold>
    }
}