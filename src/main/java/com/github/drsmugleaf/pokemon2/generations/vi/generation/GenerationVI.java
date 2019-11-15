package com.github.drsmugleaf.pokemon2.generations.vi.generation;

import com.github.drsmugleaf.pokemon2.base.game.GameRegistry;
import com.github.drsmugleaf.pokemon2.base.pokemon.species.Pokedex;
import com.github.drsmugleaf.pokemon2.base.pokemon.stat.base.BaseStatRegistry;
import com.github.drsmugleaf.pokemon2.generations.ii.generation.GenerationII;
import com.github.drsmugleaf.pokemon2.generations.iii.generation.BaseGenerationIII;
import com.github.drsmugleaf.pokemon2.generations.iii.pokemon.species.PokedexIII;
import com.github.drsmugleaf.pokemon2.generations.vi.game.GamesVI;
import com.github.drsmugleaf.pokemon2.generations.vi.pokemon.species.SpeciesVI;
import org.jetbrains.annotations.Contract;

/**
 * Created by DrSmugleaf on 01/07/2019
 */
public class GenerationVI extends BaseGenerationIII {

    private static final GenerationVI INSTANCE = new GenerationVI();

    private final GameRegistry GAMES = new GameRegistry(GamesVI.values());
    private final Pokedex<SpeciesVI> POKEDEX = new PokedexIII<>(this, SpeciesVI::new);

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
    public Pokedex<SpeciesVI> getPokedex() {
        return POKEDEX;
    }

    @Override
    public BaseStatRegistry getStats() {
        return GenerationII.get().getStats();
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
    public String getName() {
        return "Generation VI";
    }

}
