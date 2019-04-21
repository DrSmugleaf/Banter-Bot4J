package com.github.drsmugleaf.commands.api.converter;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

/**
 * Created by DrSmugleaf on 21/04/2019
 */
public class Result<E> {

    @Nullable
    private final E ELEMENT;

    @Nonnull
    private final String ERROR_RESPONSE;

    public Result(@Nullable E element, @Nonnull String errorResponse) {
        ELEMENT = element;
        ERROR_RESPONSE = errorResponse;
    }

    @Nullable
    public E getElement() {
        return ELEMENT;
    }

    public boolean isValid() {
        return ERROR_RESPONSE.isEmpty();
    }

    @Nonnull
    public String getErrorResponse() {
        return ERROR_RESPONSE;
    }

}
