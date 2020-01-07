package com.github.drsmugleaf.pokemon.base.registry;

import com.google.common.collect.ImmutableSet;

/**
 * Created by DrSmugleaf on 07/01/2020
 */
public interface IDeferredRegistry<T extends IEntry> extends IRegistry<T> {

    ImmutableSet<String> getRaw();

}
