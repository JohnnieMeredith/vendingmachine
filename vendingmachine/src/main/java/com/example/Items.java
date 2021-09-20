package com.example;

/**
 * Product
 */
public class Items {
    private String name;
    private int amount;
    private String price;

    public Items() {
    }

    public Items(String name, int amount, String price) {
        this.name = name;
        this.amount = amount;
        this.price = price;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAmount() {
        return this.amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public String getPrice() {
        return this.price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    @Override
    public String toString() {
        return "{" + " name='" + getName() + "'" + ", amount='" + getAmount() + "'" + ", price='" + getPrice() + "'"
                + "}";
    }

}