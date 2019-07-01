package com.github.drsmugleaf.pokemon.battle;

import org.jetbrains.annotations.Contract;

/**
 * Created by DrSmugleaf on 28/09/2017.
 */
public class Setup {

    public final Generation GENERATION;
    public final Variation VARIATION;
    public final Game GAME;

    public Setup(Generation generation, Variation variation, Game game) {
        GENERATION = generation;
        VARIATION = variation;
        GAME = game;
    }

    public Setup(Setup setup) {
        this(setup.GENERATION, setup.VARIATION, setup.GAME);
    }

    public static Setup getDefault() {
        Generation generation = Generation.getLatest();
        Variation variation = Variation.SINGLE_BATTLE;
        Game game = Game.getLatest(generation.getCoreGames());

        return new Setup(generation, variation, game);
    }

    @Contract(pure = true)
    public Generation getGeneration() {
        return GENERATION;
    }

    @Contract(pure = true)
    public Variation getVariation() {
        return VARIATION;
    }

    @Contract(pure = true)
    public Game getGame() {
        return GAME;
    }

}
