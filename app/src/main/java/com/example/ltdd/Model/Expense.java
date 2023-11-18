package com.example.ltdd.Model;

public class Expense {
    private double tien;
    private String noidung;

    private Category category;

    public Expense(){}

    public Expense(double tien, String noidung, Category category) {
        this.tien = tien;
        this.noidung = noidung;
        this.category = category;
    }

    public double getTien() {
        return tien;
    }

    public void setTien(double tien) {
        this.tien = tien;
    }

    public String getNoidung() {
        return noidung;
    }

    public void setNoidung(String noidung) {
        this.noidung = noidung;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

}
