package com.github.drsmugleaf.pokemon2.generations.iii.generation;

import com.github.drsmugleaf.pokemon2.base.game.GameRegistry;
import com.github.drsmugleaf.pokemon2.base.species.Pokedex;
import com.github.drsmugleaf.pokemon2.base.species.stat.StatRegistry;
import com.github.drsmugleaf.pokemon2.generations.ii.generation.GenerationII;
import com.github.drsmugleaf.pokemon2.generations.iii.game.GamesIII;
import com.github.drsmugleaf.pokemon2.generations.iii.species.PokedexIII;
import com.github.drsmugleaf.pokemon2.generations.iii.species.SpeciesIII;
import org.jetbrains.annotations.Contract;

/**
 * Created by DrSmugleaf on 01/07/2019
 */
public class GenerationIII extends BaseGenerationIII {

    private static final GenerationIII INSTANCE = new GenerationIII();

    private final GameRegistry GAMES = new GameRegistry(GamesIII.values());
    private final Pokedex<SpeciesIII> POKEDEX = new PokedexIII<>(this, SpeciesIII::new);

    protected GenerationIII() {
        super();
    }

    @Contract(pure = true)
    public static GenerationIII get() {
        return INSTANCE;
    }

    @Override
    public int getID() {
        return 3;
    }

    @Override
    public Pokedex<SpeciesIII> getPokedex() {
        return POKEDEX;
    }

    @Override
    public StatRegistry getStats() {
        return GenerationII.get().getStats();
    }

    @Override
    public String getAbbreviation() {
        return "RS";
    }

    @Override
    public GameRegistry getGames() {
        return GAMES;
    }

    @Override
    public int getNewPokemons() {
        return 135;
    }

    @Override
    public String getName() {
        return "Generation III";
    }

}
