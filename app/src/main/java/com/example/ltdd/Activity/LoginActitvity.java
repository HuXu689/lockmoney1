package com.example.ltdd.Activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.ltdd.DAO.DAO;
import com.example.ltdd.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

public class LoginActitvity extends AppCompatActivity {


    //<editor-fold defaultState="Collapse" desc="Khai báo">
    private TextView txtv_confirm_in_login;
    private TextView txvSignUp;
    private TextView txvForgotPassword;
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
        txvForgotPassword = findViewById(R.id.txtv_forgot_password);
        txvSignUp = findViewById(R.id.txv_signup);
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
                                if (result > 0) {
                                    Toast.makeText(LoginActitvity.this, "Đăng nhập thành công!", Toast.LENGTH_SHORT).show();
                                    if (result == 2) {
                                        startActivity(new Intent(LoginActitvity.this, AdminActivity.class));
                                    } else {
                                        startActivity(new Intent(LoginActitvity.this, MainActivity.class));
                                    }
                                }
                            }
                        },
                        new DAO.returnError() {
                            @Override
                            public void OnReturnResult(String error) {
                                Toast.makeText(LoginActitvity.this, "Có lỗi: " + error, Toast.LENGTH_SHORT).show();
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
        txvSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginActitvity.this, SignupActivity.class);
                startActivity(intent);
            }
        });
        txvForgotPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onClickForgotPassword();
            }
        });
        //</editor-fold>
    }

    //Lấy lại mật khẩu
    private void onClickForgotPassword() {
        String username = txt_login_username.getText().toString(); // Lấy tên người dùng từ EditText

        // Truy vấn Firestore để tìm email tương ứng với tên người dùng
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        db.collection("User") // Giả sử bạn lưu trữ thông tin người dùng trong collection 'users'
                .whereEqualTo("userName", username)
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                String emailAddress = document.getString("email"); // Lấy email từ document
                                // Gửi email đặt lại mật khẩu
                                sendPasswordResetEmail(emailAddress);
                            }
                        } else {
                            Toast.makeText(LoginActitvity.this, "Không tìm thấy người dùng!", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }

    private void sendPasswordResetEmail(String emailAddress) {
        if (emailAddress != null && !emailAddress.isEmpty()) {
            dao.getFirebase().sendPasswordResetEmail(emailAddress)
                    .addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if (task.isSuccessful()) {
                                Toast.makeText(LoginActitvity.this, "Đã gửi đến email đăng kí!", Toast.LENGTH_SHORT).show();
                            } else {
                                Toast.makeText(LoginActitvity.this, "Kiểm tra và nhập lại email!", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
        } else {
            Toast.makeText(LoginActitvity.this, "Email không tồn tại!", Toast.LENGTH_SHORT).show();
        }
    }
}