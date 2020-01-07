package com.github.drsmugleaf.pokemon;

import com.github.drsmugleaf.pokemon.base.generation.GenerationRegistry;
import com.github.drsmugleaf.pokemon.generations.i.generation.GenerationI;
import com.github.drsmugleaf.pokemon.generations.ii.generation.GenerationII;
import com.github.drsmugleaf.pokemon.generations.iii.generation.GenerationIII;
import com.github.drsmugleaf.pokemon.generations.iv.generation.GenerationIV;
import com.github.drsmugleaf.pokemon.generations.v.generation.GenerationV;
import com.github.drsmugleaf.pokemon.generations.vi.generation.GenerationVI;
import com.github.drsmugleaf.pokemon.generations.vii.generation.GenerationVII;
import org.jetbrains.annotations.Contract;

/**
 * Created by DrSmugleaf on 14/11/2019
 */
public class PokemonGame {

    private static final PokemonGame INSTANCE = new PokemonGame();

    private final GenerationRegistry GENERATIONS = new GenerationRegistry(
            GenerationI.get(),
            GenerationII.get(),
            GenerationIII.get(),
            GenerationIV.get(),
            GenerationV.get(),
            GenerationVI.get(),
            GenerationVII.get()
    );

    private PokemonGame() {}

    @Contract(pure = true)
    public static PokemonGame get() {
        return INSTANCE;
    }

    public GenerationRegistry getGenerations() {
        return GENERATIONS;
    }

}
