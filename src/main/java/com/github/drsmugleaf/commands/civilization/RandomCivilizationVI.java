package com.github.drsmugleaf.commands.civilization;

import com.github.drsmugleaf.civilization.vi.Leaders;
import com.github.drsmugleaf.commands.api.Argument;
import com.github.drsmugleaf.commands.api.Command;
import com.github.drsmugleaf.commands.api.CommandInfo;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by DrSmugleaf on 31/08/2018
 */
@CommandInfo(
        name = "rciv6",
        aliases = {
                "random civilization vi", "random civilizationvi", "r civilization vi",
                "r civilizationvi", "randomcivvi", "random civ vi", "random civvi",
                "randomcivilization6", "rcivilization6", "random civilization 6",
                "random civilization6", "r civilization 6", "r civilization6",
                "randomciv6", "random civ 6", "random civ6", "r civ 6", "r civ6"
        },
        description = "Get a set of random Civilization VI leaders"
)
public class RandomCivilizationVI extends Command {

    @Argument.Maximum("leaders")
    private static final int MAXIMUM_LEADERS = Leaders.getAmount();

    @Argument(position = 1, examples = "3")
    private Integer leaders;

    @Override
    public void run() {
        List<Leaders> leaders = Leaders.random(this.leaders);
        List<String> leadersNamesList = leaders.stream().map(Leaders::getName).collect(Collectors.toList());
        String leadersNames = String.join(", ", leadersNamesList);

        reply(leadersNames).subscribe();
    }

}
