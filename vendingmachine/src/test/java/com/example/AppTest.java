package com.example;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.File;
import java.util.Scanner;

import org.junit.jupiter.api.Test;

/**
 * Unit test for simple App.
 */
class AppTest {
    /**
     * Rigorous Test.`
     */

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
}
