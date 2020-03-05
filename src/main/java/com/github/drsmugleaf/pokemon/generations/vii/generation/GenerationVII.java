package com.github.drsmugleaf.pokemon.generations.vii.generation;

import com.github.drsmugleaf.pokemon.base.game.GameRegistry;
import com.github.drsmugleaf.pokemon.base.pokemon.species.SpeciesRegistry;
import com.github.drsmugleaf.pokemon.base.pokemon.stat.type.StatTypeRegistry;
import com.github.drsmugleaf.pokemon.generations.ii.item.ItemCategoryRegistry;
import com.github.drsmugleaf.pokemon.generations.iii.generation.BaseGenerationIII;
import com.github.drsmugleaf.pokemon.generations.iii.pokemon.species.SpeciesRegistryIII;
import com.github.drsmugleaf.pokemon.generations.vi.generation.GenerationVI;
import com.github.drsmugleaf.pokemon.generations.vii.game.GamesVII;
import com.github.drsmugleaf.pokemon.generations.vii.item.ItemCategoriesVII;
import com.github.drsmugleaf.pokemon.generations.vii.pokemon.species.SpeciesVII;
import org.jetbrains.annotations.Contract;

/**
 * Created by DrSmugleaf on 01/07/2019
 */
public class GenerationVII extends BaseGenerationIII {

    private static final GenerationVII INSTANCE = new GenerationVII();

    private final GameRegistry GAMES = new GameRegistry(GamesVII.values());
    private final SpeciesRegistry<SpeciesVII> POKEDEX = new SpeciesRegistryIII<>(this, SpeciesVII::new);
    private final StatTypeRegistry STATS = GenerationVI.get().getStats();
    private final ItemCategoryRegistry ITEM_CATEGORIES = new ItemCategoryRegistry(ItemCategoriesVII.values());

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
    public SpeciesRegistry<SpeciesVII> getPokedex() {
        return POKEDEX;
    }

    @Override
    public StatTypeRegistry getStats() {
        return STATS;
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
    public int getNewPokemon() {
        return 88;
    }

    @Override
    public String getName() {
        return "Generation VII";
    }

    @Override
    public ItemCategoryRegistry getItemCategories() {
        return ITEM_CATEGORIES;
    }

}
