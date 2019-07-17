package com.github.drsmugleaf.charactersheets.ability.effect;

import com.github.drsmugleaf.charactersheets.character.Character;
import com.github.drsmugleaf.charactersheets.state.State;
import com.github.drsmugleaf.commands.api.CommandInfo;

/**
 * Created by DrSmugleaf on 11/07/2019
 */
@CommandInfo(aliases = {"cau"})
public class StatChange implements IEffect {

    private final String CHANGED_STAT;
    private final long AMOUNT;

    public StatChange(String changedStat, long amount) {
        CHANGED_STAT = changedStat;
        AMOUNT = amount;
    }

    @Override
    public boolean isValid(Character user, Character on) {
        State userState = user.getState();
        return on.getSheet().getStats(userState).get(CHANGED_STAT) != null;
    }

    @Override
    public void use(Character on, Character user) {
        State userState = user.getState();
        on.getSheet().getStats(userState).get(CHANGED_STAT).changeValue(AMOUNT);
    }

}
