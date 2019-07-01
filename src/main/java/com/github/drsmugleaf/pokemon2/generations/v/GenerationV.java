package com.github.drsmugleaf.pokemon2.generations.v;

import com.github.drsmugleaf.pokemon.battle.Game;
import com.github.drsmugleaf.pokemon2.generations.iv.GenerationIV;
import com.google.common.collect.ImmutableSet;

/**
 * Created by DrSmugleaf on 01/07/2019
 */
public class GenerationV extends GenerationIV {

    private static final ImmutableSet<Game> CORE_GAMES = ImmutableSet.of(
            Game.BLACK_AND_WHITE,
            Game.BLACK_2_AND_WHITE_2
    );
    private static final ImmutableSet<Game> SIDE_GAMES = ImmutableSet.of();

    @Override
    public String getAbbreviation() {
        return "BW";
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
        return 156;
    }

    @Override
    public int getTotalPokemons() {
        return getNewPokemons() + super.getTotalPokemons();
    }

    @Override
    public String getName() {
        return "Generation V";
    }

}
