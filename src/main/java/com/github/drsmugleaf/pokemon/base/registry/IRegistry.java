package com.github.drsmugleaf.pokemon.base.registry;

import com.github.drsmugleaf.pokemon.base.nameable.Nameable;
import com.google.common.collect.ImmutableMap;

/**
 * Created by DrSmugleaf on 13/11/2019
 */
public interface IRegistry<T extends Nameable> {

    ImmutableMap<String, T> get();
    T get(String name);

}
