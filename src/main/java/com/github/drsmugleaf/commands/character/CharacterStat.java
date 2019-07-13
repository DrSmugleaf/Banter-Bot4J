package com.github.drsmugleaf.commands.character;

import com.github.drsmugleaf.charactersheets.character.CharacterBuilder;
import com.github.drsmugleaf.charactersheets.character.sheet.SheetBuilder;
import com.github.drsmugleaf.charactersheets.stat.Stat;
import com.github.drsmugleaf.charactersheets.stat.StatGroup;
import com.github.drsmugleaf.charactersheets.stat.StatsBuilder;
import com.github.drsmugleaf.charactersheets.state.State;
import com.github.drsmugleaf.commands.api.Argument;
import com.github.drsmugleaf.commands.api.Command;
import com.github.drsmugleaf.commands.api.CommandInfo;

import java.util.Map;

/**
 * Created by DrSmugleaf on 12/07/2019
 */
@CommandInfo(aliases = {"cs"})
public class CharacterStat extends Command {

    @Argument(position = 1, example = "Combat")
    private String stateName;

    @Argument(position = 2, example = "Strength")
    private String name;

    @Argument(position = 3, example = "5", minimum = Long.MIN_VALUE)
    private long value;

    @Override
    public void run() {
        EVENT
                .getMessage()
                .getAuthor()
                .ifPresent(author -> {
                    CharacterBuilder character = CharacterCreate.getCharacters().get(author);
                    SheetBuilder sheet = new SheetBuilder(character.getSheet());
                    State state = new State(stateName);
                    StatsBuilder stats = new StatsBuilder();
                    Map<State, StatGroup> originalStats = sheet.getStats();
                    if (originalStats.containsKey(state)) {
                        StatGroup statSheet = originalStats.get(state);
                        sheet.addStats(state, statSheet);
                    }

                    Stat stat = new Stat(name, value);
                    stats
                            .setName(stateName)
                            .addStat(stat);
                    sheet.addStats(state, stats.build());
                    character.setSheet(sheet.build());
                });
    }

}
