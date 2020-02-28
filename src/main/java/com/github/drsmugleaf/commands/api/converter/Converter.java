package com.github.drsmugleaf.commands.api.converter;

import com.github.drsmugleaf.commands.api.registry.CommandField;

import java.util.function.BiFunction;

/**
 * Created by DrSmugleaf on 19/04/2019
 */
public class Converter<T, U, R> {

    private final Identifier<T, U, R> IDENTIFIER;
    private final BiFunction<T, U, R> CONVERTER;
    private final Validator<R> VALIDATOR;

    public Converter(Identifier<T, U, R> identifier, BiFunction<T, U, R> converter, Validator<R> validator) {
        IDENTIFIER = identifier;
        CONVERTER = converter;
        VALIDATOR = validator;
    }

    public Identifier<T, U, R> getIdentifier() {
        return IDENTIFIER;
    }

    public Result<R> convert(CommandField field, T in1, U in2) throws ConversionException {
        R out;
        try {
            out = CONVERTER.apply(in1, in2);
        } catch (Exception e) {
            throw new ConversionException("Error converting value " + in1 + " and " + in2 + " for field " + field);
        }

        String error = VALIDATOR.getError(field, out);

        return new Result<>(out, error);
    }

}
