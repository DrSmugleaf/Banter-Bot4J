package com.github.drsmugleaf.pokemon2.base.pokemon.modifier;

import com.github.drsmugleaf.Nullable;

/**
 * Created by DrSmugleaf on 19/11/2019
 */
public interface IExecutableModifier extends IModifier<Void> {

    @Nullable
    @Override
    default Void run() {
        execute();
        return null;
    }
    void execute();

}
