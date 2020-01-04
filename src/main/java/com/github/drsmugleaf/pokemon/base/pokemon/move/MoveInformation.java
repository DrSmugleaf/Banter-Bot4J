package com.github.drsmugleaf.pokemon.base.pokemon.move;

import com.github.drsmugleaf.pokemon.base.pokemon.IBattlePokemon;
import com.github.drsmugleaf.pokemon.base.pokemon.move.effect.IEffect;
import com.github.drsmugleaf.pokemon.base.pokemon.type.IType;

/**
 * Created by DrSmugleaf on 13/11/2019
 */
public class MoveInformation<T extends IBattlePokemon<T>> implements IMoveInformation<T> {

    private final IType TYPE;
    private final IDamageCategory CATEGORY;
    private final int PP;
    private final int POWER;
    private final int ACCURACY;
    private final IEffect<T> EFFECT;
    private final String NAME;

    public MoveInformation(
            IType type,
            IDamageCategory category,
            int pp,
            int power,
            int accuracy,
            IEffect<T> effect,
            String name
    ) {
        TYPE = type;
        CATEGORY = category;
        PP = pp;
        POWER = power;
        ACCURACY = accuracy;
        EFFECT = effect;
        NAME = name;
    }

    @Override
    public IType getType() {
        return TYPE;
    }

    @Override
    public IDamageCategory getCategory() {
        return CATEGORY;
    }

    @Override
    public int getPP() {
        return PP;
    }

    @Override
    public int getPower() {
        return POWER;
    }

    @Override
    public int getAccuracy() {
        return ACCURACY;
    }

    @Override
    public IEffect<T> getEffect() {
        return EFFECT;
    }

    @Override
    public String getName() {
        return NAME;
    }

}
