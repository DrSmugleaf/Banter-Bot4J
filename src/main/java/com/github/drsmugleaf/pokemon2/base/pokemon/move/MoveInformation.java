package com.github.drsmugleaf.pokemon2.base.pokemon.move;

import com.github.drsmugleaf.pokemon2.base.pokemon.IPokemon;
import com.github.drsmugleaf.pokemon2.base.pokemon.type.IType;

import java.util.concurrent.ThreadLocalRandom;

/**
 * Created by DrSmugleaf on 13/11/2019
 */
public class MoveInformation<T extends IPokemon<T>> implements IMoveInformation<T> {

    private final IType TYPE;
    private final IDamageCategory CATEGORY;
    private final int PP;
    private final int POWER;
    private final int ACCURACY;
    private final String NAME;

    public MoveInformation(
            IType type,
            IDamageCategory category,
            int pp,
            int power,
            int accuracy,
            String name
    ) {
        TYPE = type;
        CATEGORY = category;
        PP = pp;
        POWER = power;
        ACCURACY = accuracy;
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
    public int getDamage(T user, T target) {
        if (!getCategory().doesDamage()) {
            return 0;
        }

        int level = user.getLevel();
        int attackStat = user.getStats().get(getCategory().getAttackStat()).calculate(user);
        int defenseStat = user.getStats().get(getCategory().getAttackStat()).calculate(user);
        double randomNumber = ThreadLocalRandom.current().nextDouble(0.85, 1.0);
        return (int) ((((((2 * level) / 5 + 2) * POWER * attackStat / defenseStat) / 50) + 2) * randomNumber);
    }

    @Override
    public String getName() {
        return NAME;
    }

}
