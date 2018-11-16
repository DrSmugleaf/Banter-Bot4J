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

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import java.io.InputStream;

/**
 * Created by DrSmugleaf on 09/11/2018
 */
public class DBDRoulette extends Command {

    protected DBDRoulette(@NotNull CommandReceivedEvent event, @NotNull Arguments args) {
        super(event, args);
    }

    @NotNull
    private static String invalidArgumentsResponse() {
        return "Invalid arguments.\n" +
               "**Formats:**\n" +
               BanterBot4J.BOT_PREFIX + "dbdroulette type\n" +
               BanterBot4J.BOT_PREFIX + "dbdroulette type minimumRating maximumRating\n" +
               "**Examples:**\n" +
               BanterBot4J.BOT_PREFIX + "dbdroulette killer\n" +
               BanterBot4J.BOT_PREFIX + "dbdroulette survivor\n" +
               BanterBot4J.BOT_PREFIX + "dbdroulette friends\n" +
               BanterBot4J.BOT_PREFIX + "dbdroulette killer 3 5\n" +
               BanterBot4J.BOT_PREFIX + "dbdroulette survivor 0 3\n" +
               BanterBot4J.BOT_PREFIX + "dbdroulette friends 2,50 4.32";
    }

    @NotNull
    private static EmbedObject getKillerResponse(@Nullable Double minimumRating, @Nullable Double maximumRating) {
        Killers randomKiller = Killers.random();
        Killer killer = KillersAPI.KILLERS.get().get(randomKiller);
        Perks<KillerPerks, KillerPerk> randomPerks = KillersAPI.getKillerData(randomKiller)
                .getWithinRating(minimumRating, maximumRating)
                .getRandom(4);
        String perkRating = String.format("%.2f", randomPerks.getAverageRating());
        Tiers perkTier = randomPerks.getTier();

        EmbedBuilder builder = new EmbedBuilder();
        builder
                .withTitle(randomKiller.FULL_NAME + ": " + randomKiller.NAME + " (" + killer.RATING + " ★)")
                .withThumbnail(killer.IMAGE_URL)
                .withFooterText(getDate())
                .withColor(perkTier.COLOR.brighter())
                .withUrl(API.HOME_URL);

        if (randomPerks.size() < 4) {
            builder.appendField("Not enough perks in this rating range", "Perks found: " + randomPerks.size(), true);
            return builder.build();
        }

        builder.withDescription("Average perk rating: " + perkRating + " ★");

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

    private void sendSurvivorResponse(@Nullable Double minimumRating, @Nullable Double maximumRating) {
        Survivors survivor = Survivors.random();
        Perks<SurvivorPerks, SurvivorPerk> randomPerks = PerksAPI.SURVIVOR_PERKS.get()
                .getWithinRating(minimumRating, maximumRating)
                .getRandom(4);
        String perkRating = String.format("%.2f", randomPerks.getAverageRating());
        Tiers perkTier = randomPerks.getTier();

        EmbedBuilder builder = new EmbedBuilder();
        builder
                .withTitle(survivor.NAME)
                .withThumbnail("attachment://image.png")
                .withFooterText(getDate())
                .withColor(perkTier.COLOR.brighter())
                .withUrl(API.HOME_URL);

        EmbedObject embed;
        InputStream image = survivor.getImage();
        if (randomPerks.size() < 4) {
            builder.appendField("Not enough perks in this rating range", "Perks found: " + randomPerks.size(), true);
            embed = builder.build();
            EVENT.getChannel().sendFile(embed, image, "image.png");
            return;
        }

        builder.withDescription("Average perk rating: " + perkRating + " ★");

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

        embed = builder.build();
        EVENT.getChannel().sendFile(embed, image, "image.png");
    }

    @Override
    public void run() {
        if (
                ARGS.isEmpty() ||
                (ARGS.size() == 2 && !ARGS.isDouble(1)) ||
                (ARGS.size() == 3 && !ARGS.isDouble(1, 2))
            ) {
            EVENT.reply(invalidArgumentsResponse());
            return;
        }

        String type = ARGS.first().toLowerCase();
        Double minimumRating = null;
        Double maximumRating = null;
        if (ARGS.size() == 2) {
            minimumRating = ARGS.getDouble(1);
        } else if (ARGS.size() == 3) {
            minimumRating = ARGS.getDouble(1);
            maximumRating = ARGS.getDouble(2);
        } else if (ARGS.size() > 3) {
            EVENT.reply(invalidArgumentsResponse());
            return;
        }

        switch (type) {
            case "killer":
                EVENT.reply(getKillerResponse(minimumRating, maximumRating));
                break;
            case "survivor":
                sendSurvivorResponse(minimumRating, maximumRating);
                break;
            case "friends":
                EVENT.reply(getKillerResponse(minimumRating, maximumRating));
                for (int i = 0; i < 4; i++) {
                    sendSurvivorResponse(minimumRating, maximumRating);
                }
                break;
            default:
                EVENT.reply(invalidArgumentsResponse());
                break;
        }
    }

}
