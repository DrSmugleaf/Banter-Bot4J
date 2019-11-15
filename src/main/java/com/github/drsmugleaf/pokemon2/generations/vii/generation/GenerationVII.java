package com.github.drsmugleaf.pokemon2.generations.vii.generation;

import com.github.drsmugleaf.pokemon2.base.game.GameRegistry;
import com.github.drsmugleaf.pokemon2.base.pokemon.species.Pokedex;
import com.github.drsmugleaf.pokemon2.base.pokemon.stat.BaseStatRegistry;
import com.github.drsmugleaf.pokemon2.generations.ii.generation.GenerationII;
import com.github.drsmugleaf.pokemon2.generations.iii.generation.BaseGenerationIII;
import com.github.drsmugleaf.pokemon2.generations.iii.pokemon.species.PokedexIII;
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
    public BaseStatRegistry getStats() {
        return GenerationII.get().getStats();
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
    public int getNewPokemons() {
        return 81;
    }

    @Override
    public String getName() {
        return "Generation VII";
    }

}
