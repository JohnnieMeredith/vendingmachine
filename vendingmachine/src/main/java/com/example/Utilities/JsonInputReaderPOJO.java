package com.example.utilities;

import java.util.Arrays;

import com.example.model.Config;
import com.example.model.Item;

/**
 * Utility class which stores the data read by MyJson class from the supplied
 * Json file.
 * 
 * @author Johnnie Meredith
 * @version 1.0
 */
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
