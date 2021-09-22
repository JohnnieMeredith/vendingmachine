package com.example;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.File;
import java.util.Scanner;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

/**
 * Unit test for simple App.
 */
class AppTest {
    private static VendingMachine vm;
    private static Payment payment;

    /**
     * Rigorous Test.`
     */

    @BeforeAll
    public static void setupObjects() {
        String path = "..\\vendingmachine\\src\\main\\resources\\input.json";
        vm = new VendingMachine(path);
        payment = new CoinPayment(1000);
    }

    @Test
    public void shouldParseJson() throws Exception {
        String path = "..\\vendingmachine\\src\\test\\java\\com\\example\\Test.json";
        JsonInputReaderPOJO testJIRPojo = MyJson.buildJsonInputReaderPOJO(path);

        assertEquals(1, (testJIRPojo.getConfig()).getRows());
        assertEquals("2", testJIRPojo.getConfig().getColumns());
        assertEquals("Test1", testJIRPojo.getItems()[0].getName());
        assertEquals(1, (testJIRPojo.getItems()[0]).getAmount());
        assertEquals("$1.00", testJIRPojo.getItems()[0].getPrice());

    }

    @Test
    public void shouldLogMessages() throws Exception {
        VendingMachineLoggerTestHelper vendingMachineLogger = new VendingMachineLoggerTestHelper();
        vendingMachineLogger.logTask();
        Scanner logFileReader = new Scanner(new File("..\\vendingmachine\\logs\\propertieslogs.log"));
        String data = "";
        while (logFileReader.hasNextLine()) {
            data = logFileReader.nextLine();
        }
        String checkValue = "This is a fatal message";
        assertEquals(data.substring(data.length() - checkValue.length()), checkValue);
    }

    @Test
    public void shouldFillandRetrieveItemsFromHashMap() {
        // VendingMachine vm = new VendingMachine();
        assertEquals("Snickers", vm.lookupItem("A0").getName());
    }

    @Test
    public void shouldConvertToCents() {
        Item item = new Item("Text", 1, "$1.00");
        assertEquals(100, item.getPriceinCents());
    }

    @Test
    public void shouldAddAmountToPayment() {
        vm.setupPayment(1000);
        int newMoneyValye = vm.addToPaymentAmount(100);
        assertEquals(newMoneyValye, vm.getPaymentAmount());

    }

    @Test
    public void shouldLookupItem() {

        assertEquals("Snickers", vm.lookupItem("A0").getName());
    }

    @Test
    public void shouldPrintProductList() {
        vm.printProductList();
    }

    @Test
    public void shouldConvertCharToInt() {

        assertEquals(0, vm.convertCharToInt('A'));
    }

    @Test
    void shouldConvertIntToChar() {

        assertEquals('A', vm.convertIntToChar(0));
    }

    @Test
    void shouldReturnStringValueOfMoney() {
        assertEquals("10.00", vm.getStringValueOfMoney(1000));
    }

    @Test
    void shouldConvertIntToStringMoney() {
        assertEquals(vm.getIntValueOfString("$1"), 100);
        assertEquals(vm.getIntValueOfString("$.15"), 15);
        assertEquals(vm.getIntValueOfString("$0"), 0);
    }

    @Test
    void shouldConvertStringMoneytoCents() {
        assertEquals("1.00", vm.getStringValueOfMoney(100));
        assertEquals(".00", vm.getStringValueOfMoney(0));
        assertEquals(".00", vm.getStringValueOfMoney(0));
        assertEquals("15.15", vm.getStringValueOfMoney(1515));
    }

    @Test
    void shouldGetUserSelection() {
        // vm.getUserSelection();
    }
}
