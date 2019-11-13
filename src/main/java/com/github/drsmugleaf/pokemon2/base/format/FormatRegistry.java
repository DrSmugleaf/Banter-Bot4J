package com.github.drsmugleaf.pokemon2.base.format;

import com.github.drsmugleaf.pokemon2.base.generation.IGeneration;
import com.github.drsmugleaf.pokemon2.base.registry.Registry;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by DrSmugleaf on 13/11/2019
 */
public class FormatRegistry extends Registry<IFormat> {

    private final Registry<IFormat> BY_ABBREVIATION;

    public FormatRegistry(IGeneration generation) {
        super(getAll(generation));
        Map<String, IFormat> byAbbreviation = new HashMap<>();
        for (IFormat format : get().values()) {
            byAbbreviation.put(format.getAbbreviation(), format);
        }

        BY_ABBREVIATION = new Registry<>(byAbbreviation);
    }

    private static Map<String, IFormat> getAll(IGeneration generation) {
        return generation.getSmogon().getFormats();
    }

    public Registry<IFormat> getByAbbreviation() {
        return BY_ABBREVIATION;
    }

}
