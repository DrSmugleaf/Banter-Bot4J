package com.github.drsmugleaf.pokemon.moves;

/**
 * Created by DrSmugleaf on 08/09/2018
 */
public class InvalidCriticalStageException extends IllegalArgumentException {

    public InvalidCriticalStageException(CriticalHitStage stage, String message) {
        super("Invalid critical hit stage: " + stage + ". " + message);
    }

    public InvalidCriticalStageException(CriticalHitStage stage) {
        this(stage, "");
    }

}
