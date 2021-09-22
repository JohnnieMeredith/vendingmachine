package com.example;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Scanner;
import java.util.regex.Pattern;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class VendingMachine {

    private int columns;
    private int rows;
    // TODO: explain linked Hash map preserves order
    private LinkedHashMap<String, Item> productLayout = new LinkedHashMap<>();
    private Payment paymentReceived;
    private Logger logger = LogManager.getLogger(VendingMachine.class);

    public VendingMachine() {
        String path = "src\\main\\resources\\input.json";
        JsonInputReaderPOJO jsonInputReaderPOJO = MyJson.buildJsonInputReaderPOJO(path);
        Item[] productList = jsonInputReaderPOJO.getItems();
        Config config = jsonInputReaderPOJO.getConfig();
        setupPayment(0);
        setupColsRows(config);
        setupProductLayout(productList);

    }

    public void setupColsRows(Config config) {
        this.rows = config.getRows();
        this.columns = Integer.valueOf(config.getColumns());

    }

    public void setupProductLayout(Item[] items) {

        int next = 0;
        for (int row = 0; row < rows; row++) {
            for (int column = 0; column < columns; column++) {
                StringBuilder productKey = new StringBuilder();
                char c = 'A';
                int numberConversion = c + row;
                char currentRow = (char) numberConversion;
                productKey.append(currentRow);
                productKey.append(column);
                String name = "Empty";
                int amount = 0;
                String price = "$0.00";
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

    public void printProductList() {
        int columnIndex = 1;

        for (Map.Entry<String, Item> entry : productLayout.entrySet()) {
            String pos = entry.getKey();
            Item item = entry.getValue();

            writeToScreen(
                    String.format("[%s] %s - %s : %s Left\t", pos, item.getName(), item.getPrice(), item.getAmount()));
            if (columnIndex % getColumns() == 0) {
                writeToScreen("\n");
            }
            ;
            columnIndex++;

        }
    }

    public int getRows() {
        return this.rows;
    }

    public int getColumns() {
        return this.columns;
    }

    public Item lookupItem(String key) {
        return productLayout.get(key);

    }

    public void writeToScreen(String s) {
        System.out.print(s);
    }

    public void promptPayment() {
        System.out.println("Enter payment amount in US Dollars");
    }

    public void setupPayment(int amount) {

        this.paymentReceived = new CoinPayment(amount);
        logger.info(String.format("PAYMENT ADDED: $ %s.%s cents", paymentReceived.getTotal() / 100,
                paymentReceived.getTotal() % 100));
    }

    public void setPayment(Payment payment) {
        this.paymentReceived = payment;
    }

    public int addToPaymentAmount(int amount) {
        this.paymentReceived.addAmount(amount);
        return paymentReceived.getTotal();

    }

    public int getPaymentAmount() {
        return this.paymentReceived.getTotal();
    }

    public String getUserSelection() {
        System.out.println("Enter your selection: ");
        Scanner scanner = new Scanner(System.in);
        while (scanner.hasNextLine()) {
            // gui.readSelection
            String selection = scanner.nextLine();

            if (Pattern.matches("[A-Z][0-9]", selection)) {
                buyItem(lookupItem(selection));
                return selection;
            } else
                writeToScreen("Invalid Selection");

            // gui.clearSelection();
        }
        return "null";

    }

    public void getUserSelection(String selection) {
        if (Pattern.matches("[A-Z][0-9]", selection)) {
            buyItem(lookupItem(selection));
        }
    }

    public boolean buyItem(Item item) {
        String name = item.getName();
        String price = item.getPrice();
        if (item.getAmount() > 0) {
            if (paymentReceived.payAmount(item.getPriceinCents())) {
                logger.info("SOLD: 1 %s at %s", name, price);
                dispenseItem(item);

                return true;
            } else {

                logger.info("FAILED PURCHASE OF %s at %s reason: Insufficient Funds", name, price);
                return false;
            }
        } else {
            logger.info("FAILED PURCHASE OF %s reason: OUT OF STOCK", name);
            return false;
        }
    }

    public void dispenseItem(Item item) {
        int tempAmount = item.getAmount() - 1;
        item.setAmount(tempAmount);
        writeToScreen(String.format("Dispensing %s.", item.getName()));
        logger.info(String.format("DISPENSED: 1 %s.", item.getName()));
    }

    public static void main(String[] args) {
        VendingMachine vendingMachine = new VendingMachine();
        vendingMachine.printProductList();
        vendingMachine.promptPayment();
        vendingMachine.getUserSelection();

    }

}
