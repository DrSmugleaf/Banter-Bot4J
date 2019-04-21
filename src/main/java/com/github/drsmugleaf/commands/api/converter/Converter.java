package com.github.drsmugleaf.commands.api.converter;

import com.github.drsmugleaf.commands.api.registry.CommandField;

import javax.annotation.Nonnull;
import java.util.function.Function;

/**
 * Created by DrSmugleaf on 19/04/2019
 */
public class Converter<T, R> {

    @Nonnull
    private final PairIdentifier<T, R> IDENTIFIER;

    @Nonnull
    private final Function<T, R> CONVERTER;

    @Nonnull
    private final Validator<R> VALIDATOR;

    public Converter(@Nonnull PairIdentifier<T, R> identifier, @Nonnull Function<T, R> converter, @Nonnull Validator<R> validator) {
        IDENTIFIER = identifier;
        CONVERTER = converter;
        VALIDATOR =  validator;
    }

    @Nonnull
    public PairIdentifier<T, R> getIdentifier() {
        return IDENTIFIER;
    }

    @Nonnull
    public Result<R> convert(@Nonnull CommandField field, T in) throws ConversionException {
        R out;
        try {
            out = CONVERTER.apply(in);
        } catch (Exception e) {
            throw new ConversionException("Error converting value " + in + " for field " + field);
        }

        String error = VALIDATOR.validate(field, out);

        return new Result<>(out, error);
    }

}
