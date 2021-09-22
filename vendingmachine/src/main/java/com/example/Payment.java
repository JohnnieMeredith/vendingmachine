package com.example;

public interface Payment {

    public int getTotal();

    public void addAmount(int amount);

    public boolean payAmount(int amount);

}
