package com.github.drsmugleaf.pokemon2.generations.i.pokemon.move.effect;

import com.github.drsmugleaf.pokemon2.base.battle.IBattlePokemon;
import com.github.drsmugleaf.pokemon2.base.pokemon.move.IMoveInformation;
import com.github.drsmugleaf.pokemon2.base.pokemon.move.effect.IEffect;
import com.github.drsmugleaf.pokemon2.generations.i.pokemon.status.NonVolatileStatusesI;

/**
 * Created by DrSmugleaf on 13/11/2019
 */
public enum EffectsI implements IEffect<IBattlePokemon<?>> {

    POUND(1) {
        @Override
        public void use(IMoveInformation<IBattlePokemon<?>> move, IBattlePokemon<?> target, IBattlePokemon<?> user) {
            user.damage(move.getDamage(target, user));
        }
    },
    SING(2) {
        @Override
        public void use(IMoveInformation<IBattlePokemon<?>> move, IBattlePokemon<?> target, IBattlePokemon<?> user) {
            NonVolatileStatusesI.SLEEP.apply(target);
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

}
