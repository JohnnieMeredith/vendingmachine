package com.example;

import com.example.gui.VendingMachineGUI;

/**
 * Hello world!
 */
public final class App {
    private App() {
    }

    /**
     * Says hello to the world.
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
