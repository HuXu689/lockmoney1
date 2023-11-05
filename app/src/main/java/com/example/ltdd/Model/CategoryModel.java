package com.example.ltdd.Model;

public class CategoryModel {
    private String id;
    private String name;
    private int image;

    private String expenseTypeId;

    public CategoryModel(){}

    public CategoryModel(String id, String name, int image, String expenseTypeId) {
        this.id = id;
        this.name = name;
        this.image = image;
        this.expenseTypeId = expenseTypeId;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getImage() {
        return image;
    }

    public void setImage(int image) {
        this.image = image;
    }

    public String getExpenseTypeId() {
        return expenseTypeId;
    }

    public void setExpenseTypeId(String expenseType) {
        this.expenseTypeId = expenseType;
    }
}
