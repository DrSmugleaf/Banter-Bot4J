package com.github.drsmugleaf.pokemon2.generations.ii;

import com.github.drsmugleaf.pokemon2.base.game.GameRegistry;
import com.github.drsmugleaf.pokemon2.base.generation.Generation;
import com.github.drsmugleaf.pokemon2.base.species.Pokedex;
import com.github.drsmugleaf.pokemon2.generations.i.GenerationI;
import com.github.drsmugleaf.pokemon2.generations.i.species.PokedexI;
import com.github.drsmugleaf.pokemon2.generations.ii.game.GamesII;
import com.github.drsmugleaf.pokemon2.generations.ii.species.SpeciesII;
import org.jetbrains.annotations.Contract;

/**
 * Created by DrSmugleaf on 01/07/2019
 */
public class GenerationII extends Generation {

    private static final GenerationII INSTANCE = new GenerationII();

    private final GameRegistry GAMES = new GameRegistry(GamesII.values());
    private final Pokedex<SpeciesII> POKEDEX = new PokedexI<>(this, SpeciesII::new);

    protected GenerationII() {
        super();
    }

    @Contract(pure = true)
    public static GenerationII get() {
        return INSTANCE;
    }

    @Override
    public Pokedex<SpeciesII> getPokedex() {
        return POKEDEX;
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
    public int getTotalPokemons() {
        return getNewPokemons() + GenerationI.get().getTotalPokemons();
    }

    @Override
    public String getName() {
        return "Generation II";
    }

}
