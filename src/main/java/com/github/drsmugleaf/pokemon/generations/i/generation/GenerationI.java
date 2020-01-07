package com.github.drsmugleaf.pokemon.generations.i.generation;

import com.github.drsmugleaf.pokemon.base.game.GameRegistry;
import com.github.drsmugleaf.pokemon.base.generation.BaseGeneration;
import com.github.drsmugleaf.pokemon.base.pokemon.species.SpeciesRegistry;
import com.github.drsmugleaf.pokemon.base.pokemon.stat.type.StatTypeRegistry;
import com.github.drsmugleaf.pokemon.generations.i.game.GamesI;
import com.github.drsmugleaf.pokemon.generations.i.pokemon.species.SpeciesRegistryI;
import com.github.drsmugleaf.pokemon.generations.i.pokemon.species.SpeciesI;
import com.github.drsmugleaf.pokemon.generations.i.pokemon.stat.StatsI;
import org.jetbrains.annotations.Contract;

/**
 * Created by DrSmugleaf on 01/07/2019
 */
public class GenerationI extends BaseGeneration {

    private static final GenerationI INSTANCE = new GenerationI();

    private final GameRegistry GAMES = new GameRegistry(GamesI.values());
    private final SpeciesRegistry<SpeciesI> POKEDEX = new SpeciesRegistryI<>(this, SpeciesI::new);
    private final StatTypeRegistry STATS = new StatTypeRegistry(StatsI.values());

    private GenerationI() {
        super();
    }

    @Contract(pure = true)
    public static GenerationI get() {
        return INSTANCE;
    }

    @Override
    public int getID() {
        return 1;
    }

    @Override
    public SpeciesRegistry<SpeciesI> getPokedex() {
        return POKEDEX;
    }

    @Override
    public StatTypeRegistry getStats() {
        return STATS;
    }

    @Override
    public String getAbbreviation() {
        return "RB";
    }

    @Override
    public GameRegistry getGames() {
        return GAMES;
    }

    @Override
    public int getNewPokemon() {
        return 151;
    }

    @Override
    public String getName() {
        return "Generation I";
    }

}
