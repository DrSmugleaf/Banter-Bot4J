package com.github.drsmugleaf.charactersheets.game;

import com.github.drsmugleaf.charactersheets.ability.Abilities;
import com.github.drsmugleaf.charactersheets.ability.Ability;
import com.github.drsmugleaf.charactersheets.character.Character;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by DrSmugleaf on 12/07/2019
 */
public class Turn {

    private long NUMBER = 1;
    private final List<Runnable> RECURRING_EFFECTS = new ArrayList<>();

    public Turn() {}

    public void addEffect(Runnable effect) {
        RECURRING_EFFECTS.add(effect);
    }

    public void execute(Game game) {
        for (Runnable effect : RECURRING_EFFECTS) {
            effect.run();
        }

        for (Character character : game.getRoster().getCharacters().values()) {
            for (Abilities abilities : character.getSheet().getAbilities().values()) {
                for (Ability ability : abilities.get().values()) {
                    ability.onTurnEnd();
                }
            }
        }

        NUMBER++;
    }

}
