package com.github.drsmugleaf.pokemon2.generations.v;

import com.github.drsmugleaf.pokemon.battle.Game;
import com.github.drsmugleaf.pokemon2.base.generation.Generation;
import com.github.drsmugleaf.pokemon2.generations.iv.GenerationIV;
import com.google.common.collect.ImmutableSet;
import org.jetbrains.annotations.Contract;

/**
 * Created by DrSmugleaf on 01/07/2019
 */
public class GenerationV extends Generation {

    private static final ImmutableSet<Game> CORE_GAMES = ImmutableSet.of(
            Game.BLACK_AND_WHITE,
            Game.BLACK_2_AND_WHITE_2
    );
    private static final ImmutableSet<Game> SIDE_GAMES = ImmutableSet.of();
    private static final GenerationV INSTANCE = new GenerationV();

    private GenerationV() {}

    @Contract(pure = true)
    public static GenerationV get() {
        return INSTANCE;
    }

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
        return getNewPokemons() + GenerationIV.get().getTotalPokemons();
    }

    @Override
    public String getName() {
        return "Generation V";
    }

}
