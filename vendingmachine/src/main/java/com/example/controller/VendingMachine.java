package com.example.controller;

import java.text.DecimalFormat;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Optional;
import java.util.regex.Pattern;

import com.example.Utilities.JsonInputReaderPOJO;
import com.example.Utilities.MyJson;
import com.example.model.CoinPayment;
import com.example.model.Config;
import com.example.model.Item;
import com.example.model.Payment;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class VendingMachine {

    private int columns;
    private int rows;
    // TODO: explain linked Hash map preserves order
    private LinkedHashMap<String, Item> productLayout = new LinkedHashMap<>();
    private Payment paymentReceived;
    private Logger logger = LogManager.getLogger(VendingMachine.class);
    private String pathToJson;

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

    /**
     * END SETTERS AND GETTERS
     */

    public void setupColsRows(Config config) {
        this.rows = config.getRows();
        this.columns = Integer.valueOf(config.getColumns());

    }

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
            // writeToScreen(String.format("[%s] %s - %s : Qty:%s ", position, name, price,
            // quantity));
            if (columnIndex % getColumns() == 0) {
                productListString.append("\n");
                // writeToScreen("\n");
            }
            columnIndex++;

        }
        productListString.append("\n");
        // writeToScreen("\n");
        return productListString.toString();
    }

    public Item lookupItem(String key) {
        // TODO: handle bad input
        return productLayout.get(key);

    }

    /*
     * public void promptPayment() {
     * System.out.println("Enter payment amount in US Dollars: "); double x = 0;
     * Scanner sc = new Scanner(System.in); while (sc.hasNextDouble()) { x =
     * sc.nextDouble(); if (sc.nextLine().length() == 0) { break;
     * 
     * } } // sc.close(); int totalAsCents = (int) (x * 100);
     * setupPayment(totalAsCents); }
     */

    public void setupPayment(int amount) {
        this.paymentReceived = new CoinPayment(amount);
        int dollars = paymentReceived.getTotal() / 100;
        int cents = paymentReceived.getTotal() % 100;
        logger.info("START OF NEW TRANSACTION");
        logger.info("PROCESSED PAYMENT: $ {}.{}", dollars, cents);
    }

    public Payment getPayment() {
        return this.paymentReceived;
    }

    public int addToPaymentAmount(int amount) {
        this.paymentReceived.addAmount(amount);
        return paymentReceived.getTotal();

    }

    public int getPaymentAmount() {
        return this.paymentReceived.getTotal();
    }

    /*
     * public String getUserSelection() {
     * System.out.println("Enter your selection: "); Scanner scanner = new
     * Scanner(System.in); while (scanner.hasNextLine()) { // gui.readSelection
     * String selection = scanner.nextLine();
     * 
     * if (Pattern.matches("[A-Z][0-9]", selection)) {
     * buyItem(lookupItem(selection)); return selection; } else {
     * writeToScreen("Invalid Selection. Selection should be a capital letter followed by a number\n"
     * ); writeToScreen("Enter another selection:\n"); }
     * 
     * // gui.clearSelection(); } return "null";
     * 
     * }
     */

    public Optional<Item> getUserSelection(String selection) {
        Optional<Item> item = Optional.ofNullable(null);
        if (Pattern.matches("[A-Z][0-9]", selection)) {
            item = Optional.ofNullable(lookupItem(selection));
        }
        return item;

    }

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

    public void dispenseItem(Item item) {
        String itemName = item.getName();
        int tempAmount = (item.getAmount() - 1);

        item.setAmount(tempAmount);
        logger.info("DISPENSED: 1 {}.", itemName);
        if (item.getAmount() == 0) {
            logger.warn("{} is OUT OF STOCK", itemName);
        }
    }

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
    public char convertIntToChar(int convertChar) {
        char c = 'A';
        int numberConversion = c + convertChar;

        return (char) numberConversion;
    }

    public int convertCharToInt(char convertInt) {
        return (int) convertInt - (int) 'A';

    }

    public void writeToScreen(String s) {
        System.out.print(s);
    }

    public String getStringValueOfMoney(int money) {
        StringBuilder stringBuilder = new StringBuilder();
        DecimalFormat moneyFormat = new DecimalFormat("#.00");

        stringBuilder.append(moneyFormat.format(((double) money / 100)));
        return stringBuilder.toString();
    }

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
