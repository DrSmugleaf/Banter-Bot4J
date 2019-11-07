package com.github.drsmugleaf.pokemon2.generations.vi;

import com.github.drsmugleaf.pokemon2.base.game.GameRegistry;
import com.github.drsmugleaf.pokemon2.base.generation.Generation;
import com.github.drsmugleaf.pokemon2.base.species.Pokedex;
import com.github.drsmugleaf.pokemon2.generations.iii.species.PokedexIII;
import com.github.drsmugleaf.pokemon2.generations.v.GenerationV;
import com.github.drsmugleaf.pokemon2.generations.vi.game.GamesVI;
import com.github.drsmugleaf.pokemon2.generations.vi.species.SpeciesVI;
import org.jetbrains.annotations.Contract;

/**
 * Created by DrSmugleaf on 01/07/2019
 */
public class GenerationVI extends Generation {

    private static final GenerationVI INSTANCE = new GenerationVI();

    private final GameRegistry GAMES = new GameRegistry(GamesVI.values());
    private final Pokedex<SpeciesVI> POKEDEX = new PokedexIII<>(this, SpeciesVI::new);

    protected GenerationVI() {
        super();
    }

    @Contract(pure = true)
    public static GenerationVI get() {
        return INSTANCE;
    }

    @Override
    public Pokedex<SpeciesVI> getPokedex() {
        return POKEDEX;
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
    public int getNewPokemons() {
        return 72;
    }

    @Override
    public int getTotalPokemons() {
        return getNewPokemons() + GenerationV.get().getTotalPokemons();
    }

    @Override
    public String getName() {
        return "Generation VI";
    }

}
