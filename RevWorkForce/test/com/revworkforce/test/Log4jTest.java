package com.revworkforce.test;

import org.apache.log4j.Logger;

public class Log4jTest {

	private static final Logger logger =
            Logger.getLogger(Log4jTest.class);

    public static void main(String[] args) {

        logger.info("Log4j working successfully in RevWorkForce project");
        logger.warn("This is a warning log");
        logger.error("This is a sample error log");
    }
	
}
