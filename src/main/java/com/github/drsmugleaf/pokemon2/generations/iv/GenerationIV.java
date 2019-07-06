package com.github.drsmugleaf.pokemon2.generations.iv;

import com.github.drsmugleaf.pokemon.battle.Game;
import com.github.drsmugleaf.pokemon2.base.generation.Generation;
import com.github.drsmugleaf.pokemon2.generations.iii.GenerationIII;
import com.google.common.collect.ImmutableSet;
import org.jetbrains.annotations.Contract;

/**
 * Created by DrSmugleaf on 01/07/2019
 */
public class GenerationIV extends Generation {

    private static final ImmutableSet<Game> CORE_GAMES = ImmutableSet.of(
            Game.DIAMOND_AND_PEARL,
            Game.PLATINUM,
            Game.HEARTGOLD_AND_SOULSILVER
    );
    private static final ImmutableSet<Game> SIDE_GAMES = ImmutableSet.of();
    private static final GenerationIV INSTANCE = new GenerationIV();

    private GenerationIV() {}

    @Contract(pure = true)
    public static GenerationIV get() {
        return INSTANCE;
    }

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
        return getNewPokemons() + GenerationIII.get().getTotalPokemons();
    }

    @Override
    public String getName() {
        return "Generation IV";
    }

}
