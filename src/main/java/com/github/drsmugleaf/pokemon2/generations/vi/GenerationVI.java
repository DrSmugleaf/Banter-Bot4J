package com.github.drsmugleaf.pokemon2.generations.vi;

import com.github.drsmugleaf.pokemon.battle.Game;
import com.github.drsmugleaf.pokemon2.base.species.Pokedex;
import com.github.drsmugleaf.pokemon2.generations.iii.species.PokedexIII;
import com.github.drsmugleaf.pokemon2.generations.v.GenerationV;
import com.github.drsmugleaf.pokemon2.generations.vi.species.SpeciesVI;
import com.google.common.collect.ImmutableSet;
import org.jetbrains.annotations.Contract;

/**
 * Created by DrSmugleaf on 01/07/2019
 */
public class GenerationVI extends GenerationV {

    private static final GenerationVI INSTANCE = new GenerationVI();

    private final ImmutableSet<Game> CORE_GAMES = ImmutableSet.of(
            Game.X_AND_Y,
            Game.OMEGA_RUBY_AND_ALPHA_SAPPHIRE
    );
    private final ImmutableSet<Game> SIDE_GAMES = ImmutableSet.of();
    private final Pokedex<SpeciesVI> POKEDEX = new PokedexIII<>(this, SpeciesVI::new);

    protected GenerationVI() {
        super();
    }

    @Contract(pure = true)
    public static GenerationVI get() {
        return INSTANCE;
    }

    @Override
    public Pokedex getPokedex() {
        return POKEDEX;
    }

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
        return getNewPokemons() + GenerationV.get().getTotalPokemons();
    }

    @Override
    public String getName() {
        return "Generation VI";
    }

}
