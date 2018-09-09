package com.github.drsmugleaf.pokemon.moves;

import javax.annotation.Nonnull;

/**
 * Created by DrSmugleaf on 14/07/2017.
 */
public class InvalidMoveCategoryException extends IllegalArgumentException {

    public InvalidMoveCategoryException() {
        super();
    }

    public InvalidMoveCategoryException(String message) {
        super(message);
    }

    public InvalidMoveCategoryException(String message, Throwable cause) {
        super(message, cause);
    }

    public InvalidMoveCategoryException(Throwable cause) {
        super(cause);
    }

    public InvalidMoveCategoryException(@Nonnull MoveCategory category, @Nonnull String message) {
        super("Invalid category: " + category.NAME + ". " + message);
    }

    public InvalidMoveCategoryException(@Nonnull MoveCategory category) {
        this(category, "");
    }

}
