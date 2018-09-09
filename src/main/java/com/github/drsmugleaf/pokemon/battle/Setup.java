package com.github.drsmugleaf.pokemon.battle;

import javax.annotation.Nonnull;

/**
 * Created by DrSmugleaf on 28/09/2017.
 */
public class Setup {

    @Nonnull
    public final Generation GENERATION;

    @Nonnull
    public final Variation VARIATION;

    public Setup(@Nonnull Generation generation, @Nonnull Variation variation) {
        GENERATION = generation;
        VARIATION = variation;
    }

    public Setup(@Nonnull Setup setup) {
        this(setup.GENERATION, setup.VARIATION);
    }

    @Nonnull
    public Generation getGeneration() {
        return GENERATION;
    }

    @Nonnull
    public Variation getVariation() {
        return VARIATION;
    }

    @Nonnull
    public static Setup getDefault() {
        return new Setup(Generation.VII, Variation.SINGLE_BATTLE);
    }

}
