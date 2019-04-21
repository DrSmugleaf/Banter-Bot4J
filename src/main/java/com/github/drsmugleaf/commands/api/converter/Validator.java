package com.github.drsmugleaf.commands.api.converter;

import com.github.drsmugleaf.commands.api.registry.CommandField;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.function.BiFunction;

/**
 * Created by DrSmugleaf on 19/04/2019
 */
public class Validator<E> {

    @Nonnull
    private final Class<E> IDENTIFIER;

    @Nullable
    private final BiFunction<CommandField, ? super E, String> VALIDATOR;

    public Validator(@Nonnull Class<E> validates, @Nullable BiFunction<CommandField, ? super E, String> validator) {
        IDENTIFIER = validates;
        VALIDATOR = validator;
    }

    @Nonnull
    public Class<E> getFor() {
        return IDENTIFIER;
    }

    @Nonnull
    public String validate(@Nonnull CommandField argument, @Nonnull E value) {
        if (VALIDATOR == null) {
            return "";
        }

        String error = VALIDATOR.apply(argument, value);
        return error == null ? "" : error;
    }

}
