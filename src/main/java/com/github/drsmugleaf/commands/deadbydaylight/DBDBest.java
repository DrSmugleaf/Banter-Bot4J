package com.github.drsmugleaf.commands.deadbydaylight;

import com.github.drsmugleaf.commands.api.Argument;
import com.github.drsmugleaf.commands.api.Command;
import com.github.drsmugleaf.commands.api.converter.TypeConverters;
import com.github.drsmugleaf.deadbydaylight.dennisreep.*;
import sx.blah.discord.api.internal.json.objects.EmbedObject;

import javax.annotation.Nonnull;
import java.io.InputStream;
import java.util.List;

/**
 * Created by DrSmugleaf on 07/11/2018
 */
public class DBDBest extends Command {

    @Argument.Maximum("perks")
    private static final int MAX_PERK_AMOUNT = Math.max(KillersAPI.getPerks().size(), SurvivorsAPI.getPerks().size());

    @Argument(position = 1, example = "8")
    private Integer perks = 4;

    @Argument(position = 2, example = "trapper", optional = true)
    private Killer killer;

    @Nonnull
    private static LargeEmbedBuilder getBaseEmbed(int perkAmount) {
        LargeEmbedBuilder builder = new LargeEmbedBuilder();
        builder
                .withUrl(API.HOME_URL)
                .withFooterText(getDate());

        if (perkAmount == 1) {
            builder
                    .withTitle("Best Dead by Daylight Perks")
                    .withDescription(perkAmount + " Perk");
        } else {
            builder
                    .withTitle("Best Dead by Daylight Perks")
                    .withDescription(perkAmount + " Perks");
        }

        builder.withThumbnail("attachment://image.png");

        return builder;
    }

    @Nonnull
    private static List<EmbedObject> getBestPerksResponse(int amount) {
        LargeEmbedBuilder builder = getBaseEmbed(amount);

        PerkList<KillerPerk> killerPerks = KillersAPI.getPerks().getBest(amount);
        String killerRating = String.format("%.2f", killerPerks.getAverageRating());

        builder.appendField("KILLER PERKS", "Average Rating: " + killerRating + " ★", false);

        for (KillerPerk perk : killerPerks) {
            builder.appendField(
                    perk.NAME + " (" + perk.TIER.NAME + ")",
                    perk.RATING + " ★ (" + perk.RATINGS + ")",
                    true
            );
        }

        PerkList<SurvivorPerk> survivorPerks = SurvivorsAPI.getPerks().getBest(amount);
        String survivorRating = String.format("%.2f", killerPerks.getAverageRating());

        builder
                .appendField("\u200b", "\u200b", false)
                .appendField("SURVIVOR PERKS", "Average Rating: " + survivorRating + " ★", false);

        for (SurvivorPerk perk : survivorPerks) {
            builder.appendField(
                    perk.NAME + " (" + perk.TIER.NAME + ")",
                    perk.RATING + " ★ (" + perk.RATINGS + ")",
                    true
            );
        }

        return builder.buildAll();
    }

    @Nonnull
    private static List<EmbedObject> getBestKillerPerksResponse(int amount, @Nonnull Killer killer) {
        LargeEmbedBuilder builder = getBaseEmbed(amount);

        PerkList<KillerPerk> killerPerks = KillersAPI.getPerks(killer).getBest(amount);
        String killerRating = String.format("%.2f", killerPerks.getAverageRating());

        builder.appendField("KILLER PERKS", "Average Rating: " + killerRating + " ★", false);

        for (KillerPerk perk : killerPerks) {
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
        List<EmbedObject> embeds;
        InputStream image;
        if (killer == null) {
            embeds = getBestPerksResponse(perks);
            image = API.getDBDLogo();
        } else {
            embeds = getBestKillerPerksResponse(perks, killer);
            image = killer.getImage();
        }

        sendFile(embeds.get(0), image, "image.png");
        if (embeds.size() > 1) {
            for (EmbedObject embed : embeds) {
                sendMessage(embed);
            }
        }
    }

    @Override
    public void registerConverters(@Nonnull TypeConverters converter) {
        converter.registerStringTo(Killer.class, KillersAPI::getKiller);
    }

}
