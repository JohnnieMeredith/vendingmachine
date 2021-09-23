package com.example.model;

public interface Payment {

    public int getTotal();

    public void addAmount(int amount);

    public boolean payAmount(int amount);

}
