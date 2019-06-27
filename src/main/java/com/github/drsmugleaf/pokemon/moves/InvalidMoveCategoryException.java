package com.github.drsmugleaf.pokemon.moves;

/**
 * Created by DrSmugleaf on 14/07/2017.
 */
public class InvalidMoveCategoryException extends IllegalArgumentException {

    public InvalidMoveCategoryException(MoveCategory category, String message) {
        super("Invalid category: " + category.NAME + ". " + message);
    }

    public InvalidMoveCategoryException(MoveCategory category) {
        this(category, "");
    }

}
