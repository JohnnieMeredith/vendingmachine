package com.example.controller;

import java.text.DecimalFormat;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Optional;
import java.util.regex.Pattern;

import com.example.model.CoinPayment;
import com.example.model.Config;
import com.example.model.Item;
import com.example.model.Payment;
import com.example.utilities.JsonInputReaderPOJO;
import com.example.utilities.MyJson;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * Vending Machine logic program
 * 
 * @author Johnnie Meredith
 * @version 1.0
 */
public class VendingMachine {

    private int columns;
    private int rows;
    // TODO: explain linked Hash map preserves order
    private LinkedHashMap<String, Item> productLayout = new LinkedHashMap<>();
    private Payment paymentReceived;
    private Logger logger = LogManager.getLogger(VendingMachine.class);
    private String pathToJson;

    /**
     * Constructor
     * 
     * @param path string path to json with config and products
     */
    public VendingMachine(String path) {
        this.pathToJson = path;
        JsonInputReaderPOJO jsonInputReaderPOJO = MyJson.buildJsonInputReaderPOJO(pathToJson);
        Item[] productList = jsonInputReaderPOJO.getItems();
        Config config = jsonInputReaderPOJO.getConfig();
        setupPayment(0);
        setupColsRows(config);
        setupProductLayout(productList);

    }

    /**
     * START SETTERS AND GETTERS
     */
    public int getColumns() {
        return this.columns;
    }

    public void setColumns(int columns) {
        this.columns = columns;
    }

    public int getRows() {
        return this.rows;
    }

    public void setRows(int rows) {
        this.rows = rows;
    }

    public Payment getPayment() {
        return this.paymentReceived;
    }

    public int getPaymentAmount() {
        return this.paymentReceived.getTotal();
    }

    /**
     * END SETTERS AND GETTERS
     */

    /**
     * Sets number of columns and rows from config
     * 
     * @param config hold the number of columns and rows
     */
    public void setupColsRows(Config config) {
        this.rows = config.getRows();
        this.columns = Integer.valueOf(config.getColumns());

    }

    /**
     * Builds the Letter Number combinations displayed on the vending machine.
     * Stored in a linkedHashMap because it preserves order while maintaining ease
     * of lookup.
     * 
     * @param items An array of items built from Json file and stored in MyJson
     *              object.
     */
    public void setupProductLayout(Item[] items) {
        int next = 0;
        for (int row = 0; row < rows; row++) {
            for (int column = 0; column < columns; column++) {
                StringBuilder productKey = new StringBuilder();
                char currentRow = convertIntToChar(row);
                productKey.append(currentRow);
                productKey.append(column);
                String name = "";
                int amount;
                String price;
                if (next < items.length) {
                    name = items[next].getName();
                    amount = items[next].getAmount();
                    price = items[next].getPrice();
                    productLayout.put(productKey.toString(), items[next]);
                    logger.info("ADDED: {} {}, Current Price : {} to Slot: {}", amount, name, price, productKey);
                }
                next++;

            }
        }
    }

    /**
     * Logic for displaying the positions and products in the vending machine.
     * 
     * @return Formatted string which can be written to display. Has columns and
     *         rows laid out.
     */
    public String printProductList() {
        int columnIndex = 1;
        StringBuilder productListString = new StringBuilder();
        for (Map.Entry<String, Item> entry : productLayout.entrySet()) {
            String position = entry.getKey();
            Item item = entry.getValue();
            String name = item.getName();
            String price = item.getPrice();
            int quantity = item.getAmount();

            productListString.append(String.format("[%s] %s-%s Qty:%s ", position, name, price, quantity));
            if (columnIndex % getColumns() == 0) {
                productListString.append("\n");
            }
            columnIndex++;

        }
        productListString.append("\n");
        return productListString.toString();
    }

    /**
     * Looks up item in our LinkedHashMap of Items using its key.
     * 
     * @param key String representing location of Item in the vending machine.
     * @return Item instance representing the item in that location
     */
    public Item lookupItem(String key) {
        return productLayout.get(key);
    }

    /**
     * Sets up a payment(Currently only CoinPayment) but Payment Interface allows
     * extension later.
     * 
     * @param amount Cash in single cent denomination.
     */
    public void setupPayment(int amount) {
        this.paymentReceived = new CoinPayment(amount);
        int dollars = getPaymentAmount() / 100;
        int cents = getPaymentAmount() % 100;
        logger.info("START OF NEW TRANSACTION");
        logger.info("PROCESSED PAYMENT: $ {}.{}", dollars, cents);
    }

