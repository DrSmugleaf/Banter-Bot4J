package com.github.drsmugleaf.pokemon2.base.species.type;

import com.github.drsmugleaf.pokemon2.base.generation.IGeneration;
import com.github.drsmugleaf.pokemon2.base.registry.Registry;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by DrSmugleaf on 06/07/2019
 */
public class TypeRegistry extends Registry<IType> {

    public TypeRegistry(IGeneration generation) {
        super(getAll(generation));
    }

    private static Map<String, IType> getAll(IGeneration generation) {
        return generation.getSmogon().getTypes().build();
    }

    public List<IType> fromJson(JSONObject pokemon) {
        List<IType> types = new ArrayList<>();
        JSONArray jsonTypes = pokemon.getJSONArray("types");

        for (int i = 0; i < jsonTypes.length(); i++) {
            String name = jsonTypes.getString(i);
            IType type = get().get(name);
            types.add(type);
        }

        return types;
    }

}
