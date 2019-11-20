package com.github.drsmugleaf.pokemon2.base.pokemon;

import com.github.drsmugleaf.Nullable;
import com.github.drsmugleaf.pokemon2.base.battle.IBattle;
import com.github.drsmugleaf.pokemon2.base.pokemon.modifier.IModifiers;
import com.github.drsmugleaf.pokemon2.base.pokemon.modifier.Modifiers;
import com.github.drsmugleaf.pokemon2.base.pokemon.stat.IStat;
import com.github.drsmugleaf.pokemon2.base.pokemon.stat.type.IStatType;
import com.github.drsmugleaf.pokemon2.base.pokemon.state.IPokemonState;
import com.github.drsmugleaf.pokemon2.base.pokemon.state.PokemonStates;
import com.github.drsmugleaf.pokemon2.base.pokemon.status.INonVolatileStatus;
import com.github.drsmugleaf.pokemon2.base.pokemon.type.IType;
import com.github.drsmugleaf.pokemon2.generations.ii.item.IItem;
import com.github.drsmugleaf.pokemon2.generations.ii.pokemon.gender.IGender;

import java.util.Map;
import java.util.Set;

/**
 * Created by DrSmugleaf on 17/11/2019
 */
public class BattlePokemon<T extends IBattlePokemon<T>> extends Pokemon<T> implements IBattlePokemon<T> {

    private final int ID;
    private final IBattle<T> BATTLE;
    @Nullable
    private INonVolatileStatus<? super T> status = null;
    private IPokemonState state;
    private final IModifiers MODIFIERS;

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
        MODIFIERS = new Modifiers();
    }

    public BattlePokemon(T pokemon) {
        super(pokemon);
        ID = pokemon.getID();
        BATTLE = pokemon.getBattle();
        state = pokemon.getState();
        MODIFIERS = new Modifiers(pokemon.getModifiers());
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
    public INonVolatileStatus<? super T> getStatus() {
        return status;
    }

    @Override
    public void setStatus(@Nullable INonVolatileStatus<? super T> status) {
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
    public IModifiers getModifiers() {
        return MODIFIERS;
    }

}
