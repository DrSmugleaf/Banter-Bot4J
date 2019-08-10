package com.github.drsmugleaf.commands.roll;

import com.github.drsmugleaf.commands.api.Argument;
import com.github.drsmugleaf.commands.api.Command;
import com.github.drsmugleaf.commands.api.CommandInfo;
import com.github.drsmugleaf.commands.api.converter.ConverterRegistry;

import java.text.DecimalFormat;
import java.util.concurrent.ThreadLocalRandom;

/**
 * Created by DrSmugleaf on 17/07/2019
 */
@CommandInfo(
        description = "Roll any amount of dice with any amount of sides"
)
public class Roll extends Command {

    private static final DecimalFormat FORMAT = new DecimalFormat("#,###");

    @Argument(position = 1, examples = "1d6")
    private RollString dice;

    @Override
    public void run() {
        long sides = dice.getSides();
        if (sides <= 0) {
            reply("Sides must be positive. Sides: " + sides).subscribe();
            return;
        }

        long amount = dice.getAmount();
        if (amount > 1000000) {
            reply("The amount of dice must be below 1000000. Amount: " + amount).subscribe();
            return;
        }

        if (sides > 1000000000) {
            reply("The amount of sides must be below 1000000000. Sides: " + sides).subscribe();
            return;
        }

        long roll = 0;
        for (int i = 0; i < amount; i++) {
            roll += ThreadLocalRandom.current().nextLong(sides) + 1;
        }

        reply("Roll: " + FORMAT.format(roll)).subscribe();
    }

    @Override
    public void registerConverters(ConverterRegistry converter) {
        converter.registerCommandTo(RollString.class, (s, e) -> new RollString(s));
    }
}
