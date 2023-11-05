package com.example.ltdd.Model;

public class ExpenseLimitModel {
    private String id;
    private String userId;
    private String categoryId;
    private int monthLimit;
    private int yearLimit;
    private int amountLimit;

    public ExpenseLimitModel() {
    }

    public ExpenseLimitModel(String id, int monthLimit, int yearLimit, int amountLimit, String userId, String categoryId) {
        this.id = id;
        this.userId = userId;
        this.categoryId = categoryId;
        this.monthLimit = monthLimit;
        this.yearLimit = yearLimit;
        this.amountLimit = amountLimit;
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

    public int getMonthLimit() {
        return monthLimit;
    }

    public void setMonthLimit(int monthLimit) {
        this.monthLimit = monthLimit;
    }

    public int getYearLimit() {
        return yearLimit;
    }

    public void setYearLimit(int yearLimit) {
        this.yearLimit = yearLimit;
    }

    public int getAmountLimit() {
        return amountLimit;
    }

    public void setAmountLimit(int amountLimit) {
        this.amountLimit = amountLimit;
    }
}
