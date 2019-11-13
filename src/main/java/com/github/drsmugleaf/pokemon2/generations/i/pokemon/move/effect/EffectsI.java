package com.github.drsmugleaf.pokemon2.generations.i.pokemon.move.effect;

import com.github.drsmugleaf.pokemon2.base.pokemon.IPokemon;
import com.github.drsmugleaf.pokemon2.base.pokemon.move.IMoveInformation;
import com.github.drsmugleaf.pokemon2.base.pokemon.move.effect.IEffect;

/**
 * Created by DrSmugleaf on 13/11/2019
 */
public enum EffectsI implements IEffect {

    POUND(1) {
        @Override
        public void use(IMoveInformation move, IPokemon target, IPokemon user) {
            user.damage(move.getDamage(target, user));
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
