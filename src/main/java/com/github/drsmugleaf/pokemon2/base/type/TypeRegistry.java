package com.github.drsmugleaf.pokemon2.base.type;

import com.github.drsmugleaf.pokemon.external.smogon.SmogonParser;
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
        TypeBuilder builder = new TypeBuilder(Type::new);
        return SmogonParser.getTypes(generation, builder);
    }

    public List<IType> fromAlt(JSONObject alt) {
        List<IType> types = new ArrayList<>();
        JSONArray jsonTypes = alt.getJSONArray("types");

        for (int i = 0; i < jsonTypes.length(); i++) {
            String name = jsonTypes.getString(i);
            IType type = get().get(name);
            types.add(type);
        }

        return types;
    }

}
