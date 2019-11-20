package com.github.drsmugleaf.commands.character;

import com.github.drsmugleaf.charactersheets.ability.AbilitySet;
import com.github.drsmugleaf.charactersheets.character.Character;
import com.github.drsmugleaf.charactersheets.character.CharacterBuilder;
import com.github.drsmugleaf.charactersheets.character.sheet.Sheet;
import com.github.drsmugleaf.charactersheets.stat.StatGroup;
import com.github.drsmugleaf.commands.api.Command;
import com.github.drsmugleaf.commands.api.CommandInfo;
import discord4j.core.object.entity.User;
import reactor.core.publisher.Mono;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by DrSmugleaf on 12/07/2019
 */
@CommandInfo(
        aliases = {"cf"},
        description = "Finish a character"
)
public class CharacterFinish extends Command {

    @Override
    public void run() {
        EVENT
                .getMessage()
                .getChannel()
                .zipWith(Mono.justOrEmpty(EVENT.getMessage().getAuthor()))
                .flatMap(tuple -> tuple.getT1().createMessage(message -> message.setEmbed(embed -> {
                    User author = tuple.getT2();
                    CharacterBuilder builder = CharacterCreate.getCharacters().get(author);
                    Character character = builder.build();
                    Sheet sheet = character.getSheet();

                    embed.setTitle(character.getName());

                    for (StatGroup statGroup : sheet.getStats().values()) {
                        List<String> statsDescription = statGroup
                                .get()
                                .values()
                                .stream()
                                .map(stat -> "**" + stat.getName() + ":** " + stat.getValue())
                                .collect(Collectors.toList());

                        embed.addField(
                                "**Stats: " + statGroup.getName().toUpperCase() + "**",
                                String.join("\n", statsDescription),
                                false
                        );
                    }

                    for (AbilitySet abilitySet : sheet.getAbilities().values()) {
                        List<String> abilitiesDescription = abilitySet
                                .get()
                                .values()
                                .stream()
                                .map(ability -> "**" + ability.getName() + ":** " + ability.getDescription() + " [" + ability.getCooldown() + " turn cooldown]")
                                .collect(Collectors.toList());

                        embed.addField(
                                "**Abilities: " + abilitySet.getName().toUpperCase() + "**",
                                String.join("\n", abilitiesDescription),
                                false
                        );
                    }
                }))).subscribe();
    }

}