    /**
     * Adds an amount to the current total of money payment.
     * 
     * @param amount Representaion of cash in single cent denomination.
     * @return
     */
    public int addToPaymentAmount(int amount) {
        this.paymentReceived.addAmount(amount);
        return getPaymentAmount();

    }

    /**
     * Logic which handles the string user selection from the gui. Verifies it and
     * returns the associated object if possible. Use of Optional here allows for
     * not existing without errors.
     * 
     * @param selection String of one char and one int from the GUI used as product
     *                  key
     * @return The Item associated with the key if it exist. Null otherwise.
     */
    public Optional<Item> getUserSelection(String selection) {
        Optional<Item> item = Optional.ofNullable(null);
        if (Pattern.matches("[A-Z][0-9]", selection)) {
            item = Optional.ofNullable(lookupItem(selection));
        }
        return item;

    }

    /**
     * Vending Machine logic to purchase an item. Checks that payment exceeds cost
     * and if the Item is in stock.
     * 
     * @param item The Item to be purchased.
     * @return Whether the purchase could be completed.
     */
    public boolean buyItem(Item item) {
        if (item != null) {
            String name = item.getName();
            String price = item.getPrice();
            if (item.getAmount() > 0) {
                if (paymentReceived.payAmount(item.getPriceinCents())) {
                    logger.info("PAYMENT PROCESSED: For Qty: 1 {} at {}", name, price);
                    dispenseItem(item);
                    return true;
                } else {

                    logger.info("FAILED PURCHASE OF {} at {} reason: Insufficient Funds", name, price);
                    return false;
                }
            } else {
                logger.info("FAILED PURCHASE: Reason: %s is OUT OF STOCK", name);
                return false;
            }
        } else {
            logger.info("FAILED PURCHASE: Reason: No such item bay");
            return false;
        }
    }

    /**
     * Logic which simulates dispensing the product and change to the customer after
     * purchase.
     * 
     * @param item Item that has been purchased that needs delivered to the
     *             Customer.
     */
    public void dispenseItem(Item item) {
        String itemName = item.getName();
        int tempAmount = (item.getAmount() - 1);

        item.setAmount(tempAmount);
        logger.info("DISPENSED: 1 {}.", itemName);
        if (item.getAmount() == 0) {
            logger.warn("{} is OUT OF STOCK", itemName);
        }
    }

    /**
     * Logic which handles the return of change to the customer.
     * 
     * @param money Amount of money in a string form
     * @return returns the amount to the GUI so that it can be displayed.
     */
    public String refundMoney(String money) {
        ((CoinPayment) this.paymentReceived).setTotal(0);
        logger.info("REFUND: ${}.", money);
        logger.info("END OF TRANSACTION\n");
        return money;
    }

    /**
     * 
     * Start Convenience Methhods
     */
    /**
     * Converts an int to a char for our Strings for the LinkedHashMap keys.
     * 
     * @param convertChar a character
     * @return
     */
    public char convertIntToChar(int convertChar) {
        char c = 'A';
        int numberConversion = c + convertChar;

        return (char) numberConversion;
    }

    /**
     * Converts an amount of money in cent to a String representation.
     * 
     * @param money Number of cents to be converted
     * @return Dollars and cents in form X.XX
     */
    public String getStringValueOfMoney(int money) {
        StringBuilder stringBuilder = new StringBuilder();
        DecimalFormat moneyFormat = new DecimalFormat("#.00");

        stringBuilder.append(moneyFormat.format(((double) money / 100)));
        return stringBuilder.toString();
    }

    /**
     * Converts String representation of dollars into an int value of money in
     * cents.
     * 
     * @param amount String which represents US dollars in cents $X.XX, X.XX, .XX
     * @return int number of cents the string converts to
     */
    public int getIntValueOfString(String amount) {
        StringBuilder sb = new StringBuilder();
        StringBuilder addZero;
        String assuredString;
        String parsed;
        String[] split;

        if (amount == null) {
            return 0;
        }
        if (!amount.contains(".")) {
            sb.append(amount);
            sb.append(".00");
        } else
            sb.append(amount);
        assuredString = sb.toString();
        parsed = assuredString.strip().replace("$", "");
        split = parsed.split("\\.");
        if (split[1].length() == 1) {
            addZero = new StringBuilder(split[1]);
            addZero.append("0");
            split[1] = addZero.toString();
        }
        if (split[0].length() == 0) {
            return Integer.valueOf((parsed.replace(".", "")));
        } else
            return (Integer.valueOf(split[0]) * 100) + Integer.valueOf(split[1]);
    }

    /**
     * End Convenience Metthods
     */

}
