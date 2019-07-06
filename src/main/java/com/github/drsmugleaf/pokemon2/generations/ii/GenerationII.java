package com.github.drsmugleaf.pokemon2.generations.ii;

import com.github.drsmugleaf.pokemon.battle.Game;
import com.github.drsmugleaf.pokemon2.base.generation.Generation;
import com.github.drsmugleaf.pokemon2.generations.i.GenerationI;
import com.google.common.collect.ImmutableSet;
import org.jetbrains.annotations.Contract;

/**
 * Created by DrSmugleaf on 01/07/2019
 */
public class GenerationII extends Generation {

    private static final ImmutableSet<Game> CORE_GAMES = ImmutableSet.of(
            Game.GOLD_AND_SILVER,
            Game.CRYSTAL
    );
    private static final ImmutableSet<Game> SIDE_GAMES = ImmutableSet.of();
    private static final GenerationII INSTANCE = new GenerationII();

    private GenerationII() {}

    @Contract(pure = true)
    public static GenerationII get() {
        return INSTANCE;
    }

    @Override
    public String getAbbreviation() {
        return "GS";
    }

    @Override
    public ImmutableSet<Game> getCoreGames() {
        return CORE_GAMES;
    }

    @Override
    public ImmutableSet<Game> getSideGames() {
        return SIDE_GAMES;
    }

    @Override
    public int getNewPokemons() {
        return 100;
    }

    @Override
    public int getTotalPokemons() {
        return getNewPokemons() + GenerationI.get().getTotalPokemons();
    }

    @Override
    public String getName() {
        return "Generation II";
    }

}
