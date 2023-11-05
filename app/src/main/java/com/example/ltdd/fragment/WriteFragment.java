package com.example.ltdd.fragment;

import android.app.DatePickerDialog;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.GridLayout;
import android.widget.GridView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import com.example.ltdd.Adapter.CategoryItemAdapter;
import com.example.ltdd.Adapter.ItemAdapter;
import com.example.ltdd.Model.CalendarSetUp;
import com.example.ltdd.Model.CategoryItem;
import com.example.ltdd.Model.Item;
import com.example.ltdd.R;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

public class WriteFragment extends Fragment implements  View.OnClickListener{
    View view;
    //<editor-fold defaultState="collapse" desc="Khởi tạo dữ liệu cho List Category in Write Fragment">
    int cate_images[] = {R.drawable.img_food, R.drawable.img_payment, R.drawable.img_cart, R.drawable.img_transit};
    String cate_names[] = {"Đồ ăn", "Thanh toán", "Cửa hàng", "Di chuyển"};
    String cate_names2[] = {"Lương", "Làm thêm", "Phụ cấp", "Đầu tư"};

    //</editor-fold>
    //<editor-fold defaultState="collapse" desc="Khai báo cho List Category in Write Fragment">

    ArrayList<CategoryItem> categoryItems;
    CategoryItemAdapter categoryItemAdapter;


    private TextView _vdate;
    private Button btnNextDay;
    private Button btnPreDay;
    GridView gvCateItem;

    // lấy dữ liệu nhập
    RadioButton radioButtonExpense;
    RadioButton radioButtonIncome;
    TextView textViewDate;
    EditText editTextAmount;
    EditText editTextNote;
    TextView buttonAdd;
    private CalendarSetUp calendarSetUp;
    String itemName;
    int itemImage;

    //</editor-fold>

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {}
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_write, container, false);
        GetComponent(view);
        RegisterEvent();
        //<editor-fold defaultState ="collapse" desc="Hiện thị list category">
        gvCateItem = view.findViewById(R.id.gv_writeFrag);
        categoryItems = new ArrayList<>();
        for (int i = 0; i < cate_images.length; i++) {
            categoryItems.add(new CategoryItem(cate_images[i],cate_names[i]));
        }
        categoryItemAdapter = new CategoryItemAdapter(requireActivity(), R.layout.category_item_in_wtite_fragment, categoryItems);
        gvCateItem.setAdapter(categoryItemAdapter);
        //</editor-fold>
        calendarSetUp = new CalendarSetUp();
        _vdate.setText(calendarSetUp.getDate());
        //Thay đổi danh mục theo thu, chi
        radioButtonIncome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Lấy giá trị từ các thành phần giao diện
                gvCateItem = view.findViewById(R.id.gv_writeFrag);
                categoryItems = new ArrayList<>();
                for (int i = 0; i < cate_images.length; i++) {
                    categoryItems.add(new CategoryItem(cate_images[i],cate_names2[i]));
                }
                categoryItemAdapter = new CategoryItemAdapter(requireActivity(), R.layout.category_item_in_wtite_fragment, categoryItems);
                gvCateItem.setAdapter(categoryItemAdapter);
            }
        });
        radioButtonExpense.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Lấy giá trị từ các thành phần giao diện
                gvCateItem = view.findViewById(R.id.gv_writeFrag);
                categoryItems = new ArrayList<>();
                for (int i = 0; i < cate_images.length; i++) {
                    categoryItems.add(new CategoryItem(cate_images[i],cate_names[i]));
                }
                categoryItemAdapter = new CategoryItemAdapter(requireActivity(), R.layout.category_item_in_wtite_fragment, categoryItems);
                gvCateItem.setAdapter(categoryItemAdapter);
            }
        });
        // lấy dữ liệu danh mục
        gvCateItem.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                // Lấy dữ liệu từ GridView
                CategoryItem selectedItem = categoryItems.get(position);
                itemName = selectedItem.getCate_name();
                itemImage = selectedItem.getCate_image_id();

                // Thực hiện các thao tác khác với dữ liệu lấy được từ GridView
            }
        });
        // lấy dữ liệu nhập

        buttonAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Lấy giá trị từ các thành phần giao diện
                String date = textViewDate.getText().toString();
                String amount = editTextAmount.getText().toString();
                String note = editTextNote.getText().toString();
                if(radioButtonIncome.isChecked())
                    Toast.makeText(getContext(), "NOTE:"+note+",Date:"+date+",Số tiền:"+amount+",Loại danh mục:"+itemName+",Loại thu chi: Thu",Toast.LENGTH_SHORT).show();
                else if(radioButtonExpense.isChecked())
                    Toast.makeText(getContext(), "NOTE:"+note+",Date:"+date+",Số tiền:"+amount+",Loại danh mục:"+itemName+",Loại thu chi: Chi",Toast.LENGTH_SHORT).show();
                else Toast.makeText(getContext(),"Chưa chọn mục thu, chi",Toast.LENGTH_SHORT).show();
                // Thực hiện các thao tác xử lý dữ liệu tại đây

                // Hiển thị thông báo hoặc thực hiện các thao tác khác sau khi lấy được dữ liệu

            }
        });

        return view;
    }
    private  void GetComponent(View view)
    {
        _vdate = view.findViewById(R.id.calendarView);
        btnNextDay = view.findViewById(R.id.btn_nextdayin);
        btnPreDay = view.findViewById(R.id.btn_predayin);
        radioButtonExpense = view.findViewById(R.id.rbtnExpense);
        radioButtonIncome = view.findViewById(R.id.rbtnIncome);
        textViewDate = view.findViewById(R.id.calendarView);
        editTextAmount = view.findViewById(R.id.edtAmount);
        editTextNote = view.findViewById(R.id.edtNote);
        buttonAdd = view.findViewById(R.id.btnThem);
    }
    private  void RegisterEvent()
    {
        _vdate.setOnClickListener(this);
        btnPreDay.setOnClickListener(this);
        btnNextDay.setOnClickListener(this);
    }
    @Override
    public void onClick(View v) {
        int id  = v.getId();

        //Date
        if(id == _vdate.getId())
        {
            DatePickerDialog g = new DatePickerDialog(getContext() ,new DatePickerDialog.OnDateSetListener() {
                @Override
                public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                    _vdate.setText(dayOfMonth + "/" + (int)(month+1) + "/" + year);
                    calendarSetUp = new CalendarSetUp(dayOfMonth,month+1,year);
                }
            }, calendarSetUp.getYear(),calendarSetUp.getMonth()-1,calendarSetUp.getDay());
            g.show();
        }
        if(id == btnNextDay.getId())
        {
            calendarSetUp.NextDay();
            _vdate.setText(calendarSetUp.getDate());
        }
        if(id == btnPreDay.getId())
        {
            calendarSetUp.PreviousDay();
            _vdate.setText(calendarSetUp.getDate());
        }
    }
}