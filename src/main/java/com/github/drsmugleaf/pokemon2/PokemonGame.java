package com.github.drsmugleaf.pokemon2;

import com.github.drsmugleaf.pokemon2.base.generation.GenerationRegistry;
import com.github.drsmugleaf.pokemon2.base.generation.IGeneration;
import com.github.drsmugleaf.pokemon2.generations.vii.GenerationVII;
import org.jetbrains.annotations.Contract;

/**
 * Created by DrSmugleaf on 07/11/2019
 */
public class PokemonGame {

    private static final GenerationRegistry GENERATIONS = new GenerationRegistry();

    private PokemonGame() {}

    @Contract(pure = true)
    public static GenerationRegistry getGenerations() {
        return GENERATIONS;
    }

    @Contract(pure = true)
    public static IGeneration getLatestGeneration() {
        return GenerationVII.get();
    }

}
