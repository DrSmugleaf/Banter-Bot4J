package com.github.drsmugbrain.pokemon;

import javax.annotation.Nonnull;

/**
 * Created by DrSmugleaf on 28/09/2017.
 */
public class Setup {

    private final Generation GENERATION;
    private final Variation VARIATION;

    public Setup(@Nonnull Generation generation, @Nonnull Variation variation) {
        GENERATION = generation;
        VARIATION = variation;
    }

    public Setup(@Nonnull Setup setup) {
        this(setup.GENERATION, setup.VARIATION);
    }

    public Generation getGeneration() {
        return GENERATION;
    }

    public Variation getVariation() {
        return VARIATION;
    }

}
