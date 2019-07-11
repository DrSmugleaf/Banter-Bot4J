package com.github.drsmugleaf.pokemon2.base.generation;

import com.github.drsmugleaf.pokemon2.base.registry.Registry;
import com.github.drsmugleaf.reflection.Reflection;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by DrSmugleaf on 06/07/2019
 */
public class GenerationRegistry extends Registry<IGeneration> {

    public GenerationRegistry() {
        super(getAll());
    }

    private static Map<String, IGeneration> getAll() {
        Map<String, IGeneration> generations = new HashMap<>();
        Reflection reflection = new Reflection("com.github.drsmugleaf.pokemon2.generations");
        List<Class<IGeneration>> generationsClasses = reflection.findSubtypesOf(IGeneration.class);

        for (Class<IGeneration> generation : generationsClasses) {
            try {
                Field instanceField = generation.getDeclaredField("INSTANCE");
                instanceField.setAccessible(true);
                IGeneration instance = (IGeneration) instanceField.get(generation);
                generations.put(instance.getName(), instance);
                generations.put(instance.getAbbreviation(), instance);
            } catch (NoSuchFieldException e) {
                throw new IllegalStateException("No INSTANCE field found for generation: " + generation);
            } catch (IllegalAccessException e) {
                throw new IllegalStateException("Error getting INSTANCE field value for generation: " + generation, e);
            }
        }

        return generations;
    }

}
