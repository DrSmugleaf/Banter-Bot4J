package com.github.drsmugleaf.pokemon2.generations.vi;

import com.github.drsmugleaf.pokemon.battle.Game;
import com.github.drsmugleaf.pokemon2.generations.v.GenerationV;
import com.google.common.collect.ImmutableSet;

/**
 * Created by DrSmugleaf on 01/07/2019
 */
public class GenerationVI extends GenerationV {

    private static final ImmutableSet<Game> CORE_GAMES = ImmutableSet.of(
            Game.X_AND_Y,
            Game.OMEGA_RUBY_AND_ALPHA_SAPPHIRE
    );
    private static final ImmutableSet<Game> SIDE_GAMES = ImmutableSet.of();

    @Override
    public String getAbbreviation() {
        return "XY";
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
        return 72;
    }

    @Override
    public int getTotalPokemons() {
        return getNewPokemons() + super.getTotalPokemons();
    }

    @Override
    public String getName() {
        return "Generation VI";
    }

}
