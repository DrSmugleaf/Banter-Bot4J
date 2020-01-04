package com.github.drsmugleaf.pokemon.generations.v.generation;

import com.github.drsmugleaf.pokemon.base.game.GameRegistry;
import com.github.drsmugleaf.pokemon.base.pokemon.species.Pokedex;
import com.github.drsmugleaf.pokemon.base.pokemon.stat.type.StatTypeRegistry;
import com.github.drsmugleaf.pokemon.generations.iii.generation.BaseGenerationIII;
import com.github.drsmugleaf.pokemon.generations.iii.pokemon.species.PokedexIII;
import com.github.drsmugleaf.pokemon.generations.iv.generation.GenerationIV;
import com.github.drsmugleaf.pokemon.generations.v.game.GamesV;
import com.github.drsmugleaf.pokemon.generations.v.pokemon.species.SpeciesV;
import org.jetbrains.annotations.Contract;

/**
 * Created by DrSmugleaf on 01/07/2019
 */
public class GenerationV extends BaseGenerationIII {

    private static final GenerationV INSTANCE = new GenerationV();

    private final GameRegistry GAMES = new GameRegistry(GamesV.values());
    private final Pokedex<SpeciesV> POKEDEX = new PokedexIII<>(this, SpeciesV::new);
    private final StatTypeRegistry STATS = GenerationIV.get().getStats();

    private GenerationV() {
        super();
    }

    @Contract(pure = true)
    public static GenerationV get() {
        return INSTANCE;
    }

    @Override
    public int getID() {
        return 5;
    }

    @Override
    public Pokedex<SpeciesV> getPokedex() {
        return POKEDEX;
    }

    @Override
    public StatTypeRegistry getStats() {
        return STATS;
    }

    @Override
    public String getAbbreviation() {
        return "BW";
    }

    @Override
    public GameRegistry getGames() {
        return GAMES;
    }

    @Override
    public int getNewPokemon() {
        return 156;
    }

    @Override
    public String getName() {
        return "Generation V";
    }

}
