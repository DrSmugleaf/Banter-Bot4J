package com.github.drsmugleaf.pokemon2.generations.v.generation;

import com.github.drsmugleaf.pokemon2.base.game.GameRegistry;
import com.github.drsmugleaf.pokemon2.base.generation.Generation;
import com.github.drsmugleaf.pokemon2.base.species.Pokedex;
import com.github.drsmugleaf.pokemon2.base.species.stats.StatRegistry;
import com.github.drsmugleaf.pokemon2.generations.ii.generation.GenerationII;
import com.github.drsmugleaf.pokemon2.generations.iii.ability.AbilityRegistry;
import com.github.drsmugleaf.pokemon2.generations.iii.generation.IGenerationIII;
import com.github.drsmugleaf.pokemon2.generations.iii.nature.NatureRegistry;
import com.github.drsmugleaf.pokemon2.generations.iii.species.PokedexIII;
import com.github.drsmugleaf.pokemon2.generations.v.game.GamesV;
import com.github.drsmugleaf.pokemon2.generations.v.species.SpeciesV;
import org.jetbrains.annotations.Contract;

/**
 * Created by DrSmugleaf on 01/07/2019
 */
public class GenerationV extends Generation implements IGenerationIII {

    private static final GenerationV INSTANCE = new GenerationV();

    private final GameRegistry GAMES = new GameRegistry(GamesV.values());
    private final Pokedex<SpeciesV> POKEDEX = new PokedexIII<>(this, SpeciesV::new);
    private final NatureRegistry NATURES = new NatureRegistry(this);
    private final AbilityRegistry ABILITIES = new AbilityRegistry(this);

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
    public String getName() {
        return "Generation V";
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
