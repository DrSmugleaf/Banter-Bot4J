package com.github.drsmugleaf.commands.api.converter;

import com.github.drsmugleaf.Nullable;

/**
 * Created by DrSmugleaf on 19/04/2019
 */
public class Validator<E> {

    private final Class<E> IDENTIFIER;
    @Nullable
    private final IValidator<? super E> VALIDATOR;

    public Validator(Class<E> validates, @Nullable IValidator<? super E> validator) {
        IDENTIFIER = validates;
        VALIDATOR = validator;
    }

    public Class<E> getFor() {
        return IDENTIFIER;
    }

    @Nullable
    public String getError(ValidatorContext<E> context) {
        return VALIDATOR == null ? null : VALIDATOR.apply(context);
    }

}
