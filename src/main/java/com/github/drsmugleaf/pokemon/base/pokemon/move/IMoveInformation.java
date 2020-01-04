package com.github.drsmugleaf.pokemon.base.pokemon.move;

import com.github.drsmugleaf.pokemon.base.nameable.Nameable;
import com.github.drsmugleaf.pokemon.base.pokemon.IBattlePokemon;
import com.github.drsmugleaf.pokemon.base.pokemon.move.effect.IEffect;
import com.github.drsmugleaf.pokemon.base.pokemon.type.IType;

/**
 * Created by DrSmugleaf on 13/11/2019
 */
public interface IMoveInformation<T extends IBattlePokemon<T>> extends Nameable {

    IType getType();
    IDamageCategory getCategory();
    int getPP();
    int getPower();
    int getAccuracy();
    IEffect<T> getEffect();
    // TODO: 13-Nov-19 Tags
    // TODO: 13-Nov-19 Target

}
