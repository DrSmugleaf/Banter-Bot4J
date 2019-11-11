package com.github.drsmugleaf.pokemon2.base.generation;

import com.github.drsmugleaf.Nullable;
import com.github.drsmugleaf.pokemon2.base.registry.Registry;
import com.github.drsmugleaf.pokemon2.generations.i.GenerationI;
import com.github.drsmugleaf.pokemon2.generations.ii.GenerationII;
import com.github.drsmugleaf.pokemon2.generations.iii.GenerationIII;
import com.github.drsmugleaf.pokemon2.generations.iv.GenerationIV;
import com.github.drsmugleaf.pokemon2.generations.v.GenerationV;
import com.github.drsmugleaf.pokemon2.generations.vi.GenerationVI;
import com.github.drsmugleaf.pokemon2.generations.vii.GenerationVII;

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
    }

    public SortedMap<Integer, IGeneration> getByID() {
        return GENERATIONS_BY_ID;
    }

    @Nullable
    public IGeneration get(Integer id) {
        return GENERATIONS_BY_ID.get(id);
    }

}
