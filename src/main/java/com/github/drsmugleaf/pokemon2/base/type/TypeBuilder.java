package com.github.drsmugleaf.pokemon2.base.type;

import java.util.*;

/**
 * Created by DrSmugleaf on 05/07/2019
 */
public class TypeBuilder {

    private final Map<String, HashSet<String>> weakTo = new HashMap<>();
    private final Map<String, HashSet<String>> resistantTo = new HashMap<>();
    private final Map<String, HashSet<String>> immuneTo = new HashMap<>();
    private final TypeConstructor<String, HashSet<String>, HashSet<String>, HashSet<String>, IType> CONSTRUCTOR;

    public TypeBuilder(TypeConstructor<String, HashSet<String>, HashSet<String>, HashSet<String>, IType> constructor) {
        CONSTRUCTOR = constructor;
    }

    private static void set(Map<String, HashSet<String>> map, String type, String... to) {
        map.computeIfAbsent(type, t -> new HashSet<>());
        HashSet<String> types = map.get(type);
        Collections.addAll(types, to);
    }

    public Map<String, IType> build() {
        Map<String, IType> types = new HashMap<>();
        Set<String> names = new HashSet<>();
        names.addAll(weakTo.keySet());
        names.addAll(resistantTo.keySet());
        names.addAll(immuneTo.keySet());

        for (String name : names) {
            HashSet<String> weaknesses = weakTo.computeIfAbsent(name, (n) -> new HashSet<>());
            HashSet<String> resistances = resistantTo.computeIfAbsent(name, (n) -> new HashSet<>());
            HashSet<String> immunities = immuneTo.computeIfAbsent(name, (n) -> new HashSet<>());
            IType type = CONSTRUCTOR.apply(name, weaknesses, resistances, immunities);

            types.put(name, type);
        }

        return types;
    }

    public SpecificTypeBuilder get(String type) {
        return new SpecificTypeBuilder(this, type);
    }

    public interface TypeConstructor<T1, T2, T3, T4, R> {

        R apply(T1 t1, T2 t2, T3 t3, T4 t4);

    }

    public class SpecificTypeBuilder {

        private final TypeBuilder BUILDER;
        private final String TYPE;

        private SpecificTypeBuilder(TypeBuilder builder, String type) {
            BUILDER = builder;
            TYPE = type;
        }

        public SpecificTypeBuilder addWeakness(String... to) {
            set(BUILDER.weakTo, TYPE, to);
            return this;
        }

        public SpecificTypeBuilder addResistance(String... to) {
            set(BUILDER.resistantTo, TYPE, to);
            return this;
        }

        public SpecificTypeBuilder addImmunity(String... to) {
            set(BUILDER.immuneTo, TYPE, to);
            return this;
        }

    }

}
