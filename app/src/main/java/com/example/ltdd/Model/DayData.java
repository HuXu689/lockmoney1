package com.example.ltdd.Model;

import java.util.List;

public class DayData {
    private String dayName;
    private List<Expense> expenseList;
    private int totalAmount;

    public DayData() {
    }

    public DayData(String dayName, List<Expense> expenseList, int totalAmount) {
        this.dayName = dayName;
        this.expenseList = expenseList;
        this.totalAmount = totalAmount;
    }

    public String getDayName() {
        return dayName;
    }

    public void setDayName(String dayName) {
        this.dayName = dayName;
    }

    public List<Expense> getExpenseList() {
        return expenseList;
    }

    public void setExpenseList(List<Expense> expenseList) {
        this.expenseList = expenseList;
    }

    public int getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(int totalAmount) {
        this.totalAmount = totalAmount;
    }
}
