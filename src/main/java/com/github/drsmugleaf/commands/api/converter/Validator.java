package com.github.drsmugleaf.commands.api.converter;

import com.github.drsmugleaf.Nullable;
import com.github.drsmugleaf.commands.api.registry.CommandField;

import java.util.function.BiFunction;

/**
 * Created by DrSmugleaf on 19/04/2019
 */
public class Validator<E> {

    private final Class<E> IDENTIFIER;
    @Nullable
    private final BiFunction<CommandField, ? super E, String> VALIDATOR;

    public Validator(Class<E> validates, @Nullable BiFunction<CommandField, ? super E, String> validator) {
        IDENTIFIER = validates;
        VALIDATOR = validator;
    }

    public Class<E> getFor() {
        return IDENTIFIER;
    }

    @Nullable
    public String getError(CommandField argument, E value) {
        return VALIDATOR == null ? null : VALIDATOR.apply(argument, value);
    }

}
