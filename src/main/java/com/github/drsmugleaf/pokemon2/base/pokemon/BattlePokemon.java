package com.github.drsmugleaf.pokemon2.base.pokemon;

import com.github.drsmugleaf.Nullable;
import com.github.drsmugleaf.pokemon2.base.battle.IBattle;
import com.github.drsmugleaf.pokemon2.base.nameable.Nameable;
import com.github.drsmugleaf.pokemon2.base.pokemon.modifier.IModifier;
import com.github.drsmugleaf.pokemon2.base.pokemon.modifier.INamedModifier;
import com.github.drsmugleaf.pokemon2.base.pokemon.modifier.NamedModifier;
import com.github.drsmugleaf.pokemon2.base.pokemon.stat.IStat;
import com.github.drsmugleaf.pokemon2.base.pokemon.stat.type.IStatType;
import com.github.drsmugleaf.pokemon2.base.pokemon.state.IPokemonState;
import com.github.drsmugleaf.pokemon2.base.pokemon.state.PokemonStates;
import com.github.drsmugleaf.pokemon2.base.pokemon.status.IStatus;
import com.github.drsmugleaf.pokemon2.base.pokemon.type.IType;
import com.github.drsmugleaf.pokemon2.generations.ii.item.IItem;
import com.github.drsmugleaf.pokemon2.generations.ii.pokemon.gender.IGender;
import com.google.common.collect.ArrayListMultimap;
import com.google.common.collect.ImmutableMultimap;
import com.google.common.collect.Multimap;

import java.util.Map;
import java.util.Set;

/**
 * Created by DrSmugleaf on 17/11/2019
 */
public class BattlePokemon<T extends IBattlePokemon<T>> extends Pokemon<T> implements IBattlePokemon<T> {

    private final int ID;
    private final IBattle<T> BATTLE;
    @Nullable
    private IStatus<? super T> status = null;
    private IPokemonState state;
    private final Multimap<IPokemonState, INamedModifier> MODIFIERS;

    public BattlePokemon(
            int id,
            T species,
            String nickname,
            Set<IType> types,
            IItem item,
            IGender gender,
            int level,
            Map<IStatType, IStat<IPokemon<T>>> stats,
            int hp,
            IBattle<T> battle
    ) {
        super(species, nickname, types, item, gender, level, stats, hp);
        ID = id;
        BATTLE = battle;
        state = PokemonStates.DEFAULT;
        MODIFIERS = ArrayListMultimap.create();
    }

    public BattlePokemon(T pokemon) {
        super(pokemon);
        ID = pokemon.getID();
        BATTLE = pokemon.getBattle();
        state = pokemon.getState();
        MODIFIERS = ArrayListMultimap.create(pokemon.getModifiers());
    }

    @Override
    public int getID() {
        return ID;
    }

    @Override
    public IBattle<T> getBattle() {
        return BATTLE;
    }

    @Nullable
    @Override
    public IStatus<? super T> getStatus() {
        return status;
    }

    @Override
    public void setStatus(@Nullable IStatus<? super T> status) {
        this.status = status;
    }

    @Override
    public IPokemonState getState() {
        return state;
    }

    @Override
    public void setState(IPokemonState state) {
        this.state = state;
    }

    @Override
    public ImmutableMultimap<IPokemonState, INamedModifier> getModifiers() {
        return ImmutableMultimap.copyOf(MODIFIERS);
    }

    @Override
    public boolean hasModifier(String name) {
        return getModifier(name) != null;
    }

    @Nullable
    @Override
    public INamedModifier getModifier(String name) {
        for (INamedModifier modifier : MODIFIERS.values()) {
            if (name.equalsIgnoreCase(modifier.getName())) {
                return modifier;
            }
        }

        return null;
    }

    @Override
    public void addModifier(IPokemonState state, Nameable nameable, IModifier modifier) {
        MODIFIERS.put(state, new NamedModifier(nameable, modifier));
    }

    public void addModifierUnique(IPokemonState state, Nameable nameable, IModifier modifier) {
        if (!MODIFIERS.containsEntry(state, modifier)) {
            addModifier(state, nameable, modifier);
        }
    }

}
