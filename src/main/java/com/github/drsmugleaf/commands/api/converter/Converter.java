package com.github.drsmugleaf.commands.api.converter;

import com.github.drsmugleaf.commands.api.registry.CommandField;

import javax.annotation.Nonnull;
import java.util.function.BiFunction;

/**
 * Created by DrSmugleaf on 19/04/2019
 */
public class Converter<T, U, R> {

    @Nonnull
    private final TripleIdentifier<T, U, R> IDENTIFIER;

    @Nonnull
    private final BiFunction<T, U, R> CONVERTER;

    @Nonnull
    private final Validator<R> VALIDATOR;

    public Converter(@Nonnull TripleIdentifier<T, U, R> identifier, @Nonnull BiFunction<T, U, R> converter, @Nonnull Validator<R> validator) {
        IDENTIFIER = identifier;
        CONVERTER = converter;
        VALIDATOR = validator;
    }

    @Nonnull
    public TripleIdentifier<T, U, R> getIdentifier() {
        return IDENTIFIER;
    }

    @Nonnull
    public Result<R> convert(@Nonnull CommandField field, T in1, U in2) throws ConversionException {
        R out;
        try {
            out = CONVERTER.apply(in1, in2);
        } catch (Exception e) {
            throw new ConversionException("Error converting value " + in1 + " and " + in2 + " for field " + field);
        }

        String error = VALIDATOR.validate(field, out);

        return new Result<>(out, error);
    }

}
