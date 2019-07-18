package com.github.drsmugleaf.commands.deadbydaylight;

import com.github.drsmugleaf.commands.api.Argument;
import com.github.drsmugleaf.commands.api.Command;
import com.github.drsmugleaf.commands.api.CommandInfo;
import com.github.drsmugleaf.commands.api.converter.ConverterRegistry;
import com.github.drsmugleaf.deadbydaylight.dennisreep.*;
import discord4j.core.spec.EmbedCreateSpec;

import java.io.InputStream;
import java.util.function.Consumer;

/**
 * Created by DrSmugleaf on 07/11/2018
 */
@CommandInfo(
        description = "The best perks for each character in Dead by Daylight"
)
public class DBDBest extends Command {

    @Argument.Maximum("perks")
    private static final int MAX_PERKS = Math.max(KillersAPI.getPerks().size(), SurvivorsAPI.getPerks().size());

    @Argument(position = 1, example = "8")
    private Integer perks = 4;

    @Argument(position = 2, example = "trapper", optional = true)
    private Killer killer;

    private static Consumer<EmbedCreateSpec> getBaseEmbed(int perkAmount) {
        return (spec) -> {
            spec
                    .setUrl(API.HOME_URL)
                    .setFooter(getDate(), null);

            if (perkAmount == 1) {
                spec
                        .setTitle("Best Dead by Daylight Perk")
                        .setDescription(perkAmount + " Perk");
            } else {
                spec
                        .setTitle("Best Dead by Daylight Perks")
                        .setDescription(perkAmount + " Perks");
            }

            spec.setThumbnail("attachment://image.png");
        };
    }

    private static Consumer<EmbedCreateSpec> getBestPerksResponse(int amount) {
        return getBaseEmbed(amount).andThen(embed -> {
            PerkList<KillerPerk> killerPerks = KillersAPI.getPerks().getBest(amount);
            String killerRating = String.format("%.2f", killerPerks.getAverageRating());

            embed.addField("KILLER PERKS", "Average Rating: " + killerRating + " ★", false);

            for (KillerPerk perk : killerPerks) {
                embed.addField(
                        perk.NAME + " (" + perk.TIER.NAME + ")",
                        perk.RATING + " ★ (" + perk.RATINGS + ")",
                        true
                );
            }

            PerkList<SurvivorPerk> survivorPerks = SurvivorsAPI.getPerks().getBest(amount);
            String survivorRating = String.format("%.2f", killerPerks.getAverageRating());

            embed
                    .addField("\u200b", "\u200b", false)
                    .addField("SURVIVOR PERKS", "Average Rating: " + survivorRating + " ★", false);

            for (SurvivorPerk perk : survivorPerks) {
                embed.addField(
                        perk.NAME + " (" + perk.TIER.NAME + ")",
                        perk.RATING + " ★ (" + perk.RATINGS + ")",
                        true
                );
            }
        });
    }

    private static Consumer<EmbedCreateSpec> getBestKillerPerksResponse(int amount, Killer killer) {
        return getBaseEmbed(amount).andThen(embed -> {
            PerkList<KillerPerk> killerPerks = KillersAPI.getPerks(killer).getBest(amount);
            String killerRating = String.format("%.2f", killerPerks.getAverageRating());

            embed.addField("KILLER PERKS", "Average Rating: " + killerRating + " ★", false);

            for (KillerPerk perk : killerPerks) {
                embed.addField(
                        perk.NAME + " (" + perk.TIER.NAME + ")",
                        perk.RATING + " ★ (" + perk.RATINGS + ")",
                        true
                );
            }
        });
    }

    @Override
    public void run() {
        Consumer<EmbedCreateSpec> embed;
        InputStream image;
        if (killer == null) {
            embed = getBestPerksResponse(perks);
            image = API.getDBDLogo();
        } else {
            embed = getBestKillerPerksResponse(perks, killer);
            image = killer.getImage();
        }

        reply(message -> message
                .setEmbed(embed)
                .addFile("image.png", image)
        ).subscribe();
    }

    @Override
    public void registerConverters(ConverterRegistry converter) {
        converter.registerCommandTo(Killer.class, (s, e) -> KillersAPI.getKiller(s));
    }

}
