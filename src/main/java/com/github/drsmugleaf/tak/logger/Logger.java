package com.github.drsmugleaf.tak.logger;

import org.slf4j.LoggerFactory;

/**
 * Created by DrSmugleaf on 03/02/2020
 */
public class Logger {

    private static final org.slf4j.Logger LOGGER = LoggerFactory.getLogger(Logger.class);

    private Logger() {}

    public static org.slf4j.Logger getLogger() {
        return LOGGER;
    }

}
