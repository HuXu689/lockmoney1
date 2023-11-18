package com.example.ltdd.fragment;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ExpandableListView;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;


import com.example.ltdd.Activity.MainActivity;
import com.example.ltdd.Adapter.ExpandableListAdapter;
import com.example.ltdd.Model.Category;
import com.example.ltdd.Model.DayData;
import com.example.ltdd.Model.Expense;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import com.example.ltdd.Activity.CategoryManagerActivity;
import com.example.ltdd.R;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link HomeFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class HomeFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private ExpandableListView expandableListView;
    private ExpandableListAdapter expandableListAdapter;
    private FirebaseDatabase database = FirebaseDatabase.getInstance();

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public HomeFragment() {
        // Required empty public constructor
    }


    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment HomeFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static HomeFragment newInstance(String param1, String param2) {
        HomeFragment fragment = new HomeFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }

    }
    View view;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_home, container, false);
        ImageView notifi_click = (ImageView) view.findViewById(R.id.notifi_click);
        notifi_click.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onClickAddAllExpenses();
            }
        });

        expandableListView = (ExpandableListView) view.findViewById(R.id.lvChiTieu);

        getListExpensesFromFirebase();
        getDetailExpenseFromFirebase();
        return view;
    }
    private void getDetailExpenseFromFirebase(){
        String[] data = {"Tất cả", "Ngày 31", "Ngày 30", "Ngày 29", "Ngày 28", "Ngày 27", "Ngày 26", "Ngày 25", "Ngày 24", "Ngày 23",
                "Ngày 22", "Ngày 21", "Ngày 20", "Ngày 19", "Ngày 18", "Ngày 17", "Ngày 16", "Ngày 15", "Ngày 14", "Ngày 13", "Ngày 12",
                "Ngày 11", "Ngày 10", "Ngày 09", "Ngày 08", "Ngày 07", "Ngày 06", "Ngày 05", "Ngày 04", "Ngày 03", "Ngày 02", "Ngày 01"};

        ArrayAdapter<String> adapterDay = new ArrayAdapter<>(requireContext(), android.R.layout.simple_spinner_item, data);
        adapterDay.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        Spinner spinner = view.findViewById(R.id.spnDay);
        spinner.setAdapter(adapterDay);

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                DatabaseReference myRef = database.getReference("list_expenses");

                String selectedDate = parent.getItemAtPosition(position).toString(); // Lấy ngày đã chọn từ Spinner


                // Truy vấn dữ liệu từ Firebase dựa trên ngày đã chọn
                DatabaseReference dateRef = myRef.child(selectedDate);
                dateRef.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        if(selectedDate.equals("Tất cả")){
                            getListExpensesFromFirebase();
                        }else{
                            List<DayData> dayDataList = new ArrayList<>();
                            for (DataSnapshot daySnapshot : dataSnapshot.getChildren()) {
                                String dayName = selectedDate;
                                List<Expense> expenseList = new ArrayList<>();
                                int totalAmount = 0;

                                for (DataSnapshot expenseSnapshot : daySnapshot.getChildren()) {
                                    String noidung = expenseSnapshot.child("noidung").getValue(String.class);
                                    int tien = expenseSnapshot.child("tien").getValue(Integer.class);
                                    String tenDanhMuc = expenseSnapshot.child("category").child("tenDanhMuc").getValue(String.class);
                                    int anhDanhMuc = expenseSnapshot.child("category").child("anhDanhMuc").getValue(Integer.class);
                                    totalAmount += tien;

                                    Expense expense = new Expense(tien, noidung, new Category(tenDanhMuc, anhDanhMuc));
                                    expenseList.add(expense);
                                }

                                DayData dayData = new DayData(dayName, expenseList, totalAmount);
                                dayDataList.add(dayData);
                            }

                            // Xử lý dữ liệu trả về từ Firebase ở đây và hiển thị trong ExpandableListView
                            expandableListAdapter = new ExpandableListAdapter(requireContext() ,dayDataList); // Tạo adapter của bạn và truyền dữ liệu từ dataSnapshot
                            expandableListView.setAdapter(expandableListAdapter);
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {
                        // Xử lý lỗi nếu có
                    }
                });
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    private void onClickAddAllExpenses(){
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference("list_expenses");

//        myRef.child("Năm 2023").child("Tháng 11").child("Ngày 21").child("content").push().setValue(new Expense(-40000, "Mua cơm", new Category("Đồ ăn", R.drawable.img_food)) );
//        myRef.child("Năm 2023").child("Tháng 11").child("Ngày 21").child("content").push().setValue(new Expense(-40000, "Mua cơm", new Category("Đồ ăn", R.drawable.img_food)) );
//        myRef.child("Năm 2023").child("Tháng 11").child("Ngày 21").child("content").push().setValue(new Expense(-750000, "Thanh toán tiền nhà", new Category("Thanh toán", R.drawable.img_payment)) );
//        myRef.child("Năm 2023").child("Tháng 11").child("Ngày 20").child("content").push().setValue(new Expense(-40000, "Mua cơm", new Category("Đồ ăn", R.drawable.img_food)) );
//        myRef.child("Năm 2023").child("Tháng 11").child("Ngày 20").child("content").push().setValue(new Expense(-20000, "Đi học cơ sở 2", new Category("Di chuyển", R.drawable.img_transit)) );
//        myRef.child("Năm 2023").child("Tháng 11").child("Ngày 19").child("content").push().setValue(new Expense(-40000, "Mua cơm", new Category("Đồ ăn", R.drawable.img_food)) );
//        myRef.child("Năm 2023").child("Tháng 11").child("Ngày 19").child("content").push().setValue(new Expense(-150000, "Thanh toán tiền điện", new Category("Thanh toán", R.drawable.img_payment)) );
//        myRef.child("Năm 2023").child("Tháng 11").child("Ngày 18").child("content").push().setValue(new Expense(-40000, "Mua cơm", new Category("Đồ ăn", R.drawable.img_food)) );
//        myRef.child("Năm 2023").child("Tháng 11").child("Ngày 18").child("content").push().setValue(new Expense(-250000, "Mua giày", new Category("Cửa hàng", R.drawable.img_cart)) );
//        myRef.child("Năm 2023").child("Tháng 11").child("Ngày 17").child("content").push().setValue(new Expense(-20000, "Mua cơm", new Category("Đồ ăn", R.drawable.img_food)) );
//        myRef.child("Năm 2023").child("Tháng 11").child("Ngày 17").child("content").push().setValue(new Expense(-100000, "Mua đồ dùng sinh hoạt", new Category("Cửa hàng", R.drawable.img_cart)) );
//        myRef.child("Năm 2023").child("Tháng 11").child("Ngày 16").child("content").push().setValue(new Expense(-40000, "Mua cơm", new Category("Đồ ăn", R.drawable.img_food)) );
        Toast.makeText(getContext(),"Đã thêm",Toast.LENGTH_SHORT).show();
    }

    private void getListExpensesFromFirebase(){
        DatabaseReference myRef = database.getReference("list_expenses");

        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                List<DayData> dayDataList = new ArrayList<>();

                for (DataSnapshot daySnapshot : snapshot.getChildren()) {
                    String dayName = daySnapshot.getKey();
                    List<Expense> expenseList = new ArrayList<>();
                    int totalAmount = 0;

                    for (DataSnapshot expenseSnapshot : daySnapshot.child("content").getChildren()) {
                        String noidung = expenseSnapshot.child("noidung").getValue(String.class);
                        int tien = expenseSnapshot.child("tien").getValue(Integer.class);
                        String tenDanhMuc = expenseSnapshot.child("category").child("tenDanhMuc").getValue(String.class);
                        int anhDanhMuc = expenseSnapshot.child("category").child("anhDanhMuc").getValue(Integer.class);
                        totalAmount += tien;

                        Expense expense = new Expense(tien, noidung, new Category(tenDanhMuc, anhDanhMuc));
                        expenseList.add(expense);
                    }

                    DayData dayData = new DayData(dayName, expenseList, totalAmount);
                    dayDataList.add(dayData);
                }

                Collections.sort(dayDataList, new Comparator<DayData>() {
                    @Override
                    public int compare(DayData day1, DayData day2) {
                        String date1 = day1.getDayName();
                        String date2 = day2.getDayName();
                        // Sắp xếp giảm dần theo ngày
                        return date2.compareTo(date1);
                    }
                });

                expandableListAdapter = new ExpandableListAdapter(requireContext(), dayDataList);
                expandableListView.setAdapter(expandableListAdapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(requireContext(), "Get list expenses faild!", Toast.LENGTH_SHORT).show();
            }
        });
    }
}