package com.github.drsmugleaf.commands.character;

import com.github.drsmugleaf.charactersheets.character.CharacterBuilder;
import com.github.drsmugleaf.charactersheets.character.sheet.SheetBuilder;
import com.github.drsmugleaf.charactersheets.stat.Stat;
import com.github.drsmugleaf.charactersheets.state.State;
import com.github.drsmugleaf.commands.api.arguments.Argument;
import com.github.drsmugleaf.commands.api.Command;
import com.github.drsmugleaf.commands.api.CommandInfo;

/**
 * Created by DrSmugleaf on 12/07/2019
 */
@CommandInfo(
        aliases = {"cs"},
        description = "Create a stat for your character"
)
public class CharacterStat extends Command {

    @Argument(position = 1, examples = "Combat")
    private String stateName;

    @Argument(position = 2, examples = "Strength")
    private String name;

    @Argument(position = 3, examples = "5", minimum = Long.MIN_VALUE)
    private long value;

    @Override
    public void run() {
        EVENT
                .getMessage()
                .getAuthor()
                .ifPresent(author -> {
                    CharacterBuilder character = CharacterCreate.getCharacters().get(author);
                    SheetBuilder sheet = character.getSheet();
                    State state = new State(stateName);
                    Stat stat = new Stat(name, value);
                    sheet.getStats(state).addStat(stat);
                });
    }

}
