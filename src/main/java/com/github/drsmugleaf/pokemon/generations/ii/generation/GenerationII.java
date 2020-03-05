package com.github.drsmugleaf.pokemon.generations.ii.generation;

import com.github.drsmugleaf.pokemon.base.game.GameRegistry;
import com.github.drsmugleaf.pokemon.base.pokemon.species.SpeciesRegistry;
import com.github.drsmugleaf.pokemon.base.pokemon.stat.type.StatTypeRegistry;
import com.github.drsmugleaf.pokemon.generations.i.pokemon.species.SpeciesRegistryI;
import com.github.drsmugleaf.pokemon.generations.ii.game.GamesII;
import com.github.drsmugleaf.pokemon.generations.ii.item.ItemCategoriesII;
import com.github.drsmugleaf.pokemon.generations.ii.item.ItemCategoryRegistry;
import com.github.drsmugleaf.pokemon.generations.ii.pokemon.species.SpeciesII;
import com.github.drsmugleaf.pokemon.generations.ii.pokemon.stat.StatsII;
import org.jetbrains.annotations.Contract;

/**
 * Created by DrSmugleaf on 01/07/2019
 */
public class GenerationII extends BaseGenerationII {

    private static final GenerationII INSTANCE = new GenerationII();

    private final GameRegistry GAMES = new GameRegistry(GamesII.values());
    private final SpeciesRegistry<SpeciesII> POKEDEX = new SpeciesRegistryI<>(this, SpeciesII::new);
    private final StatTypeRegistry STATS = new StatTypeRegistry(StatsII.values());
    private final ItemCategoryRegistry ITEM_CATEGORIES = new ItemCategoryRegistry(ItemCategoriesII.values());

    private GenerationII() {
        super();
    }

    @Contract(pure = true)
    public static GenerationII get() {
        return INSTANCE;
    }

    @Override
    public int getID() {
        return 2;
    }

    @Override
    public SpeciesRegistry<SpeciesII> getPokedex() {
        return POKEDEX;
    }

    @Override
    public StatTypeRegistry getStats() {
        return STATS;
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
    public int getNewPokemon() {
        return 100;
    }

    @Override
    public String getName() {
        return "Generation II";
    }

    @Override
    public ItemCategoryRegistry getItemCategories() {
        return ITEM_CATEGORIES;
    }

}
