package com.github.drsmugleaf.pokemon2.generations.ii.generation;

import com.github.drsmugleaf.pokemon2.base.game.GameRegistry;
import com.github.drsmugleaf.pokemon2.base.generation.BaseGeneration;
import com.github.drsmugleaf.pokemon2.base.species.Pokedex;
import com.github.drsmugleaf.pokemon2.base.species.stats.StatRegistry;
import com.github.drsmugleaf.pokemon2.generations.i.species.PokedexI;
import com.github.drsmugleaf.pokemon2.generations.ii.game.GamesII;
import com.github.drsmugleaf.pokemon2.generations.ii.species.SpeciesII;
import com.github.drsmugleaf.pokemon2.generations.ii.stats.StatsII;
import org.jetbrains.annotations.Contract;

/**
 * Created by DrSmugleaf on 01/07/2019
 */
public class GenerationII extends BaseGeneration {

    private static final GenerationII INSTANCE = new GenerationII();

    private final GameRegistry GAMES = new GameRegistry(GamesII.values());
    private final Pokedex<SpeciesII> POKEDEX = new PokedexI<>(this, SpeciesII::new);
    private final StatRegistry STATS = new StatRegistry(StatsII.values());

    private GenerationII() {
        super();
    }

    @Contract(pure = true)
    public static GenerationII get() {
        return INSTANCE;
    }

    @Override
    public int getID() {
        return 2;
    }

    @Override
    public Pokedex<SpeciesII> getPokedex() {
        return POKEDEX;
    }

    @Override
    public StatRegistry getStats() {
        return STATS;
    }

    @Override
    public String getAbbreviation() {
        return "GS";
    }

    @Override
    public GameRegistry getGames() {
        return GAMES;
    }

    @Override
    public int getNewPokemons() {
        return 100;
    }

    @Override
    public String getName() {
        return "Generation II";
    }

}
