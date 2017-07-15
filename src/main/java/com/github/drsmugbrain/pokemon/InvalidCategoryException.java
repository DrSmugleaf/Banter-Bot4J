package com.github.drsmugbrain.pokemon;

/**
 * Created by DrSmugleaf on 14/07/2017.
 */
public class InvalidCategoryException extends IllegalArgumentException {

    public InvalidCategoryException() {
        super();
    }

    public InvalidCategoryException(String message) {
        super(message);
    }

    public InvalidCategoryException(String message, Throwable cause) {
        super(message, cause);
    }

    public InvalidCategoryException(Throwable cause) {
        super(cause);
    }

    public InvalidCategoryException(Category category) {
        super("Invalid category: " + category.getName());
    }

}
