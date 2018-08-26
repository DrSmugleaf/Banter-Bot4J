package com.github.drsmugleaf.commands.bridge;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

/**
 * Created by DrSmugleaf on 24/08/2018
 */
public class Validation {

    public final boolean IS_VALID;

    @Nullable
    public final String RESPONSE;

    Validation(boolean isValid, @Nullable String response) {
        IS_VALID = isValid;
        RESPONSE = response;
    }

    Validation(boolean isValid) {
        this(isValid, null);
    }

    @Nonnull
    public static Validation from(@Nonnull Validation... validations) {
        StringBuilder response = new StringBuilder();

        for (Validation validation : validations) {
            if (!validation.IS_VALID) {
                response.append(validation.RESPONSE).append("\n");
            }
        }

        if (response.length() > 0) {
            return new Validation(false, response.toString());
        }

        return new Validation(true);
    }

}
