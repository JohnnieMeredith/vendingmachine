package com.example;

import com.example.controller.VendingMachine;
import com.example.view.VendingMachineGUI;

import org.apache.commons.lang3.ClassPathUtils;

/**
 * Main Class for a project that simulates the operation of a vending machine.
 * Reads the items and layout from a Json file. Creates GUI and logic.
 * 
 * @author Johnnie Meredith
 * @version 1.0
 */
public final class App {
    private App() {
    }

    /**
     * Builds GUI and logic class needed to simulate the operation of a Vending
     * Machine.
     * 
     * @param args The arguments of the program.
     */
    public static void main(String[] args) {

        String path = "vendingmachine\\src\\main\\resources\\input.json";
        VendingMachine vendingMachine = new VendingMachine(path);
        VendingMachineGUI gui = new VendingMachineGUI();
        gui.setVendingMachine(vendingMachine);
        gui.run();
    }

}
