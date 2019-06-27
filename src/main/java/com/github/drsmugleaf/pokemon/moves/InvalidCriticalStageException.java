package com.github.drsmugleaf.pokemon.moves;

/**
 * Created by DrSmugleaf on 08/09/2018
 */
public class InvalidCriticalStageException extends IllegalArgumentException {

    public InvalidCriticalStageException() {
        super();
    }

    public InvalidCriticalStageException(String s) {
        super(s);
    }

    public InvalidCriticalStageException(String message, Throwable cause) {
        super(message, cause);
    }

    public InvalidCriticalStageException(Throwable cause) {
        super(cause);
    }

    public InvalidCriticalStageException(CriticalHitStage stage, String message) {
        super("Invalid critical hit stage: " + stage + ". " + message);
    }

    public InvalidCriticalStageException(CriticalHitStage stage) {
        this(stage, "");
    }

}
