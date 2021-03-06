package com.github.drsmugleaf.commands.character.ability;

import com.github.drsmugleaf.charactersheets.ability.Ability;
import com.github.drsmugleaf.charactersheets.ability.effect.StatChange;
import com.github.drsmugleaf.charactersheets.character.CharacterBuilder;
import com.github.drsmugleaf.charactersheets.state.State;
import com.github.drsmugleaf.commands.api.arguments.Argument;
import com.github.drsmugleaf.commands.api.Command;
import com.github.drsmugleaf.commands.api.CommandInfo;
import com.github.drsmugleaf.commands.character.CharacterCreate;

/**
 * Created by DrSmugleaf on 13/07/2019
 */
@CommandInfo(
        aliases = "casc",
        description = "Create a character ability that changes stats"
)
public class CharacterAbilityStatChange extends Command {

    @Argument(position = 1, examples = "Combat")
    private String state;

    @Argument(position = 2, examples = "Strength")
    private String stat;

    @Argument(position = 3, examples = "3", minimum = Long.MIN_VALUE)
    private long amount;

    @Argument(position = 4, examples = "7")
    private long cooldown;

    @Argument(position = 5, examples = "Swords Dance")
    private String name;

    @Argument(position = 6, examples = "Raises attack by 1")
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
