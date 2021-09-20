package com.example;

import java.util.Arrays;

public class JsonInputReaderPOJO {
    private Config config;
    private Items[] items;

    public JsonInputReaderPOJO() {
    }

    public JsonInputReaderPOJO(Config config, Items[] items) {
        this.config = config;
        this.items = items;
    }

    public Config getConfig() {
        return this.config;
    }

    public void setConfig(Config config) {
        this.config = config;
    }

    public Items[] getItems() {
        return this.items;
    }

    public void setItems(Items[] items) {
        this.items = items;
    }

    @Override
    public String toString() {
        return "{" + " config='" + getConfig() + "'" + ", items='" + Arrays.toString(getItems()) + "'" + "}";
    }

}
