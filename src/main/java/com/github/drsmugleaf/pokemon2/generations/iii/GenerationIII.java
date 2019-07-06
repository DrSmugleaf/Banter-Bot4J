package com.github.drsmugleaf.pokemon2.generations.iii;

import com.github.drsmugleaf.pokemon.battle.Game;
import com.github.drsmugleaf.pokemon2.base.generation.Generation;
import com.github.drsmugleaf.pokemon2.generations.ii.GenerationII;
import com.google.common.collect.ImmutableSet;
import org.jetbrains.annotations.Contract;

/**
 * Created by DrSmugleaf on 01/07/2019
 */
public class GenerationIII extends Generation {

    private static final ImmutableSet<Game> CORE_GAMES = ImmutableSet.of(
            Game.RUBY_AND_SAPPHIRE,
            Game.FIRERED_AND_LEAFGREEN,
            Game.EMERALD
    );
    private static final ImmutableSet<Game> SIDE_GAMES = ImmutableSet.of();
    private static final GenerationIII INSTANCE = new GenerationIII();

    private GenerationIII() {}

    @Contract(pure = true)
    public static GenerationIII get() {
        return INSTANCE;
    }

    @Override
    public String getAbbreviation() {
        return "RS";
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
        return 135;
    }

    @Override
    public int getTotalPokemons() {
        return getNewPokemons() + GenerationII.get().getTotalPokemons();
    }

    @Override
    public String getName() {
        return "Generation III";
    }

}
