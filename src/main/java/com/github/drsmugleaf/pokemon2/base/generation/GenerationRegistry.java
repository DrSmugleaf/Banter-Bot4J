package com.github.drsmugleaf.pokemon2.base.generation;

import com.github.drsmugleaf.pokemon2.base.registry.Registry;
import com.github.drsmugleaf.pokemon2.generations.i.GenerationI;
import com.github.drsmugleaf.pokemon2.generations.ii.GenerationII;
import com.github.drsmugleaf.pokemon2.generations.iii.GenerationIII;
import com.github.drsmugleaf.pokemon2.generations.iv.GenerationIV;
import com.github.drsmugleaf.pokemon2.generations.v.GenerationV;
import com.github.drsmugleaf.pokemon2.generations.vi.GenerationVI;
import com.github.drsmugleaf.pokemon2.generations.vii.GenerationVII;
import com.google.common.collect.ImmutableSet;

/**
 * Created by DrSmugleaf on 06/07/2019
 */
public class GenerationRegistry extends Registry<IGeneration> {

    private static final ImmutableSet<IGeneration> GENERATIONS = new ImmutableSet
            .Builder<IGeneration>()
            .add(
                    GenerationI.get(),
                    GenerationII.get(),
                    GenerationIII.get(),
                    GenerationIV.get(),
                    GenerationV.get(),
                    GenerationVI.get(),
                    GenerationVII.get()
            ).build();

    public GenerationRegistry() {
        super(GENERATIONS);
    }

}
