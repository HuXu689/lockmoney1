package com.example.ltdd.fragment;

import android.app.DatePickerDialog;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ExpandableListView;
import android.widget.GridLayout;
import android.widget.GridView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import com.example.ltdd.Adapter.CategoryItemAdapter;
import com.example.ltdd.Adapter.ExpandableListAdapter;
import com.example.ltdd.Adapter.ItemAdapter;
import com.example.ltdd.Model.CalendarSetUp;
import com.example.ltdd.Model.Category;
import com.example.ltdd.Model.CategoryItem;
import com.example.ltdd.Model.Expense;
import com.example.ltdd.Model.Item;
import com.example.ltdd.R;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

public class WriteFragment extends Fragment implements  View.OnClickListener{
    View view;
    //<editor-fold defaultState="collapse" desc="Khởi tạo dữ liệu cho List Category in Write Fragment">
    int cate_images[] = {R.drawable.img_food, R.drawable.img_payment, R.drawable.img_cart, R.drawable.img_transit};
    int cate_images2[] = {R.drawable.img_tienluong, R.drawable.img_lamthem, R.drawable.img_phucap, R.drawable.img_dautu};
    String cate_names[] = {"Đồ ăn", "Thanh toán", "Cửa hàng", "Di chuyển"};
    String cate_names2[] = {"Lương", "Làm thêm", "Phụ cấp", "Đầu tư"};

    private ExpandableListView expandableListView;
    private ExpandableListAdapter expandableListAdapter;

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
                    categoryItems.add(new CategoryItem(cate_images2[i],cate_names2[i]));
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
                String dateString = textViewDate.getText().toString(); // Chuỗi ngày "15/10/2023"
                int day = 1;
                int month;
                int year;
                SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
                Date date;
                try {
                    date = dateFormat.parse(dateString); // Chuyển chuỗi thành đối tượng Date
                    Calendar calendar = Calendar.getInstance();
                    calendar.setTime(date);

                    day = calendar.get(Calendar.DAY_OF_MONTH);
                    month = calendar.get(Calendar.MONTH); // Lấy giá trị tháng từ Calendar
                    year = calendar.get(Calendar.YEAR); // Lấy giá trị năm từ Calendar
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                int amount = Integer.parseInt(editTextAmount.getText().toString());
                String note = editTextNote.getText().toString();
                if(radioButtonIncome.isChecked()){
                    AddExpenses("Ngày "+ day, amount, note, itemName,"Thu");
                    Toast.makeText(getContext(),"Nhập thành công!",Toast.LENGTH_SHORT).show();
                }
                else if(radioButtonExpense.isChecked()){
                    AddExpenses("Ngày "+ day, amount, note, itemName,"Chi");
                    Toast.makeText(getContext(),"Nhập thành công!",Toast.LENGTH_SHORT).show();
                }
                else Toast.makeText(getContext(),"Chưa chọn mục thu, chi",Toast.LENGTH_SHORT).show();
                // Thực hiện các thao tác xử lý dữ liệu tại đây
                
                // Hiển thị thông báo hoặc thực hiện các thao tác khác sau khi lấy được dữ liệu

            }
        });

        return view;
    }
    private void AddExpenses(String day, int amount, String note, String loaiDanhMuc, String loaiThuChi){
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference("list_expenses");

        // Find the matching CategoryItem object
        CategoryItem selectedCategoryItem = null;
        for (CategoryItem categoryItem : categoryItems) {
            if (categoryItem.getCate_name().equals(loaiDanhMuc)) {
                selectedCategoryItem = categoryItem;
                break;
            }
        }

        // If a matching CategoryItem is found, use it
        if (selectedCategoryItem != null) {
            if(loaiThuChi.equals("Thu")) {
                myRef.child(day).child("content").push().setValue(new Expense(amount, note, new Category(selectedCategoryItem.getCate_name(), selectedCategoryItem.getCate_image_id())));
            } else if(loaiThuChi.equals("Chi")) {
                myRef.child(day).child("content").push().setValue(new Expense(-1 * amount, note, new Category(selectedCategoryItem.getCate_name(), selectedCategoryItem.getCate_image_id())));
            }
        } else {
            // Handle the case where no matching category is found
            Log.e("AddExpenses", "No matching category found for: " + loaiDanhMuc);
        }
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