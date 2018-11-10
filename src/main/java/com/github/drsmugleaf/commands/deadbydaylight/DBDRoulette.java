package com.github.drsmugleaf.commands.deadbydaylight;

import com.github.drsmugleaf.BanterBot4J;
import com.github.drsmugleaf.commands.api.Arguments;
import com.github.drsmugleaf.commands.api.Command;
import com.github.drsmugleaf.commands.api.CommandReceivedEvent;
import com.github.drsmugleaf.deadbydaylight.KillerPerks;
import com.github.drsmugleaf.deadbydaylight.Killers;
import com.github.drsmugleaf.deadbydaylight.SurvivorPerks;
import com.github.drsmugleaf.deadbydaylight.Survivors;
import com.github.drsmugleaf.deadbydaylight.dennisreep.*;
import sx.blah.discord.api.internal.json.objects.EmbedObject;
import sx.blah.discord.util.EmbedBuilder;

import javax.annotation.Nonnull;
import java.io.InputStream;

/**
 * Created by DrSmugleaf on 09/11/2018
 */
public class DBDRoulette extends Command {

    protected DBDRoulette(@Nonnull CommandReceivedEvent event, @Nonnull Arguments args) {
        super(event, args);
    }

    @Nonnull
    private static String invalidArgumentsResponse() {
        return "Invalid arguments.\n" +
               "**Formats:**\n" +
               BanterBot4J.BOT_PREFIX + "dbdroulette type\n" +
               "**Examples:**\n" +
               BanterBot4J.BOT_PREFIX + "dbdroulette killer\n" +
               BanterBot4J.BOT_PREFIX + "dbdroulette survivor\n" +
               BanterBot4J.BOT_PREFIX + "dbdroulette friends";
    }

    @Nonnull
    private static EmbedObject getKillerResponse() {
        Killers randomKiller = Killers.random();
        Killer killer = KillersAPI.KILLERS.get().get(randomKiller);
        Perks<KillerPerks, KillerPerk> randomPerks = KillersAPI.getKillerData(randomKiller).getRandom(4);
        String perkRating = String.format("%.2f", randomPerks.getAverageRating());
        Tiers perkTier = randomPerks.getTier();

        EmbedBuilder builder = new EmbedBuilder();
        builder
                .withTitle(randomKiller.FULL_NAME + ": " + randomKiller.NAME + " (" + killer.RATING + " ★)")
                .withDescription("Average perk rating: " + perkRating + " ★")
                .withThumbnail(killer.IMAGE_URL)
                .withFooterText(getDate())
                .withColor(perkTier.COLOR.brighter());

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

    private void sendSurvivorResponse() {
        Survivors survivor = Survivors.random();
        Perks<SurvivorPerks, SurvivorPerk> randomPerks = PerksAPI.SURVIVOR_PERKS.get().getRandom(4);
        String perkRating = String.format("%.2f", randomPerks.getAverageRating());
        Tiers perkTier = randomPerks.getTier();

        EmbedBuilder builder = new EmbedBuilder();
        builder
                .withTitle(survivor.NAME)
                .withDescription("Average perk rating: " + perkRating + " ★")
                .withThumbnail("attachment://image.png")
                .withFooterText(getDate())
                .withColor(perkTier.COLOR.brighter())
                .withThumbnail("attachment://image.png");

        int i = 1;
        for (SurvivorPerk perk : randomPerks.values()) {
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

        EmbedObject embed = builder.build();
        InputStream image = survivor.getImage();
        EVENT.getChannel().sendFile(embed, image, "image.png");
    }

    @Override
    public void run() {
        if (ARGS.isEmpty()) {
            EVENT.reply(invalidArgumentsResponse());
            return;
        }

        String type = ARGS.first().toLowerCase();
        switch (type) {
            case "killer":
                EVENT.reply(getKillerResponse());
                break;
            case "survivor":
                sendSurvivorResponse();
                break;
            case "friends":
                EVENT.reply(getKillerResponse());
                for (int i = 0; i < 4; i++) {
                    sendSurvivorResponse();
                }
                break;
            default:
                EVENT.reply(invalidArgumentsResponse());
                break;
        }
    }

}
