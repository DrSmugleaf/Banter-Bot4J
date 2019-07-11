package com.github.drsmugleaf.pokemon2.generations.ii;

import com.github.drsmugleaf.pokemon.battle.Game;
import com.github.drsmugleaf.pokemon2.base.species.Pokedex;
import com.github.drsmugleaf.pokemon2.generations.i.species.PokedexI;
import com.github.drsmugleaf.pokemon2.generations.i.GenerationI;
import com.github.drsmugleaf.pokemon2.generations.ii.species.SpeciesII;
import com.google.common.collect.ImmutableSet;
import org.jetbrains.annotations.Contract;

/**
 * Created by DrSmugleaf on 01/07/2019
 */
public class GenerationII extends GenerationI {

    private static final GenerationII INSTANCE = new GenerationII();

    private final ImmutableSet<Game> CORE_GAMES = ImmutableSet.of(
            Game.GOLD_AND_SILVER,
            Game.CRYSTAL
    );
    private final ImmutableSet<Game> SIDE_GAMES = ImmutableSet.of();
    private final Pokedex<SpeciesII> POKEDEX = new PokedexI<>(this, SpeciesII::new);

    protected GenerationII() {
        super();
    }

    @Contract(pure = true)
    public static GenerationII get() {
        return INSTANCE;
    }

    @Override
    public Pokedex getPokedex() {
        return POKEDEX;
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
