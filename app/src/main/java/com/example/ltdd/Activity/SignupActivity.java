package com.example.ltdd.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.ltdd.DAO.DAO;
import com.example.ltdd.R;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class SignupActivity extends AppCompatActivity {

    //<editor-fold defaultState="Collapse" desc="Khai báo">
    private TextView txtv_confirm;
    private EditText txt_username, txt_password, txt_fullname, txt_confirm_password, txt_email;
    private String text_email,text_password,text_username,text_fullname,text_confirm_password;
    private DAO dao;
    //</editor-fold>

    //<editor-fold defaultState="Collapse" desc="Internal Function">
    private void LoadData(){
        text_email = txt_email.getText().toString();
        text_password = txt_password.getText().toString();
        text_username = txt_username.getText().toString();
        text_fullname = txt_fullname.getText().toString();
        text_confirm_password = txt_confirm_password.getText().toString();
    }
    private String CheckData(){
        if (text_email.isEmpty() || text_password.isEmpty() || text_username.isEmpty() || text_fullname.isEmpty() || text_confirm_password.isEmpty())
            return "Vui lòng điền đầy đủ thông tin.";

        // Kiểm tra định dạng email bằng regex
        String emailRegex = "^[A-Za-z0-9+_.-]+@(.+)$";
        Pattern pattern = Pattern.compile(emailRegex);
        Matcher matcher = pattern.matcher(text_email);
        if (!matcher.matches())
            return "Email không đúng định dạng.";

        if (text_password.length() < 6)
            return "Mật khẩu phải có ít nhất 6 ký tự.";

        if (!text_password.equals(text_confirm_password))
            return "Xác nhận mật khẩu không khớp với mật khẩu.";
        // Nếu không có lỗi, trả về một chuỗi rỗng để chỉ ra dữ liệu hợp lệ.
        return "";
    }

    //</editor-fold>
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);
        //<editor-fold defaultState="Collapse" desc="Liên kết với components">
        txtv_confirm = (TextView) findViewById(R.id.txtv_confirm_in_signup);
        txt_username = (EditText) findViewById(R.id.txt_username);
        txt_password = (EditText) findViewById(R.id.txt_password);
        txt_fullname = (EditText) findViewById(R.id.txt_fullname);
        txt_confirm_password = (EditText) findViewById(R.id.txt_confirm_password);
        txt_email = (EditText) findViewById(R.id.txt_email);
        dao = new DAO();
        //</editor-fold>

        //<editor-fold defaultState="Collapse" desc="Xử lý sự kiện">
        txtv_confirm.setOnClickListener(view ->{
            LoadData();
            String chk = CheckData();
            if(!chk.isEmpty()){
                Toast.makeText(this, chk, Toast.LENGTH_SHORT).show();
            }
            else{
                dao.registerUser(text_username, text_fullname, text_password, text_email, new DAO.returnResult() {
                    @Override
                    public void OnReturnResult(int result) {
                        if (result == 1){
                            Toast.makeText(SignupActivity.this, "Đăng ký tài khoản thành công!", Toast.LENGTH_SHORT).show();
                            startActivity(new Intent(SignupActivity.this, LoginActitvity.class));
                        } else {
                            Toast.makeText(SignupActivity.this, "Đăng ký tài khoản thất bại!", Toast.LENGTH_SHORT).show();
                        }
                    }});
            }
        });
        findViewById(R.id.imageArrowback).setOnClickListener(view ->{
            startActivity(new Intent(this, MainActivity.class));
        });
        //</editor-fold>

    }
}