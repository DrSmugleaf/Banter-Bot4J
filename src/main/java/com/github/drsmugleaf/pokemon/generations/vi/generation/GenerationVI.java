package com.github.drsmugleaf.pokemon.generations.vi.generation;

import com.github.drsmugleaf.pokemon.base.game.GameRegistry;
import com.github.drsmugleaf.pokemon.base.pokemon.species.SpeciesRegistry;
import com.github.drsmugleaf.pokemon.base.pokemon.stat.type.StatTypeRegistry;
import com.github.drsmugleaf.pokemon.generations.iii.generation.BaseGenerationIII;
import com.github.drsmugleaf.pokemon.generations.iii.pokemon.species.SpeciesRegistryIII;
import com.github.drsmugleaf.pokemon.generations.v.generation.GenerationV;
import com.github.drsmugleaf.pokemon.generations.vi.game.GamesVI;
import com.github.drsmugleaf.pokemon.generations.vi.pokemon.species.SpeciesVI;
import org.jetbrains.annotations.Contract;

/**
 * Created by DrSmugleaf on 01/07/2019
 */
public class GenerationVI extends BaseGenerationIII {

    private static final GenerationVI INSTANCE = new GenerationVI();

    private final GameRegistry GAMES = new GameRegistry(GamesVI.values());
    private final SpeciesRegistry<SpeciesVI> POKEDEX = new SpeciesRegistryIII<>(this, SpeciesVI::new);
    private final StatTypeRegistry STATS = GenerationV.get().getStats();

    private GenerationVI() {
        super();
    }

    @Contract(pure = true)
    public static GenerationVI get() {
        return INSTANCE;
    }

    @Override
    public int getID() {
        return 6;
    }

    @Override
    public SpeciesRegistry<SpeciesVI> getPokedex() {
        return POKEDEX;
    }

    @Override
    public StatTypeRegistry getStats() {
        return STATS;
    }

    @Override
    public String getAbbreviation() {
        return "XY";
    }

    @Override
    public GameRegistry getGames() {
        return GAMES;
    }

    @Override
    public int getNewPokemon() {
        return 72;
    }

    @Override
    public String getName() {
        return "Generation VI";
    }

}
