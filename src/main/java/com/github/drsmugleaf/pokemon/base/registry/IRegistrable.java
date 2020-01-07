package com.github.drsmugleaf.pokemon.base.registry;

import com.github.drsmugleaf.pokemon.base.nameable.Nameable;

import java.util.Map;

/**
 * Created by DrSmugleaf on 04/01/2020
 */
public interface IRegistrable extends Nameable {

    Map<String, String> export();

}
