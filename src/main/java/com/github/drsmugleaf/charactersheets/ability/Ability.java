package com.github.drsmugleaf.charactersheets.ability;

import com.github.drsmugleaf.charactersheets.Nameable;
import com.github.drsmugleaf.charactersheets.ability.effect.IEffect;
import com.github.drsmugleaf.charactersheets.character.Character;
import com.github.drsmugleaf.charactersheets.state.State;

/**
 * Created by DrSmugleaf on 11/07/2019
 */
public class Ability implements Nameable {

    private final String NAME;
    private final String DESCRIPTION;
    private final State VALID_STATE;
    private final IEffect EFFECT;
    private final long COOLDOWN;
    private long AVAILABLE_IN = 0;

    public Ability(String name, String description, State validState, IEffect effect, long cooldown) {
        NAME = name;
        DESCRIPTION = description;
        VALID_STATE = validState;
        EFFECT = effect;
        COOLDOWN = cooldown;
    }

    @Override
    public String getName() {
        return NAME;
    }

    public String getDescription() {
        return DESCRIPTION;
    }

    public State getValidState() {
        return VALID_STATE;
    }

    public long getCooldown() {
        return COOLDOWN;
    }

    public long getAvailableIn() {
        return AVAILABLE_IN;
    }

    public boolean isValid(State state) {
        return AVAILABLE_IN < 1 && state.equals(VALID_STATE);
    }

    public void use(Character user, Character on) {
        EFFECT.use(on, user);
        AVAILABLE_IN = COOLDOWN;
    }

    public void onTurnEnd() {
        AVAILABLE_IN--;
    }

}
