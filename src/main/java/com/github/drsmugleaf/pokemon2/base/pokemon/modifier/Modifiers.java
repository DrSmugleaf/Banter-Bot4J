package com.github.drsmugleaf.pokemon2.base.pokemon.modifier;

import com.github.drsmugleaf.pokemon2.base.nameable.Nameable;
import com.github.drsmugleaf.pokemon2.base.pokemon.state.IPokemonState;

import java.util.stream.Stream;

/**
 * Created by DrSmugleaf on 20/11/2019
 */
public class Modifiers implements IModifiers {

    private final IModifierGroup<IPokemonState, IAllowedModifier, Boolean> ALLOWED_MODIFIERS;
    private final IModifierGroup<IPokemonState, IMultiplierModifier, Double> MULTIPLIER_MODIFIERS;
    private final IModifierGroup<IPokemonState, IExecutableModifier, Void> EXECUTABLE_MODIFIERS;

    public Modifiers() {
        ALLOWED_MODIFIERS = new ModifierGroup<>();
        MULTIPLIER_MODIFIERS = new ModifierGroup<>();
        EXECUTABLE_MODIFIERS = new ModifierGroup<>();
    }

    public Modifiers(IModifiers modifiers) {
        ALLOWED_MODIFIERS = new ModifierGroup<>(modifiers.getAllowed());
        MULTIPLIER_MODIFIERS = new ModifierGroup<>(modifiers.getMultiplier());
        EXECUTABLE_MODIFIERS = new ModifierGroup<>(modifiers.getExecutable());
    }

    @Override
    public IModifierGroup<IPokemonState, IAllowedModifier, Boolean> getAllowed() {
        return ALLOWED_MODIFIERS;
    }

    @Override
    public IModifierGroup<IPokemonState, IExecutableModifier, Void> getExecutable() {
        return EXECUTABLE_MODIFIERS;
    }

    @Override
    public IModifierGroup<IPokemonState, IMultiplierModifier, Double> getMultiplier() {
        return MULTIPLIER_MODIFIERS;
    }

    @Override
    public boolean hasModifier(String name) {
        return Stream
                .of(ALLOWED_MODIFIERS, EXECUTABLE_MODIFIERS, MULTIPLIER_MODIFIERS)
                .anyMatch(group -> group.has(name));
    }

    @Override
    public boolean hasModifier(Nameable nameable) {
        return hasModifier(nameable.getName());
    }

    @Override
    public void removeAll(String name) {
        ALLOWED_MODIFIERS.remove(name);
        EXECUTABLE_MODIFIERS.remove(name);
        MULTIPLIER_MODIFIERS.remove(name);
    }

    @Override
    public void removeAll(Nameable nameable) {
        removeAll(nameable.getName());
    }

}
