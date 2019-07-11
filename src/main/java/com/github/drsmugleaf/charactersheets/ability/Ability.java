package com.github.drsmugleaf.charactersheets.ability;

import com.github.drsmugleaf.Nullable;
import com.github.drsmugleaf.charactersheets.Nameable;
import com.github.drsmugleaf.charactersheets.ability.effect.Effect;
import com.github.drsmugleaf.charactersheets.character.Character;
import com.github.drsmugleaf.charactersheets.state.State;

/**
 * Created by DrSmugleaf on 11/07/2019
 */
public class Ability implements Nameable {

    private final String NAME;
    private final String DESCRIPTION;
    @Nullable
    private final State VALID_STATE;
    private final Effect EFFECT;

    public Ability(String name, String description, @Nullable State validState, Effect effect) {
        NAME = name;
        DESCRIPTION = description;
        VALID_STATE = validState;
        EFFECT = effect;
    }

    @Override
    public String getName() {
        return NAME;
    }

    public String getDescription() {
        return DESCRIPTION;
    }

    public boolean isValid(State state) {
        return state.equals(VALID_STATE);
    }

    public void use(Character user, Character on) {
        EFFECT.use(user, on);
    }

}
