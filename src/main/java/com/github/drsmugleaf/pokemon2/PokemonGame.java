package com.github.drsmugleaf.pokemon2;

import com.github.drsmugleaf.pokemon2.base.generation.GenerationRegistry;
import org.jetbrains.annotations.Contract;

/**
 * Created by DrSmugleaf on 14/11/2019
 */
public class PokemonGame {

    private static final PokemonGame INSTANCE = new PokemonGame();

    private final GenerationRegistry GENERATIONS = new GenerationRegistry();

    private PokemonGame() {}

    @Contract(pure = true)
    public static PokemonGame get() {
        return INSTANCE;
    }

    public GenerationRegistry getGenerations() {
        return GENERATIONS;
    }

}
