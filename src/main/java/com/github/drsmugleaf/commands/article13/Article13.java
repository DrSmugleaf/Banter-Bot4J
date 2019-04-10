package com.github.drsmugleaf.commands.article13;

import com.github.drsmugleaf.article13.entities.Country;
import com.github.drsmugleaf.commands.api.Arguments;
import com.github.drsmugleaf.commands.api.Command;
import com.github.drsmugleaf.commands.api.CommandReceivedEvent;

import javax.annotation.Nonnull;

/**
 * Created by DrSmugleaf on 07/04/2019
 */
public class Article13 extends Command {

    protected Article13(@Nonnull CommandReceivedEvent event, @Nonnull Arguments args) {
        super(event, args);
    }

    @Override
    public void run() {
        if (ARGS.size() < 1) {
            EVENT.reply(
                    "Missing a country to get votes from. Valid countries:\n" +
                            String.join(", ", Country.getCountryNames())
            );
            return;
        }

        String countryName = ARGS.get(0);
        Country country = Country.getCountry(countryName);
        EVENT.reply("\n" + String.join("\n", country.getPartyNames()));
    }

}
