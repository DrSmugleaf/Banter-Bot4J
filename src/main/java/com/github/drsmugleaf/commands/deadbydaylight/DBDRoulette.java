package com.github.drsmugleaf.commands.deadbydaylight;

import com.github.drsmugleaf.commands.api.Argument;
import com.github.drsmugleaf.commands.api.Command;
import com.github.drsmugleaf.commands.api.converter.TypeConverters;
import com.github.drsmugleaf.deadbydaylight.ICharacter;
import com.github.drsmugleaf.deadbydaylight.dennisreep.*;
import sx.blah.discord.api.internal.json.objects.EmbedObject;
import sx.blah.discord.util.EmbedBuilder;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.io.InputStream;

/**
 * Created by DrSmugleaf on 09/11/2018
 */
public class DBDRoulette extends Command {

    @Argument(position = 1, example = "(killer/survivor/friends)")
    private RouletteTypes type;

    @Argument(position = 2, example = "2.50", minimum = 0, optional = true)
    private double minimumRating = 0;

    @Argument(position = 3, example = "4,32", maximum = 5, optional = true)
    private double maximumRating = 5;

    private void sendResponse(
            @Nonnull ICharacter character,
            @Nonnull PerkList<? extends Perk> perks,
            @Nullable Double minimumRating,
            @Nullable Double maximumRating
    ) {
        PerkList<? extends Perk> randomPerks = perks
                .getWithinRating(minimumRating, maximumRating)
                .getRandom(4);
        String perkRating = String.format("%.2f", randomPerks.getAverageRating());
        Tiers perkTier = randomPerks.getTier();

        EmbedBuilder builder = new EmbedBuilder();
        String title = character.getName();
        if (character.getRating() != null) {
            title += " (" + character.getRating() + " ★)";
        }

        builder
                .withTitle(title)
                .withThumbnail("attachment://image.png")
                .withFooterText(getDate())
                .withColor(perkTier.COLOR.brighter())
                .withUrl(API.HOME_URL);

        EmbedObject embed;
        InputStream image = character.getImage();
        if (randomPerks.size() < 4) {
            builder.appendField("Not enough perks in this rating range", "Perks found: " + randomPerks.size(), true);
            embed = builder.build();
            sendFile(embed, image, "image.png");
            return;
        }

        builder.withDescription("Average perk rating: " + perkRating + " ★");

        for (int i = 0; i < randomPerks.size(); i++) {
            if (i % 2 == 0) {
                builder.appendField("\u200b", "\u200b", false);
            }

            Perk perk = randomPerks.get(i);
            builder.appendField(
                    perk.NAME + " (" + perk.TIER.NAME + ")",
                    perk.RATING + " ★ (" + perk.RATINGS + ")",
                    true
            );
        }

        embed = builder.build();
        sendFile(embed, image, "image.png");
    }

    @Override
    public void run() {
        if (minimumRating > maximumRating) {
            double temp = minimumRating;
            minimumRating = maximumRating;
            maximumRating = temp;
        }

        switch (type) {
            case KILLER: {
                Killer killer = KillersAPI.randomKiller();
                PerkList<KillerPerk> perks = KillersAPI.getPerks(killer);
                sendResponse(killer, perks, minimumRating, maximumRating);
                break;
            }
            case SURVIVOR: {
                Survivor survivor = SurvivorsAPI.randomSurvivor();
                PerkList<SurvivorPerk> perks = SurvivorsAPI.getPerks();
                sendResponse(survivor, perks, minimumRating, maximumRating);
                break;
            }
            case FRIENDS: {
                Killer killer = KillersAPI.randomKiller();
                PerkList<KillerPerk> killerPerks = KillersAPI.getPerks(killer);
                sendResponse(killer, killerPerks, minimumRating, maximumRating);

                for (int i = 0; i < 4; i++) {
                    Survivor survivor = SurvivorsAPI.randomSurvivor();
                    PerkList<SurvivorPerk> survivorPerks = SurvivorsAPI.getPerks();
                    sendResponse(survivor, survivorPerks, minimumRating, maximumRating);
                }

                break;
            }
            default:
                throw new IllegalStateException("Missing switch case for roulette type " + type);
        }
    }

    @Override
    public void registerConverters(@Nonnull TypeConverters converter) {
        converter.registerStringTo(RouletteTypes.class, RouletteTypes::from);
    }

}
