package com.github.drsmugleaf.pokemon2.generations.vii;

import com.github.drsmugleaf.pokemon2.base.game.GameRegistry;
import com.github.drsmugleaf.pokemon2.base.generation.Generation;
import com.github.drsmugleaf.pokemon2.base.species.Pokedex;
import com.github.drsmugleaf.pokemon2.generations.iii.species.PokedexIII;
import com.github.drsmugleaf.pokemon2.generations.vi.GenerationVI;
import com.github.drsmugleaf.pokemon2.generations.vii.game.GamesVII;
import com.github.drsmugleaf.pokemon2.generations.vii.species.SpeciesVII;
import org.jetbrains.annotations.Contract;

/**
 * Created by DrSmugleaf on 01/07/2019
 */
public class GenerationVII extends Generation {

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
    public Pokedex<SpeciesVII> getPokedex() {
        return POKEDEX;
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
    public int getTotalPokemons() {
        return getNewPokemons() + GenerationVI.get().getTotalPokemons();
    }

    @Override
    public String getName() {
        return "Generation VII";
    }

}
