package com.github.drsmugleaf.pokemon2.generations.i.pokemon.move.effect;

import com.github.drsmugleaf.pokemon2.base.battle.IBattlePokemon;
import com.github.drsmugleaf.pokemon2.base.pokemon.move.IMoveInformation;
import com.github.drsmugleaf.pokemon2.base.pokemon.move.effect.IEffect;
import com.github.drsmugleaf.pokemon2.generations.i.pokemon.status.NonVolatileStatusesI;
import com.google.common.math.IntMath;

import java.math.RoundingMode;

/**
 * Created by DrSmugleaf on 13/11/2019
 */
public enum EffectsI implements IEffect<IBattlePokemon<?>> {

    POUND(1),
    SING(2) {
        @Override
        public void effect(int damageDealt, IBattlePokemon<?> target, IBattlePokemon<?> user, IMoveInformation<IBattlePokemon<?>> move) {
            NonVolatileStatusesI.SLEEP.apply(target);
        }
    },
    POISON_STING(3) {
        @Override
        public void effect(int damageDealt, IBattlePokemon<?> target, IBattlePokemon<?> user, IMoveInformation<IBattlePokemon<?>> move) {
            NonVolatileStatusesI.POISON.apply(target);
        }
    },
    ABSORB(4) {
        @Override
        public void effect(int damageDealt, IBattlePokemon<?> target, IBattlePokemon<?> user, IMoveInformation<IBattlePokemon<?>> move) {
            int heal = IntMath.divide(damageDealt, 2, RoundingMode.CEILING);
            user.heal(heal);
        }
    };

    private final int ID;

    EffectsI(int id) {
        ID = id;
    }

    @Override
    public int getID() {
        return ID;
    }

    @Override
    public int use(IMoveInformation<IBattlePokemon<?>> move, IBattlePokemon<?> target, IBattlePokemon<?> user) {
        int damage = 0;
        if (move.getCategory().doesDamage()) {
            damage = move.getDamage(target, user);
            user.damage(damage);
        }

        return damage;
    }

    @Override
    public void effect(int damageDealt, IBattlePokemon<?> target, IBattlePokemon<?> user, IMoveInformation<IBattlePokemon<?>> move) {}

}
