package com.github.drsmugleaf.pokemon.base.logger;

import org.jetbrains.annotations.Contract;
import org.slf4j.LoggerFactory;

/**
 * Created by DrSmugleaf on 01/07/2019
 */
public class Logger {

    private static final org.slf4j.Logger LOGGER = LoggerFactory.getLogger(Logger.class);

    @Contract(pure = true)
    public static org.slf4j.Logger get() {
        return LOGGER;
    }

}
