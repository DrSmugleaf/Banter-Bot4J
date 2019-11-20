package com.github.drsmugleaf.pokemon2.generations.i.pokemon.move.effect;

import com.github.drsmugleaf.pokemon2.base.pokemon.IBattlePokemon;
import com.github.drsmugleaf.pokemon2.base.pokemon.move.IMoveInformation;
import com.github.drsmugleaf.pokemon2.base.pokemon.move.IMoveReport;
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
        public void effect(IMoveInformation<IBattlePokemon<?>> move, IBattlePokemon<?> user, IBattlePokemon<?> target, IMoveReport<IBattlePokemon<?>> report) {
            NonVolatileStatusesI.SLEEP.apply(target);
        }
    },
    POISON_STING(3) {
        @Override
        public void effect(IMoveInformation<IBattlePokemon<?>> move, IBattlePokemon<?> user, IBattlePokemon<?> target, IMoveReport<IBattlePokemon<?>> report) {
            NonVolatileStatusesI.POISON.apply(target);
        }
    },
    ABSORB(4) {
        @Override
        public void effect(IMoveInformation<IBattlePokemon<?>> move, IBattlePokemon<?> user, IBattlePokemon<?> target, IMoveReport<IBattlePokemon<?>> report) {
            int heal = IntMath.divide(report.getDamage(), 2, RoundingMode.CEILING);
            boolean hadSubstitute = report.getBeforeSnapshot().getBattle().getField().getPokemon(target).hasModifier("SUBSTITUTE");
            boolean hasSubstitute = target.hasModifier("SUBSTITUTE");
            if (hadSubstitute && !hasSubstitute) {
                heal = 0;
            }

            user.heal(heal);
        }
    },
    FIRE_PUNCH(5) {
        @Override
        public void effect(IMoveInformation<IBattlePokemon<?>> move, IBattlePokemon<?> user, IBattlePokemon<?> target, IMoveReport<IBattlePokemon<?>> report) {
            NonVolatileStatusesI.BURN.apply(target);
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
    public int use(IMoveInformation<IBattlePokemon<?>> move, IBattlePokemon<?> user, IBattlePokemon<?> target) {
        int damage = 0;
        if (move.getCategory().doesDamage()) {
            damage = move.getDamage(target, user);
            user.damage(damage);
        }

        return damage;
    }

    @Override
    public void effect(IMoveInformation<IBattlePokemon<?>> move, IBattlePokemon<?> user, IBattlePokemon<?> target, IMoveReport<IBattlePokemon<?>> report) {}

}
