package com.example.ltdd.Model;

import java.sql.Date;

public class ExpenseModel {
    private String id;
    private String userId;
    private String categoryId;
    private String notes;
    private int amount;
    private Date dateExpense;

    public ExpenseModel() {
    }

    public ExpenseModel(String id, String notes, int amount, Date dateExpense, String userId, String categoryId) {
        this.id = id;
        this.userId = userId;
        this.categoryId = categoryId;
        this.notes = notes;
        this.amount = amount;
        this.dateExpense = dateExpense;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(String categoryId) {
        this.categoryId = categoryId;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public Date getDateExpense() {
        return dateExpense;
    }

    public void setDateExpense(Date dateExpense) {
        this.dateExpense = dateExpense;
    }
}
