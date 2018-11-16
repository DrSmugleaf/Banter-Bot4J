package com.github.drsmugleaf.pokemon.moves;

import org.jetbrains.annotations.NotNull;

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

    public InvalidCriticalStageException(@NotNull CriticalHitStage stage, @NotNull String message) {
        super("Invalid critical hit stage: " + stage + ". " + message);
    }

    public InvalidCriticalStageException(@NotNull CriticalHitStage stage) {
        this(stage, "");
    }

}
