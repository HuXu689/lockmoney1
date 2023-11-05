package com.example.ltdd.Model;

public class CategoryItem {
    private String cate_id;
    private boolean isExpense;
    private int cate_image_id;
    private String cate_name;

    //<editor-fold> defaultState="collapse" desc="get, set, cons"
    public CategoryItem() {
    }

    public CategoryItem(int cate_image_id, String cate_name) {
        this.cate_image_id = cate_image_id;
        this.cate_name = cate_name;
    }

    public CategoryItem(String cate_id, boolean isExpense, int cate_image_id, String cate_name) {
        this.cate_id = cate_id;
        this.isExpense = isExpense;
        this.cate_image_id = cate_image_id;
        this.cate_name = cate_name;
    }

    public String getCate_id() {
        return cate_id;
    }

    public void setCate_id(String cate_id) {
        this.cate_id = cate_id;
    }

    public boolean isExpense() {
        return isExpense;
    }

    public void setExpense(boolean expense) {
        isExpense = expense;
    }

    public int getCate_image_id() {
        return cate_image_id;
    }

    public void setCate_image_id(int cate_image_id) {
        this.cate_image_id = cate_image_id;
    }

    public String getCate_name() {
        return cate_name;
    }

    public void setCate_name(String cate_name) {
        this.cate_name = cate_name;
    }
    //</editor-fold>
}
