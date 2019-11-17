package com.github.drsmugleaf.pokemon2.generations.vii.generation;

import com.github.drsmugleaf.pokemon2.base.game.GameRegistry;
import com.github.drsmugleaf.pokemon2.base.pokemon.species.Pokedex;
import com.github.drsmugleaf.pokemon2.base.pokemon.stat.type.StatTypeRegistry;
import com.github.drsmugleaf.pokemon2.generations.iii.generation.BaseGenerationIII;
import com.github.drsmugleaf.pokemon2.generations.iii.pokemon.species.PokedexIII;
import com.github.drsmugleaf.pokemon2.generations.vi.generation.GenerationVI;
import com.github.drsmugleaf.pokemon2.generations.vii.game.GamesVII;
import com.github.drsmugleaf.pokemon2.generations.vii.pokemon.species.SpeciesVII;
import org.jetbrains.annotations.Contract;

/**
 * Created by DrSmugleaf on 01/07/2019
 */
public class GenerationVII extends BaseGenerationIII {

    private static final GenerationVII INSTANCE = new GenerationVII();

    private final GameRegistry GAMES = new GameRegistry(GamesVII.values());
    private final Pokedex<SpeciesVII> POKEDEX = new PokedexIII<>(this, SpeciesVII::new);
    private final StatTypeRegistry STATS = GenerationVI.get().getStats();

    private GenerationVII() {
        super();
    }

    @Contract(pure = true)
    public static GenerationVII get() {
        return INSTANCE;
    }

    @Override
    public int getID() {
        return 7;
    }

    @Override
    public Pokedex<SpeciesVII> getPokedex() {
        return POKEDEX;
    }

    @Override
    public StatTypeRegistry getStats() {
        return STATS;
    }

    @Override
    public String getAbbreviation() {
        return "SM";
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
        return "Generation VII";
    }

}
