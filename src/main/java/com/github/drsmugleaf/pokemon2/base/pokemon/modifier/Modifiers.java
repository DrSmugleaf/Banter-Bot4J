package com.github.drsmugleaf.pokemon2.base.pokemon.modifier;

import com.github.drsmugleaf.pokemon2.base.nameable.Nameable;
import com.github.drsmugleaf.pokemon2.base.pokemon.state.IPokemonState;
import com.google.common.collect.*;

/**
 * Created by DrSmugleaf on 20/11/2019
 */
public class Modifiers implements IModifiers {

    private final Multimap<IPokemonState, INamedModifier<Boolean>> ALLOWED_MODIFIERS;
    private final Multimap<IPokemonState, INamedModifier<Double>> MULTIPLIER_MODIFIERS;
    private final Multimap<IPokemonState, INamedModifier<Void>> EXECUTABLE_MODIFIERS;

    public Modifiers() {
        ALLOWED_MODIFIERS = ArrayListMultimap.create();
        MULTIPLIER_MODIFIERS = ArrayListMultimap.create();
        EXECUTABLE_MODIFIERS = ArrayListMultimap.create();
    }

    public Modifiers(IModifiers modifiers) {
        ALLOWED_MODIFIERS = ArrayListMultimap.create(modifiers.getAllowed());
        MULTIPLIER_MODIFIERS = ArrayListMultimap.create(modifiers.getMultiplier());
        EXECUTABLE_MODIFIERS = ArrayListMultimap.create(modifiers.getExecutable());
    }

    @Override
    public void addAllowed(IPokemonState state, Nameable nameable, IModifier<Boolean> multiplier) {
        ALLOWED_MODIFIERS.put(state, new NamedModifier<>(nameable, multiplier));
    }

    @Override
    public void addAllowedUnique(IPokemonState state, Nameable nameable, IModifier<Boolean> multiplier) {
        if (ALLOWED_MODIFIERS.values().stream().map(Nameable::getName).noneMatch(name -> name.equalsIgnoreCase(nameable.getName()))) {
            addAllowed(state, nameable, multiplier);
        }
    }

    @Override
    public ImmutableMultimap<IPokemonState, INamedModifier<Boolean>> getAllowed() {
        return ImmutableMultimap.copyOf(ALLOWED_MODIFIERS);
    }

    @Override
    public ImmutableCollection<INamedModifier<Boolean>> getAllowed(IPokemonState state) {
        return ImmutableList.copyOf(ALLOWED_MODIFIERS.get(state));
    }

    @Override
    public void addExecutable(IPokemonState state, Nameable nameable, IModifier<Void> executable) {
        EXECUTABLE_MODIFIERS.put(state, new NamedModifier<>(nameable, executable));
    }

    @Override
    public void addExecutableUnique(IPokemonState state, Nameable nameable, IModifier<Void> executable) {
        if (EXECUTABLE_MODIFIERS.values().stream().map(Nameable::getName).noneMatch(name -> name.equalsIgnoreCase(nameable.getName()))) {
            addExecutable(state, nameable, executable);
        }
    }

    @Override
    public ImmutableMultimap<IPokemonState, INamedModifier<Void>> getExecutable() {
        return ImmutableMultimap.copyOf(EXECUTABLE_MODIFIERS);
    }

    @Override
    public ImmutableCollection<INamedModifier<Void>> getExecutable(IPokemonState state) {
        return ImmutableList.copyOf(EXECUTABLE_MODIFIERS.get(state));
    }

    @Override
    public void addMultiplier(IPokemonState state, Nameable nameable, IModifier<Double> multiplier) {
        MULTIPLIER_MODIFIERS.put(state, new NamedModifier<>(nameable, multiplier));
    }

    @Override
    public void addMultiplierUnique(IPokemonState state, Nameable nameable, IModifier<Double> multiplier) {
        if (MULTIPLIER_MODIFIERS.values().stream().map(Nameable::getName).noneMatch(name -> name.equalsIgnoreCase(nameable.getName()))) {
            addMultiplier(state, nameable, multiplier);
        }
    }

    @Override
    public ImmutableMultimap<IPokemonState, INamedModifier<Double>> getMultiplier() {
        return ImmutableMultimap.copyOf(MULTIPLIER_MODIFIERS);
    }

    @Override
    public ImmutableCollection<INamedModifier<Double>> getMultiplier(IPokemonState state) {
        return ImmutableList.copyOf(MULTIPLIER_MODIFIERS.get(state));
    }

    @Override
    public boolean hasModifier(String name) {
        for (INamedModifier<Boolean> modifier : ALLOWED_MODIFIERS.values()) {
            if (modifier.getName().equalsIgnoreCase(name)) {
                return true;
            }
        }

        for (INamedModifier<Double> modifier : MULTIPLIER_MODIFIERS.values()) {
            if (modifier.getName().equalsIgnoreCase(name)) {
                return true;
            }
        }

        for (INamedModifier<Void> modifier : EXECUTABLE_MODIFIERS.values()) {
            if (modifier.getName().equalsIgnoreCase(name)) {
                return true;
            }
        }

        return false;
    }

    @Override
    public boolean hasModifier(Nameable nameable) {
        return hasModifier(nameable.getName());
    }

    private <T> void removeAll(Multimap<IPokemonState, INamedModifier<T>> map, String name) {
        map.values().removeIf(modifier -> name.equalsIgnoreCase(modifier.getName()));
    }

    @Override
    public void removeAll(String name) {
        removeAll(ALLOWED_MODIFIERS, name);
        removeAll(EXECUTABLE_MODIFIERS, name);
        removeAll(MULTIPLIER_MODIFIERS, name);
    }

    @Override
    public void removeAll(Nameable nameable) {
        removeAll(nameable.getName());
    }

}
