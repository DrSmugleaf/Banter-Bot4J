package com.github.drsmugleaf.pokemon2.base.pokemon.modifier;

/**
 * Created by DrSmugleaf on 19/11/2019
 */
public interface IModifier extends IAllowedModifier {

    default boolean allowed() {
        run();
        return true;
    }
    void run();

}
