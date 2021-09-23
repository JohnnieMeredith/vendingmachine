package com.example.Utilities;

import java.util.Arrays;

import com.example.model.Config;
import com.example.model.Item;

public class JsonInputReaderPOJO {
    private Config config;
    private Item[] items;

    public JsonInputReaderPOJO() {
    }

    public JsonInputReaderPOJO(Config config, Item[] items) {
        this.config = config;
        this.items = items;
    }

    public Config getConfig() {
        return this.config;
    }

    public void setConfig(Config config) {
        this.config = config;
    }

    public Item[] getItems() {
        return this.items;
    }

    public void setItems(Item[] items) {
        this.items = items;
    }

    @Override
    public String toString() {
        return "{" + " config='" + getConfig() + "'" + ", items='" + Arrays.toString(getItems()) + "'" + "}";
    }

}
