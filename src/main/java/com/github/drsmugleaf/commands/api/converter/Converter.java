package com.github.drsmugleaf.commands.api.converter;

import com.github.drsmugleaf.commands.api.CommandReceivedEvent;
import com.github.drsmugleaf.commands.api.registry.CommandField;

/**
 * Created by DrSmugleaf on 19/04/2019
 */
public class Converter<T, R> {

    private final Identifier<T, R> IDENTIFIER;
    private final Transformer<R> TRANSFORMER;
    private final Validator<R> VALIDATOR;

    public Converter(Identifier<T, R> identifier, Transformer<R> transformer, Validator<R> validator) {
        IDENTIFIER = identifier;
        TRANSFORMER = transformer;
        VALIDATOR = validator;
    }

    public Identifier<T, R> getIdentifier() {
        return IDENTIFIER;
    }

    public Result<R> convert(CommandField field, String value, CommandReceivedEvent event) throws ConversionException {
        R out;
        try {
            out = TRANSFORMER.getTransformer().apply(value, event);
        } catch (Exception e) {
            throw new ConversionException("Error converting value " + value + " for field " + field, e);
        }

        ValidatorContext<R> context = new ValidatorContext<>(field, out, event);
        String error = VALIDATOR.getError(context);

        return new Result<>(out, error);
    }

}
