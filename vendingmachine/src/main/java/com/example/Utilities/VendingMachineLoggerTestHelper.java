package com.example.utilities;

import org.apache.logging.log4j.LogManager;

import org.apache.logging.log4j.Logger;

/**
 * Test Class for logging
 */
public class VendingMachineLoggerTestHelper {
    Logger logger = LogManager.getLogger(this.getClass().getName());

    public void logTask() {
        logger.fatal("This is a fatal message");
    }

}
