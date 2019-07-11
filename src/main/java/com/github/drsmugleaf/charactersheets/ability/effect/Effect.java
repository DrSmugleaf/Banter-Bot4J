package com.github.drsmugleaf.charactersheets.ability.effect;

import com.github.drsmugleaf.charactersheets.Nameable;
import com.github.drsmugleaf.charactersheets.character.Character;
import com.github.drsmugleaf.charactersheets.game.Game;

/**
 * Created by DrSmugleaf on 11/07/2019
 */
public abstract class Effect implements Nameable {

    private final String NAME;

    public Effect(String name) {
        NAME = name;
    }

    @Override
    public String getName() {
        return NAME;
    }

    public abstract boolean isValid(Character user, Character on);

    public abstract void use(Game game, Character on, Character user);

}
