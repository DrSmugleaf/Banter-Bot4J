package com.github.drsmugleaf.pokemon.base.generation;

import com.github.drsmugleaf.Nullable;
import com.github.drsmugleaf.pokemon.base.registry.Registry;
import com.github.drsmugleaf.pokemon.generations.i.generation.GenerationI;
import com.github.drsmugleaf.pokemon.generations.ii.generation.GenerationII;
import com.github.drsmugleaf.pokemon.generations.iii.generation.GenerationIII;
import com.github.drsmugleaf.pokemon.generations.iv.generation.GenerationIV;
import com.github.drsmugleaf.pokemon.generations.v.generation.GenerationV;
import com.github.drsmugleaf.pokemon.generations.vi.generation.GenerationVI;
import com.github.drsmugleaf.pokemon.generations.vii.generation.GenerationVII;

import java.util.Collection;
import java.util.SortedMap;
import java.util.TreeMap;

/**
 * Created by DrSmugleaf on 06/07/2019
 */
public class GenerationRegistry extends Registry<IGeneration> {

    private final SortedMap<Integer, IGeneration> GENERATIONS_BY_ID = new TreeMap<>();

    public GenerationRegistry() {
        this(
                GenerationI.get(),
                GenerationII.get(),
                GenerationIII.get(),
                GenerationIV.get(),
                GenerationV.get(),
                GenerationVI.get(),
                GenerationVII.get()
        );
    }

    public GenerationRegistry(IGeneration... generations) {
        super(generations);
        for (IGeneration generation : generations) {
            GENERATIONS_BY_ID.put(generation.getID(), generation);
        }
    }

    public GenerationRegistry(Collection<IGeneration> generations) {
        super(generations.toArray(new IGeneration[0]));
    }

    public SortedMap<Integer, IGeneration> getByID() {
        return GENERATIONS_BY_ID;
    }

    @Nullable
    public IGeneration get(Integer id) {
        return GENERATIONS_BY_ID.get(id);
    }

}
