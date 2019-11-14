package com.github.drsmugleaf.pokemon2.base.pokemon.status;

import com.github.drsmugleaf.Nullable;
import com.github.drsmugleaf.pokemon2.base.nameable.Nameable;

/**
 * Created by DrSmugleaf on 13/11/2019
 */
public interface IStatus extends Nameable {

    boolean isVolatile();
    @Nullable
    Integer getDuration();

}
