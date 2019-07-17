package com.github.drsmugleaf.commands.character.ability;

import com.github.drsmugleaf.charactersheets.ability.Ability;
import com.github.drsmugleaf.charactersheets.ability.effect.StatChange;
import com.github.drsmugleaf.charactersheets.character.CharacterBuilder;
import com.github.drsmugleaf.charactersheets.state.State;
import com.github.drsmugleaf.commands.api.Argument;
import com.github.drsmugleaf.commands.api.Command;
import com.github.drsmugleaf.commands.api.CommandInfo;
import com.github.drsmugleaf.commands.character.CharacterCreate;

/**
 * Created by DrSmugleaf on 13/07/2019
 */
@CommandInfo(aliases = "casc")
public class CharacterAbilityStatChange extends Command {

    @Argument(position = 1, example = "Combat")
    private String state;

    @Argument(position = 2, example = "Strength")
    private String stat;

    @Argument(position = 3, example = "3", minimum = Long.MIN_VALUE)
    private long amount;

    @Argument(position = 4, example = "7")
    private long cooldown;

    @Argument(position = 5, example = "Swords Dance")
    private String name;

    @Argument(position = 6, example = "Raises attack by 1")
    private String description;

    @Override
    public void run() {
        State state = new State(this.state);
        Ability ability = new Ability(name, description, state, new StatChange(stat, amount), cooldown);
        EVENT
                .getMessage()
                .getAuthor()
                .ifPresent(author -> {
                    CharacterBuilder character = CharacterCreate.getCharacters().get(author);
                    character.getSheet().addAbilities(ability);
                });
    }

}
