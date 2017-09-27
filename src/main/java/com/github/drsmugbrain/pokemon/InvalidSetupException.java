package com.github.drsmugbrain.pokemon;

/**
 * Created by DrSmugleaf on 26/09/2017.
 */
public class InvalidSetupException extends IllegalArgumentException {

    public InvalidSetupException(Setup setup) {
        super("Invalid setup: " + setup.getName());
    }

}
