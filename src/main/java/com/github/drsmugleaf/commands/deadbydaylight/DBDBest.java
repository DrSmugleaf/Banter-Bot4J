package com.github.drsmugleaf.commands.deadbydaylight;

import com.github.drsmugleaf.BanterBot4J;
import com.github.drsmugleaf.commands.api.Arguments;
import com.github.drsmugleaf.commands.api.Command;
import com.github.drsmugleaf.commands.api.CommandReceivedEvent;
import com.github.drsmugleaf.deadbydaylight.KillerPerks;
import com.github.drsmugleaf.deadbydaylight.Killers;
import com.github.drsmugleaf.deadbydaylight.SurvivorPerks;
import com.github.drsmugleaf.deadbydaylight.dennisreep.*;
import sx.blah.discord.api.internal.json.objects.EmbedObject;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import java.io.InputStream;
import java.util.List;

/**
 * Created by DrSmugleaf on 07/11/2018
 */
public class DBDBest extends Command {

    private static final int MAX_PERK_AMOUNT = Math.max(KillerPerks.values().length, SurvivorPerks.values().length);

    protected DBDBest(@NotNull CommandReceivedEvent event, @NotNull Arguments args) {
        super(event, args);
    }

    @NotNull
    private static String invalidArgumentsResponse() {
        return "**Formats:**\n" +
               BanterBot4J.BOT_PREFIX + "dbdbest amount\n" +
               BanterBot4J.BOT_PREFIX + "dbdbest killer\n" +
               BanterBot4J.BOT_PREFIX + "dbdbest killer amount\n" +
               "**Examples:**\n" +
               BanterBot4J.BOT_PREFIX + "dbdbest 8\n" +
               BanterBot4J.BOT_PREFIX + "dbdbest michael myers\n" +
               BanterBot4J.BOT_PREFIX + "dbdbest trapper 5\n\n" +
               "Survivors don't have a survivor specific perk ranking.";
    }

    @NotNull
    private static LargeEmbedBuilder getBaseEmbed(int perkAmount, @Nullable Killers killer) {
        LargeEmbedBuilder builder = new LargeEmbedBuilder();
        builder
                .withUrl(API.HOME_URL)
                .withFooterText(getDate());

        if (perkAmount == 1) {
            builder
                    .withTitle("Best Dead by Daylight Perk")
                    .withDescription(perkAmount + " Perk");
        } else {
            builder
                    .withTitle("Best Dead by Daylight Perks")
                    .withDescription(perkAmount + " Perks");
        }

        builder.withThumbnail("attachment://image.png");

        return builder;
    }

    @NotNull
    private static Perks<KillerPerks, KillerPerk> getBestKillerPerks(int amount, @Nullable Killers killer) {
        if (killer == null) {
            return PerksAPI.KILLER_PERKS.get().getBest(amount);
        }

        Perks<KillerPerks, KillerPerk> killerData = KillersAPI.getKillerData(killer);
        return killerData.getBest(amount);
    }

    @NotNull
    private static Perks<SurvivorPerks, SurvivorPerk> getBestSurvivorPerks(int amount) {
        return PerksAPI.SURVIVOR_PERKS.get().getBest(amount);
    }

    @NotNull
    private static List<EmbedObject> getBestPerksResponse(int amount) {
        LargeEmbedBuilder builder = getBaseEmbed(amount, null);

        Perks<KillerPerks, KillerPerk> killerPerks = getBestKillerPerks(amount, null);
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

    @NotNull
    private static List<EmbedObject> getBestKillerPerksResponse(int amount, @NotNull Killers killer) {
        LargeEmbedBuilder builder = getBaseEmbed(amount, killer);

        Perks<KillerPerks, KillerPerk> killerPerks = getBestKillerPerks(amount, killer);
        String killerRating = String.format("%.2f", killerPerks.getAverageRating());

        builder.appendField("KILLER PERKS", "Average Rating: " + killerRating + " ★", false);

        for (KillerPerk perk : killerPerks.values()) {
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
            InputStream image = API.getDBDLogo();
            EVENT.getChannel().sendFile(embeds.get(0), image, "image.png");
            if (embeds.size() > 1) {
                for (EmbedObject embed : embeds) {
                    sendMessage(embed);
                }
            }
        } else if (ARGS.size() == 1) {
            String characterName = null;
            int amount = 4;
            if (ARGS.isInteger(0)) {
                amount = ARGS.getInteger(0);
            } else {
                characterName = ARGS.get(0);
            }

            if (amount > MAX_PERK_AMOUNT) {
                EVENT.reply("Too many perks requested. Maximum amount of perks: " + MAX_PERK_AMOUNT);
                return;
            }

            Killers killer = Killers.from(characterName);
            if (characterName != null && killer == null) {
                EVENT.reply("No killer found with name " + characterName + ".\n" + invalidArgumentsResponse());
                return;
            } else if (killer != null) {
                List<EmbedObject> embeds = getBestKillerPerksResponse(amount, killer);
                InputStream image = killer.getImage();
                EVENT.getChannel().sendFile(embeds.get(0), image, "image.png");
                if (embeds.size() > 1) {
                    for (EmbedObject embed : embeds) {
                        sendMessage(embed);
                    }
                }
                return;
            }

            List<EmbedObject> embeds = getBestPerksResponse(amount);
            InputStream image = API.getDBDLogo();
            EVENT.getChannel().sendFile(embeds.get(0), image, "image.png");
            if (embeds.size() > 1) {
                for (EmbedObject embed : embeds) {
                    sendMessage(embed);
                }
            }
        } else {
            String characterName;
            int amount;
            if (ARGS.isInteger(0)) {
                characterName = ARGS.getFrom(1);
                amount = ARGS.getInteger(0);
            } else if (ARGS.isInteger(1)) {
                characterName = ARGS.get(0);
                amount = ARGS.getInteger(1);
            } else {
                Integer firstIntegerIndex = ARGS.findFirstIntegerIndex();
                if (firstIntegerIndex == null) {
                    characterName = ARGS.toString();
                    amount = 4;
                } else {
                    characterName = ARGS.getFrom(0, firstIntegerIndex);
                    amount = ARGS.getInteger(firstIntegerIndex);
                }
            }

            if (amount > MAX_PERK_AMOUNT) {
                EVENT.reply("Too many perks requested. Maximum amount of perks: " + MAX_PERK_AMOUNT);
                return;
            }

            Killers killer = Killers.from(characterName);
            if (killer == null) {
                EVENT.reply("No killer found with name " + characterName + ".\n" + invalidArgumentsResponse());
                return;
            }

            List<EmbedObject> embeds = getBestKillerPerksResponse(amount, killer);
            InputStream image = killer.getImage();
            EVENT.getChannel().sendFile(embeds.get(0), image, "image.png");
            if (embeds.size() > 1) {
                for (EmbedObject embed : embeds) {
                    sendMessage(embed);
                }
            }
        }
    }

}
