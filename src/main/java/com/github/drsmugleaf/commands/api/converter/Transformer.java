package com.github.drsmugleaf.commands.api.converter;

import com.github.drsmugleaf.commands.api.CommandReceivedEvent;

import java.util.function.BiFunction;

/**
 * Created by DrSmugleaf on 28/02/2020
 */
public class Transformer<T> {

    private final Class<T> TYPE;
    private final BiFunction<String, CommandReceivedEvent, T> TRANSFORMER;

    public Transformer(Class<T> type, BiFunction<String, CommandReceivedEvent, T> transformer) {
        TYPE = type;
        TRANSFORMER = transformer;
    }

    public Class<T> getType() {
        return TYPE;
    }

    public BiFunction<String, CommandReceivedEvent, T> getTransformer() {
        return TRANSFORMER;
    }

}
