package com.github.drsmugleaf.pokemon.battle;

import org.jetbrains.annotations.Contract;

/**
 * Created by DrSmugleaf on 28/09/2017.
 */
public class Setup {

    public final Generation GENERATION;
    public final Variation VARIATION;

    public Setup(Generation generation, Variation variation) {
        GENERATION = generation;
        VARIATION = variation;
    }

    public Setup(Setup setup) {
        this(setup.GENERATION, setup.VARIATION);
    }

    @Contract(pure = true)
    public Generation getGeneration() {
        return GENERATION;
    }

    @Contract(pure = true)
    public Variation getVariation() {
        return VARIATION;
    }

    @Contract(" -> new")
    public static Setup getDefault() {
        return new Setup(Generation.VII, Variation.SINGLE_BATTLE);
    }

}
