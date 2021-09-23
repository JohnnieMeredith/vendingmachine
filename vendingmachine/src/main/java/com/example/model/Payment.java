package com.example.model;

/**
 * Interface for payments that our vending machine will take.
 * 
 * @author Johnnie Meredith
 * @version 1.0
 */
public interface Payment {

    public int getTotal();

    public void addAmount(int amount);

    public boolean payAmount(int amount);

}
