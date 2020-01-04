package com.github.drsmugleaf.pokemon.base.pokemon.modifier;

import com.github.drsmugleaf.pokemon.base.nameable.Nameable;
import com.google.common.collect.*;

/**
 * Created by DrSmugleaf on 20/11/2019
 */
public class ModifierGroup<K, V extends IModifier<R>, R> implements IModifierGroup<K, V, R> {

    private final Multimap<K, INamedModifier<R>> MODIFIERS;

    public ModifierGroup() {
        MODIFIERS = ArrayListMultimap.create();
    }

    public ModifierGroup(IModifierGroup<K, V, R> group) {
        MODIFIERS = ArrayListMultimap.create(group.get());
    }

    @Override
    public void add(K key, Nameable nameable, V modifier) {
        MODIFIERS.put(key, new NamedModifier<>(nameable, modifier));
    }

    @Override
    public void addUnique(K key, Nameable nameable, V modifier) {
        if (MODIFIERS.values().stream().map(Nameable::getName).noneMatch(name -> name.equalsIgnoreCase(nameable.getName()))) {
            add(key, nameable, modifier);
        }
    }

    @Override
    public ImmutableMultimap<K, INamedModifier<R>> get() {
        return ImmutableMultimap.copyOf(MODIFIERS);
    }

    @Override
    public ImmutableCollection<INamedModifier<R>> get(K key) {
        return ImmutableList.copyOf(MODIFIERS.values());
    }

    @Override
    public boolean has(String name) {
        return MODIFIERS.values().stream().map(Nameable::getName).anyMatch(valueName -> valueName.equalsIgnoreCase(name));
    }

    @Override
    public boolean has(Nameable nameable) {
        return has(nameable.getName());
    }

    @Override
    public void remove(String name) {
        MODIFIERS.values().removeIf(modifier -> modifier.getName().equalsIgnoreCase(name));
    }

    @Override
    public void remove(Nameable nameable) {
        remove(nameable.getName());
    }

}
