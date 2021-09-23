package com.example.model;

/**
 * Data Class modelling an Item for sale in a vending machine.
 * 
 * @author Johnnie Meredith
 * @version 1.0
 */
public class Item {
    private String name;
    private int amount;
    private String price;

    public Item() {
    }

    public Item(String name, int amount, String price) {
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

    public int getPriceinCents() {
        String stringPrice = getPrice().replace("$", "").replace(".", "");
        return Integer.valueOf(stringPrice);

    }

    @Override
    public String toString() {
        return "{" + " name='" + getName() + "'" + ", amount='" + getAmount() + "'" + ", price='" + getPrice() + "'"
                + "}";
    }

}