package com.github.drsmugleaf.pokemon2.generations.v;

import com.github.drsmugleaf.pokemon.battle.Game;
import com.github.drsmugleaf.pokemon2.base.generation.Generation;
import com.github.drsmugleaf.pokemon2.base.species.SpeciesBuilder;
import com.github.drsmugleaf.pokemon2.generations.iv.GenerationIV;
import com.github.drsmugleaf.pokemon2.generations.v.species.SpeciesV;
import com.google.common.collect.ImmutableSet;
import org.jetbrains.annotations.Contract;

import java.util.function.Function;

/**
 * Created by DrSmugleaf on 01/07/2019
 */
public class GenerationV extends Generation<SpeciesV> {

    private static final GenerationV INSTANCE = new GenerationV(SpeciesV::new);

    private final ImmutableSet<Game> CORE_GAMES = ImmutableSet.of(
            Game.BLACK_AND_WHITE,
            Game.BLACK_2_AND_WHITE_2
    );
    private final ImmutableSet<Game> SIDE_GAMES = ImmutableSet.of();

    protected GenerationV(Function<SpeciesBuilder<SpeciesV>, SpeciesV> constructor) {
        super(constructor);
    }

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
