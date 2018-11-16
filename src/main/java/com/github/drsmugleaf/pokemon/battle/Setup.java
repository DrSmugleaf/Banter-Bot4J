package com.github.drsmugleaf.pokemon.battle;

import org.jetbrains.annotations.NotNull;

/**
 * Created by DrSmugleaf on 28/09/2017.
 */
public class Setup {

    @NotNull
    public final Generation GENERATION;

    @NotNull
    public final Variation VARIATION;

    public Setup(@NotNull Generation generation, @NotNull Variation variation) {
        GENERATION = generation;
        VARIATION = variation;
    }

    public Setup(@NotNull Setup setup) {
        this(setup.GENERATION, setup.VARIATION);
    }

    @NotNull
    public Generation getGeneration() {
        return GENERATION;
    }

    @NotNull
    public Variation getVariation() {
        return VARIATION;
    }

    @NotNull
    public static Setup getDefault() {
        return new Setup(Generation.VII, Variation.SINGLE_BATTLE);
    }

}
