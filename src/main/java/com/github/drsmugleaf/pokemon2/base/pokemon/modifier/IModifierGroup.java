package com.github.drsmugleaf.pokemon2.base.pokemon.modifier;

import com.github.drsmugleaf.pokemon2.base.nameable.Nameable;
import com.google.common.collect.ImmutableCollection;
import com.google.common.collect.ImmutableMultimap;

/**
 * Created by DrSmugleaf on 20/11/2019
 */
public interface IModifierGroup<K, V extends IModifier<R>, R> {

    void add(K key, Nameable nameable, V modifier);
    void addUnique(K key, Nameable nameable, V modifier);
    ImmutableMultimap<K, INamedModifier<R>> get();
    ImmutableCollection<INamedModifier<R>> get(K key);
    boolean has(String name);
    boolean has(Nameable nameable);
    void remove(String name);
    void remove(Nameable nameable);

}
