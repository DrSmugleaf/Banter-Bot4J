package com.github.drsmugleaf.commands.deadbydaylight;

import com.github.drsmugleaf.commands.api.Arguments;
import com.github.drsmugleaf.commands.api.Command;
import com.github.drsmugleaf.commands.api.CommandReceivedEvent;
import com.github.drsmugleaf.deadbydaylight.KillerPerks;
import com.github.drsmugleaf.deadbydaylight.Killers;
import com.github.drsmugleaf.deadbydaylight.dennisreep.Killer;
import com.github.drsmugleaf.deadbydaylight.dennisreep.KillerPerk;
import com.github.drsmugleaf.deadbydaylight.dennisreep.KillersAPI;
import com.github.drsmugleaf.deadbydaylight.dennisreep.Perks;
import sx.blah.discord.api.internal.json.objects.EmbedObject;
import sx.blah.discord.util.EmbedBuilder;

import javax.annotation.Nonnull;

/**
 * Created by DrSmugleaf on 09/11/2018
 */
public class DBDRoulette extends Command {

    protected DBDRoulette(@Nonnull CommandReceivedEvent event, @Nonnull Arguments args) {
        super(event, args);
    }

    @Nonnull
    private static EmbedObject getKillerResponse() {
        Killers randomKiller = Killers.random();
        Killer killer = KillersAPI.KILLERS.get().get(randomKiller);
        Perks<KillerPerks, KillerPerk> randomPerks = KillersAPI.getKillerData(randomKiller).getRandom(4);
        String perkRating = String.format("%.2f", randomPerks.getAverageRating());

        EmbedBuilder builder = new EmbedBuilder();
        builder
                .withTitle(randomKiller.FULL_NAME + ": " + randomKiller.NAME + " (" + killer.RATING + " ★)")
                .withDescription("Average perk rating: " + perkRating + " ★")
                .withThumbnail(killer.IMAGE_URL)
                .withFooterText(getDate());

        int i = 1;
        for (KillerPerk perk : randomPerks.values()) {
            if (i == 3) {
                builder.appendField("\u200b", "\u200b", false);
            }

            builder.appendField(
                    perk.NAME + " (" + perk.TIER.NAME + ")",
                    perk.RATING + " ★ (" + perk.RATINGS + ")",
                    true
            );

            i++;
        }

        return builder.build();
    }

    @Override
    public void run() {
        if (ARGS.isEmpty()) {
            EVENT.reply(getKillerResponse());
            return;
        }
    }

}
