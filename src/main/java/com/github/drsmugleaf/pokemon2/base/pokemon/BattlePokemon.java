package com.github.drsmugleaf.pokemon2.base.pokemon;

import com.github.drsmugleaf.Nullable;
import com.github.drsmugleaf.pokemon2.base.battle.IBattle;
import com.github.drsmugleaf.pokemon2.base.battle.side.ISide;
import com.github.drsmugleaf.pokemon2.base.pokemon.modifier.IModifiers;
import com.github.drsmugleaf.pokemon2.base.pokemon.modifier.Modifiers;
import com.github.drsmugleaf.pokemon2.base.pokemon.move.IMoveReport;
import com.github.drsmugleaf.pokemon2.base.pokemon.species.ISpecies;
import com.github.drsmugleaf.pokemon2.base.pokemon.stat.IStat;
import com.github.drsmugleaf.pokemon2.base.pokemon.stat.type.IStatType;
import com.github.drsmugleaf.pokemon2.base.pokemon.state.IPokemonState;
import com.github.drsmugleaf.pokemon2.base.pokemon.state.PokemonStates;
import com.github.drsmugleaf.pokemon2.base.pokemon.status.INonVolatileStatus;
import com.github.drsmugleaf.pokemon2.base.pokemon.type.IType;
import com.github.drsmugleaf.pokemon2.base.trainer.IBattleTrainer;
import com.github.drsmugleaf.pokemon2.base.trainer.party.IParty;
import com.github.drsmugleaf.pokemon2.generations.i.pokemon.move.effect.EffectsI;
import com.github.drsmugleaf.pokemon2.generations.ii.item.IItem;
import com.github.drsmugleaf.pokemon2.generations.ii.pokemon.gender.IGender;
import com.google.common.collect.ImmutableMap;

import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;

/**
 * Created by DrSmugleaf on 17/11/2019
 */
public class BattlePokemon<T extends BattlePokemon<T, S>, S extends ISpecies> extends Pokemon<T, S> implements IBattlePokemon<T> {

    private final int ID;
    private final IBattle<T> BATTLE;
    @Nullable
    private INonVolatileStatus<T> status;
    private IPokemonState state;
    private final IModifiers MODIFIERS;
    private final LinkedHashMap<IBattlePokemon<T>, IMoveReport<T>> LAST_TARGETED_BY;
    private boolean active;

    public BattlePokemon(
            int id,
            S species,
            String nickname,
            Set<IType> types,
            IItem item,
            IGender gender,
            int level,
            Map<IStatType, IStat<T>> stats,
            int hp,
            int maxHp,
            IBattle<T> battle
    ) {
        super(species, nickname, types, item, gender, level, stats, hp, maxHp);
        ID = id;
        BATTLE = battle;
        status = null;
        state = PokemonStates.DEFAULT;
        MODIFIERS = new Modifiers();
        LAST_TARGETED_BY = new LinkedHashMap<>();
        active = false;
    }

    public BattlePokemon(T pokemon) {
        super(pokemon);
        ID = pokemon.getID();
        BATTLE = pokemon.getBattle();
        status = pokemon.getStatus();
        state = pokemon.getState();
        MODIFIERS = new Modifiers(pokemon.getModifiers());
        LAST_TARGETED_BY = new LinkedHashMap<>(pokemon.getLastTargetedBy());
        active = pokemon.isActive();
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
    public INonVolatileStatus<T> getStatus() {
        return status;
    }

    @Override
    public void setStatus(@Nullable INonVolatileStatus<T> status) {
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

    @Override
    public void targeted(IMoveReport<T> report) {
        int effectID = report.getMove().getEffect().getID();
        if (!EffectsI.MIRROR_MOVE.getIgnoredEffects().contains(effectID)) {
            T user = report.getUser();
            LAST_TARGETED_BY.remove(user);
            LAST_TARGETED_BY.put(user, report);
        }
    }

    @Override
    public ImmutableMap<IBattlePokemon<T>, IMoveReport<T>> getLastTargetedBy() {
        return ImmutableMap.copyOf(LAST_TARGETED_BY);
    }

    @Override
    public void removeLastTargetedBy(IBattlePokemon<T> pokemon) {
        LAST_TARGETED_BY.remove(pokemon);
    }

    @Override
    public boolean isActive() {
        return active;
    }

    @Override
    public void setActive() {
        this.active = true;
    }

    @Override
    public void setInactive() {
        this.active = false;

        getBattle()
                .getField()
                .getSides()
                .stream()
                .map(ISide::getTrainers)
                .flatMap(Collection::stream)
                .map(IBattleTrainer::getParty)
                .map(IParty::getPokemon)
                .flatMap(Collection::stream)
                .forEach(pokemon -> pokemon.removeLastTargetedBy(this));
    }

}
