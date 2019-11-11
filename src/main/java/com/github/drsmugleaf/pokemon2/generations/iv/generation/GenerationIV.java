package com.github.drsmugleaf.pokemon2.generations.iv.generation;

import com.github.drsmugleaf.pokemon2.base.game.GameRegistry;
import com.github.drsmugleaf.pokemon2.base.generation.Generation;
import com.github.drsmugleaf.pokemon2.base.species.Pokedex;
import com.github.drsmugleaf.pokemon2.base.species.stats.StatRegistry;
import com.github.drsmugleaf.pokemon2.generations.ii.generation.GenerationII;
import com.github.drsmugleaf.pokemon2.generations.iii.ability.AbilityRegistry;
import com.github.drsmugleaf.pokemon2.generations.iii.generation.IGenerationIII;
import com.github.drsmugleaf.pokemon2.generations.iii.nature.NatureRegistry;
import com.github.drsmugleaf.pokemon2.generations.iii.species.PokedexIII;
import com.github.drsmugleaf.pokemon2.generations.iv.game.GamesIV;
import com.github.drsmugleaf.pokemon2.generations.iv.species.SpeciesIV;
import org.jetbrains.annotations.Contract;

/**
 * Created by DrSmugleaf on 01/07/2019
 */
public class GenerationIV extends Generation implements IGenerationIII {

    private static final GenerationIV INSTANCE = new GenerationIV();

    private final GameRegistry GAMES = new GameRegistry(GamesIV.values());
    private final Pokedex<SpeciesIV> POKEDEX = new PokedexIII<>(this, SpeciesIV::new);
    private final NatureRegistry NATURES = new NatureRegistry(this);
    private final AbilityRegistry ABILITIES = new AbilityRegistry(this);

    private GenerationIV() {
        super();
    }

    @Contract(pure = true)
    public static GenerationIV get() {
        return INSTANCE;
    }

    @Override
    public int getID() {
        return 4;
    }

    @Override
    public Pokedex<SpeciesIV> getPokedex() {
        return POKEDEX;
    }

    @Override
    public StatRegistry getStats() {
        return GenerationII.get().getStats();
    }

    @Override
    public String getAbbreviation() {
        return "DP";
    }

    @Override
    public GameRegistry getGames() {
        return GAMES;
    }

    @Override
    public int getNewPokemons() {
        return 107;
    }

    @Override
    public String getName() {
        return "Generation IV";
    }

    @Override
    public NatureRegistry getNatures() {
        return NATURES;
    }

    @Override
    public AbilityRegistry getAbilities() {
        return ABILITIES;
    }

}
