package com.github.drsmugleaf.commands.api.converter;

import com.github.drsmugleaf.Nullable;
import org.jetbrains.annotations.Contract;

/**
 * Created by DrSmugleaf on 21/04/2019
 */
public class Result<E> {

    @Nullable
    private final E ELEMENT;
    @Nullable
    private final String ERROR_RESPONSE;

    public Result(@Nullable E element, @Nullable String errorResponse) {
        ELEMENT = element;
        ERROR_RESPONSE = errorResponse;
    }

    @Contract(pure = true)
    @Nullable
    public E getElement() {
        return ELEMENT;
    }

    public boolean isValid() {
        return ERROR_RESPONSE == null;
    }

    @Contract(pure = true)
    @Nullable
    public String getErrorResponse() {
        return ERROR_RESPONSE;
    }

}
