package com.github.drsmugleaf.commands.api.converter;

import com.github.drsmugleaf.NonNull;
import com.github.drsmugleaf.commands.api.CommandReceivedEvent;

import java.util.function.BiFunction;

/**
 * Created by DrSmugleaf on 28/02/2020
 */
public interface ITransformer<T> extends BiFunction<String, CommandReceivedEvent, T> {

    @NonNull
    @Override
    T apply(String s, CommandReceivedEvent commandReceivedEvent);

}
