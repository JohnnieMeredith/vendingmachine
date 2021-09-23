package com.example;

import com.example.controller.VendingMachine;
import com.example.view.VendingMachineGUI;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * Main Class for a project that simulates the operation of a vending machine.
 * Reads the items and layout from a Json file. Creates GUI and logic.
 * 
 * @author Johnnie Meredith
 * @version 1.0
 */
public final class App {
    private static Logger logger = LogManager.getLogger(App.class);

    private App() {
    }

    /**
     * Builds GUI and logic class needed to simulate the operation of a Vending
     * Machine.
     * 
     * @param args The arguments of the program.
     */
    public static void main(String[] args) {
        logger.debug("STARTING VENDING MACHINE");
        String path = "vendingmachine\\src\\main\\resources\\input.json";
        logger.debug("Path set to {}", path);
        VendingMachine vendingMachine = new VendingMachine(path);
        VendingMachineGUI gui = new VendingMachineGUI();
        gui.setVendingMachine(vendingMachine);
        gui.run();
    }

}
