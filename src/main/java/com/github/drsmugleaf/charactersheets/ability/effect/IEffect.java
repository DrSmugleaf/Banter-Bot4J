package com.github.drsmugleaf.charactersheets.ability.effect;

import com.github.drsmugleaf.charactersheets.character.Character;

/**
 * Created by DrSmugleaf on 11/07/2019
 */
public interface IEffect {

    boolean isValid(Character user, Character on);
    void use(Character on, Character user);

}
