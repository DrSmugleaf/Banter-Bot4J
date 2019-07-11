package com.github.drsmugleaf.charactersheets.ability.effect;

import com.github.drsmugleaf.charactersheets.character.Character;
import com.github.drsmugleaf.charactersheets.game.Game;

/**
 * Created by DrSmugleaf on 12/07/2019
 */
public class RecurringEffect extends Effect {

    private final Effect EFFECT;
    private final long TIMES;
    private long elapsed = 0;

    public RecurringEffect(String name, Effect effect, long times) {
        super(name);
        EFFECT = effect;
        TIMES = times;
    }

    @Override
    public boolean isValid(Character user, Character on) {
        return EFFECT.isValid(user, on);
    }

    @Override
    public void use(Game game, Character on, Character user) {
        Runnable effect = () -> EFFECT.use(game, on, user);
        elapsed++;
        if (elapsed <= TIMES) {
            game.getTurn().addEffect(effect);
        }
    }

    public long getTimes() {
        return TIMES;
    }

    public long getElapsed() {
        return elapsed;
    }

}
