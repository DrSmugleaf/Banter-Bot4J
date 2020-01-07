package com.github.drsmugleaf.pokemon.base.generation;

import com.github.drsmugleaf.Nullable;
import com.github.drsmugleaf.pokemon.PokemonGame;
import com.github.drsmugleaf.pokemon.base.nameable.Nameable;
import com.github.drsmugleaf.pokemon.base.registry.DeferredRegistry;

import java.util.Arrays;
import java.util.Collection;
import java.util.stream.Collectors;

/**
 * Created by DrSmugleaf on 06/07/2019
 */
public class GenerationRegistry extends DeferredRegistry<IGeneration> {

    public GenerationRegistry(IGeneration... generations) {
        this(Arrays.stream(generations).map(Nameable::getName).collect(Collectors.toSet()));
    }

    public GenerationRegistry(String... generations) {
        this(Arrays.asList(generations));
    }

    public GenerationRegistry(Collection<String> generations) {
        super(generations, (name) -> PokemonGame.get().getGenerations().get(name));
    }

    @Nullable
    public IGeneration get(Integer id) {
        return get()
                .values()
                .stream()
                .filter(gen -> gen.getID() == id)
                .findFirst()
                .orElse(null);
    }

}
