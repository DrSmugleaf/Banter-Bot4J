package com.github.drsmugleaf.pokemon2.generations.ii;

import com.github.drsmugleaf.pokemon.battle.Game;
import com.github.drsmugleaf.pokemon2.generations.i.GenerationI;
import com.google.common.collect.ImmutableSet;

/**
 * Created by DrSmugleaf on 01/07/2019
 */
public class GenerationII extends GenerationI {

    private static final ImmutableSet<Game> CORE_GAMES = ImmutableSet.of(
            Game.GOLD_AND_SILVER,
            Game.CRYSTAL
    );
    private static final ImmutableSet<Game> SIDE_GAMES = ImmutableSet.of();

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
        return getNewPokemons() + super.getTotalPokemons();
    }

    @Override
    public String getName() {
        return "Generation II";
    }

}
