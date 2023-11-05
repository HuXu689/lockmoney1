package com.example.ltdd.Model;

public class Category {
    private String tenDanhMuc;
    private int anhDanhMuc;
    public Category(){}

    public Category(String tenDanhMuc, int anhDanhMuc) {
        this.tenDanhMuc = tenDanhMuc;
        this.anhDanhMuc = anhDanhMuc;
    }

    public String getTenDanhMuc() {
        return tenDanhMuc;
    }

    public void setTenDanhMuc(String tenDanhMuc) {
        this.tenDanhMuc = tenDanhMuc;
    }

    public int getAnhDanhMuc() {
        return anhDanhMuc;
    }

    public void setAnhDanhMuc(int anhDanhMuc) {
        this.anhDanhMuc = anhDanhMuc;
    }
}
