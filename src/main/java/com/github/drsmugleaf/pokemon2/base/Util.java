package com.github.drsmugleaf.pokemon2.base;

import org.jetbrains.annotations.Contract;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by DrSmugleaf on 01/07/2019
 */
public class Util {

    private static final Logger LOGGER = LoggerFactory.getLogger(Util.class);

    @Contract(pure = true)
    public static Logger getLogger() {
        return LOGGER;
    }

}
