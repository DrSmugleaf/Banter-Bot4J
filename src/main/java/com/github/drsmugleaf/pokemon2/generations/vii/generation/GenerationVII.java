package com.github.drsmugleaf.pokemon2.generations.vii.generation;

import com.github.drsmugleaf.pokemon2.base.game.GameRegistry;
import com.github.drsmugleaf.pokemon2.base.generation.Generation;
import com.github.drsmugleaf.pokemon2.base.species.Pokedex;
import com.github.drsmugleaf.pokemon2.base.species.stats.StatRegistry;
import com.github.drsmugleaf.pokemon2.generations.ii.generation.GenerationII;
import com.github.drsmugleaf.pokemon2.generations.iii.ability.AbilityRegistry;
import com.github.drsmugleaf.pokemon2.generations.iii.generation.IGenerationIII;
import com.github.drsmugleaf.pokemon2.generations.iii.nature.NatureRegistry;
import com.github.drsmugleaf.pokemon2.generations.iii.species.PokedexIII;
import com.github.drsmugleaf.pokemon2.generations.vii.game.GamesVII;
import com.github.drsmugleaf.pokemon2.generations.vii.species.SpeciesVII;
import org.jetbrains.annotations.Contract;

/**
 * Created by DrSmugleaf on 01/07/2019
 */
public class GenerationVII extends Generation implements IGenerationIII {

    private static final GenerationVII INSTANCE = new GenerationVII();

    private final GameRegistry GAMES = new GameRegistry(GamesVII.values());
    private final Pokedex<SpeciesVII> POKEDEX = new PokedexIII<>(this, SpeciesVII::new);
    private final NatureRegistry NATURES = new NatureRegistry(this);
    private final AbilityRegistry ABILITIES = new AbilityRegistry(this);

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
    public StatRegistry getStats() {
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

    @Override
    public NatureRegistry getNatures() {
        return NATURES;
    }

    @Override
    public AbilityRegistry getAbilities() {
        return ABILITIES;
    }

}
