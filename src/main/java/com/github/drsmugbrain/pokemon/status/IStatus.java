package com.github.drsmugbrain.pokemon.status;

import javax.annotation.Nonnull;

/**
 * Created by DrSmugleaf on 11/10/2017.
 */
public interface IStatus {

    void apply(@Nonnull BaseVolatileStatus status);

}
