package com.example.model;

/**
 * Data class for holding config portion of Json read from file by MyJson.
 * 
 * @author Johnnie Meredith
 * @version 1.0
 */
public class Config {
    private int rows;
    private String columns;

    public Config() {
    }

    public Config(int rows, String columns) {
        this.rows = rows;
        this.columns = columns;
    }

    public int getRows() {
        return this.rows;
    }

    public void setRows(int rows) {
        this.rows = rows;
    }

    public String getColumns() {
        return this.columns;
    }

    public void setColumns(String columns) {
        this.columns = columns;
    }

    @Override
    public String toString() {
        return "{" + " rows='" + getRows() + "'" + ", columns='" + getColumns() + "'" + "}";
    }
}
