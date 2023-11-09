package com.example.ltdd.Adapter;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.ltdd.Model.Item;
import com.example.ltdd.Model.UserModel;
import com.example.ltdd.R;

import java.util.ArrayList;

public class UserAdater extends ArrayAdapter<UserModel> {
    Activity context;
    int id_layout;
    ArrayList<UserModel> userModels;

    public UserAdater(Activity context, int id_layout, ArrayList<UserModel> userModels) {
        super(context, id_layout, userModels);
        this.context = context;
        this.id_layout = id_layout;
        this.userModels = userModels;
    }
    // Gọi getView sắp xếp dữ liệu

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        // Tạo Infractor chứa layout
        LayoutInflater layoutInflater = context.getLayoutInflater();
        // Gọi id Layout tạo thành View
        convertView = layoutInflater.inflate(id_layout, null);
        // Lấy 1 phần tử
        UserModel user = userModels.get(position);
        // Khai báo, tham chiếu id và hiện thị lên imageView
//        ImageView user_image = convertView.findViewById(R.id.user_image);
//        // Mặc định vì userModel méo có image_id
//        user_image.setImageResource(R.drawable.img_user);
//        // Tương tự
        TextView txt_username = convertView.findViewById(R.id.username);
        txt_username.setText(user.getUsername());
        return convertView;
    }
}
