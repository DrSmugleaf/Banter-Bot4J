package com.github.drsmugleaf.pokemon2.base.battle;

import com.github.drsmugleaf.pokemon2.base.pokemon.BasePokemon;
import com.github.drsmugleaf.pokemon2.base.pokemon.stat.IStat;
import com.github.drsmugleaf.pokemon2.base.pokemon.stat.type.IStatType;
import com.github.drsmugleaf.pokemon2.base.pokemon.type.IType;
import com.github.drsmugleaf.pokemon2.generations.ii.item.IItem;
import com.github.drsmugleaf.pokemon2.generations.ii.pokemon.gender.IGender;

import java.util.Map;
import java.util.Set;

/**
 * Created by DrSmugleaf on 17/11/2019
 */
public class BattlePokemon<T extends IBattlePokemon<T>> extends BasePokemon<T> implements IBattlePokemon<T> {

    private final IBattle<T> BATTLE;

    public BattlePokemon(
            T species,
            String nickname,
            Set<IType> types,
            IItem item,
            IGender gender,
            int level,
            Map<IStatType, IStat<T>> stats,
            int hp,
            IBattle<T> battle
    ) {
        super(species, nickname, types, item, gender, level, stats, hp);
        BATTLE = battle;
    }

    @Override
    public IBattle<T> getBattle() {
        return BATTLE;
    }

}
