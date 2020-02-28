package com.github.drsmugleaf.commands.deadbydaylight;

import com.github.drsmugleaf.Nullable;
import com.github.drsmugleaf.commands.api.Argument;
import com.github.drsmugleaf.commands.api.Command;
import com.github.drsmugleaf.commands.api.CommandInfo;
import com.github.drsmugleaf.commands.api.converter.TransformerSet;
import com.github.drsmugleaf.deadbydaylight.dennisreep.*;

import java.io.InputStream;

/**
 * Created by DrSmugleaf on 09/11/2018
 */
@CommandInfo(
        description = "Random perks for Dead by Daylight"
)
public class DBDRoulette extends Command {

    @Argument(position = 1, examples = {"killer", "survivor", "friends"})
    private RouletteTypes type;

    @Argument(position = 2, examples = "2.50", minimum = 0, optional = true)
    private double minimumRating = 0;

    @Argument(position = 3, examples = "4,32", maximum = 5, optional = true)
    private double maximumRating = 5;

    private void sendResponse(
            ICharacter character,
            PerkList<? extends Perk> perks,
            @Nullable Double minimumRating,
            @Nullable Double maximumRating
    ) {
        PerkList<? extends Perk> randomPerks = perks
                .getWithinRating(minimumRating, maximumRating)
                .getRandom(4);
        String perkRating = String.format("%.2f", randomPerks.getAverageRating());
        Tiers perkTier = randomPerks.getTier();

        EVENT
                .getMessage()
                .getChannel()
                .flatMap(channel -> channel.createMessage(message -> {
                    message.setEmbed(embed -> {
                        String title = character.getName();
                        if (character.getRating() != null) {
                            title += " (" + character.getRating() + " ★)";
                        }

                        embed
                                .setTitle(title)
                                .setThumbnail("attachment://image.png")
                                .setFooter(getDate(), null)
                                .setColor(perkTier.COLOR.brighter())
                                .setUrl(API.HOME_URL);


                        if (randomPerks.size() < 4) {
                            embed.addField("Not enough perks in this rating range", "Perks found: " + randomPerks.size(), true);
                            return;
                        }

                        embed.setDescription("Average perk rating: " + perkRating + " ★");

                        for (int i = 0; i < randomPerks.size(); i++) {
                            if (i % 2 == 0) {
                                embed.addField("\u200b", "\u200b", false);
                            }

                            Perk perk = randomPerks.get(i);
                            embed.addField(
                                    perk.NAME + " (" + perk.TIER.NAME + ")",
                                    perk.RATING + " ★ (" + perk.RATINGS + ")",
                                    true
                            );
                        }
                    });

                    InputStream image = character.getImage();
                    message.addFile("image.png", image);
                })).subscribe();
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
    public TransformerSet getTransformers() {
        return TransformerSet.of(
                RouletteTypes.class, (s, e) -> RouletteTypes.from(s)
        );
    }

}
