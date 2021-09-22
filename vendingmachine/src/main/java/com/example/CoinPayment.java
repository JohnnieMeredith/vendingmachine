package com.example;

public class CoinPayment implements Payment {
    private int total;

    public CoinPayment(int total) {
        this.total = total;
    }

    public int getTotal() {
        return this.total;
    }

    private void setTotal(int total) {
        this.total = total;
    }

    public void addAmount(int amount) {

        this.total += amount;

    }

    public boolean payAmount(int amount) {
        if (getTotal() >= amount) {
            int leftOver = getTotal() - amount;
            setTotal(leftOver);
            return true;
        } else
            return false;
    }
}
