package com.example.model;

/**
 * Coin Payment models adding cash to a vending machine Dealing with only cents
 * makes life easier in coversions
 * 
 * @author Johnnie Meredith
 * @version 1.0
 * 
 */
public class CoinPayment implements Payment {
    private int total;

    public CoinPayment(int total) {
        this.total = total;
    }

    public int getTotal() {
        return this.total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public void addAmount(int amount) {

        this.total += amount;

    }

    /**
     * @param amount is the cost of an Item
     * @return whether the payment covers the cost
     */
    public boolean payAmount(int amount) {
        if (getTotal() >= amount) {
            int leftOver = (getTotal() - amount);
            setTotal(leftOver);
            return true;
        } else
            return false;
    }
}
