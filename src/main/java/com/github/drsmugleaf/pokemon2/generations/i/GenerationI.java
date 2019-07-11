package com.github.drsmugleaf.pokemon2.generations.i;

import com.github.drsmugleaf.pokemon.battle.Game;
import com.github.drsmugleaf.pokemon2.base.generation.Generation;
import com.github.drsmugleaf.pokemon2.base.species.Pokedex;
import com.github.drsmugleaf.pokemon2.generations.i.species.PokedexI;
import com.github.drsmugleaf.pokemon2.generations.i.species.SpeciesI;
import com.google.common.collect.ImmutableSet;
import org.jetbrains.annotations.Contract;

/**
 * Created by DrSmugleaf on 01/07/2019
 */
public class GenerationI extends Generation {

    private static final GenerationI INSTANCE = new GenerationI();

    private final ImmutableSet<Game> CORE_GAMES = ImmutableSet.of(
            Game.RED_AND_GREEN,
            Game.BLUE,
            Game.RED_AND_BLUE,
            Game.YELLOW
    );
    private final ImmutableSet<Game> SIDE_GAMES = ImmutableSet.of(Game.STADIUM);
    private final Pokedex<SpeciesI> POKEDEX = new PokedexI<>(this, SpeciesI::new);

    protected GenerationI() {
        super();
    }

    @Contract(pure = true)
    public static GenerationI get() {
        return INSTANCE;
    }

    @Override
    public Pokedex getPokedex() {
        return POKEDEX;
    }

    @Override
    public String getAbbreviation() {
        return "RB";
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
        return 151;
    }

    @Override
    public int getTotalPokemons() {
        return getNewPokemons();
    }

    @Override
    public String getName() {
        return "Generation I";
    }

}
