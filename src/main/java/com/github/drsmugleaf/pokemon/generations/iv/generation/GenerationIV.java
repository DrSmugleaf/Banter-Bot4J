package com.github.drsmugleaf.pokemon.generations.iv.generation;

import com.github.drsmugleaf.pokemon.base.game.GameRegistry;
import com.github.drsmugleaf.pokemon.base.pokemon.species.Pokedex;
import com.github.drsmugleaf.pokemon.base.pokemon.stat.type.StatTypeRegistry;
import com.github.drsmugleaf.pokemon.generations.iii.generation.BaseGenerationIII;
import com.github.drsmugleaf.pokemon.generations.iii.generation.GenerationIII;
import com.github.drsmugleaf.pokemon.generations.iii.pokemon.species.PokedexIII;
import com.github.drsmugleaf.pokemon.generations.iv.game.GamesIV;
import com.github.drsmugleaf.pokemon.generations.iv.pokemon.species.SpeciesIV;
import org.jetbrains.annotations.Contract;

/**
 * Created by DrSmugleaf on 01/07/2019
 */
public class GenerationIV extends BaseGenerationIII {

    private static final GenerationIV INSTANCE = new GenerationIV();

    private final GameRegistry GAMES = new GameRegistry(GamesIV.values());
    private final Pokedex<SpeciesIV> POKEDEX = new PokedexIII<>(this, SpeciesIV::new);
    private final StatTypeRegistry STATS = GenerationIII.get().getStats();

    private GenerationIV() {
        super();
    }

    @Contract(pure = true)
    public static GenerationIV get() {
        return INSTANCE;
    }

    @Override
    public int getID() {
        return 4;
    }

    @Override
    public Pokedex<SpeciesIV> getPokedex() {
        return POKEDEX;
    }

    @Override
    public StatTypeRegistry getStats() {
        return STATS;
    }

    @Override
    public String getAbbreviation() {
        return "DP";
    }

    @Override
    public GameRegistry getGames() {
        return GAMES;
    }

    @Override
    public int getNewPokemon() {
        return 107;
    }

    @Override
    public String getName() {
        return "Generation IV";
    }

}
