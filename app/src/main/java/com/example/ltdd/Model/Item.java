package com.example.ltdd.Model;

public class Item {
    private int item_image;
    private String item_name;
    private int count;
    private int amount;

    // <editor-fold defaultstate="collapsed" desc="Set-get">//GEN-BEGIN:initComponents

    public int getItem_image() {
        return item_image;
    }

    public void setItem_image(int item_image) {
        this.item_image = item_image;
    }

    public String getItem_name() {
        return item_name;
    }

    public void setItem_name(String item_name) {
        this.item_name = item_name;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }
    // </editor-fold>//GEN-END:initComponents
    public Item() {}
    public Item(int item_image, String item_name, int count, int amount) {
        this.item_image = item_image;
        this.item_name = item_name;
        this.count = count;
        this.amount = amount;
    }
}
