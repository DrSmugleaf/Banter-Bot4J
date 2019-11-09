package com.github.drsmugleaf.pokemon2.generations.v;

import com.github.drsmugleaf.pokemon2.base.game.GameRegistry;
import com.github.drsmugleaf.pokemon2.base.generation.Generation;
import com.github.drsmugleaf.pokemon2.base.species.Pokedex;
import com.github.drsmugleaf.pokemon2.base.species.stats.StatRegistry;
import com.github.drsmugleaf.pokemon2.generations.ii.GenerationII;
import com.github.drsmugleaf.pokemon2.generations.iii.species.PokedexIII;
import com.github.drsmugleaf.pokemon2.generations.iv.GenerationIV;
import com.github.drsmugleaf.pokemon2.generations.v.game.GamesV;
import com.github.drsmugleaf.pokemon2.generations.v.species.SpeciesV;
import org.jetbrains.annotations.Contract;

/**
 * Created by DrSmugleaf on 01/07/2019
 */
public class GenerationV extends Generation {

    private static final GenerationV INSTANCE = new GenerationV();

    private final GameRegistry GAMES = new GameRegistry(GamesV.values());
    private final Pokedex<SpeciesV> POKEDEX = new PokedexIII<>(this, SpeciesV::new);

    protected GenerationV() {
        super();
    }

    @Contract(pure = true)
    public static GenerationV get() {
        return INSTANCE;
    }

    @Override
    public Pokedex<SpeciesV> getPokedex() {
        return POKEDEX;
    }

    @Override
    public StatRegistry getStats() {
        return GenerationII.get().getStats();
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
    public int getNewPokemons() {
        return 156;
    }

    @Override
    public int getTotalPokemons() {
        return getNewPokemons() + GenerationIV.get().getTotalPokemons();
    }

    @Override
    public String getName() {
        return "Generation V";
    }

}
