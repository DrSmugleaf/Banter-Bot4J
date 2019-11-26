package com.github.drsmugleaf.pokemon2.generations.i.pokemon.move.effect;

import com.github.drsmugleaf.pokemon2.base.pokemon.move.IDamageCategory;
import com.github.drsmugleaf.pokemon2.base.pokemon.move.IMoveInformation;
import com.github.drsmugleaf.pokemon2.base.pokemon.move.IMoveReport;
import com.github.drsmugleaf.pokemon2.base.pokemon.move.effect.IEffect;
import com.github.drsmugleaf.pokemon2.generations.i.pokemon.IBattlePokemonI;
import com.github.drsmugleaf.pokemon2.generations.i.pokemon.status.NonVolatileStatusesI;
import com.google.common.math.IntMath;

import java.math.RoundingMode;
import java.util.concurrent.ThreadLocalRandom;

/**
 * Created by DrSmugleaf on 13/11/2019
 */
public enum EffectsI implements IEffect<IBattlePokemonI> {

    NONE,
    POUND,
    SING {
        @Override
        public void effect(IMoveInformation<IBattlePokemonI> move, IBattlePokemonI user, IBattlePokemonI target, IMoveReport<IBattlePokemonI> report) {
            NonVolatileStatusesI.SLEEP.apply(target);
        }
    },
    POISON_STING {
        @Override
        public void effect(IMoveInformation<IBattlePokemonI> move, IBattlePokemonI user, IBattlePokemonI target, IMoveReport<IBattlePokemonI> report) {
            NonVolatileStatusesI.POISON.apply(target);
        }
    },
    ABSORB {
        @Override
        public void effect(IMoveInformation<IBattlePokemonI> move, IBattlePokemonI user, IBattlePokemonI target, IMoveReport<IBattlePokemonI> report) {
            int heal = IntMath.divide(report.getDamage(), 2, RoundingMode.CEILING);
            boolean hadSubstitute = report.getBeforeSnapshot().getBattle().getField().getPokemon(target).getModifiers().hasModifier("SUBSTITUTE");
            boolean hasSubstitute = target.getModifiers().hasModifier("SUBSTITUTE");
            if (hadSubstitute && !hasSubstitute) {
                heal = 0;
            }

            user.heal(heal);
        }
    },
    FIRE_PUNCH {
        @Override
        public void effect(IMoveInformation<IBattlePokemonI> move, IBattlePokemonI user, IBattlePokemonI target, IMoveReport<IBattlePokemonI> report) {
            NonVolatileStatusesI.BURN.apply(target);
        }
    },
    ICE_PUNCH {
        @Override
        public void effect(IMoveInformation<IBattlePokemonI> move, IBattlePokemonI user, IBattlePokemonI target, IMoveReport<IBattlePokemonI> report) {
            NonVolatileStatusesI.FREEZE.apply(target);
        }
    },
    THUNDER_PUNCH {
        @Override
        public void effect(IMoveInformation<IBattlePokemonI> move, IBattlePokemonI user, IBattlePokemonI target, IMoveReport<IBattlePokemonI> report) {
            NonVolatileStatusesI.PARALYSIS.apply(target);
        }
    },
    SELF_DESTRUCT {
        @Override
        public void effect(IMoveInformation<IBattlePokemonI> move, IBattlePokemonI user, IBattlePokemonI target, IMoveReport<IBattlePokemonI> report) {
            user.faint(); // TODO: 21-Nov-19 No attack or recurrent damage for opponent, Stadium: 260 power,no defense halving, fainting after breaking substitute
        }

        @Override
        public boolean doesEffect(IMoveInformation<IBattlePokemonI> move, IBattlePokemonI user, IBattlePokemonI target, IMoveReport<IBattlePokemonI> report) {
            boolean hadSubstitute = report.getBeforeSnapshot().getBattle().getField().getPokemon(target).getModifiers().hasModifier("SUBSTITUTE");
            boolean hasSubstitute = target.getModifiers().hasModifier("SUBSTITUTE");
            return !hadSubstitute || hasSubstitute;
        }

        @Override
        public int getDamage(IMoveInformation<IBattlePokemonI> move, IBattlePokemonI user, IBattlePokemonI target) {
            IDamageCategory category = move.getCategory();
            if (!category.doesDamage()) {
                return 0;
            }

            int level = user.getLevel();
            int power = move.getPower();
            int attackStat = user.getStats().get(category.getAttackStat()).calculate(user);
            int defenseStat = target.getStats().get(category.getDefenseStat()).calculate(target);
            if (defenseStat > 1) {
                defenseStat /= 2;
            }
            double randomNumber = ThreadLocalRandom.current().nextDouble(0.85, 1.0);
            return (int) ((((((2 * level) / 5 + 2) * power * attackStat / defenseStat) / 50) + 2) * randomNumber);
        }
    };

    EffectsI() {}

    @Override
    public int getID() {
        return ordinal();
    }

    @Override
    public int use(IMoveInformation<IBattlePokemonI> move, IBattlePokemonI user, IBattlePokemonI target) {
        int damage = 0;
        if (move.getCategory().doesDamage()) {
            damage = getDamage(move, user, target);
            user.damage(damage);
        }

        return damage;
    }

    @Override
    public void effect(IMoveInformation<IBattlePokemonI> move, IBattlePokemonI user, IBattlePokemonI target, IMoveReport<IBattlePokemonI> report) {}

    @Override
    public boolean doesEffect(IMoveInformation<IBattlePokemonI> move, IBattlePokemonI user, IBattlePokemonI target, IMoveReport<IBattlePokemonI> report) {
        return report.hit();
    }

    @Override
    public int getDamage(IMoveInformation<IBattlePokemonI> move, IBattlePokemonI user, IBattlePokemonI target) {
        IDamageCategory category = move.getCategory();
        if (!category.doesDamage()) {
            return 0;
        }

        int level = user.getLevel();
        int power = move.getPower();
        int attackStat = user.getStats().get(category.getAttackStat()).calculate(user);
        int defenseStat = target.getStats().get(category.getDefenseStat()).calculate(target);
        double randomNumber = ThreadLocalRandom.current().nextDouble(0.85, 1.0);
        return (int) ((((((2 * level) / 5 + 2) * power * attackStat / defenseStat) / 50) + 2) * randomNumber);
    }

    @Override
    public String getName() {
        return name();
    }

}
