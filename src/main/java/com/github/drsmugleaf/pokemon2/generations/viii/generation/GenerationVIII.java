package com.github.drsmugleaf.pokemon2.generations.viii.generation;

import com.github.drsmugleaf.pokemon2.base.game.GameRegistry;
import com.github.drsmugleaf.pokemon2.base.pokemon.species.Pokedex;
import com.github.drsmugleaf.pokemon2.base.pokemon.stat.base.BaseStatRegistry;
import com.github.drsmugleaf.pokemon2.generations.ii.generation.GenerationII;
import com.github.drsmugleaf.pokemon2.generations.iii.pokemon.species.PokedexIII;
import com.github.drsmugleaf.pokemon2.generations.viii.game.GamesVIII;
import com.github.drsmugleaf.pokemon2.generations.viii.pokemon.species.SpeciesVIII;
import org.jetbrains.annotations.Contract;

/**
 * Created by DrSmugleaf on 13/11/2019
 */
public class GenerationVIII extends BaseGenerationVIII {

    private static final GenerationVIII INSTANCE = null; // TODO: 13-Nov-19 Instantiate Generation VIII after Smogon adds it

    private final GameRegistry GAMES = new GameRegistry(GamesVIII.values());
    private final Pokedex<SpeciesVIII> POKEDEX = new PokedexIII<>(this, SpeciesVIII::new);

    private GenerationVIII() {
        super();
    }

    @Contract(pure = true)
    public static GenerationVIII get() {
        return INSTANCE;
    }

    @Override
    public int getRemovedPokemon() {
        return 458;
    }

    @Override
    public int getID() {
        return 8;
    }

    @Override
    public Pokedex<SpeciesVIII> getPokedex() {
        return POKEDEX;
    }

    @Override
    public BaseStatRegistry getStats() {
        return GenerationII.get().getStats();
    }

    @Override
    public String getAbbreviation() {
        return "SW";
    }

    @Override
    public GameRegistry getGames() {
        return GAMES;
    }

    @Override
    public int getNewPokemon() {
        return 81;
    }

    @Override
    public String getName() {
        return "Generation VIII";
    }

}
