package com.github.drsmugleaf.pokemon2.generations.iv;

import com.github.drsmugleaf.pokemon.battle.Game;
import com.github.drsmugleaf.pokemon2.generations.iii.GenerationIII;
import com.google.common.collect.ImmutableSet;

/**
 * Created by DrSmugleaf on 01/07/2019
 */
public class GenerationIV extends GenerationIII {

    private static final ImmutableSet<Game> CORE_GAMES = ImmutableSet.of(
            Game.DIAMOND_AND_PEARL,
            Game.PLATINUM,
            Game.HEARTGOLD_AND_SOULSILVER
    );
    private static final ImmutableSet<Game> SIDE_GAMES = ImmutableSet.of();

    @Override
    public String getAbbreviation() {
        return "DP";
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
        return 107;
    }

    @Override
    public int getTotalPokemons() {
        return getNewPokemons() + super.getTotalPokemons();
    }

    @Override
    public String getName() {
        return "Generation IV";
    }

}
