package com.github.drsmugleaf.pokemon2.base.pokemon.move;

import com.github.drsmugleaf.pokemon2.base.nameable.Nameable;
import com.github.drsmugleaf.pokemon2.base.pokemon.IPokemon;
import com.github.drsmugleaf.pokemon2.base.pokemon.type.IType;

/**
 * Created by DrSmugleaf on 13/11/2019
 */
public interface IMoveInformation extends Nameable {

    IType getType();
    IDamageCategory getCategory();
    int getPP();
    int getPower();
    int getAccuracy();
    // TODO: 13-Nov-19 Tags
    // TODO: 13-Nov-19 Target
    int getDamage(IPokemon<?> user, IPokemon<?> target);

}
