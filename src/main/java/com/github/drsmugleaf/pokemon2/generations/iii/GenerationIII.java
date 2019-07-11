package com.github.drsmugleaf.pokemon2.generations.iii;

import com.github.drsmugleaf.pokemon.battle.Game;
import com.github.drsmugleaf.pokemon2.base.generation.Generation;
import com.github.drsmugleaf.pokemon2.base.species.Pokedex;
import com.github.drsmugleaf.pokemon2.generations.ii.GenerationII;
import com.github.drsmugleaf.pokemon2.generations.iii.ability.AbilityRegistry;
import com.github.drsmugleaf.pokemon2.generations.iii.nature.NatureRegistry;
import com.github.drsmugleaf.pokemon2.generations.iii.species.PokedexIII;
import com.github.drsmugleaf.pokemon2.generations.iii.species.SpeciesIII;
import com.google.common.collect.ImmutableSet;
import org.jetbrains.annotations.Contract;

/**
 * Created by DrSmugleaf on 01/07/2019
 */
public class GenerationIII extends Generation {

    private static final GenerationIII INSTANCE = new GenerationIII();

    private final ImmutableSet<Game> CORE_GAMES = ImmutableSet.of(
            Game.RUBY_AND_SAPPHIRE,
            Game.FIRERED_AND_LEAFGREEN,
            Game.EMERALD
    );
    private final ImmutableSet<Game> SIDE_GAMES = ImmutableSet.of();
    private final Pokedex<SpeciesIII> POKEDEX = new PokedexIII<>(this, SpeciesIII::new);
    private final NatureRegistry NATURES = new NatureRegistry(this);
    private final AbilityRegistry ABILITIES = new AbilityRegistry(this);

    protected GenerationIII() {
        super();
    }

    @Contract(pure = true)
    public static GenerationIII get() {
        return INSTANCE;
    }

    @Override
    public Pokedex<SpeciesIII> getPokedex() {
        return POKEDEX;
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
