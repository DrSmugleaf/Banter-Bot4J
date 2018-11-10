package com.github.drsmugleaf.commands.deadbydaylight;

import com.github.drsmugleaf.BanterBot4J;
import com.github.drsmugleaf.commands.api.Arguments;
import com.github.drsmugleaf.commands.api.Command;
import com.github.drsmugleaf.commands.api.CommandReceivedEvent;
import com.github.drsmugleaf.deadbydaylight.KillerPerks;
import com.github.drsmugleaf.deadbydaylight.SurvivorPerks;
import com.github.drsmugleaf.deadbydaylight.dennisreep.*;
import sx.blah.discord.api.internal.json.objects.EmbedObject;

import javax.annotation.Nonnull;
import java.util.List;

/**
 * Created by DrSmugleaf on 07/11/2018
 */
public class DBDBest extends Command {

    private static final int MAX_PERK_AMOUNT = Math.max(KillerPerks.values().length, SurvivorPerks.values().length);

    protected DBDBest(@Nonnull CommandReceivedEvent event, @Nonnull Arguments args) {
        super(event, args);
    }

    @Nonnull
    private static String invalidArgumentsResponse() {
        return "Invalid arguments.\n" +
               "**Formats:**\n" +
               BanterBot4J.BOT_PREFIX + "dbdbest charactername\n" +
               BanterBot4J.BOT_PREFIX + "dbdbest amount\n" +
               "**Examples:**\n" +
               BanterBot4J.BOT_PREFIX + "dbdbest 8\n" +
               BanterBot4J.BOT_PREFIX + "dbdbest michael myers\n" +
               BanterBot4J.BOT_PREFIX + "dbdbest trapper 5\n" +
               BanterBot4J.BOT_PREFIX + "dbdbest claudette";
    }

    @Nonnull
    private static Perks<KillerPerks, KillerPerk> getBestKillerPerks(int amount) {
        return PerksAPI.KILLER_PERKS.get().getBest(amount);
    }

    @Nonnull
    private static Perks<SurvivorPerks, SurvivorPerk> getBestSurvivorPerks(int amount) {
        return PerksAPI.SURVIVOR_PERKS.get().getBest(amount);
    }

    @Nonnull
    private static List<EmbedObject> getBestPerksResponse(int amount) {
        LargeEmbedBuilder builder = new LargeEmbedBuilder();
        builder
                .withTitle("Best Dead by Daylight Perks")
                .withUrl(API.HOME_URL)
                .withDescription(amount + " Perks")
                .withThumbnail(API.LOGO_URL)
                .withFooterText(getDate());

        if (amount == 1) {
            builder
                    .withTitle("Best Dead by Daylight Perk")
                    .withDescription(amount + " Perk");
        }

        Perks<KillerPerks, KillerPerk> killerPerks = getBestKillerPerks(amount);
        String killerRating = String.format("%.2f", killerPerks.getAverageRating());

        builder.appendField("KILLER PERKS", "Average Rating: " + killerRating + " ★", false);

        for (KillerPerk perk : killerPerks.values()) {
            builder.appendField(
                    perk.NAME + " (" + perk.TIER.NAME + ")",
                    perk.RATING + " ★ (" + perk.RATINGS + ")",
                    true
            );
        }

        Perks<SurvivorPerks, SurvivorPerk> survivorPerks = getBestSurvivorPerks(amount);
        String survivorRating = String.format("%.2f", killerPerks.getAverageRating());

        builder
                .appendField("\u200b", "\u200b", false)
                .appendField("SURVIVOR PERKS", "Average Rating: " + survivorRating + " ★", false);

        for (SurvivorPerk perk : survivorPerks.values()) {
            builder.appendField(
                    perk.NAME + " (" + perk.TIER.NAME + ")",
                    perk.RATING + " ★ (" + perk.RATINGS + ")",
                    true
            );
        }

        return builder.buildAll();
    }

    @Override
    public void run() {
        if (ARGS.isEmpty()) {
            List<EmbedObject> embeds = getBestPerksResponse(4);
            EVENT.reply(embeds.get(0));
            if (embeds.size() > 1) {
                for (EmbedObject embed : embeds) {
                    sendMessage(embed);
                }
            }
        } else if (ARGS.size() == 1) {
            if (!ARGS.isInteger(0)) {
                EVENT.reply(invalidArgumentsResponse());
                return;
            }

            int amount = ARGS.getInteger(0);
            if (amount > MAX_PERK_AMOUNT) {
                EVENT.reply("Too many perks requested. Maximum amount of perks: " + MAX_PERK_AMOUNT);
                return;
            }

            List<EmbedObject> embeds = getBestPerksResponse(amount);
            EVENT.reply(embeds.get(0));
            if (embeds.size() > 1) {
                for (EmbedObject embed : embeds) {
                    sendMessage(embed);
                }
            }
        }
    }

}
